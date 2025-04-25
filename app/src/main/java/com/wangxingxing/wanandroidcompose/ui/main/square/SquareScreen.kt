package com.wangxingxing.wanandroidcompose.ui.main.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blankj.utilcode.util.StringUtils
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.lib_base.utils.CommonUtil
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.core.navigation.Route
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.Structure
import com.wangxingxing.wanandroidcompose.ui.main.square.ask.AskScreen
import com.wangxingxing.wanandroidcompose.ui.main.square.child.SquareChildScreen
import com.wangxingxing.wanandroidcompose.ui.main.square.navgation.NavigationScreen
import com.wangxingxing.wanandroidcompose.ui.main.square.system.SystemScreen
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/4/8 14:24
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SquareScreen(
    onStructureClick: ((Structure, Int) -> Unit)? = null,
    onNavigationClick: ((Article) -> Unit)? = null,
    onArticleClick: (Article) -> Unit
) {
    val navHostController = LocalNavController.current

    val titleList = remember { StringUtils.getStringArray(R.array.square_titles) }

    val pagerState = rememberPagerState { titleList.size }
    val coroutineScope = rememberCoroutineScope()

    val lazyListStates = (1..titleList.size).map { rememberLazyListState() }

    Column {
        Box {
            TitleBar(menu = {
                if (pagerState.currentPage == 0) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.clickable {
                            navHostController.navigate(Route.ADD_ARTICLE)
                        }
                    )
                }
            })
            ScrollableTabRow(
                modifier = Modifier.padding(horizontal = 40.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 10.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }) {
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
                        Text(text = title, color = MaterialTheme.colorScheme.onPrimary)
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
                key = { index -> index}
            ) {
                if (it == pagerState.currentPage) {
                    when (it) {
                        0 -> SquareChildScreen(
                            lazyListState = lazyListStates[0],
                            onArticleClick = onArticleClick
                        )

                        1 -> AskScreen(
                            lazyListState = lazyListStates[1],
                            onArticleClick = onArticleClick
                        )

                        2 -> SystemScreen(
                            lazyListState = lazyListStates[2],
                            onStructureClick = { structure: Structure, pageIndex: Int ->
                                onStructureClick?.invoke(structure, pageIndex)
                            }
                        )

                        3 -> NavigationScreen(
                            lazyListState = lazyListStates[3],
                            onNavigationClick = { article ->
                                onNavigationClick?.invoke(article)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StructureItem(
    structure: Structure,
    onStructureClick: ((Structure, Int) -> Unit)
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onStructureClick(structure, 0) }
        ) {
            Text(
                text = structure.name.toHtml().toString(),
                color = LocalContentColor.current.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            FlowRow(
                modifier = Modifier.padding(top = 10.dp),
            ) {
                structure.children.forEachIndexed { index, classify ->

                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onStructureClick(structure, index) }
                            .background(LocalContentColor.current.copy(0.1f), shape = RoundedCornerShape(8.dp))
                            .padding(vertical = 6.dp, horizontal = 10.dp),
                        text = classify.name.toHtml().toString(),
                        color = CommonUtil.randomColor(),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
