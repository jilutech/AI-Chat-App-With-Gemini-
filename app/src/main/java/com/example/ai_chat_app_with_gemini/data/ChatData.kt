package com.example.ai_chat_app_with_gemini.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

object ChatData {

    val api_Key = "AIzaSyDGp91HAtmIqMUBJqTFKEXaCtGeVJ5-eEU"

    suspend fun getResponse(prompt : String) : ChatDataModelClass{
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro" , apiKey = api_Key
        )

        try {
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }

            return ChatDataModelClass(
                prompt = response.text ?: "Error",
                bitmap = null,
                isFromUser = false
            )
        }catch (e : Exception){
            return ChatDataModelClass(
                prompt = e.message ?: "Error",
                bitmap = null,
                isFromUser = false
            )
        }
    }
    suspend fun getResponseWithImage(prompt : String,bitmap : Bitmap) : ChatDataModelClass{
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision" , apiKey = api_Key
        )

        try {

            val inputContent = content {
                image(bitmap)
                text(prompt)
            }

            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(inputContent)
            }

            return ChatDataModelClass(
                prompt = response.text ?: "Error",
                bitmap = null,
                isFromUser = false
            )
        }catch (e : Exception){
            return ChatDataModelClass(
                prompt = e.message ?: "Error",
                bitmap = null,
                isFromUser = false
            )
        }
    }

}