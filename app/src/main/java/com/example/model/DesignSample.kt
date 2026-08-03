package com.example.model

data class DesignSample(
    val id: String,
    val titleOdia: String,
    val titleEnglish: String,
    val category: String, // Poster, Banner, Frame, Card, Flex, Portrait
    val aspectSize: String, // e.g. 1:1, 16:9, A4, 3x2 ft
    val priceEstimate: String,
    val descriptionOdia: String,
    val descriptionEnglish: String,
    val sampleImageTag: String // Icon or style identifier
)

enum class MessageSender {
    USER, AI
}

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isOrderCreated: Boolean = false,
    val orderDetailsSummary: String? = null
)
