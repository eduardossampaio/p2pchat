@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.apps.esampaio.p2pchat.core.viewModels

import kotlinx.coroutines.CoroutineScope

expect open class BaseViewModel() {
    var scope: CoroutineScope
}