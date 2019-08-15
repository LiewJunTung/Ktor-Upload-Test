package org.liewjuntung.testktor

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

internal actual val CommonCoroutineContext: CoroutineContext = Dispatchers.Main
