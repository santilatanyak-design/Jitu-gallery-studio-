package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.StudioBottomBar
import com.example.ui.components.StudioHeader
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AiChatScreen
import com.example.ui.screens.GalleryCatalogScreen
import com.example.ui.screens.MyOrdersScreen
import com.example.ui.screens.OrderFormScreen
import com.example.ui.theme.JituGalleryStudioTheme
import com.example.ui.viewmodel.StudioTab
import com.example.ui.viewmodel.StudioViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: StudioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JituGalleryStudioTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(
    viewModel: StudioViewModel
) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val currentUserEmail by viewModel.currentUserEmail.collectAsStateWithLifecycle()
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsStateWithLifecycle()

    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val chatInput by viewModel.chatInput.collectAsStateWithLifecycle()
    val isThinking by viewModel.isAiThinking.collectAsStateWithLifecycle()

    val formDesignType by viewModel.formDesignType.collectAsStateWithLifecycle()
    val formDimensions by viewModel.formDimensions.collectAsStateWithLifecycle()
    val formColors by viewModel.formColors.collectAsStateWithLifecycle()
    val formNotes by viewModel.formNotes.collectAsStateWithLifecycle()
    val formPhone by viewModel.formPhone.collectAsStateWithLifecycle()
    val formEmail by viewModel.formEmail.collectAsStateWithLifecycle()
    val formSuccessMsg by viewModel.formSubmissionSuccess.collectAsStateWithLifecycle()

    val ordersList by viewModel.orders.collectAsStateWithLifecycle()
    val selectedCat by viewModel.selectedGalleryCategory.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(formSuccessMsg) {
        formSuccessMsg?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissFormSuccessMessage()
        }
    }

    Scaffold(
        topBar = {
            StudioHeader(
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() }
            )
        },
        bottomBar = {
            StudioBottomBar(
                currentTab = currentTab,
                language = language,
                currentUserEmail = currentUserEmail,
                onTabSelected = { tab -> viewModel.selectTab(tab) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                StudioTab.AI_CHAT -> {
                    AiChatScreen(
                        messages = chatMessages,
                        input = chatInput,
                        isThinking = isThinking,
                        language = language,
                        onInputChange = { text -> viewModel.updateChatInput(text) },
                        onSendMessage = { viewModel.sendChatMessage() }
                    )
                }

                StudioTab.ORDER_FORM -> {
                    OrderFormScreen(
                        designType = formDesignType,
                        dimensions = formDimensions,
                        colors = formColors,
                        notes = formNotes,
                        phone = formPhone,
                        email = formEmail,
                        language = language,
                        onDesignTypeChange = { type -> viewModel.setFormDesignType(type) },
                        onDimensionsChange = { dims -> viewModel.setFormDimensions(dims) },
                        onColorsChange = { cols -> viewModel.setFormColors(cols) },
                        onNotesChange = { notes -> viewModel.setFormNotes(notes) },
                        onPhoneChange = { phone -> viewModel.setFormPhone(phone) },
                        onEmailChange = { email -> viewModel.setFormEmail(email) },
                        onSubmitOrder = { viewModel.submitDirectOrder() }
                    )
                }

                StudioTab.GALLERY -> {
                    GalleryCatalogScreen(
                        sampleList = viewModel.sampleDesigns,
                        selectedCategory = selectedCat,
                        language = language,
                        onCategorySelect = { cat -> viewModel.setGalleryCategory(cat) },
                        onSelectSampleOrder = { sample -> viewModel.startOrderFromSample(sample) }
                    )
                }

                StudioTab.MY_ORDERS -> {
                    MyOrdersScreen(
                        ordersList = ordersList,
                        language = language,
                        onRequestPayment = { order -> viewModel.requestPaymentVerification(order) },
                        onDeleteOrder = { id -> viewModel.deleteOrder(id) }
                    )
                }

                StudioTab.ADMIN_PANEL -> {
                    AdminDashboardScreen(
                        ordersList = ordersList,
                        currentUserEmail = currentUserEmail,
                        language = language,
                        isAdminLoggedIn = isAdminLoggedIn,
                        onAdminLoginSuccess = { viewModel.onAdminLoginSuccess() },
                        onLogoutAdmin = { viewModel.logoutAdmin() },
                        onUpdateOrderStatus = { order, newStatus -> viewModel.updateOrderStatus(order, newStatus) },
                        onSendDesign = { order, driveLink -> viewModel.sendDesignToCustomer(order, driveLink) },
                        onSetLinkStatus = { order, linkStatus -> viewModel.setOrderLinkStatus(order, linkStatus) },
                        onDeleteOrder = { id -> viewModel.deleteOrder(id) },
                        onSwitchUserEmail = { email -> viewModel.setCurrentUserEmail(email) }
                    )
                }
            }
        }
    }
}
