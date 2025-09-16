package com.apps.esampaio.p2pchat.core.platform_spefics

expect class Preferences {
    fun getString(key: String, defaultValue: String? = null): String?

    fun setString(key: String, value: String)
}