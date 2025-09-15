package com.apps.esampaio.p2pchat.core.viewModels.impl

import com.apps.esampaio.p2pchat.core.model.Chat
import com.apps.esampaio.p2pchat.core.model.User
import com.apps.esampaio.p2pchat.core.viewModels.BaseViewModel

sealed class ChatScreenViewModelState{
    object Loading : ChatScreenViewModelState()
    object NoChatsFound: ChatScreenViewModelState()
    data class ChatsFound(val chats: List<Chat>): ChatScreenViewModelState()
}

class ChatScreenViewModel : BaseViewModel() {
}