package com.wangxingxing.wanandroidcompose.ui.search.result

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/27 14:29
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class SearchResultViewModel @Inject constructor() : ArticleViewModel() {

    /**
     * 请求搜索结果分页列表
     *
     * @param searchKey 搜索关键词
     * @param isRefresh 是否是刷新
     */
    fun fetchSearchResultPageList(searchKey: String, isRefresh: Boolean = true) {
        getArticlePageList(Const.ArticleType.Search, isRefresh, searchKey = searchKey)
    }
}