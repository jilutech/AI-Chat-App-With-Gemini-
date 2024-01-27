package com.example.ai_chat_app_with_gemini.data

import android.graphics.Bitmap

sealed class ChatUIEvent {

    data class UpdatePrompt(val newPrompt : String) : ChatUIEvent()
    data class SendPrompt(val newPrompt: String,val bitmap: Bitmap?) : ChatUIEvent()


}