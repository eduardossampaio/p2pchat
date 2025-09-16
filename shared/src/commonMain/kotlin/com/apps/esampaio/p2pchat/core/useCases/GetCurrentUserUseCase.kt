package com.apps.esampaio.p2pchat.core.useCases

import com.apps.esampaio.p2pchat.core.model.User
import com.apps.esampaio.p2pchat.core.repository.UserRepository

class GetCurrentUserUseCase(val userRepository: UserRepository) {
    fun getCurrentUser(): User? {
        return userRepository.getUser()
    }
}