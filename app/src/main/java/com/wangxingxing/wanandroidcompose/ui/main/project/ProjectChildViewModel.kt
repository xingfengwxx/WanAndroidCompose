package com.wangxingxing.wanandroidcompose.ui.main.project

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/21 11:53
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class ProjectChildViewModel @Inject constructor() : ArticleViewModel() {

    /** 请求最新项目分页列表 */
    fun fetchNewProjectPageList(isRefresh: Boolean = true) {
        getArticlePageList(Const.ArticleType.LatestProject, isRefresh)
    }

    fun fetchProjectPageList(categoryId: Int, isRefresh: Boolean = true) {
        getArticlePageList(Const.ArticleType.Project, isRefresh, categoryId)
    }
}