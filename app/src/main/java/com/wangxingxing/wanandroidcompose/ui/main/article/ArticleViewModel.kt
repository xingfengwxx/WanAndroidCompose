package com.wangxingxing.wanandroidcompose.ui.main.article

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.Const.ArticleType
import com.wangxingxing.wanandroidcompose.Const.Config.PAGE_SIZE
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.CollectData
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * author : 王星星
 * date : 2025/4/18 9:57
 * email : 1099420259@qq.com
 * description :
 */
open class ArticleViewModel : BaseViewModel<List<Article>>() {

    /** 我收藏的文章列表中取消收藏 */
    private val _unCollectEvent = MutableStateFlow<Int?>(null)
    val unCollectEvent = _unCollectEvent.asStateFlow()

    protected val articleList = ArrayList<Article>()
    protected var currentPage = 0

    fun handleCollect(article: Article,
                      isInCollectPage: Boolean = false,
                      successCallBack: () -> Unit = {}
    ) {
        launch({
            val response = if (isInCollectPage) {
                DataRepository.unCollectArticleInCollectPage(article.id, article.originId)
            } else {
                if (article.collect) DataRepository.unCollectArticle(article.id) else
                    DataRepository.collectArticle(article.id)
            }
            handleRequest(response) {
                // 刷新article收藏状态
                App.appViewModel.emitCollectEvent(
                    CollectData(
                        article.id,
                        article.link,
                        if (isInCollectPage) false else !article.collect
                    )
                )
                if (isInCollectPage) {
                    _unCollectEvent.value = article.id
                }
                successCallBack.invoke()
            }
        })
    }

    protected fun getArticlePageList(
        articleType: ArticleType,
        isRefresh: Boolean = true,
        categoryId: Int? = null,
        authorId: Int? = null,
        searchKey: String? = null
    ) {
        emitUiState(isRefresh, articleList)
        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }

            val response = when (articleType) {
                ArticleType.LatestProject -> DataRepository.getNewProjectPageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Project -> DataRepository.getProjectPageList(
                    currentPage, PAGE_SIZE, categoryId!!
                )

                ArticleType.Square -> DataRepository.getSquarePageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Ask -> DataRepository.getAskPageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Wechat -> DataRepository.getAuthorArticlePageList(
                    authorId!!, currentPage, PAGE_SIZE
                )

                ArticleType.Collect -> DataRepository.getCollectArticlePageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Search -> DataRepository.getSearchDataByKey(
                    currentPage, PAGE_SIZE, searchKey ?: ""
                )

                ArticleType.SystemDetails -> DataRepository.getArticlePageList(
                    currentPage, PAGE_SIZE, categoryId!!
                )
            }

            handleRequest(response) {
                articleList.addAll(it.data.datas)
                if (articleList.size >= it.data.total) {
                    emitUiState(data = articleList, showLoadingMore = false, noMoreData = true)
                    return@handleRequest
                }
                currentPage++
                emitUiState(data = articleList, showLoadingMore = true)
            }
        })
    }
}