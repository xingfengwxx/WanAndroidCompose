package com.wangxingxing.wanandroidcompose.ui.main.square.navgation

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.bean.Navigation
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/21 17:05
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class NavigationViewModel @Inject constructor() : BaseViewModel<List<Navigation>>() {

    /** 请求导航列表 */
    fun fetchNavigationList() {
        emitUiState(true)
        launch({
            handleRequest(DataRepository.getNavigationList()) {
                emitUiState(data = it.data)
            }
        })
    }
}