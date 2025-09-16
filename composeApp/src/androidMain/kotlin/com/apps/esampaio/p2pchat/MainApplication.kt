package com.apps.esampaio.p2pchat

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.apps.esampaio.p2pchat.core.viewModels.impl.ChatListScreenViewModel
import com.apps.esampaio.p2pchat.core.viewModels.impl.SetupProfileViewModel
import com.apps.esampaio.p2pchat.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

class MainApplication : Application() {
    fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE


    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                viewModelModule
            )
        }
    }

    val viewModelModule = module {
        viewModelOf(::SetupProfileViewModel)
        viewModelOf(::ChatListScreenViewModel)
    }
}