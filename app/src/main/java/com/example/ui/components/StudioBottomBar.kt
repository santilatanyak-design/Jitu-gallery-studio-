package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.StudioCrimson
import com.example.ui.theme.StudioNavy
import com.example.ui.viewmodel.StudioTab

@Composable
fun StudioBottomBar(
    currentTab: StudioTab,
    language: String,
    currentUserEmail: String = "nayakjitu986@gmail.com",
    onTabSelected: (StudioTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val isAdmin = currentUserEmail.equals("nayakjitu986@gmail.com", ignoreCase = true)

    NavigationBar(
        modifier = modifier.testTag("studio_bottom_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        // Tab 1: AI Chat Assistant
        NavigationBarItem(
            selected = currentTab == StudioTab.AI_CHAT,
            onClick = { onTabSelected(StudioTab.AI_CHAT) },
            icon = {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = "AI Assistant Chat"
                )
            },
            label = {
                Text(
                    text = if (language == "ENGLISH") "AI Assistant" else "AI ମ୍ୟାନେଜର",
                    fontWeight = if (currentTab == StudioTab.AI_CHAT) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = StudioCrimson,
                indicatorColor = StudioCrimson,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray
            ),
            modifier = Modifier.testTag("nav_ai_chat")
        )

        // Tab 2: Direct Order Form
        NavigationBarItem(
            selected = currentTab == StudioTab.ORDER_FORM,
            onClick = { onTabSelected(StudioTab.ORDER_FORM) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCard,
                    contentDescription = "Direct Order Form"
                )
            },
            label = {
                Text(
                    text = if (language == "ENGLISH") "New Order" else "ନୂଆ ଅର୍ଡର",
                    fontWeight = if (currentTab == StudioTab.ORDER_FORM) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = StudioCrimson,
                indicatorColor = StudioCrimson,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray
            ),
            modifier = Modifier.testTag("nav_order_form")
        )

        // Tab 3: Design Gallery
        NavigationBarItem(
            selected = currentTab == StudioTab.GALLERY,
            onClick = { onTabSelected(StudioTab.GALLERY) },
            icon = {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "Design Gallery Showcase"
                )
            },
            label = {
                Text(
                    text = if (language == "ENGLISH") "Gallery" else "ଗ୍ୟାଲେରୀ",
                    fontWeight = if (currentTab == StudioTab.GALLERY) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = StudioCrimson,
                indicatorColor = StudioCrimson,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray
            ),
            modifier = Modifier.testTag("nav_gallery")
        )

        // Tab 4: Saved Orders
        NavigationBarItem(
            selected = currentTab == StudioTab.MY_ORDERS,
            onClick = { onTabSelected(StudioTab.MY_ORDERS) },
            icon = {
                Icon(
                    imageVector = Icons.Default.ReceiptLong,
                    contentDescription = "My Orders"
                )
            },
            label = {
                Text(
                    text = if (language == "ENGLISH") "My Orders" else "ମୋର ଅର୍ଡର",
                    fontWeight = if (currentTab == StudioTab.MY_ORDERS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = StudioCrimson,
                indicatorColor = StudioCrimson,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray
            ),
            modifier = Modifier.testTag("nav_my_orders")
        )

        // Tab 5: Admin Panel (ONLY visible to nayakjitu986@gmail.com)
        if (isAdmin) {
            NavigationBarItem(
                selected = currentTab == StudioTab.ADMIN_PANEL,
                onClick = { onTabSelected(StudioTab.ADMIN_PANEL) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin Panel"
                    )
                },
                label = {
                    Text(
                        text = if (language == "ENGLISH") "Admin" else "ଆଡମିନ୍",
                        fontWeight = if (currentTab == StudioTab.ADMIN_PANEL) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = StudioCrimson,
                    indicatorColor = StudioCrimson,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray
                ),
                modifier = Modifier.testTag("nav_admin_panel")
            )
        }
    }
}
