package com.example.ai_chat_app_with_gemini.ui.vm

import android.graphics.Bitmap
import android.media.metrics.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai_chat_app_with_gemini.data.ChatData
import com.example.ai_chat_app_with_gemini.data.ChatDataModelClass
import com.example.ai_chat_app_with_gemini.data.ChatState
import com.example.ai_chat_app_with_gemini.data.ChatUIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private var   _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()



    fun onEvent(event: ChatUIEvent){
        when(event){
           is ChatUIEvent.SendPrompt -> {
              if (event.newPrompt.isNotEmpty()){
                 addPrompt(newPrompt = event.newPrompt, bitmap = event.bitmap)

                 if (event.bitmap != null){
                     getResponseWithImage(event.newPrompt,event.bitmap)
                 }else{
                     getResponse(event.newPrompt)
                 }
              }
           }
           is ChatUIEvent.UpdatePrompt -> {
               _chatState.update {
                   it.copy(prompt = event.newPrompt)
               }
           }
        }
    }

    private fun addPrompt(newPrompt : String, bitmap: Bitmap?){
        _chatState.update { it.copy(
            chatList = it.chatList.toMutableList().apply {
                add(0, ChatDataModelClass(prompt = newPrompt, bitmap = bitmap, isFromUser = true))
            },
            prompt = "",
            bitmap = null
        ) }
    }

    private fun getResponse(prompt: String){

        viewModelScope.launch {
            val chat = ChatData.getResponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0,chat)
                    }
                )
            }
        }
    }

    private fun getResponseWithImage(prompt: String,bitmap: Bitmap){

        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt,bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0,chat)
                    }
                )
            }
        }
    }

}