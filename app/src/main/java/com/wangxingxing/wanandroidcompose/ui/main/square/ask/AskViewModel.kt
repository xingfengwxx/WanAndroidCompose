package com.wangxingxing.wanandroidcompose.ui.main.square.ask

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/21 16:20
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class AskViewModel @Inject constructor() : ArticleViewModel() {

    /** 请求每日一问分页列表 */
    fun fetchAskPageList(isRefresh: Boolean = true) {
        getArticlePageList(Const.ArticleType.Ask, isRefresh)
    }
}