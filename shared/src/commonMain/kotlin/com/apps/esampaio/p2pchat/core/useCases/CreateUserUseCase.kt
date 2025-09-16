package com.apps.esampaio.p2pchat.core.useCases

import com.apps.esampaio.p2pchat.core.model.User
import com.apps.esampaio.p2pchat.core.repository.UserRepository

class CreateUserUseCase(val userRepository: UserRepository) {

    fun registerUser(userName: String, imageUrl: String?): User {
        userRepository.saveUser(userName, imageUrl)
        return User(userName,imageUrl)
    }
}