package com.apps.esampaio.p2pchat.di

import com.apps.esampaio.p2pchat.core.useCases.CreateUserUseCase
import com.apps.esampaio.p2pchat.core.useCases.GetCurrentUserUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            useCasesModule

        )
    }

val useCasesModule: Module = module {
    single<CreateUserUseCase>{CreateUserUseCase()}
    single<GetCurrentUserUseCase>{GetCurrentUserUseCase()}
}