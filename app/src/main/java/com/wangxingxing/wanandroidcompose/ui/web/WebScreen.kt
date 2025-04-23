package com.wangxingxing.wanandroidcompose.ui.web

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankj.utilcode.util.StringUtils
import com.btpj.lib_base.ui.widgets.TitleBar
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.Const.WebType
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.data.bean.CollectData
import com.wangxingxing.wanandroidcompose.data.local.UserManager
import com.wangxingxing.wanandroidcompose.ui.theme.Green_19791D

/**
 * author : 王星星
 * date : 2025/4/23 15:23
 * email : 1099420259@qq.com
 * description :
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(
    viewModel: WebViewModel = hiltViewModel(),
    webType: WebType,
    collectedFlag: String? = null // null表示未知，"0"表示未收藏，"1"表示已收藏
) {
    val navHostController = LocalNavController.current
    val context = LocalContext.current

    var pageTitle by remember { mutableStateOf(StringUtils.getString(R.string.txt_loading)) }
    var expanded by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0.1f) }
    val webViewState = rememberWebViewState(webType.link)
    val webViewNavigator = rememberWebViewNavigator()
    var collected by remember { mutableStateOf(collectedFlag == "1") }
    val collectUrlList by viewModel.collectUrlList.collectAsState()

    LaunchedEffect(webType.link) {
        if (collectedFlag == null && UserManager.isLogin()) {
            viewModel.fetchCollectUrlList()
        }
    }

    Column {
        TitleBar(
            title = pageTitle,
            menu = {
                if (collectedFlag == null) {
                    // 遍历用户收藏的网址，手动设置为已收藏
                    collectUrlList.find { it.link == webType.link }?.let {
                        collected = true
                    }
                }

                Icon(
                    imageVector = if (collected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Collect",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable {
                            when (webType) {
                                is WebType.OnSiteArticle -> {
                                    if (collected) {
                                        viewModel.unCollectArticle(
                                            webType.articleId,
                                            successCallBack = {
                                                collected = false
                                                App.appViewModel.emitCollectEvent(
                                                    CollectData(
                                                        webType.articleId,
                                                        webType.link,
                                                        false
                                                    )
                                                )
                                            }
                                        )
                                    } else {
                                        viewModel.collectArticle(
                                            webType.articleId,
                                            successCallBack = {
                                                collected = true
                                                App.appViewModel.emitCollectEvent(
                                                    CollectData(
                                                        webType.articleId,
                                                        webType.link,
                                                        true
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }

                                is WebType.OutSiteArticle -> {
                                    if (collected) {
                                        viewModel.unCollectArticle(webType.articleId) {
                                            collected = false
                                        }
                                    } else {
                                        viewModel.collectOutSiteArticle(
                                            webType.title,
                                            webType.author,
                                            webType.link
                                        ) {
                                            collected = true
                                        }
                                    }
                                }

                                is WebType.Url -> {
                                    if (collected) {
                                        webType.id?.let {
                                            viewModel.unCollectUrl(it) {
                                                collected = false
                                            }
                                        }
                                     } else {
                                         viewModel.collectUrl(webType.name, webType.link) {
                                             collected = true
                                         }
                                    }
                                }
                            }
                        }
                )

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "MoreVert",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            expanded = true
                        }
                )

                DropdownMenu(
                    expanded = expanded,
                    offset = DpOffset(0.dp, 16.dp),
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.txt_share)) },
                        onClick = {
                            context.startActivity(Intent.createChooser(Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "${pageTitle}:${webType.link}")
                                type = "text/plain"
                            }, StringUtils.getString(R.string.txt_share_to)))
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(stringResource(R.string.refresh))
                        },
                        onClick = {
                            webViewNavigator.reload()
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(stringResource(R.string.txt_open_with_browser))
                        },
                        onClick = {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webType.link)))
                            expanded = false
                        }
                    )
                }
            }
        ) {
            navHostController.popBackStack()
        }

        if (progress != 1.0f) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = Green_19791D
            )
        }

        WebView(
            state = webViewState,
            navigator = webViewNavigator,
            onCreated = {
                it.settings.run { javaScriptEnabled = true }
            },
            client = remember { object : AccompanistWebViewClient() {

            } },
            chromeClient = remember { object : AccompanistWebChromeClient() {
                override fun onReceivedTitle(view: WebView, title: String?) {
                    super.onReceivedTitle(view, title)
                    pageTitle = title ?: ""
                }

                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    progress = newProgress / 100f
                }
            } }
        )
    }
}