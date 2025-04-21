package com.wangxingxing.wanandroidcompose.ui.main.square.ask

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleItem
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleRefreshList

/**
 * author : 王星星
 * date : 2025/4/21 16:19
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun AskScreen(
    viewModel: AskViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = viewModel,
        lazyListState = lazyListState,
        onRefresh = {
            viewModel.fetchAskPageList()
        },
        onLoadMore = {
            viewModel.fetchAskPageList(false)
        }
    ) {
        ArticleItem(
            article = it,
            articleViewModel = viewModel,
            onArticleClick = onArticleClick
        )
    }
}