package com.wangxingxing.wanandroidcompose.ui.search.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleItem
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleRefreshList

/**
 * author : 王星星
 * date : 2025/4/27 14:29
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SearchResultScreen(
    searchKey: String,
    viewModel: SearchResultViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit,
) {
    val navHostController = LocalNavController.current

    Column {
        TitleBar(
            title = searchKey,
            onBackClick = {
                navHostController.popBackStack()
            }
        )

        ArticleRefreshList(
            viewModel = viewModel,
            lazyListState = rememberLazyListState(),
            onRefresh = { viewModel.fetchSearchResultPageList(searchKey) },
            onLoadMore = { viewModel.fetchSearchResultPageList(searchKey, false) }
        ) {
            ArticleItem(
                article = it,
                articleViewModel = viewModel,
                onArticleClick = onArticleClick
            )
        }
    }
}