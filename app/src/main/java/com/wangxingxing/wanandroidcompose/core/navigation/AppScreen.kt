package com.wangxingxing.wanandroidcompose.core.navigation

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.wangxingxing.wanandroidcompose.ext.decorFitsSystemWindows
import com.wangxingxing.wanandroidcompose.ui.main.MainScreen
import com.wangxingxing.wanandroidcompose.ui.splash.SplashScreen
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme

/**
 * author : 王星星
 * date : 2025/4/14 16:56
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun AppScreen(
    appScreenViewModel: AppScreenViewModel = hiltViewModel()
) {
    val window = LocalActivity.current?.window
    val showSplash by appScreenViewModel.isFirstUse.collectAsState()

    WanAndroidComposeTheme(isStatusBarTransparent = showSplash) {
        if (showSplash) {
            // 沉浸式状态栏
            window?.decorFitsSystemWindows(false)
            // 显示闪屏页
            SplashScreen {
                appScreenViewModel.emitFirstUse(false)
            }
        } else {
            // 取消沉浸式状态栏
            window?.decorFitsSystemWindows(true)
            MainScreen()
        }
    }
}