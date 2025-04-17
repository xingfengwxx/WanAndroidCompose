package com.wangxingxing.wanandroidcompose.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.core.navigation.NavGraph
import com.wangxingxing.wanandroidcompose.core.navigation.Route

/**
 * author : 王星星
 * date : 2025/4/16 16:56
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun MainScreen() {
    val navHostController = LocalNavController.current
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // 除去几个navBarItems其他页面不展示BottomBar
            if (destination?.hierarchy?.any { hierarchyItem ->
                    navBarItems?.any { navBarItem -> navBarItem.route == hierarchyItem.route } == true
                } == true) {
                BottomBar(destination)
            }
        }
    ) {
        NavGraph(paddingValues = it)
    }
}

val navBarItems = listOf(
    NavBarItem.Home,
    NavBarItem.Project,
    NavBarItem.Square,
    NavBarItem.Wechat,
    NavBarItem.Mine
)

@Composable
fun BottomBar(navDestination: NavDestination) {
    val navHostController = LocalNavController.current
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        navBarItems.forEach { item ->
            BottomNavigationItem(
                selected = navDestination?.hierarchy?.any { it.route == item.route } == true,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(bottom = 4.dp)
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onPrimary.copy(0.3f),
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        fontSize = 12.sp
                    )
                },
                onClick = {
                    navHostController.navigate(item.route) {
                        // 这里让多个Tab下返回时，不是回到首页，而是直接退出
                        navDestination?.id?.let {
                            popUpTo(it) {
                                // 跳转时保存页面状态
                                saveState = true
                                // 回退到栈顶时，栈顶页面是否也关闭
                                inclusive = true
                            }
                        }
                        // 栈顶复用，避免重复点击同一个导航按钮，回退栈中多次创建实例
                        launchSingleTop = true
                        // 回退时恢复页面状态
                        restoreState = true
                    }
                })
        }
    }
}

sealed class NavBarItem(val label: Int, val icon: Int, val route: String) {
    object Home : NavBarItem(R.string.tab_home, R.drawable.ic_tab_home, Route.HOME)
    object Project : NavBarItem(R.string.tab_project, R.drawable.ic_tab_project, Route.PROJECT)
    object Square : NavBarItem(R.string.tab_square, R.drawable.ic_tab_square, Route.SQUARE)
    object Wechat : NavBarItem(R.string.tab_wechat, R.drawable.ic_tab_wechat, Route.WECHAT)
    object Mine : NavBarItem(R.string.tab_mine, R.drawable.ic_tab_mine, Route.MINE)
}