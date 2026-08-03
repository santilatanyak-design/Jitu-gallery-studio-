package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SupervisorAccount
import com.example.ui.theme.StudioTeal
import com.example.util.NotificationHelper
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.OrderEntity
import com.example.ui.theme.StudioCrimson
import com.example.ui.theme.StudioGold
import com.example.ui.theme.StudioNavy
import com.example.ui.theme.StudioNavyDark
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminDashboardScreen(
    ordersList: List<OrderEntity>,
    currentUserEmail: String,
    language: String,
    isAdminLoggedIn: Boolean,
    onAdminLoginSuccess: () -> Unit,
    onLogoutAdmin: () -> Unit,
    onUpdateOrderStatus: (OrderEntity, String) -> Unit,
    onSendDesign: (OrderEntity, String) -> Unit = { _, _ -> },
    onSetLinkStatus: (OrderEntity, String) -> Unit = { _, _ -> },
    onDeleteOrder: (Long) -> Unit,
    onSwitchUserEmail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val isAdmin = currentUserEmail.equals("nayakjitu986@gmail.com", ignoreCase = true)

    if (!isAdminLoggedIn || !isAdmin) {
        AdminLoginScreen(
            language = language,
            onLoginSuccess = onAdminLoginSuccess,
            modifier = modifier
        )
        return
    }

    // Filter Logic
    val filteredOrders = ordersList.filter { order ->
        val matchesFilter = when (selectedFilter) {
            "Submitted" -> order.status.contains("Submitted") || order.status.contains("ଗ୍ରହଣ")
            "In Progress" -> order.status.contains("Progress") || order.status.contains("ପ୍ରକ୍ରିୟା")
            "Completed" -> order.status.contains("Completed") || order.status.contains("ସମ୍ପୂର୍ଣ୍ଣ")
            else -> true
        }

        val matchesSearch = searchQuery.isBlank() ||
                order.orderNumber.contains(searchQuery, ignoreCase = true) ||
                order.designType.contains(searchQuery, ignoreCase = true) ||
                order.customerNotes.contains(searchQuery, ignoreCase = true) ||
                order.customerPhone.contains(searchQuery, ignoreCase = true)

        matchesFilter && matchesSearch
    }

    val totalCount = ordersList.size
    val submittedCount = ordersList.count { it.status.contains("Submitted") || it.status.contains("ଗ୍ରହଣ") }
    val inProgressCount = ordersList.count { it.status.contains("Progress") || it.status.contains("ପ୍ରକ୍ରିୟା") }
    val completedCount = ordersList.count { it.status.contains("Completed") || it.status.contains("ସମ୍ପୂର୍ଣ୍ଣ") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Admin Banner
        Surface(
            color = StudioNavyDark,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
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
                                imageVector = Icons.Default.SupervisorAccount,
                                contentDescription = "Admin Badge",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (language == "ENGLISH") "Admin Control Panel" else "ଆଡମିନ୍ ନିୟନ୍ତ୍ରଣ ପ୍ୟାନେଲ୍",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Authenticated: nayakjitu986@gmail.com",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = StudioGold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AssistChip(
                            onClick = {
                                Toast.makeText(context, "Admin session active", Toast.LENGTH_SHORT).show()
                            },
                            label = {
                                Text(
                                    text = "ADMIN ACTIVE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = StudioCrimson
                            )
                        )

                        AssistChip(
                            onClick = {
                                onLogoutAdmin()
                                Toast.makeText(context, "Logged out of Admin", Toast.LENGTH_SHORT).show()
                            },
                            label = {
                                Text(
                                    text = "LOGOUT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = StudioNavy
                            ),
                            modifier = Modifier.testTag("admin_logout_button")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Stats Dashboard Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminStatCard(
                        title = "Total Orders",
                        value = totalCount.toString(),
                        bgColor = StudioNavy,
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "Pending",
                        value = submittedCount.toString(),
                        bgColor = Color(0xFFD32F2F),
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "In Progress",
                        value = inProgressCount.toString(),
                        bgColor = Color(0xFFF57C00),
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "Completed",
                        value = completedCount.toString(),
                        bgColor = Color(0xFF388E3C),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Search & Filter Row
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = if (language == "ENGLISH") "Search orders, phone, notes..." else "ଅର୍ଡର ନମ୍ବର, ଫୋନ୍ କିମ୍ବା ଡିଜାଇନ୍ ଖୋଜନ୍ତୁ...",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = StudioGold
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Clear search",
                                tint = Color.LightGray
                            )
                        }
                    }
                },
                textStyle = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = StudioCrimson,
                    unfocusedBorderColor = StudioNavy.copy(alpha = 0.5f),
                    focusedContainerColor = StudioNavyDark,
                    unfocusedContainerColor = StudioNavyDark
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_search_field")
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = listOf("All", "Submitted", "In Progress", "Completed")
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = {
                            Text(
                                text = filter,
                                fontSize = 11.sp,
                                fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal,
                                color = Color.White
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StudioCrimson,
                            containerColor = StudioNavyDark
                        ),
                        modifier = Modifier.testTag("admin_filter_$filter")
                    )
                }
            }
        }

        // Orders List
        if (filteredOrders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (language == "ENGLISH") "No matching orders found" else "କୌଣସି ଅର୍ଡର ମିଳିଲା ନାହିଁ",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredOrders, key = { it.id }) { order ->
                    AdminOrderCard(
                        order = order,
                        language = language,
                        onUpdateStatus = { newStatus -> onUpdateOrderStatus(order, newStatus) },
                        onSendDesign = { driveLink -> onSendDesign(order, driveLink) },
                        onSetLinkStatus = { linkStatus -> onSetLinkStatus(order, linkStatus) },
                        onDelete = { onDeleteOrder(order.id) },
                        onContactEmail = {
                            sendEmailToCustomer(context, order)
                        },
                        onContactCall = {
                            callCustomer(context, order.customerPhone)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminStatCard(
    title: String,
    value: String,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp
                )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 10.sp
                )
            )
        }
    }
}

@Composable
private fun AdminOrderCard(
    order: OrderEntity,
    language: String,
    onUpdateStatus: (String) -> Unit,
    onSendDesign: (String) -> Unit,
    onSetLinkStatus: (String) -> Unit,
    onDelete: () -> Unit,
    onContactEmail: () -> Unit,
    onContactCall: () -> Unit
) {
    val context = LocalContext.current
    val dateFormatted = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(order.createdAt))

    var driveLinkInput by remember(order.driveLink) { mutableStateOf(order.driveLink) }

    val statusColor = when {
        order.status.contains("Approved", ignoreCase = true) || order.status.contains("ଅନୁମୋଦିତ", ignoreCase = true) -> StudioTeal
        order.status.contains("Progress") || order.status.contains("ପ୍ରକ୍ରିୟା") -> Color(0xFFFF9800)
        order.status.contains("Completed") || order.status.contains("ସମ୍ପୂର୍ଣ୍ଣ") -> Color(0xFF4CAF50)
        else -> StudioCrimson
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = StudioNavyDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_order_card_${order.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Header Row: Order Number & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = order.orderNumber,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = StudioGold
                        )
                    )
                    Text(
                        text = dateFormatted,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.LightGray,
                            fontSize = 10.sp
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = statusColor,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = order.status,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Surface(
                        color = when (order.linkStatus) {
                            "UNLOCKED" -> Color(0xFF2E7D32)
                            "DELETED" -> StudioCrimson
                            else -> Color(0xFFFF9800)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "LINK: ${order.linkStatus}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 9.sp
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.Gray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(10.dp))

            // Details
            Text(
                text = "• Design Type: ${order.designType}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
            Text(
                text = "• Size/Dimensions: ${order.sizeDimensions}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
            )
            Text(
                text = "• Colors: ${order.colorPreferences}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                color = StudioNavy.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Customer Notes:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = StudioGold
                        )
                    )
                    Text(
                        text = order.customerNotes,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contact Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Phone: ${order.customerPhone}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.LightGray,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Email: ${order.customerEmail}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.Gray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(10.dp))

            // Admin Action Section
            Text(
                text = "Admin Actions:",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // APPROVE BUTTON
                Button(
                    onClick = {
                        onUpdateStatus("Approved")
                        Toast.makeText(context, "Order ${order.orderNumber} Approved!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StudioTeal),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("admin_approve_button_${order.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Approve",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Approve", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                OutlinedButton(
                    onClick = { onUpdateStatus("In Progress") },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF9800)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("In Progress", fontSize = 10.sp, maxLines = 1)
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(StudioCrimson.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Order",
                        tint = StudioCrimson,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Google Drive Download Link & Control Panel
            Text(
                text = "Google Drive Download Link Control:",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = StudioGold,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = driveLinkInput,
                onValueChange = { driveLinkInput = it },
                placeholder = {
                    Text("Paste Google Drive link here...", fontSize = 11.sp, color = Color.Gray)
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = StudioGold,
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                    focusedContainerColor = StudioNavy,
                    unfocusedContainerColor = StudioNavy
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_drive_input_${order.id}")
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3 Explicit Link Action Buttons: Unlock, Lock, Delete Link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // UNLOCK BUTTON
                Button(
                    onClick = {
                        val finalLink = driveLinkInput.trim()
                        if (finalLink.isBlank()) {
                            Toast.makeText(context, "Please paste Google Drive link first", Toast.LENGTH_SHORT).show()
                        } else {
                            onSendDesign(finalLink)
                            onSetLinkStatus("UNLOCKED")
                            NotificationHelper.sendDesignNotification(
                                context = context,
                                orderNumber = order.orderNumber,
                                driveLink = finalLink
                            )
                            Toast.makeText(context, "Link UNLOCKED for customer!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("admin_unlock_link_button_${order.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.LockOpen,
                        contentDescription = "Unlock Link",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Unlock", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // LOCK BUTTON
                Button(
                    onClick = {
                        onSetLinkStatus("LOCKED")
                        Toast.makeText(context, "Link LOCKED for order ${order.orderNumber}", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("admin_lock_link_button_${order.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock Link",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Lock", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // DELETE LINK BUTTON
                Button(
                    onClick = {
                        driveLinkInput = ""
                        onSetLinkStatus("DELETED")
                        Toast.makeText(context, "Link DELETED from order ${order.orderNumber}", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StudioCrimson),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("admin_delete_link_button_${order.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = "Delete Link",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Delete Link", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Direct Customer Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onContactEmail,
                    colors = ButtonDefaults.buttonColors(containerColor = StudioNavy),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Customer",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Email Customer", fontSize = 11.sp, color = Color.White)
                }

                if (order.customerPhone.isNotBlank() && order.customerPhone != "N/A" && order.customerPhone != "Not Provided") {
                    Button(
                        onClick = onContactCall,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Call Customer",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call Customer", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

private fun sendEmailToCustomer(context: Context, order: OrderEntity) {
    val recipient = if (order.customerEmail.isNotBlank()) order.customerEmail else "nayakjitu986@gmail.com"
    val subject = "Update regarding your Order ${order.orderNumber} - Jitu Gallery Studio"
    val body = """
        Hello,

        This is Jitu Gallery Studio (nayakjitu986@gmail.com) following up on your order:
        • Order Number: ${order.orderNumber}
        • Design Type: ${order.designType}
        • Current Status: ${order.status}

        Please let us know if you have any additional instructions or file attachments.

        Best regards,
        Jitu Gallery Studio Team
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$recipient")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Contact Customer via Email"))
    } catch (e: Exception) {
        Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
    }
}

private fun callCustomer(context: Context, phone: String) {
    if (phone.isBlank()) return
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to launch dialer", Toast.LENGTH_SHORT).show()
    }
}
