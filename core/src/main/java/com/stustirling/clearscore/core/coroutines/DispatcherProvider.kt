package com.stustirling.clearscore.core.coroutines

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class DispatcherProvider @Inject constructor() {
    open fun providesIO() = Dispatchers.IO
}