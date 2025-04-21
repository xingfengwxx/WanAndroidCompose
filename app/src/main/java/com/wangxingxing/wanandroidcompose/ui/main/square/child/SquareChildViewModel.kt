package com.wangxingxing.wanandroidcompose.ui.main.square.child

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/21 16:03
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class SquareChildViewModel @Inject constructor() : ArticleViewModel() {

    /** 请求广场分页列表 */
    fun fetchSquarePageList(isRefresh: Boolean = true) {
        getArticlePageList(Const.ArticleType.Square, isRefresh)
    }
}