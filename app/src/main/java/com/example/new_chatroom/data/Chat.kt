package com.example.chatroom_line.data

import java.sql.Timestamp

data class Chat(
    val name: String?,
    val message: String?,
    val timestamp: Long
)