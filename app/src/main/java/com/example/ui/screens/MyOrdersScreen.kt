package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Verified
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.OrderEntity
import com.example.ui.theme.StudioCrimson
import com.example.ui.theme.StudioGold
import com.example.ui.theme.StudioNavy
import com.example.ui.theme.StudioNavyDark
import com.example.ui.theme.StudioTeal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyOrdersScreen(
    ordersList: List<OrderEntity>,
    language: String,
    onRequestPayment: (OrderEntity) -> Unit = {},
    onDeleteOrder: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Studio Support Contact Card
        Surface(
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = StudioNavy)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(StudioCrimson),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Jitu Gallery Studio Support",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "nayakjitu986@gmail.com",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = StudioGold,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                openEmailClient(context, "nayakjitu986@gmail.com", "Studio Order Inquiry")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = StudioCrimson),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == "ENGLISH") "Email Admin" else "ଇମେଲ୍ କରନ୍ତୁ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                openLocationInMap(context)
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == "ENGLISH") "Odisha Studio" else "ଓଡ଼ିଶା ଷ୍ଟୁଡିଓ",
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Saved Orders Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (language == "ENGLISH") "Saved Orders (${ordersList.size})" else "ମୋର ଦାଖଲ ହୋଇଥିବା ଅର୍ଡର (${ordersList.size})",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        if (ordersList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        text = if (language == "ENGLISH") "No orders saved yet." else "କୌଣସି ଅର୍ଡର ନାହିଁ।",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = if (language == "ENGLISH")
                            "Use AI Manager or New Order Form to place your design requests!"
                        else
                            "AI ମ୍ୟାନେଜର କିମ୍ବା ନୂଆ ଅର୍ଡର ଫର୍ମ ଜରିଆରେ ଅର୍ଡର ରଖନ୍ତୁ!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ordersList, key = { it.id }) { order ->
                    OrderItemCard(
                        order = order,
                        language = language,
                        onRequestPayment = onRequestPayment,
                        onCopyDetails = {
                            val details = "Jitu Gallery Studio Order ${order.orderNumber}\nDesign: ${order.designType}\nDimensions: ${order.sizeDimensions}\nNotes: ${order.customerNotes}\nStatus: ${order.status}\nDrive Link: ${order.driveLink}\nContact Admin: nayakjitu986@gmail.com"
                            clipboardManager.setText(AnnotatedString(details))
                            Toast.makeText(context, if (language == "ENGLISH") "Order details copied!" else "ଅର୍ଡର ବିବରଣୀ କପି ହେଲା!", Toast.LENGTH_SHORT).show()
                        },
                        onDelete = { onDeleteOrder(order.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItemCard(
    order: OrderEntity,
    language: String,
    onRequestPayment: (OrderEntity) -> Unit,
    onCopyDetails: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val formattedDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(order.createdAt))

    var showPaymentDialog by remember { mutableStateOf(false) }

    val isCompleted = order.status.contains("Completed", ignoreCase = true) ||
            order.status.contains("ସମ୍ପୂର୍ଣ୍ଣ", ignoreCase = true)
    val isPendingPayment = order.status == "Payment Verification Pending"
    val isUnlocked = order.linkStatus == "UNLOCKED"

    if (showPaymentDialog) {
        PaymentDialog(
            order = order,
            language = language,
            onRequestPayment = onRequestPayment,
            onDismiss = { showPaymentDialog = false }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("order_card_${order.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(StudioTeal.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AssignmentTurnedIn,
                            contentDescription = null,
                            tint = StudioTeal,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = order.orderNumber,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = StudioNavy
                            )
                        )
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = StudioNavy.copy(alpha = 0.6f),
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Surface(
                    color = if (isCompleted || isUnlocked) Color(0xFF2E7D32).copy(alpha = 0.15f) else StudioTeal.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = order.status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isCompleted || isUnlocked) Color(0xFF2E7D32) else StudioTeal,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            HorizontalDivider(color = DividerDefaults.color.copy(alpha = 0.5f))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (language == "ENGLISH") "Type: " else "ଡିଜାଇନ୍: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = StudioNavy
                )
                Text(
                    text = order.designType,
                    fontSize = 13.sp,
                    color = StudioCrimson,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (language == "ENGLISH") "Dimensions: " else "ସାଇଜ୍: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = StudioNavy
                )
                Text(
                    text = order.sizeDimensions,
                    fontSize = 13.sp,
                    color = StudioNavy
                )
            }

            if (order.customerNotes.isNotBlank()) {
                Text(
                    text = if (language == "ENGLISH") "Notes: ${order.customerNotes}" else "ବିବରଣୀ: ${order.customerNotes}",
                    fontSize = 12.sp,
                    color = StudioNavy.copy(alpha = 0.85f),
                    maxLines = 3
                )
            }

            // Real-time Order Tracking Timeline
            OrderTrackingTimeline(
                currentStatus = order.status,
                language = language
            )

            // Preview & Download Design Actions Section
            Surface(
                color = StudioNavy.copy(alpha = 0.05f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, StudioNavy.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Preview Design Button
                    OutlinedButton(
                        onClick = {
                            val link = if (order.driveLink.isNotBlank()) order.driveLink else "https://drive.google.com"
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Opening Design Preview...", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = StudioNavy),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("preview_design_button_${order.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Preview Design",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (language == "ENGLISH") "Preview Design" else "ଡିଜାଇନ୍ ଦେଖନ୍ତୁ (Preview)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Download Design Button (Dynamic relative to linkStatus and payment verification)
                    if (isUnlocked) {
                        // UNLOCKED: Direct Download
                        Button(
                            onClick = {
                                val link = if (order.driveLink.isNotBlank()) order.driveLink else "https://drive.google.com"
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Could not open Drive link", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("download_high_quality_design_button_${order.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDownload,
                                contentDescription = "Download High-Quality Design",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == "ENGLISH") "Download High-Quality Design" else "ଉଚ୍ଚ ମାନର ଡିଜାଇନ୍ ଡାଉନ୍‌ଲୋଡ୍ କରନ୍ତୁ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    } else if (isPendingPayment) {
                        // PAYMENT PENDING: Waiting Button
                        Button(
                            onClick = {
                                Toast.makeText(context, "Payment under review by Admin (nayakjitu986@gmail.com). Please wait.", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = StudioGold),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("waiting_unlock_button_${order.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Please wait",
                                tint = StudioNavy,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Please wait, unlocking download...",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudioNavy
                            )
                        }
                    } else {
                        // LOCKED: Requires Paytm Payment Dialog
                        Button(
                            onClick = { showPaymentDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = StudioCrimson),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("download_design_paytm_button_${order.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Download Design",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == "ENGLISH") "Download Design" else "ଡିଜାଇନ୍ ଡାଉନ୍‌ଲୋଡ୍ କରନ୍ତୁ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Admin Review: nayakjitu986@gmail.com",
                    fontSize = 11.sp,
                    color = StudioNavy.copy(alpha = 0.6f)
                )

                Row {
                    IconButton(
                        onClick = onCopyDetails,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Order Info",
                            tint = StudioNavy,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Order",
                            tint = StudioCrimson,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentDialog(
    order: OrderEntity,
    language: String,
    onRequestPayment: (OrderEntity) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = null,
                    tint = StudioCrimson,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (language == "ENGLISH") "Paytm QR Code Payment" else "Paytm QR କୋଡ୍ ପେମେଣ୍ଟ",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = StudioNavy
                    )
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (language == "ENGLISH")
                        "Scan Paytm QR Code below to pay for Order ${order.orderNumber}:"
                    else
                        "ଅର୍ଡର ${order.orderNumber} ପାଇଁ ନିମ୍ନ Paytm QR ସ୍କାନ୍ କରନ୍ତୁ:",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
                )

                // Authentic Paytm QR Card Component
                OriginalPaytmQRCard(
                    upiId = "9337971679@ptsbi",
                    payeeName = "Jitu Nayak"
                )

                Surface(
                    color = StudioNavy.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (language == "ENGLISH")
                            "After payment, click 'Payment Done'. An email notification will be sent to nayakjitu986@gmail.com for verification."
                        else
                            "ପେମେଣ୍ଟ କରିସାରିବା ପରେ 'Payment Done' କ୍ଲିକ୍ କରନ୍ତୁ। ନିଶ୍ଚିତକରଣ ପାଇଁ nayakjitu986@gmail.com କୁ ଇମେଲ୍ ଯିବ।",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = StudioNavy,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onRequestPayment(order)
                    sendPaymentNotificationEmail(context, order)
                    Toast.makeText(context, "Payment notified! Status set to Verification Pending.", Toast.LENGTH_LONG).show()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = StudioTeal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("payment_done_dialog_button")
            ) {
                Text("Payment Done", fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancel", color = StudioNavy)
            }
        }
    )
}

@Composable
fun OriginalPaytmQRCard(
    upiId: String = "9337971679@ptsbi",
    payeeName: String = "Jitu Nayak",
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F5F9), RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        // Top Profile Avatar Circle
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(StudioGold)
                .padding(2.dp)
                .clip(CircleShape)
                .background(Color(0xFF8B0000)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🌸",
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Name + Verified Badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = payeeName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color(0xFF0F172A)
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified Merchant",
                tint = Color(0xFF00B9F1),
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Two-tone Paytm Card Frame
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF00B9F1)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF00B9F1), // Paytm Cyan top
                                Color(0xFF00B9F1),
                                Color(0xFF002970), // Paytm Dark Navy bottom
                                Color(0xFF002970)
                            )
                        )
                    )
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Inner White Container
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        // paytm ❤ UPI Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "paytm",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = Color(0xFF002970)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "❤",
                                fontSize = 14.sp,
                                color = Color(0xFFE11D48)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "UPI",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = Color(0xFF002970)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // QR Code Image
                        Image(
                            painter = painterResource(id = R.drawable.paytm_qr),
                            contentDescription = "Paytm QR Code for $upiId",
                            modifier = Modifier
                                .size(190.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // UPI ID Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "▶",
                                fontSize = 11.sp,
                                color = Color(0xFFFF9900)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = upiId,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF1E293B)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(upiId))
                                    Toast.makeText(context, "UPI ID Copied: $upiId", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy UPI ID",
                                    tint = Color(0xFF002970),
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Direct App Switcher Payment Button
        Button(
            onClick = {
                val upiUri = Uri.parse("upi://pay?pa=$upiId&pn=${Uri.encode(payeeName)}&cu=INR")
                val intent = Intent(Intent.ACTION_VIEW, upiUri)
                try {
                    context.startActivity(Intent.createChooser(intent, "Pay via UPI App"))
                } catch (e: Exception) {
                    clipboardManager.setText(AnnotatedString(upiId))
                    Toast.makeText(context, "UPI ID copied: $upiId. Open Paytm/PhonePe to pay.", Toast.LENGTH_LONG).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002970)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.QrCode2,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Pay via Paytm / PhonePe / GPay",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Footer Text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Scan with any UPI app ",
                fontSize = 11.sp,
                color = Color.Gray
            )
            Text(
                text = "paytm | BHIM | GPay",
                fontSize = 11.sp,
                color = Color(0xFF002970),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun sendPaymentNotificationEmail(context: Context, order: OrderEntity) {
    val recipient = "nayakjitu986@gmail.com"
    val subject = "Payment Confirmation for Order ${order.orderNumber}"
    val body = """
        Hello Admin,

        I have completed the Paytm payment for my design order:
        • Order Number: ${order.orderNumber}
        • Design Type: ${order.designType}
        • Customer Email: ${order.customerEmail}
        • Customer Phone: ${order.customerPhone}

        Please verify the payment and unlock my high-resolution download link.

        Thank you!
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$recipient")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send Payment Notification Email"))
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open email client", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun OrderTrackingTimeline(
    currentStatus: String,
    language: String,
    modifier: Modifier = Modifier
) {
    val isApproved = currentStatus.contains("Approved", ignoreCase = true) ||
            currentStatus.contains("ଅନୁମୋଦିତ", ignoreCase = true) ||
            currentStatus.contains("Completed", ignoreCase = true) ||
            currentStatus.contains("ସମ୍ପୂର୍ଣ୍ଣ", ignoreCase = true)

    val isCompleted = currentStatus.contains("Completed", ignoreCase = true) ||
            currentStatus.contains("ସମ୍ପୂର୍ଣ୍ଣ", ignoreCase = true)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(StudioNavyDark.copy(alpha = 0.04f), RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        Text(
            text = if (language == "ENGLISH") "Order Status Timeline" else "ଅର୍ଡର ଟ୍ରାକିଂ (Order Tracking)",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = StudioNavy
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Step 1: Pending
            TimelineStepItem(
                title = if (language == "ENGLISH") "Pending" else "ପେଣ୍ଡିଂ",
                isActive = true,
                isFinished = isApproved || isCompleted,
                icon = Icons.Default.Schedule,
                modifier = Modifier.weight(1f)
            )

            HorizontalDivider(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 2.dp),
                color = if (isApproved) StudioTeal else Color.LightGray,
                thickness = 2.dp
            )

            // Step 2: Approved
            TimelineStepItem(
                title = if (language == "ENGLISH") "Approved" else "ଅନୁମୋଦିତ",
                isActive = isApproved,
                isFinished = isCompleted,
                icon = Icons.Default.CheckCircle,
                modifier = Modifier.weight(1f)
            )

            HorizontalDivider(
                modifier = Modifier
                    .width(20.dp)
                    .padding(horizontal = 2.dp),
                color = if (isCompleted) Color(0xFF2E7D32) else Color.LightGray,
                thickness = 2.dp
            )

            // Step 3: Completed
            TimelineStepItem(
                title = if (language == "ENGLISH") "Completed" else "ସମ୍ପୂର୍ଣ୍ଣ",
                isActive = isCompleted,
                isFinished = isCompleted,
                icon = Icons.Default.CloudDownload,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TimelineStepItem(
    title: String,
    isActive: Boolean,
    isFinished: Boolean,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val circleBg = when {
        isFinished -> Color(0xFF2E7D32)
        isActive -> StudioCrimson
        else -> Color.LightGray
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(circleBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                color = if (isActive) StudioNavy else Color.Gray
            )
        )
    }
}

private fun openEmailClient(context: Context, email: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Email client not available.", Toast.LENGTH_SHORT).show()
    }
}

private fun openLocationInMap(context: Context) {
    try {
        val gmmIntentUri = Uri.parse("geo:20.2961,85.8245?q=Jitu+Gallery+Studio+Odisha")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Jitu Gallery Studio, Odisha, India", Toast.LENGTH_LONG).show()
    }
}
