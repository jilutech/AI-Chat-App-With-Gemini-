package com.example.ai_chat_app_with_gemini.data

import android.graphics.Bitmap

data class ChatState (
    val chatList : MutableList<ChatDataModelClass> = mutableListOf(),
    val prompt : String = "",
    val bitmap: Bitmap ? = null
)