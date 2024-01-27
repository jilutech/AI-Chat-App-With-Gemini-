package com.example.ai_chat_app_with_gemini.data

import android.graphics.Bitmap

data class ChatDataModelClass(
    val prompt : String,
    val bitmap: Bitmap ?,
    val isFromUser : Boolean
)
