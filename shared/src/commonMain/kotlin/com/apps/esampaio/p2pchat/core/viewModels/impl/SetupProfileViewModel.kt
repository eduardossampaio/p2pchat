package com.apps.esampaio.p2pchat.core.viewModels.impl

import com.apps.esampaio.p2pchat.core.model.User
import com.apps.esampaio.p2pchat.core.useCases.CreateUserUseCase
import com.apps.esampaio.p2pchat.core.useCases.GetCurrentUserUseCase
import com.apps.esampaio.p2pchat.core.viewModels.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


sealed class SetupProfileViewModelState{
    data class UserCreated(val user: User) : SetupProfileViewModelState()
    object UserNotFound: SetupProfileViewModelState()
}
class SetupProfileViewModel(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val createUserUseCase: CreateUserUseCase
) : BaseViewModel() {
    val state = MutableStateFlow<SetupProfileViewModelState>(SetupProfileViewModelState.UserNotFound)

    fun start(){
       scope.launch {
           val user = getCurrentUserUseCase.getCurrentUser()
           if(user != null){
               state.value = SetupProfileViewModelState.UserCreated(user)
           }
       }
    }

    fun createUser(user: User){
        val user = createUserUseCase.registerUser(user.userName, user.imageUrl)
        if(user != null) {
            state.value = SetupProfileViewModelState.UserCreated(user)
        }

    }
}