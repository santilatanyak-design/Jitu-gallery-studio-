package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.OrderEntity
import com.example.data.StudioRepository
import com.example.model.ChatMessage
import com.example.model.DesignSample
import com.example.model.MessageSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class StudioTab {
    AI_CHAT,
    ORDER_FORM,
    GALLERY,
    MY_ORDERS,
    ADMIN_PANEL
}

class StudioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudioRepository
    val orders: StateFlow<List<OrderEntity>>

    private val _currentTab = MutableStateFlow(StudioTab.AI_CHAT)
    val currentTab: StateFlow<StudioTab> = _currentTab.asStateFlow()

    private val _language = MutableStateFlow("ODIA") // "ODIA" or "ENGLISH"
    val language: StateFlow<String> = _language.asStateFlow()

    // Authenticated User Email (Defaulted to admin email)
    private val _currentUserEmail = MutableStateFlow("nayakjitu986@gmail.com")
    val currentUserEmail: StateFlow<String> = _currentUserEmail.asStateFlow()

    // Admin Login Authentication State
    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    // Chat State
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _chatInput = MutableStateFlow("")
    val chatInput: StateFlow<String> = _chatInput.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Direct Order Form State
    private val _formDesignType = MutableStateFlow("ଫ୍ଲେକ୍ସ ବ୍ୟାନର (Flex Banner)")
    val formDesignType: StateFlow<String> = _formDesignType.asStateFlow()

    private val _formDimensions = MutableStateFlow("16:9 Banner")
    val formDimensions: StateFlow<String> = _formDimensions.asStateFlow()

    private val _formColors = MutableStateFlow("Crimson Red & Royal Blue")
    val formColors: StateFlow<String> = _formColors.asStateFlow()

    private val _formNotes = MutableStateFlow("")
    val formNotes: StateFlow<String> = _formNotes.asStateFlow()

    private val _formPhone = MutableStateFlow("")
    val formPhone: StateFlow<String> = _formPhone.asStateFlow()

    private val _formEmail = MutableStateFlow("nayakjitu986@gmail.com")
    val formEmail: StateFlow<String> = _formEmail.asStateFlow()

    private val _formSubmissionSuccess = MutableStateFlow<String?>(null)
    val formSubmissionSuccess: StateFlow<String?> = _formSubmissionSuccess.asStateFlow()

    // Gallery Category Filter
    private val _selectedGalleryCategory = MutableStateFlow("All")
    val selectedGalleryCategory: StateFlow<String> = _selectedGalleryCategory.asStateFlow()

    val sampleDesigns: List<DesignSample>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = StudioRepository(database.orderDao())
        orders = repository.allOrders.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        sampleDesigns = repository.getSampleDesigns()

        // Setup Initial Welcoming Message from AI Studio Manager
        val initialMessageOdia = "ନମସ୍କାର! ମୁଁ Jitu Gallery Studio ର AI ମ୍ୟାନେଜର। ଆପଣ କେଉଁ ପ୍ରକାରର ଡିଜାଇନ୍ (ପୋଷ୍ଟର, ବ୍ୟାନର, ବାହାଘର କାର୍ଡ ଇତ୍ୟାଦି) ତିଆରି କରିବାକୁ ଚାହୁଁଛନ୍ତି?"
        _chatMessages.value = listOf(
            ChatMessage(
                sender = MessageSender.AI,
                text = initialMessageOdia
            )
        )
    }

    fun selectTab(tab: StudioTab) {
        _currentTab.value = tab
    }

    fun toggleLanguage() {
        val newLang = if (_language.value == "ODIA") "ENGLISH" else "ODIA"
        _language.value = newLang

        // If chat has only initial message, update initial welcome text
        if (_chatMessages.value.size <= 1) {
            val welcomeText = if (newLang == "ENGLISH") {
                "Hello! Welcome to Jitu Gallery Studio's AI Order System. What type of design (Flex Banner, 1:1 Poster, Wedding Card, Photo Frame) would you like to order today?"
            } else {
                "ନମସ୍କାର! ମୁଁ Jitu Gallery Studio ର AI ମ୍ୟାନେଜର। ଆପଣ କେଉଁ ପ୍ରକାରର ଡିଜାଇନ୍ (ପୋଷ୍ଟର, ବ୍ୟାନର, ବାହାଘର କାର୍ଡ ଇତ୍ୟାଦି) ତିଆରି କରିବାକୁ ଚାହୁଁଛନ୍ତି?"
            }
            _chatMessages.value = listOf(ChatMessage(sender = MessageSender.AI, text = welcomeText))
        }
    }

    fun updateChatInput(text: String) {
        _chatInput.value = text
    }

    fun sendChatMessage() {
        val text = _chatInput.value.trim()
        if (text.isEmpty() || _isAiThinking.value) return

        val userMsg = ChatMessage(sender = MessageSender.USER, text = text)
        _chatMessages.value = _chatMessages.value + userMsg
        _chatInput.value = ""
        _isAiThinking.value = true

        viewModelScope.launch {
            val aiReplyText = repository.getGeminiAiResponse(
                chatHistory = _chatMessages.value,
                userMessage = text,
                languagePreference = _language.value
            )

            val aiMsg = ChatMessage(sender = MessageSender.AI, text = aiReplyText)
            _chatMessages.value = _chatMessages.value + aiMsg
            _isAiThinking.value = false

            // Automatically check if user is placing an order to create a database record
            checkAndAutoSaveOrderFromChat(text, aiReplyText)
        }
    }

    private fun checkAndAutoSaveOrderFromChat(userText: String, aiReply: String) {
        val lower = userText.lowercase()
        val orderKeywords = listOf("order", "poster", "banner", "card", "design", "flex", "frame", "photo", "ଅର୍ଡର", "ପୋଷ୍ଟର", "ବ୍ୟାନର", "କାର୍ଡ", "ଫ୍ରେମ୍", "ଲୋଗୋ")

        if (orderKeywords.any { lower.contains(it) }) {
            viewModelScope.launch {
                val orderNum = generateOrderNumber()
                val inferredType = when {
                    lower.contains("banner") || lower.contains("flex") || lower.contains("ବ୍ୟାନର") -> " Flex Banner"
                    lower.contains("poster") || lower.contains("ପୋଷ୍ଟର") -> "1:1 Digital Poster"
                    lower.contains("card") || lower.contains("କାର୍ଡ") -> "Wedding / Visiting Card"
                    lower.contains("frame") || lower.contains("ଫ୍ରେମ୍") -> "Custom Photo Frame"
                    lower.contains("logo") || lower.contains("ଲୋଗୋ") -> "Logo & Branding"
                    else -> "Custom Graphic Design"
                }

                val newOrder = OrderEntity(
                    orderNumber = orderNum,
                    designType = inferredType,
                    sizeDimensions = "1:1 / 16:9 Standard",
                    colorPreferences = "Customer Preferred Accent",
                    customerNotes = userText,
                    customerPhone = if (_formPhone.value.isNotBlank()) _formPhone.value else "Not Provided",
                    customerEmail = "nayakjitu986@gmail.com",
                    status = "ଅର୍ଡର ଗ୍ରହଣ ହୋଇଛି (Submitted)",
                    aiSummary = aiReply
                )
                repository.saveOrder(newOrder)
            }
        }
    }

    // Direct Order Form Field Updaters
    fun setFormDesignType(type: String) { _formDesignType.value = type }
    fun setFormDimensions(dims: String) { _formDimensions.value = dims }
    fun setFormColors(colors: String) { _formColors.value = colors }
    fun setFormNotes(notes: String) { _formNotes.value = notes }
    fun setFormPhone(phone: String) { _formPhone.value = phone }
    fun setFormEmail(email: String) { _formEmail.value = email }

    fun submitDirectOrder() {
        if (_formNotes.value.isBlank()) return

        viewModelScope.launch {
            val orderNum = generateOrderNumber()
            val newOrder = OrderEntity(
                orderNumber = orderNum,
                designType = _formDesignType.value,
                sizeDimensions = _formDimensions.value,
                colorPreferences = _formColors.value,
                customerNotes = _formNotes.value,
                customerPhone = if (_formPhone.value.isNotBlank()) _formPhone.value else "N/A",
                customerEmail = if (_formEmail.value.isNotBlank()) _formEmail.value else "nayakjitu986@gmail.com",
                status = "ଅର୍ଡର ଗ୍ରହଣ ହୋଇଛି (Submitted)",
                aiSummary = "Direct Order placed via Order Form. Admin notified at nayakjitu986@gmail.com"
            )

            repository.saveOrder(newOrder)
            _formNotes.value = ""
            _formSubmissionSuccess.value = "ଅର୍ଡର ସଫଳତାର ସହ ଦାଖଲ ହେଲା! Order Number: $orderNum"
            _currentTab.value = StudioTab.MY_ORDERS
        }
    }

    fun dismissFormSuccessMessage() {
        _formSubmissionSuccess.value = null
    }

    fun startOrderFromSample(sample: DesignSample) {
        _formDesignType.value = sample.titleOdia
        _formDimensions.value = sample.aspectSize
        _formNotes.value = "Order requested from Sample Catalog: ${sample.titleEnglish} (${sample.priceEstimate})"
        _currentTab.value = StudioTab.ORDER_FORM
    }

    fun setGalleryCategory(cat: String) {
        _selectedGalleryCategory.value = cat
    }

    fun setCurrentUserEmail(email: String) {
        _currentUserEmail.value = email
        if (!email.equals("nayakjitu986@gmail.com", ignoreCase = true) && _currentTab.value == StudioTab.ADMIN_PANEL) {
            _currentTab.value = StudioTab.AI_CHAT
        }
    }

    fun loginAdmin(id: String, pass: String): Boolean {
        return if (id.trim() == "578785" && pass.trim() == "543213") {
            _currentUserEmail.value = "nayakjitu986@gmail.com"
            _isAdminLoggedIn.value = true
            true
        } else {
            false
        }
    }

    fun onAdminLoginSuccess() {
        _currentUserEmail.value = "nayakjitu986@gmail.com"
        _isAdminLoggedIn.value = true
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
    }

    fun updateOrderStatus(order: OrderEntity, newStatus: String) {
        viewModelScope.launch {
            repository.updateOrder(order.copy(status = newStatus))
        }
    }

    fun requestPaymentVerification(order: OrderEntity) {
        viewModelScope.launch {
            repository.updateOrder(
                order.copy(
                    status = "Payment Verification Pending",
                    linkStatus = "LOCKED"
                )
            )
        }
    }

    fun setOrderLinkStatus(order: OrderEntity, linkStatus: String) {
        viewModelScope.launch {
            val updatedLink = if (linkStatus == "DELETED") "" else order.driveLink
            val updatedStatus = if (linkStatus == "UNLOCKED") "Completed" else order.status
            repository.updateOrder(
                order.copy(
                    driveLink = updatedLink,
                    linkStatus = linkStatus,
                    status = updatedStatus
                )
            )
        }
    }

    fun sendDesignToCustomer(order: OrderEntity, driveLink: String) {
        viewModelScope.launch {
            repository.updateOrder(
                order.copy(
                    status = "Completed",
                    linkStatus = "UNLOCKED",
                    driveLink = driveLink
                )
            )
        }
    }

    fun deleteOrder(id: Long) {
        viewModelScope.launch {
            repository.deleteOrder(id)
        }
    }

    private fun generateOrderNumber(): String {
        val timeStamp = SimpleDateFormat("HHmm", Locale.getDefault()).format(Date())
        val randomNum = (100..999).random()
        return "#JGS-$timeStamp$randomNum"
    }
}
