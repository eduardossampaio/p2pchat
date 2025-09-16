package com.apps.esampaio.p2pchat.core.viewModels.impl

import com.apps.esampaio.p2pchat.core.model.Author
import com.apps.esampaio.p2pchat.core.model.Chat
import com.apps.esampaio.p2pchat.core.model.Message
import com.apps.esampaio.p2pchat.core.viewModels.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

sealed class ChatViewModelState{
    object Loading: ChatViewModelState()

    data class MessagesLoaded(val messages: List<Message>): ChatViewModelState()

    data class OnNewMessage(val messages: List<Message>, val newMessage: Message): ChatViewModelState()
}
class ChatViewModel : BaseViewModel() {
    val state = MutableStateFlow<ChatViewModelState>(ChatViewModelState.Loading)

    var allMessages: List<Message> = emptyList()
    fun start(chat: Chat){
        scope.launch {
            state.update { ChatViewModelState.Loading }
            delay(100)

            state.update {
                ChatViewModelState.MessagesLoaded(loadMessages())
            }
        }
    }


    private fun loadMessages():List<Message>{
        return listOf(
            Message(1, "Olá! Tudo bem?", Author.OTHER, "16:00"),
            Message(2, "Tudo ótimo, e com você?", Author.ME, "16:01"),
            Message(3, "Estou bem também. O que está fazendo?", Author.OTHER, "16:01"),
            Message(4, "Estudando Jetpack Compose. É incrível!", Author.ME, "16:02"),
            Message(5, "Que legal! Também quero aprender.", Author.OTHER, "16:03")
        )
    }

     fun sendMessage(message: String){
        scope.launch {
            state.update {
                ChatViewModelState.OnNewMessage(
                    allMessages,
                    Message(Random.nextInt(), message, Author.ME, "16:04")
                )
            }
        }
    }


}