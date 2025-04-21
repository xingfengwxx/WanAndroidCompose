package com.wangxingxing.wanandroidcompose.ui.main.article

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.CollectData
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
}