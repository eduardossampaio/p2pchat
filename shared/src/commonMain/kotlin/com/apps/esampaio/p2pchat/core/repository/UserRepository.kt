package com.apps.esampaio.p2pchat.core.repository

import com.apps.esampaio.p2pchat.core.model.User
import com.apps.esampaio.p2pchat.core.platform_spefics.Preferences
import kotlinx.serialization.json.Json

class UserRepository(val preferences: Preferences) {

    companion object{
        private const val USER_KEY = "current_user"
    }
    fun saveUser(userName: String, imageUrl: String?) {
        val user = User(userName, imageUrl)
        val string = Json.encodeToString(user)
        preferences.setString(USER_KEY, string)

    }

    fun getUser(): User? {
       val serializedUser =  preferences.getString(USER_KEY)
        try{
            return Json.decodeFromString(serializedUser ?: "")
        }catch (e: Exception){
            return null
        }
    }
}