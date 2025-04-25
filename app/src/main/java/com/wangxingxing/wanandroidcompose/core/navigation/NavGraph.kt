package com.wangxingxing.wanandroidcompose.core.navigation

import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.btpj.lib_base.ext.navigate
import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.Structure
import com.wangxingxing.wanandroidcompose.ui.collect.CollectScreen
import com.wangxingxing.wanandroidcompose.ui.integral.rank.IntegralRankScreen
import com.wangxingxing.wanandroidcompose.ui.integral.record.IntegralRecordScreen
import com.wangxingxing.wanandroidcompose.ui.login.LoginScreen
import com.wangxingxing.wanandroidcompose.ui.main.MainScreen
import com.wangxingxing.wanandroidcompose.ui.main.home.HomeScreen
import com.wangxingxing.wanandroidcompose.ui.main.mine.MineScreen
import com.wangxingxing.wanandroidcompose.ui.main.project.ProjectScreen
import com.wangxingxing.wanandroidcompose.ui.main.project.SquareScreen
import com.wangxingxing.wanandroidcompose.ui.main.square.system.details.SystemDetailsScreen
import com.wangxingxing.wanandroidcompose.ui.main.wechat.WechatScreen
import com.wangxingxing.wanandroidcompose.ui.search.SearchScreen
import com.wangxingxing.wanandroidcompose.ui.setting.SettingScreen
import com.wangxingxing.wanandroidcompose.ui.share.add.AddArticleScreen
import com.wangxingxing.wanandroidcompose.ui.share.list.MyArticleScreen
import com.wangxingxing.wanandroidcompose.ui.web.WebScreen

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
                    navHostController.navigate(Route.WEB, bundleOf(
                        Const.ParamKey.WEB_TYPE to Const.WebType.Url(name = it.title, link = it.url)
                    ))
                }, onArticleClick = {
                    navToWeb(navHostController, it)
                }
            )
        }
        composable(Route.PROJECT) {
            ProjectScreen {
                navToWeb(navHostController, it)
            }
        }
        composable(Route.SQUARE) {
            SquareScreen(
                onStructureClick = { structure, pageIndex ->
                    navHostController.navigate(
                        Route.SYSTEM_DETAILS,
                        bundleOf(
                            Const.ParamKey.STRUCTURE to structure,
                            Const.ParamKey.PAGE_INDEX to pageIndex
                        )
                    )
                },
                onNavigationClick = {
                    navToWeb(navHostController, it)
                },
                onArticleClick = {
                    navToWeb(navHostController, it)
                }
            )
        }
        composable(Route.WECHAT) {
            WechatScreen {
                navToWeb(navHostController, it)
            }
        }
        composable(Route.MINE) {
            MineScreen()
        }
        composable(Route.LOGIN) {
            LoginScreen()
        }
        composable(Route.SETTING) {
            SettingScreen()
        }
        composable(Route.SEARCH) {
            SearchScreen()
        }
        composable(Route.WEB) {
            it.arguments?.apply {
                val webType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getParcelable(Const.ParamKey.WEB_TYPE, Const.WebType::class.java)
                } else {
                    getParcelable(Const.ParamKey.WEB_TYPE)
                }
                val collectedFlag = getString(Const.ParamKey.COLLECTED_FLAG)
                webType?.let { type ->
                    WebScreen(
                        webType = type,
                        collectedFlag = collectedFlag
                    )
                }
            }
        }
        composable(Route.MY_COLLECT) {
            CollectScreen(
                onCollectUrlClick = {
                    navHostController.navigate(Route.WEB, bundleOf(
                        Const.ParamKey.WEB_TYPE to Const.WebType.Url(it.id, it.name, it.link),
                        Const.ParamKey.COLLECTED_FLAG to "1"
                    ))
                },
                onArticleClick = {
                    navHostController.navigate(Route.WEB, bundleOf(
                        Const.ParamKey.WEB_TYPE to Const.WebType.OnSiteArticle(it.id, it.link),
                        Const.ParamKey.COLLECTED_FLAG to "1"
                    ))
                }
            )
        }
        composable(Route.INTEGRAL_RANK) {
            IntegralRankScreen()
        }
        composable(Route.INTEGRAL_RANK_RECORD) {
            IntegralRecordScreen()
        }
        composable(Route.SHARE_LIST) {
            MyArticleScreen() {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf(
                        Const.ParamKey.WEB_TYPE to Const.WebType.OnSiteArticle(it.id, it.link),
                        Const.ParamKey.COLLECTED_FLAG to "1"
                    )
                )
            }
        }
        composable(Route.ADD_ARTICLE) {
            AddArticleScreen()
        }
        composable(Route.SYSTEM_DETAILS) {
            it.arguments?.apply {
                val structure = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getParcelable(Const.ParamKey.STRUCTURE, Structure::class.java)
                } else {
                    getParcelable(Const.ParamKey.STRUCTURE)
                }
                val pageIndex = getInt(Const.ParamKey.PAGE_INDEX)
                structure?.let { structure ->
                    SystemDetailsScreen(structure = structure, pageIndex = pageIndex) { article ->
                        navToWeb(navHostController, article)
                    }
                }
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided!") // 如果没有提供 NavHostController，抛出错误
}

private fun navToWeb(navHostController: NavHostController, it: Article) {
    navHostController.navigate(
        Route.WEB,
        bundleOf(
            Const.ParamKey.WEB_TYPE to Const.WebType.OnSiteArticle(it.id, it.link),
            Const.ParamKey.COLLECTED_FLAG to if (it.collect) "1" else "0"
        )
    )
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

