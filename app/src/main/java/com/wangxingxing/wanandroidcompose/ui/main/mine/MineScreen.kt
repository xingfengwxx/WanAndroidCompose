package com.wangxingxing.wanandroidcompose.ui.main.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.blankj.utilcode.util.StringUtils
import com.btpj.lib_base.ext.navigate
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.core.navigation.Route
import com.wangxingxing.wanandroidcompose.data.local.UserManager
import com.wangxingxing.wanandroidcompose.ext.navigateNeedLogin
import com.wangxingxing.wanandroidcompose.ui.theme.Blue_4cd2f5
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme

/**
 * author : 王星星
 * date : 2025/4/8 14:24
 * email : 1099420259@qq.com
 * description :
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MineScreen(
    viewModel: MineViewModel = hiltViewModel(),
) {
    val navHostController = LocalNavController.current // 隐式获取 NavHostController

    val uiState by viewModel.uiState.collectAsState()
    val user by App.appViewModel.user.collectAsState()
    val isLogin by remember { mutableStateOf(UserManager.isLogin()) }

    fun onRefresh() {
        viewModel.fetchPoints()
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.showLoading,
        onRefresh = { onRefresh() }
    )

    LaunchedEffect(Unit) {
        if (uiState.data == null || user == null) {
            onRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // pullRefresh必须配合LazyColumn
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(16.dp)
                            .clickable(enabled = !UserManager.isLogin()) {
                                navHostController.navigate(Route.LOGIN)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isLogin) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = Blue_4cd2f5,
                                modifier = Modifier.size(72.dp)
                            )
                        } else {
                            AsyncImage(
                                model = R.mipmap.ic_launcher_round,
                                contentDescription = null,
                                placeholder = painterResource(com.btpj.lib_base.R.drawable.ic_default_img),
                                modifier = Modifier.size(72.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = user?.nickname ?: stringResource(R.string.txt_click_login),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                modifier = Modifier.padding(top = 10.dp),
                                text = "id: ${uiState.data?.userId ?: "-"}  ${stringResource(R.string.txt_rank)}：${uiState.data?.rank ?: "-"}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp
                            )
                        }
                    }

                    ListItemWithIcon(
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_points),
                                contentDescription = "Points",
                                tint = Blue_4cd2f5
                            )
                        },
                        title = stringResource(R.string.integral_rank),
                        value = {
                            Text(
                                text = stringResource(R.string.my_integral),
                                color = LocalContentColor.current.copy(0.5f),
                                fontSize = 14.sp
                            )
                            Text(
                                text = uiState.data?.coinCount?.toString() ?: "-",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 15.sp
                            )
                        }
                    ) {
                        navHostController.navigate(Route.INTEGRAL_RANK)
                    }

                    ListItemWithIcon(
                        icon = {
                            Icon(
                                Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = Blue_4cd2f5
                            )
                        },
                        title = stringResource(R.string.my_collect)
                    ) {
                        navHostController.navigateNeedLogin(Route.MY_COLLECT)
                    }

                    ListItemWithIcon(
                        icon = {
                            Icon(
                                painterResource(R.drawable.ic_article),
                                contentDescription = null,
                                tint = Blue_4cd2f5
                            )
                        },
                        title = stringResource(R.string.my_share_article)
                    ) {
                        navHostController.navigate(Route.SHARE_LIST)
                    }

                    Divider(
                        Modifier.height(5.dp),
                        color = LocalContentColor.current.copy(0.1f)
                    )

                    ListItemWithIcon(
                        icon = {
                            Icon(
                                painterResource(R.drawable.ic_web),
                                contentDescription = null,
                                tint = Blue_4cd2f5
                            )
                        },
                        title = stringResource(R.string.open_web)
                    ) {
                        navHostController.navigate(
                            Route.WEB,
                            bundleOf(
                                Const.ParamKey.WEB_TYPE to Const.WebType.Url(
                                    name = StringUtils.getString(R.string.open_web),
                                    link = Const.Config.URL_WAN_ANDROID
                                )
                            )
                        )
                    }

                    ListItemWithIcon(
                        icon = {
                           Icon(
                               Icons.Default.Settings,
                               contentDescription = "Settings",
                               tint = Blue_4cd2f5
                           )
                        },
                        title = stringResource(R.string.system_settings)
                    ) {
                        navHostController.navigate(Route.SETTING)
                    }
                }
            }
        }

        user?.let {
            PullRefreshIndicator(
                refreshing = uiState.showLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun ListItemWithIcon(
    icon: @Composable () -> Unit,
    title: String,
    value: @Composable (() -> Unit)? = null,
    onItemClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onItemClick.invoke() }
            .padding(horizontal = 10.dp)
            .height(60.dp)
    ) {
        icon()
        Text(text = title, modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        value?.invoke()
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = LocalContentColor.current.copy(alpha = 0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MineScreenPreview() {
    WanAndroidComposeTheme {
        MineScreen()
    }
}