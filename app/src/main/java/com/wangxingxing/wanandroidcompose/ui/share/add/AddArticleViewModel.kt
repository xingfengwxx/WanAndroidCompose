package com.wangxingxing.wanandroidcompose.ui.share.add

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.ApiResponse
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/25 14:37
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class AddArticleViewModel @Inject constructor() : BaseViewModel<Unit>() {

    /**
     * 添加文章分享
     *
     * @param title 文章标题
     * @param link 文章链接
     * @param errorCallback 错误回调
     * @param successCallback 成功回调
     */
    fun addArticle(
        title: String,
        link: String,
        errorCallback: () -> Unit = {},
        successCallback: (ApiResponse<Any?>) -> Unit = {}
    ) {
        launch({
            val response = DataRepository.addArticle(title, link)
            handleRequest(response, errorBlock = {
                errorCallback()
                false
            }) {
                successCallback(it)
            }
        })
    }
}