package com.wangxingxing.wanandroidcompose.ui.collect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blankj.utilcode.util.StringUtils
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.CollectUrl
import com.wangxingxing.wanandroidcompose.ui.collect.article.CollectArticleScreen
import com.wangxingxing.wanandroidcompose.ui.collect.url.CollectUrlScreen
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/4/23 17:34
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun CollectScreen(
    onCollectUrlClick: (CollectUrl) -> Unit = {},
    onArticleClick: (Article) -> Unit = {}
) {
    val navHostController = LocalNavController.current

    val titleList = StringUtils.getStringArray(R.array.collect_titles)

    val pagerState = rememberPagerState { titleList.size }
    val coroutineScope = rememberCoroutineScope()

    val lazyListStates = (1..titleList.size).map { rememberLazyListState() }

    Column {
        Box {
            TitleBar { navHostController.popBackStack() }

            TabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                selectedTabIndex = pagerState.currentPage,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            ) {
                titleList.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier
                            .height(52.dp)
                            .padding(horizontal = 10.dp),
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            HorizontalPager(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.Top,
                state = pagerState,
                key = { index -> index }
            ) {
                if (it == pagerState.currentPage) {
                    when (it) {
                        0 -> CollectArticleScreen(
                            lazyListState = lazyListStates[0],
                            onArticleClick = onArticleClick
                        )

                        1 -> CollectUrlScreen(
                            lazyListState = lazyListStates[1],
                            onUrlClick = onCollectUrlClick
                        )
                    }
                }
            }
        }
    }
}