package com.wangxingxing.wanandroidcompose.ui.main.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.Banner
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.Banner
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleItem
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleRefreshList
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * author : 王星星
 * date : 2025/4/8 14:24
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onSearch: () -> Unit,
    onBannerClick: (Banner) -> Unit = {},
    onArticleClick: (Article) -> Unit
) {
    val banners by homeViewModel.bannerList.collectAsState()
    Column {
        TitleBar(title = stringResource(R.string.tab_home), menu = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onSearch() }
            )
        })

        ArticleRefreshList(
            viewModel = homeViewModel,
            lazyListState = rememberLazyListState(),
            onRefresh = {
                homeViewModel.fetchBanners()
                homeViewModel.fetchHomeArticlePageList()
            },
            onLoadMore = {
                homeViewModel.fetchHomeArticlePageList(false)
            },
            headerContent = {
                if (banners.isNotEmpty()) {
                    // 轮播图
                    Banner(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        images = banners.map { it.imagePath }
                    ) {
                        onBannerClick(banners[it])
                    }
                }
            }
        ) {
            ArticleItem(article = it, homeViewModel, onArticleClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WanAndroidComposeTheme {
        HomeScreen(
            homeViewModel = HomeViewModel(),
            onSearch = {},
            onArticleClick = {},
            onBannerClick = {})
    }
}