package com.hejulian.testdemo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 全局 Application 类，用于提供全局的 Context 实例。
 */
class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
