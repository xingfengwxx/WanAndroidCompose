package com.wangxingxing.wanandroidcompose.core.navigation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wangxingxing.wanandroidcompose.ui.splash.SplashScreen

/**
 * author : 王星星
 * date : 2025/4/14 16:56
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun AppScreen(
    navHostController: NavHostController,
    appScreenViewModel: AppScreenViewModel = hiltViewModel()
) {
    val window = LocalActivity.current?.window
    val showSplash = appScreenViewModel.isFirstUse.collectAsState()

    SplashScreen()
}