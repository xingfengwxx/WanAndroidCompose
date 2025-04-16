package com.wangxingxing.wanandroidcompose.core.navigation

import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.btpj.lib_base.ext.navigate
import com.wangxingxing.wanandroidcompose.ui.home.HomeScreen
import com.wangxingxing.wanandroidcompose.ui.splash.SplashScreen

/**
 * author : 王星星
 * date : 2025/4/15 17:52
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun NavGraph(paddingValues: PaddingValues) {
    val navHostController = rememberNavController() // 创建 NavHostController

    CompositionLocalProvider(LocalNavController provides navHostController) {
        NavHost(
            navController = navHostController,
            startDestination = Route.SPLASH,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.SPLASH) {
                SplashScreen()
            }
            composable(Route.HOME) {
                HomeScreen(navHostController, "Android")
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided!") // 如果没有提供 NavHostController，抛出错误
}

object Route {
    const val SPLASH = "splash"
    const val MAIN = "main"
    const val HOME = "home"
    const val PROJECT = "project"
    const val SQUARE = "square"
    const val WECHAT = "wechat"
    const val MINE = "mine"
    const val MY_COLLECT = "myCollect"
    const val SETTING = "setting"
    const val WEB = "web"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val SHARE_LIST = "share_list"
    const val ADD_ARTICLE = "add_article"
    const val INTEGRAL_RANK = "integral_rank"
    const val INTEGRAL_RANK_RECORD = "integral_rank_record"
    const val Search = "search"
    const val SEARCH_RECORD = "search_record"
    const val SYSTEM_DETAILS = "system_details"
}

