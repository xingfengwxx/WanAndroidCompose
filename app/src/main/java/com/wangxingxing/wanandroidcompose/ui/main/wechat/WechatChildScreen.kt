package com.wangxingxing.wanandroidcompose.ui.main.wechat

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleItem
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleRefreshList

/**
 * author : 王星星
 * date : 2025/4/22 9:32
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun WechatChildScreen(
    authorId: Int,
    lazyListState: LazyListState,
    // authorId作为key，确保每个作者的页面使用不同的ViewModel
    viewModel: WechatChildViewModel = hiltViewModel(key = "$authorId"),
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = viewModel,
        lazyListState = lazyListState,
        onRefresh = {
            viewModel.fetchAuthorPageList(authorId)
        },
        onLoadMore = {
            viewModel.fetchAuthorPageList(authorId, false)
        },
    ) {
        ArticleItem(it, viewModel, onArticleClick)
    }
}