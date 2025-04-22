package com.wangxingxing.wanandroidcompose.ui.main.wechat

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/22 9:34
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class WechatChildViewModel @Inject constructor() : ArticleViewModel() {

    fun fetchAuthorPageList(authorId: Int, isRefresh: Boolean = true) {
        getArticlePageList(Const.ArticleType.Wechat, isRefresh, authorId)
    }
}