package io.arjuningole.gpt.services

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import io.arjuningole.gpt.BuildConfig
import kotlinx.coroutines.*

val apiKey = BuildConfig.API_KEY;
val openAI = OpenAI(apiKey)
var story = "Say Hello! Maybe"

suspend fun generateResponse(user_prompt: String, model: String, token: Int, penalty: Double, temp: Double, topP: Double): String{
    val request = CompletionRequest(
        model = ModelId(model),
        maxTokens = token,
        presencePenalty = penalty,
        prompt = user_prompt,
        temperature = temp,
        topP = topP,
    )
    return openAI.completion(request).choices[0].text
}

fun getResponse(user_prompt: String, model: String, token: Int, penalty: Double, temp: Double, topP: Double){
        runBlocking {
            launch {
                story = generateResponse(user_prompt, model, token, penalty, temp, topP).replace("\n", " ")
            }
        }
}