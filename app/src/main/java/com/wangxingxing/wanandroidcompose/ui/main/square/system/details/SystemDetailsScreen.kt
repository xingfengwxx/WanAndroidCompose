package com.wangxingxing.wanandroidcompose.ui.main.square.system.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.Structure
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/4/25 16:08
 * email : 1099420259@qq.com
 * description : 体系列表
 */
@Composable
fun SystemDetailsScreen(
    structure: Structure,
    pageIndex: Int,
    onArticleClick: (Article) -> Unit
) {
    val navHostController = LocalNavController.current

    val titleList = structure.children.map { it.name }
    val lazyListStates = (1 .. titleList.size).map { rememberLazyListState() }

    val pagerState = rememberPagerState(pageIndex) { structure.children.size }
    val coroutineScope = rememberCoroutineScope()

    Column() {
        TitleBar(
            title = structure.name
        ) { navHostController.popBackStack() }

        ScrollableTabRow(
            containerColor = MaterialTheme.colorScheme.primary,
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 10.dp,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]).padding(bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
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
                    },
                    text = {
                        androidx.compose.material3.Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onPrimary
                       )
                    }
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            state = pagerState,
            key = {index -> index }
        ) {
            if (it == pagerState.currentPage) {
                SystemDetailsChildScreen(
                    lazyListState = lazyListStates[it],
                    categoryId = structure.children[it].id,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}