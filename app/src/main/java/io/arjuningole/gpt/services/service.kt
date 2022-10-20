package io.arjuningole.gpt.services

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.*

val apiKey = "API_KEY"
val openAI = OpenAI(apiKey)
var story = ""

suspend fun generateResponse(user_prompt: String, category: String): String{
    val request = CompletionRequest(
        model = ModelId("davinci-instruct-beta-v3"),
        maxTokens = 2000,
        presencePenalty = 1.0,
        n = 1,
        prompt = "Generate a very long $category story on the following prompt, $user_prompt",
        temperature = 0.0,
        topP = 1.0,
    )
    return openAI.completion(request).choices[0].text
}

fun getResponse(user_prompt: String, category: String){
    runBlocking {
        launch {
            story = generateResponse(user_prompt, category).replace("\n", " ")
        }
    }
}