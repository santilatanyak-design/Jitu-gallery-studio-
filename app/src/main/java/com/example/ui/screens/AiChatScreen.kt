package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.model.MessageSender
import com.example.ui.theme.StudioCrimson
import com.example.ui.theme.StudioNavy
import com.example.ui.theme.StudioNavyDark
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AiChatScreen(
    messages: List<ChatMessage>,
    input: String,
    isThinking: Boolean,
    language: String,
    onInputChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Auto scroll to bottom on new messages
    LaunchedEffect(messages.size, isThinking) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickPrompts = if (language == "ENGLISH") {
        listOf(
            "I want a 1:1 Social Media Poster",
            "Flex Banner size 16:9 for shop",
            "Wedding Invitation Card design",
            "Photo Frame & Digital Oil Portrait",
            "Visiting Card with QR code"
        )
    } else {
        listOf(
            "ମୋତେ 1:1 ସୋସିଆଲ୍ ମିଡିଆ ପୋଷ୍ଟର ଦରକାର",
            "ଦୋକାନ ପାଇଁ 16:9 ଫ୍ଲେକ୍ସ ବ୍ୟାନର",
            "ଓଡ଼ିଆ ବାହାଘର ନିମନ୍ତ୍ରଣ କାର୍ଡ ଡିଜାଇନ୍",
            "ଫୋଟୋ ଫ୍ରେମ୍ ଓ ଡିଜିଟାଲ୍ ଅଏଲ୍ ପେଣ୍ଟିଂ",
            "ବିଜିନେସ୍ ଭିଜିଟିଂ କାର୍ଡ ଡିଜାଇନ୍"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Contact Notice Header Banner
        Surface(
            color = StudioNavy.copy(alpha = 0.08f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Admin Contact Email",
                    tint = StudioCrimson,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (language == "ENGLISH")
                        "Admin review contact: nayakjitu986@gmail.com"
                    else
                        "ଆଡମିନ୍ ଯୋଗାଯୋଗ: nayakjitu986@gmail.com",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                )
            }
        }

        // Chat Message List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatMessageBubble(message = msg, language = language)
            }

            if (isThinking) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(StudioCrimson),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = StudioCrimson,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == "ENGLISH") "Studio AI is typing..." else "ଷ୍ଟୁଡିଓ ଟାଇପ୍ କରୁଛି...",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.LightGray
                            )
                        )
                    }
                }
            }
        }

        // Quick Suggestion Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(quickPrompts) { prompt ->
                FilterChip(
                    selected = false,
                    onClick = { onInputChange(prompt) },
                    label = {
                        Text(
                            text = prompt,
                            fontSize = 11.sp,
                            maxLines = 1,
                            color = Color.White
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = StudioNavyDark,
                        labelColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("quick_prompt_chip")
                )
            }
        }

        // Input Field Container
        Surface(
            tonalElevation = 6.dp,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = onInputChange,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    placeholder = {
                        Text(
                            text = if (language == "ENGLISH") "Type your order details..." else "ଆପଣଙ୍କ ଅର୍ଡର ବିଷୟରେ ଲେଖନ୍ତୁ...",
                            fontSize = 13.sp,
                            color = Color.LightGray
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray,
                        focusedBorderColor = StudioCrimson,
                        unfocusedBorderColor = StudioNavy.copy(alpha = 0.5f),
                        focusedContainerColor = StudioNavyDark,
                        unfocusedContainerColor = StudioNavyDark
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { onSendMessage() }),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onSendMessage,
                    enabled = input.isNotBlank() && !isThinking,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            if (input.isNotBlank() && !isThinking) StudioCrimson else StudioNavy.copy(
                                alpha = 0.3f
                            )
                        )
                        .testTag("send_chat_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send Message",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(
    message: ChatMessage,
    language: String
) {
    val isUser = message.sender == MessageSender.USER
    val timeFormatted = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(message.timestamp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(StudioNavyDark),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Palette,
                    contentDescription = "Studio Avatar",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isUser) StudioCrimson else StudioNavyDark
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isUser) 2.dp else 1.dp
                )
            ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                    if (!isUser) {
                        Text(
                            text = "Jitu Gallery Studio AI",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = StudioCrimson
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }

                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            lineHeight = 20.sp,
                            fontSize = 14.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = timeFormatted,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.LightGray,
                            fontSize = 10.sp
                        ),
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(StudioCrimson),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Avatar",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
