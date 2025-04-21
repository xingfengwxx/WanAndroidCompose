package com.wangxingxing.wanandroidcompose.ui.main.home

import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.Const.Config.PAGE_SIZE
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.data.bean.Banner
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import com.wangxingxing.wanandroidcompose.ui.main.article.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/8 14:27
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ArticleViewModel() {
    private val _bannerList = MutableStateFlow<List<Banner>>(emptyList())
    val bannerList = _bannerList.asStateFlow()

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(DataRepository.getBanner()) {
                _bannerList.value = it.data
            }
        })
    }

    /**
     * 请求文章列表，第一页包括一个请求置顶的接口和一个文章分页列表的接口
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchHomeArticlePageList(isRefresh: Boolean = true) {
        emitUiState(isRefresh, articleList)
        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }

            if (currentPage == 0) {
                // 第一页会同时请求置顶文章列表接口和分页文章列表的接口，使用async进行并行请求速度更快（默认是串行的）
                // 需要加SupervisorJob()来自行处理协程（https://juejin.cn/post/7130132604568731655）
                val job1 = async(Dispatchers.IO + SupervisorJob()) {
                    DataRepository.getArticlePageList(currentPage++, PAGE_SIZE)
                }
                val job2 = async(Dispatchers.IO + SupervisorJob()) {
                    DataRepository.getArticleTopList()
                }

                try {
                    val response1 = job1.await()
                    val response2 = job2.await()

                    handleRequest(response1) {
                        handleRequest(response2) {
                            (response1.data.datas as ArrayList<Article>).addAll(
                                0, response2.data
                            )
                            emitUiState(
                                data = articleList.apply { addAll(response1.data.datas) },
                                showLoadingMore = true
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    emitUiState(showLoading = false, error = e.message)
                }
            } else {
                handleRequest(DataRepository.getArticlePageList(currentPage++, PAGE_SIZE)) {
                    articleList.addAll(it.data.datas)
                    if (articleList.size == it.data.total) {
                        emitUiState(
                            data = articleList,
                            showLoadingMore = false,
                            noMoreData = true
                        )
                        return@handleRequest
                    }
                    currentPage++
                    emitUiState(data = articleList, showLoadingMore = true)
                }
            }
        })
    }

}