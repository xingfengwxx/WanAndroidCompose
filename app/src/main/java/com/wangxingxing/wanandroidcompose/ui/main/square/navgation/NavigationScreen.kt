package com.wangxingxing.wanandroidcompose.ui.main.square.navgation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.lib_base.utils.CommonUtil
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.Navigation
import com.wangxingxing.wanandroidcompose.data.bean.Structure

/**
 * author : 王星星
 * date : 2025/4/21 17:04
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun NavigationScreen(
    lazyListState: LazyListState,
    viewModel: NavigationViewModel = hiltViewModel(),
    onNavigationClick: (Article) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    val collectData by App.appViewModel.collectEvent.observeAsState()

    // 收藏事件监听
    if (collectData != null) {
        uiState.data?.forEach { navigation ->
            navigation.articles.forEach { article ->
                if (collectData?.id == article.id) {
                    article.collect = collectData?.collect ?: false
                }
            }
        }
    }

    RefreshList(
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 10.dp),
        uiState = uiState,
        lazyListState = lazyListState,
        onRefresh = { viewModel.fetchNavigationList() },
        itemContent = { NavigationItem(it, onNavigationClick) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationItem(
    navigation: Navigation,
    onNavigationClick: (Article) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = navigation.name.toHtml().toString(),
                color = LocalContentColor.current.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            FlowRow(
                modifier = Modifier.padding(top = 10.dp),
            ) {
                navigation.articles.forEach { article ->

                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {  }
                            .background(LocalContentColor.current.copy(0.1f), shape = RoundedCornerShape(8.dp))
                            .padding(vertical = 6.dp, horizontal = 10.dp),
                        text = article.title.toHtml().toString(),
                        color = CommonUtil.randomColor(),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}