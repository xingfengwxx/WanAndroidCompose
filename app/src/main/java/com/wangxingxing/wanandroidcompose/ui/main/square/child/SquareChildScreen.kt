package com.wangxingxing.wanandroidcompose.ui.main.square.child

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleItem
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleRefreshList

/**
 * author : 王星星
 * date : 2025/4/21 16:02
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SquareChildScreen(
    viewModel: SquareChildViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    onArticleClick: (Article) -> Unit
) {
    val needRefresh by App.appViewModel.shareArticleEvent.observeAsState()

    LaunchedEffect(key1 = Unit) {
        if (needRefresh == true) {
            viewModel.fetchSquarePageList()
        }
    }

    ArticleRefreshList(
        viewModel = viewModel,
        lazyListState = lazyListState,
        onRefresh = {
            viewModel.fetchSquarePageList()
        },
        onLoadMore = {
            viewModel.fetchSquarePageList(false)
        },
        itemContent = {
            ArticleItem(article = it, articleViewModel = viewModel, onArticleClick = onArticleClick)
        }
    )
}