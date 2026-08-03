package com.example.data

import com.example.BuildConfig
import com.example.api.Content
import com.example.api.GenerateContentRequest
import com.example.api.Part
import com.example.api.RetrofitClient
import com.example.model.ChatMessage
import com.example.model.DesignSample
import com.example.model.MessageSender
import kotlinx.coroutines.flow.Flow

class StudioRepository(private val orderDao: OrderDao) {

    val allOrders: Flow<List<OrderEntity>> = orderDao.getAllOrders()

    suspend fun saveOrder(order: OrderEntity): Long {
        return orderDao.insertOrder(order)
    }

    suspend fun updateOrder(order: OrderEntity) {
        orderDao.updateOrder(order)
    }

    suspend fun deleteOrder(id: Long) {
        orderDao.deleteOrderById(id)
    }

    suspend fun getGeminiAiResponse(
        chatHistory: List<ChatMessage>,
        userMessage: String,
        languagePreference: String // "ODIA" or "ENGLISH"
    ): String {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return generateLocalFallbackResponse(userMessage, languagePreference)
        }

        val systemInstructionText = if (languagePreference == "ENGLISH") {
            """
            You are a polite, helpful AI Manager for "Jitu Gallery Studio" in Odisha.
            Your role is to handle customer graphic design orders, inquiries, banner prints, posters, wedding cards, visiting cards, photo frames, and photo editing requests.
            - Acknowledge customer request warmly.
            - Ask for missing key details if needed: size/aspect ratio (e.g., 1:1 square, 16:9 banner, 3x2 ft flex, 8x12 frame), preferred colors, text content, deadline, or photo attachment note.
            - Confirm that the studio team will review their order and contact them via nayakjitu986@gmail.com.
            - Keep responses professional, clear, and encouraging.
            """.trimIndent()
        } else {
            """
             You are a polite AI assistant for "Jitu Gallery Studio" in Odisha. Respond primarily in Odia language (using clean Odia script with English technical terms like 1:1 size, flex printing, poster where appropriate).
            The customer is placing an order for a graphic design (Flex Banner, Poster, Wedding Card, Visiting Card, Photo Frame, Digital Portrait, Logo).
            - Acknowledge their request warmly in Odia: "ନମସ୍କାର! Jitu Gallery Studio କୁ ସ୍ୱାଗତ।..."
            - Ask for any missing details (such as 1:1 or 16:9 size, color palette preference, photos, or special message).
            - Assure them that the admin will check this order and contact them via nayakjitu986@gmail.com.
            """.trimIndent()
        }

        val contentList = mutableListOf<Content>()
        // Append conversation history (up to last 6 turns)
        chatHistory.takeLast(6).forEach { msg ->
            val role = if (msg.sender == MessageSender.USER) "user" else "model"
            contentList.add(Content(role = role, parts = listOf(Part(text = msg.text))))
        }
        // Current user message
        contentList.add(Content(role = userMessageRole(userMessage), parts = listOf(Part(text = userMessage))))

        val request = GenerateContentRequest(
            contents = contentList,
            systemInstruction = Content(parts = listOf(Part(text = systemInstructionText)))
        )

        return try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            val replyText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            if (replyText != null && replyText.isNotBlank()) {
                replyText
            } else {
                generateLocalFallbackResponse(userMessage, languagePreference)
            }
        } catch (e: Exception) {
            generateLocalFallbackResponse(userMessage, languagePreference)
        }
    }

    private fun userMessageRole(text: String) = "user"

    private fun generateLocalFallbackResponse(userMsg: String, lang: String): String {
        return if (lang == "ENGLISH") {
            "Thank you for reaching out to Jitu Gallery Studio! We received your request: '$userMsg'. " +
                    "Please let us know your preferred dimensions (e.g. 1:1 square, 16:9 banner), target colors, and text details. " +
                    "Our studio team will review your order and contact you directly via nayakjitu986@gmail.com!"
        } else {
            "ଧନ୍ୟବାଦ! Jitu Gallery Studio ରେ ଆପଣଙ୍କ ଅର୍ଡର ଗ୍ରହଣ କରାଗଲା: '$userMsg'।" +
                    "\n\nଦୟାକରି ଆପଣଙ୍କ ଡିଜାଇନ୍ ର ଆକାର (1:1 ସାଇଜ୍, ବ୍ୟାନର Size), ରଙ୍ଗ (Color) ଏବଂ ଅନ୍ୟାନ୍ୟ ସୂଚନା ନିଶ୍ଚିତ କରନ୍ତୁ।" +
                    "\n\nଆମର ଆଡମିନ୍ ଶୀଘ୍ର ଏହି ଅର୍ଡର ଦେଖି nayakjitu986@gmail.com ଜରିଆରେ ଆପଣଙ୍କ ସହ ଯୋଗାଯୋଗ କରିବେ।"
        }
    }

    fun getSampleDesigns(): List<DesignSample> {
        return listOf(
            DesignSample(
                id = "s1",
                titleOdia = "ଫ୍ଲେକ୍ସ ବ୍ୟାନର (Flex Banner)",
                titleEnglish = "Flex Banner Design",
                category = "Flex",
                aspectSize = "16:9 / Custom Ft",
                priceEstimate = "₹250 - ₹1200",
                descriptionOdia = "ଦୋକାନ ବ୍ୟାନର, ଉତ୍ସବ, ନିର୍ବାଚନ ଓ ଆୟୋଜନ ପାଇଁ ଆକର୍ଷଣୀୟ HD ଫ୍ଲେକ୍ସ ଡିଜାଇନ୍।",
                descriptionEnglish = "Eye-catching HD Flex banner designs for shops, festivals, and events.",
                sampleImageTag = "flex_banner"
            ),
            DesignSample(
                id = "s2",
                titleOdia = "ସୋସିଆଲ୍ ମିଡିଆ ପୋଷ୍ଟର",
                titleEnglish = "Social Media Poster (1:1)",
                category = "Poster",
                aspectSize = "1:1 Square",
                priceEstimate = "₹150 - ₹400",
                descriptionOdia = "ଇନଷ୍ଟାଗ୍ରାମ, ଫେସବୁକ୍ ଓ ହ୍ୱାଟସ୍‌ଆପ୍ ପାଇଁ ସ୍ମାର୍ଟ 1:1 ସ୍କୋୟାର୍ ପୋଷ୍ଟର ଡିଜାଇନ୍।",
                descriptionEnglish = "Smart 1:1 square digital posters for Instagram, Facebook, and WhatsApp.",
                sampleImageTag = "poster_square"
            ),
            DesignSample(
                id = "s3",
                titleOdia = "ବାହାଘର ନିମନ୍ତ୍ରଣ କାର୍ଡ",
                titleEnglish = "Wedding Card Design",
                category = "Card",
                aspectSize = "5x7 Inch / Folding",
                priceEstimate = "₹300 - ₹800",
                descriptionOdia = "ପାରମ୍ପରିକ ଓ ଆଧୁନିକ ଓଡ଼ିଆ/ଇଂରାଜୀ ବାହାଘର ୱେଡିଂ କାର୍ଡ ଡିଜାଇନ୍।",
                descriptionEnglish = "Traditional and modern wedding invitation cards in Odia & English.",
                sampleImageTag = "wedding_card"
            ),
            DesignSample(
                id = "s4",
                titleOdia = "ଫୋଟୋ ଫ୍ରେମ୍ ଓ ଡିଜିଟାଲ୍ ଆର୍ଟ",
                titleEnglish = "Photo Frame & Digital Art",
                category = "Frame",
                aspectSize = "8x12 / 12x18 Inch",
                priceEstimate = "₹350 - ₹1500",
                descriptionOdia = "ଜନ୍ମଦିନ, ଆନିଭର୍ସରୀ ଗିଫ୍ଟ ପାଇଁ ପ୍ରିମିୟମ୍ ଫୋଟୋ ଫ୍ରେମିଂ ଓ ଅଏଲ୍ ପେଣ୍ଟିଂ।",
                descriptionEnglish = "Premium custom photo framing and digital oil portrait gifts.",
                sampleImageTag = "photo_frame"
            ),
            DesignSample(
                id = "s5",
                titleOdia = "ବିଜିନେସ୍ ଭିଜିଟିଂ କାର୍ଡ",
                titleEnglish = "Visiting / Business Card",
                category = "Card",
                aspectSize = "3.5x2.0 Inch",
                priceEstimate = "₹200 - ₹500",
                descriptionOdia = "ବ୍ୟବସାୟ ଓ ପ୍ରୋଫେସନାଲ୍ ବ୍ୟକ୍ତିଙ୍କ ପାଇଁ ପ୍ରିମିୟମ୍ ଭିଜିଟିଂ କାର୍ଡ ଡିଜାଇନ୍।",
                descriptionEnglish = "Professional business cards with modern layout & QR code integration.",
                sampleImageTag = "visiting_card"
            ),
            DesignSample(
                id = "s6",
                titleOdia = "ଲୋଗୋ ଓ ବ୍ରାଣ୍ଡିଂ (Logo)",
                titleEnglish = "Logo & Branding",
                category = "Logo",
                aspectSize = "Vector / High Res",
                priceEstimate = "₹500 - ₹2000",
                descriptionOdia = "ଷ୍ଟୋର୍, ୟୁଟ୍ୟୁବ୍ ଚ୍ୟାନେଲ୍ ଓ କମ୍ପାନୀ ପାଇଁ ଆକର୍ଷଣୀୟ କଷ୍ଟମ୍ ଲୋଗୋ।",
                descriptionEnglish = "Custom vector logos and brand identity design for businesses and YouTube channels.",
                sampleImageTag = "logo_design"
            )
        )
    }
}

private fun String?.isNull_or_blank(): Boolean {
    return this == null || this.trim().isEmpty()
}
