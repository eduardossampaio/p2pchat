package com.apps.esampaio.p2pchat.core.viewModels

import kotlinx.coroutines.CoroutineScope

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class BaseViewModel(val mainScope: CoroutineScope) {
    actual var scope: CoroutineScope = mainScope
}