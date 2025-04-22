package com.wangxingxing.wanandroidcompose.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wangxingxing.wanandroidcompose.ui.main.MainScreen
import com.wangxingxing.wanandroidcompose.ui.main.home.HomeScreen
import com.wangxingxing.wanandroidcompose.ui.main.project.MineScreen
import com.wangxingxing.wanandroidcompose.ui.main.project.ProjectScreen
import com.wangxingxing.wanandroidcompose.ui.main.project.SquareScreen
import com.wangxingxing.wanandroidcompose.ui.main.wechat.WechatScreen
import com.wangxingxing.wanandroidcompose.ui.search.SearchScreen

/**
 * author : 王星星
 * date : 2025/4/15 17:52
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun NavGraph(paddingValues: PaddingValues) {
    val navHostController = LocalNavController.current // 隐式获取 NavHostController
    NavHost(
        navController = navHostController,
        startDestination = Route.HOME,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Route.MAIN) {
            MainScreen()
        }
        composable(Route.HOME) {
            HomeScreen(
                onSearch = {
                    navHostController.navigate(Route.SEARCH)
                },
                onBannerClick = {

                }, onArticleClick = {

                }
            )
        }
        composable(Route.PROJECT) {
            ProjectScreen {
            }
        }
        composable(Route.SQUARE) {
            SquareScreen(
                onStructureClick = { structure, position ->
                    // TODO:  
                },
                onNavigationClick = {
                    // TODO:  
                },
                onArticleClick = {
                    // TODO:  
                }
            )
        }
        composable(Route.WECHAT) {
            WechatScreen {
                // TODO: to web
            }
        }
        composable(Route.MINE) {
            MineScreen()
        }
        composable(Route.SEARCH) {
            SearchScreen()
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
    const val SEARCH = "search"
    const val SEARCH_RECORD = "search_record"
    const val SYSTEM_DETAILS = "system_details"
}

