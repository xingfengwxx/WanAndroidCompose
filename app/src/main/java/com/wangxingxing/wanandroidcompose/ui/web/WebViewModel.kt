package com.wangxingxing.wanandroidcompose.ui.web

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.bean.CollectUrl
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/23 15:23
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class WebViewModel @Inject constructor(): BaseViewModel<Unit>() {

    private val _collectUrlList = MutableStateFlow<List<CollectUrl>>(emptyList())
    val collectUrlList: StateFlow<List<CollectUrl>> = _collectUrlList

    /**
     * 请求收藏网址列表
     *
     */
    fun fetchCollectUrlList() {
        emitUiState(true)
        launch({
            handleRequest(DataRepository.getCollectUrlList()) {
                _collectUrlList.value = it.data
            }
        })
    }

    /**
     * 收藏站内文章
     *
     * @param id 文章id
     * @param successCallBack 成功回调
     */
    fun collectArticle(id: Int, successCallBack: () -> Unit = {}) {
        launch({
            handleRequest(DataRepository.collectArticle(id)) {
                successCallBack.invoke()
            }
        })
    }

    /**
     * 取消收藏站内文章
     *
     * @param id 文章id
     * @param successCallBack 成功回调
     */
    fun unCollectArticle(id: Int, successCallBack: () -> Unit = {}) {
        launch({
            handleRequest(DataRepository.unCollectArticle(id)) {
                successCallBack.invoke()
            }
        })
    }

    /**
     * 收藏网址
     *
     * @param name 网址名
     * @param link 网址链接
     * @param successCallBack 成功回调
     */
    fun collectUrl(
        name: String,
        link: String,
        successCallBack: (collectUrl: CollectUrl?) -> Unit = {}
    ) {
        launch({
            handleRequest(DataRepository.collectUrl(name, link)) {
                successCallBack.invoke(it.data)
            }
        })
    }

    /**
     * 取消收藏网址
     *
     * @param name 网址名
     * @param link 网址链接
     * @param successCallBack 成功回调
     */
    fun unCollectUrl(
        id: Int,
        successCallBack: () -> Unit = {}
    ) {
        launch({
            handleRequest(DataRepository.unCollectUrl(id)) {
                successCallBack.invoke()
            }
        })
    }

    /**
     * 收藏站外文章
     *
     * @param title 文章标题
     * @param author 文章作者
     * @param link 文章链接
     * @param successCallBack 成功回调
     */
    fun collectOutSiteArticle(
        title: String,
        author: String,
        link: String,
        successCallBack: () -> Unit = {}
    ) {
        launch({
            handleRequest(DataRepository.collectOutSiteArticle(title, author, link)) {
                successCallBack.invoke()
            }
        })
    }
}