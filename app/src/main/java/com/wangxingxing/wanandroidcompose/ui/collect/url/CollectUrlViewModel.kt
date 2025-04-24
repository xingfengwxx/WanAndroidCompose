package com.wangxingxing.wanandroidcompose.ui.collect.url

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.bean.CollectUrl
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/24 10:49
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class CollectUrlViewModel @Inject constructor() : BaseViewModel<List<CollectUrl>>() {

    /**
     * 请求收藏网址列表
     *
     */
    fun fetchCollectUrlList() {
        emitUiState(true)
        launch({
            handleRequest(DataRepository.getCollectUrlList()) {
                emitUiState(data = it.data)
            }
        })
    }

    /**
     * 取消收藏网址
     *
     * @param id
     * @param successCallBack
     */
    fun unCollectUrl(id: Int, successCallBack: () -> Unit = {}) {
        launch({
            handleRequest(DataRepository.unCollectUrl(id)) {
                emitUiState(data = uiState.value.data?.filter { it.id != id })
                successCallBack()
            }
        })
    }
}