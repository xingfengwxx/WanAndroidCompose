package com.wangxingxing.wanandroidcompose.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.btpj.lib_base.base.BaseActivity
import com.btpj.lib_base.utils.ToastUtil
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.AppScreen
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import dagger.hilt.android.AndroidEntryPoint

/**
 * author : 王星星
 * date : 2025/4/8 16:00
 * email : 1099420259@qq.com
 * description :
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var navHostController: NavHostController

    /** 上一次点击返回键的时间 */
    private var lastBackMills: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            CompositionLocalProvider(LocalNavController provides navHostController) {
                AppScreen()
            }
        }

        // 返回处理
        onBackPressedDispatcher.addCallback(this, mBackPressedCallback)
    }

    /** 返回监听回调 */
    private val mBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - lastBackMills > 2000) {
                lastBackMills = System.currentTimeMillis()
                ToastUtil.showShort(
                    this@MainActivity,
                    getString(R.string.toast_double_back_exit)
                )
            } else {
                finish()
            }
        }
    }

}

