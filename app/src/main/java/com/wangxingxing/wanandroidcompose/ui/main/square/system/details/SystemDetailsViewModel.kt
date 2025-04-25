package com.wangxingxing.wanandroidcompose.ui.main.square.system.details

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/25 16:41
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class SystemDetailsViewModel @Inject constructor() : ArticleViewModel() {

    fun fetchSystemDetailsPageList(categoryId: Int, isRefresh: Boolean = true) {
        getArticlePageList(
            articleType = Const.ArticleType.SystemDetails,
            isRefresh = isRefresh,
            categoryId = categoryId
        )
    }
}