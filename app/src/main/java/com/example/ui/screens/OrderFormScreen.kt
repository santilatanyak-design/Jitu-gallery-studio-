package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.StudioCrimson
import com.example.ui.theme.StudioGold
import com.example.ui.theme.StudioNavy

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderFormScreen(
    designType: String,
    dimensions: String,
    colors: String,
    notes: String,
    phone: String,
    email: String,
    language: String,
    onDesignTypeChange: (String) -> Unit,
    onDimensionsChange: (String) -> Unit,
    onColorsChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSubmitOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val designTypesList = listOf(
        "ଫ୍ଲେକ୍ସ ବ୍ୟାନର (Flex Banner)",
        "ସୋସିଆଲ୍ ମିଡିଆ ପୋଷ୍ଟର (1:1 Poster)",
        "ବାହାଘର ନିମନ୍ତ୍ରଣ କାର୍ଡ (Wedding Card)",
        "ଫୋଟୋ ଫ୍ରେମ୍ (Photo Frame)",
        "ଭିଜିଟିଂ କାର୍ଡ (Visiting Card)",
        "ଲୋଗୋ ଡିଜାଇନ୍ (Logo Design)"
    )

    val dimensionPresets = listOf(
        "1:1 Square",
        "16:9 Banner",
        "3x2 Ft Flex",
        "8x12 Inch Frame",
        "A4 Document",
        "Custom Size"
    )

    val colorOptions = listOf(
        "Crimson & Royal Blue",
        "Warm Gold & Navy",
        "Vibrant Gradient",
        "Classic Black & White",
        "Soft Pastel Colors"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form Title Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = StudioNavy),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (language == "ENGLISH") "Direct Studio Order Form" else "ନୂଆ ଡିଜାଇନ୍ ଅର୍ଡର ଫର୍ମ",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (language == "ENGLISH")
                        "Specify your requirements below. Our studio admin will check and contact you via nayakjitu986@gmail.com."
                    else
                        "ଆପଣଙ୍କ ଡିଜାଇନ୍ ଆବଶ୍ୟକତା ପୂରଣ କରନ୍ତୁ। ଆମ ଆଡମିନ୍ nayakjitu986@gmail.com ଜରିଆରେ ଯୋଗାଯୋଗ କରିବେ।",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                )
            }
        }

        // Section 1: Design Category Selection
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Style,
                        contentDescription = null,
                        tint = StudioCrimson,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == "ENGLISH") "1. Select Design Type" else "୧. ଡିଜାଇନ୍ ପ୍ରକାର ବାଛନ୍ତୁ",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = StudioNavy
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    designTypesList.forEach { type ->
                        val selected = designType == type
                        FilterChip(
                            selected = selected,
                            onClick = { onDesignTypeChange(type) },
                            label = { Text(text = type, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = StudioCrimson,
                                selectedLabelColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.background,
                                labelColor = StudioNavy
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("chip_design_$type")
                        )
                    }
                }
            }
        }

        // Section 2: Size & Dimensions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Straighten,
                        contentDescription = null,
                        tint = StudioCrimson,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == "ENGLISH") "2. Select Size / Dimensions" else "୨. ଆକାର / ସାଇଜ୍ ବାଛନ୍ତୁ (Dimensions)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = StudioNavy
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    dimensionPresets.forEach { dim ->
                        val selected = dimensions == dim
                        FilterChip(
                            selected = selected,
                            onClick = { onDimensionsChange(dim) },
                            label = { Text(text = dim, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = StudioNavy,
                                selectedLabelColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.background,
                                labelColor = StudioNavy
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("chip_dim_$dim")
                        )
                    }
                }
            }
        }

        // Section 3: Colors & Aesthetics
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ColorLens,
                        contentDescription = null,
                        tint = StudioCrimson,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == "ENGLISH") "3. Preferred Color Theme" else "୩. ରଙ୍ଗ ପସନ୍ଦ (Color Theme)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = StudioNavy
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    colorOptions.forEach { col ->
                        val selected = colors == col
                        FilterChip(
                            selected = selected,
                            onClick = { onColorsChange(col) },
                            label = { Text(text = col, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = StudioGold,
                                selectedLabelColor = StudioNavy,
                                containerColor = MaterialTheme.colorScheme.background,
                                labelColor = StudioNavy
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }
        }

        // Section 4: Design Description & Contact
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.NoteAdd,
                        contentDescription = null,
                        tint = StudioCrimson,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == "ENGLISH") "4. Design Instructions & Contact" else "୪. ଡିଜାଇନ୍ ବିବରଣୀ ଓ ସମ୍ପର୍କ",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = StudioNavy
                        )
                    )
                }

                // Notes Field
                OutlinedTextField(
                    value = notes,
                    onValueChange = onNotesChange,
                    label = {
                        Text(
                            text = if (language == "ENGLISH") "Design details, text, & photo notes *" else "ଡିଜାଇନ୍ ବିବରଣୀ, ଲେଖା, ଓ ଫୋଟୋ ସୂଚନା *"
                        )
                    },
                    placeholder = {
                        Text(
                            text = if (language == "ENGLISH")
                                "Write shop name, festival date, text content, phone numbers to include, or photo instructions..."
                            else
                                "ଦୋକାନ ନାମ, ନିମନ୍ତ୍ରଣ ତାରିଖ, ଫୋନ୍ ନମ୍ବର ଓ ଅନ୍ୟାନ୍ୟ ସୂଚନା ଲେଖନ୍ତୁ..."
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_notes_input"),
                    minLines = 3,
                    maxLines = 6,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = StudioCrimson,
                        unfocusedBorderColor = StudioNavy.copy(alpha = 0.3f)
                    )
                )

                // Phone Input
                OutlinedTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    label = {
                        Text(
                            text = if (language == "ENGLISH") "Phone Number / WhatsApp" else "ଫୋନ୍ / ହ୍ୱାଟସ୍‌ଆପ୍ ନମ୍ବର"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = StudioNavy
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_phone_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = StudioCrimson,
                        unfocusedBorderColor = StudioNavy.copy(alpha = 0.3f)
                    )
                )

                // Email Input
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = {
                        Text(
                            text = if (language == "ENGLISH") "Admin Review Email" else "ଆଡମିନ୍ ଯୋଗାଯୋଗ ଇମେଲ୍"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = StudioNavy
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_email_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = StudioCrimson,
                        unfocusedBorderColor = StudioNavy.copy(alpha = 0.3f)
                    )
                )
            }
        }

        // Submit Button
        Button(
            onClick = {
                sendOrderEmail(
                    context = context,
                    designType = designType,
                    dimensions = dimensions,
                    colors = colors,
                    notes = notes,
                    phone = phone,
                    customerEmail = email
                )
                onSubmitOrder()
            },
            enabled = notes.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("submit_order_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = StudioCrimson,
                disabledContainerColor = StudioNavy.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (language == "ENGLISH") "Submit & Email Order" else "ଅର୍ଡର ପଠାନ୍ତୁ (Submit)",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun sendOrderEmail(
    context: Context,
    designType: String,
    dimensions: String,
    colors: String,
    notes: String,
    phone: String,
    customerEmail: String
) {
    val emailSubject = "New Design Order: $designType"
    val emailContent = """
        Jitu Gallery Studio - New Order Details
        ========================================
        • Design Type: $designType
        • Size / Dimensions: $dimensions
        • Color Preference: $colors
        
        Customer Instructions & Requirements:
        $notes

        Contact Details:
        • Phone / WhatsApp: ${phone.ifBlank { "Not provided" }}
        • Customer Email: ${customerEmail.ifBlank { "nayakjitu986@gmail.com" }}
        
        Sent via Jitu Gallery Studio Mobile App
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:nayakjitu986@gmail.com")
        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        putExtra(Intent.EXTRA_TEXT, emailContent)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send Order Email"))
    } catch (e: Exception) {
        Toast.makeText(context, "No email client found to send order", Toast.LENGTH_SHORT).show()
    }
}
