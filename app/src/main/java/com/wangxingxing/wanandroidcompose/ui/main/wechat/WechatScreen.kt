package com.wangxingxing.wanandroidcompose.ui.main.wechat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/4/8 14:24
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun WechatScreen(
    viewModel: WeChatViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit
) {
    val authorTitleList by viewModel.authorTitleListLiveData.observeAsState()
    val lazyListStates =
        if (authorTitleList.isNullOrEmpty()) emptyList() else (1..authorTitleList!!.size).map {
            rememberLazyListState()
        }

    val pagerState = rememberPagerState { authorTitleList?.size ?: 0 }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        viewModel.fetchAuthorTitleList()
    }

    Column {
        Box {
            TitleBar()
            if (!authorTitleList.isNullOrEmpty()) {
                ScrollableTabRow(
                    containerColor = MaterialTheme.colorScheme.primary,
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 20.dp,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                ) {
                    authorTitleList?.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier
                                .height(52.dp)
                                .padding(horizontal = 10.dp),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = title.name.toHtml().toString(),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = if (authorTitleList.isNullOrEmpty()) Alignment.Center else Alignment.TopCenter
        ) {
            if (authorTitleList.isNullOrEmpty()) {
                CircularProgressIndicator()
            } else {
                HorizontalPager(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.Top,
                    state = pagerState,
                    key = { index -> index }
                ) {
                    if (it == pagerState.currentPage) {
                        WechatChildScreen(
                            authorId = authorTitleList!![it].id,
                            lazyListState = lazyListStates[it],
                            onArticleClick = onArticleClick
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WechatScreenPreview() {
    WanAndroidComposeTheme {
        WechatScreen(viewModel = WeChatViewModel(), onArticleClick = {})
    }
}