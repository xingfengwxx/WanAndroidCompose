package com.wangxingxing.wanandroidcompose.ui.main.square.system.details

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleItem
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleRefreshList

/**
 * author : 王星星
 * date : 2025/4/25 16:40
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SystemDetailsChildScreen(
    categoryId: Int,
    lazyListState: LazyListState,
    // 因为categoryId是动态的，所以需要添加key来确保每个categoryId对应一个ViewModel
    viewModel: SystemDetailsViewModel = hiltViewModel(key = "$categoryId"),
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = viewModel,
        lazyListState = lazyListState,
        onRefresh = { viewModel.fetchSystemDetailsPageList(categoryId) },
        onLoadMore = { viewModel.fetchSystemDetailsPageList(categoryId, false)}
    ) {
        ArticleItem(
            article = it,
            articleViewModel = viewModel,
            onArticleClick = onArticleClick
        )
    }
}