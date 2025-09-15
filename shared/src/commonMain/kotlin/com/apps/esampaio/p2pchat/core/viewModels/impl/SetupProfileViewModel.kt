package com.apps.esampaio.p2pchat.core.viewModels.impl

import com.apps.esampaio.p2pchat.core.model.User
import com.apps.esampaio.p2pchat.core.viewModels.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow


sealed class SetupProfileViewModelState{
    data class UserCreated(val user: User) : SetupProfileViewModelState()
    object UserNotFound: SetupProfileViewModelState()
}
class SetupProfileViewModel : BaseViewModel() {
    val state =
        MutableStateFlow<SetupProfileViewModelState>(SetupProfileViewModelState.UserNotFound)

    fun start(){
        //TODO load user from storage
    }

    fun createUser(user: User){
        //TODO store user in storage
        state.value = SetupProfileViewModelState.UserCreated(user)

    }
}