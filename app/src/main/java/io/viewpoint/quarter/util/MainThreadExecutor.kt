package io.viewpoint.quarter.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

object MainThreadExecutor : Executor {
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable?) {
        if (Thread.currentThread() == mainHandler.looper.thread) {
            command?.run()
        } else {
            mainHandler.post {
                command?.run()
            }
        }
    }
}