package com.wangxingxing.wanandroidcompose.ui.share.list

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.Const.Config.PAGE_SIZE
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/25 13:52
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class MyArticleViewModel @Inject constructor() : BaseViewModel<List<Article>>() {

    private val articleList = ArrayList<Article>()
    private var curPage = 0

    /**
     * 获取我的分享文章列表
     *
     * @param isRefresh 是否刷新
     */
    fun fetchMyShareArticleList(isRefresh: Boolean = true) {
        launch({
            emitUiState(isRefresh, articleList)
            if (isRefresh) {
                articleList.clear()
                curPage = 0
            }

            handleRequest(DataRepository.getMyShareArticlePageList(curPage, PAGE_SIZE)) {
                articleList.addAll(it.data.shareArticles.datas)
                if (articleList.size == it.data.shareArticles.total) {
                    emitUiState(
                        data = articleList,
                        showLoadingMore = true,
                        noMoreData = true
                    )
                    return@handleRequest
                }

                curPage++
                emitUiState(data = articleList, showLoadingMore = true)
            }
        })
    }

    fun delMyShareArticle(id: Int, delSuccess: () -> Any = {}) {
        launch({
            val response = DataRepository.deleteShareArticle(id)
            handleRequest(response) {
                // 删除成功
                emitUiState(data = uiState.value.data?.filter { it.id != id })
                delSuccess.invoke()
            }
        })
    }
}