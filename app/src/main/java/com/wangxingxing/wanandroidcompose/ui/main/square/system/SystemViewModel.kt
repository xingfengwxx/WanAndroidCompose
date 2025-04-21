package com.wangxingxing.wanandroidcompose.ui.main.square.system

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.bean.Structure
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/21 16:30
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class SystemViewModel @Inject constructor() : BaseViewModel<List<Structure>>() {

    /** 请求体系列表 */
    fun fetchSystemList() {
        emitUiState(true)
        launch({
            handleRequest(DataRepository.getTreeList()) {
                emitUiState(data = it.data)
            }
        })
    }
}