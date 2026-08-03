package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val designType: String,
    val sizeDimensions: String,
    val colorPreferences: String,
    val customerNotes: String,
    val customerPhone: String,
    val customerEmail: String,
    val status: String = "Pending",
    val linkStatus: String = "LOCKED",
    val createdAt: Long = System.currentTimeMillis(),
    val aiSummary: String = "",
    val driveLink: String = ""
)
