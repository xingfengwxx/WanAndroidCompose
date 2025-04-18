package com.wangxingxing.wanandroidcompose

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.btpj.lib_base.BaseApp
import com.btpj.lib_base.BuildConfig
import com.tencent.bugly.Bugly
import com.wangxingxing.wanandroidcompose.core.base.AppViewModel
import dagger.hilt.android.HiltAndroidApp

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
@HiltAndroidApp
class App : BaseApp() {

    companion object {
        const val TAG = "WanAndroidCompose"

        lateinit var appViewModel: AppViewModel

        lateinit var instance: App
            private set

        fun isDebug() = BuildConfig.DEBUG
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]

        // bugly初始化
        Bugly.init(applicationContext, Const.BUGLY_APP_ID, false)

        initLog()
    }

    private fun initLog() {
        Utils.init(instance)
        LogUtils.getConfig()
            .setLogSwitch(isDebug())
            .setGlobalTag(TAG)
            .setBorderSwitch(true)
    }


}