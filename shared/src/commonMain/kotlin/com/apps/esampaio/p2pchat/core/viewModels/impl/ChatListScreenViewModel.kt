package com.apps.esampaio.p2pchat.core.viewModels.impl

import com.apps.esampaio.p2pchat.core.model.Chat
import com.apps.esampaio.p2pchat.core.repository.ChatRepository
import com.apps.esampaio.p2pchat.core.services.ChatService
import com.apps.esampaio.p2pchat.core.viewModels.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ChatListScreenViewModelState{
    object Loading: ChatListScreenViewModelState()
    object Connecting: ChatListScreenViewModelState()
    data class Connected(val chat: Chat): ChatListScreenViewModelState()
    object NoChatsFound: ChatListScreenViewModelState()
    data class ChatsFound(val chats: List<Chat>): ChatListScreenViewModelState()
}

class ChatListScreenViewModel(
    val chatRepository: ChatRepository,
    val chatService: ChatService
) : BaseViewModel() {
    val state = MutableStateFlow<ChatListScreenViewModelState>(ChatListScreenViewModelState.Loading)
    var chatList = mutableListOf<Chat>()
    fun start(){
//        //todo load chats from storage
//
//        scope.launch {
//            chatList =  listOf(
//                Chat(1, "Alice", "Hey, how are you?", "https://i.pravatar.cc/150?img=1"),
//                Chat(2, "Bob", "Let's catch up tomorrow.", "https://i.pravatar.cc/150?img=2"),
//                Chat(3, "Charlie", "Can you send me the file?", "https://i.pravatar.cc/150?img=3"),
//                Chat(4, "Diana", "Just saw your message!", "https://i.pravatar.cc/150?img=4"),
//                Chat(5, "Evan", "Thanks for the help.", "https://i.pravatar.cc/150?img=5"),
//                Chat(6, "Fiona", "See you at the meeting.", "https://i.pravatar.cc/150?img=6")
//            ).toMutableList()
//            delay(2000)
            state.value = ChatListScreenViewModelState.ChatsFound(chatList)
//
//        }

    }

    fun startNewChat(ip: String){
//        scope.launch {
//            state.update { ChatListScreenViewModelState.Loading }
//            delay(100)
//            chatList.add(Chat(7, "New Chat($ip)", "", ""))
//            state.update { ChatListScreenViewModelState.ChatsFound(chatList) }
//        }
        scope.launch {
            chatService.connectToChat(ip){ connected ->
                if(connected){

                }
            }
        }
    }
}