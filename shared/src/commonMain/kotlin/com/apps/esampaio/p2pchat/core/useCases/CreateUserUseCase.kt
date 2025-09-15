package com.apps.esampaio.p2pchat.core.useCases

import com.apps.esampaio.p2pchat.core.model.User

class CreateUserUseCase {

    fun registerUser(userName: String, imageUrl: String?): User {
        //todo implement logic
        return User(userName,imageUrl)
    }
}