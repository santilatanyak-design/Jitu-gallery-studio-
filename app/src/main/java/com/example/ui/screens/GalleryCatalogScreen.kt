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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DesignSample
import com.example.ui.theme.StudioCrimson
import com.example.ui.theme.StudioGold
import com.example.ui.theme.StudioNavy
import com.example.ui.theme.StudioNavyDark
import com.example.ui.theme.StudioTeal

@Composable
fun GalleryCatalogScreen(
    sampleList: List<DesignSample>,
    selectedCategory: String,
    language: String,
    onCategorySelect: (String) -> Unit,
    onSelectSampleOrder: (DesignSample) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Flex", "Poster", "Card", "Frame", "Logo")

    val filteredList = if (selectedCategory == "All") {
        sampleList
    } else {
        sampleList.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header Category Bar
        Surface(
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
                Text(
                    text = if (language == "ENGLISH") "Studio Design Catalog & Samples" else "Jitu Gallery ଡିଜାଇନ୍ ଗ୍ୟାଲେରୀ",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = StudioNavy
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategorySelect(cat) },
                            label = { Text(text = cat, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = StudioCrimson,
                                selectedLabelColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = StudioNavy
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("gallery_cat_$cat")
                        )
                    }
                }
            }
        }

        // Sample Cards List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredList, key = { it.id }) { sample ->
                DesignSampleCard(
                    sample = sample,
                    language = language,
                    onOrderClick = { onSelectSampleOrder(sample) }
                )
            }
        }
    }
}

@Composable
fun DesignSampleCard(
    sample: DesignSample,
    language: String,
    onOrderClick: () -> Unit
) {
    val icon = when (sample.category) {
        "Flex" -> Icons.Default.AspectRatio
        "Poster" -> Icons.Default.CropSquare
        "Card" -> Icons.Default.CardGiftcard
        "Frame" -> Icons.Default.PhotoCamera
        "Logo" -> Icons.Default.Brush
        else -> Icons.Default.Image
    }

    val gradientColors = when (sample.category) {
        "Flex" -> listOf(StudioNavyDark, StudioNavy)
        "Poster" -> listOf(StudioNavy, StudioCrimson)
        "Card" -> listOf(StudioNavy, StudioTeal)
        "Frame" -> listOf(StudioNavyDark, StudioGold)
        "Logo" -> listOf(StudioNavy, StudioCrimson)
        else -> listOf(StudioNavyDark, StudioNavy)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("sample_card_${sample.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            // Visual Preview Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        brush = Brush.linearGradient(gradientColors)
                    )
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (language == "ENGLISH") sample.titleEnglish else sample.titleOdia,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AspectRatio,
                                contentDescription = null,
                                tint = StudioGold,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Aspect Ratio: ${sample.aspectSize}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = sample.category,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            // Description & Price Section
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = if (language == "ENGLISH") sample.descriptionEnglish else sample.descriptionOdia,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = StudioNavy,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalOffer,
                            contentDescription = null,
                            tint = StudioCrimson,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Est. Price: ${sample.priceEstimate}",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = StudioCrimson
                            )
                        )
                    }

                    Button(
                        onClick = onOrderClick,
                        colors = ButtonDefaults.buttonColors(containerColor = StudioNavy),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("btn_order_sample_${sample.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (language == "ENGLISH") "Order This" else "ଅର୍ଡର କରନ୍ତୁ",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}
