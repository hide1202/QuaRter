package io.viewpoint.quarter.extensions

import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import kotlin.coroutines.resume

suspend fun <T> ListenableFuture<T>.await(executor: Executor): T = suspendCancellableCoroutine {
    addListener({
        if (it.isActive) {
            it.resume(this.get())
        }
    }, executor)
}