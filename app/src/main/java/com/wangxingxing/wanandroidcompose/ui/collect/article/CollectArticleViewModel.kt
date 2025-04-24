package com.wangxingxing.wanandroidcompose.ui.collect.article

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/24 9:47
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class CollectArticleViewModel @Inject constructor() : ArticleViewModel() {

    /**
     * 请求收藏文章分页列表
     *
     * @param isRefresh
     */
    fun fetchCollectArticlePageList(isRefresh: Boolean = true) {
       getArticlePageList(Const.ArticleType.Collect, isRefresh)
    }
}