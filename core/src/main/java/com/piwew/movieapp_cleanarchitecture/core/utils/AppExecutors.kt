@file:Suppress("unused")

package com.piwew.movieapp_cleanarchitecture.core.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor,
) {

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    fun diskIO(): Executor = diskIO

    fun networkIO(): Executor = networkIO // if necessary

    fun mainThread(): Executor = mainThread // if necessary

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            if (command != null) {
                mainThreadHandler.post(command)
            }
        }
    }

    companion object {
        private const val THREAD_COUNT = 3
    }
}