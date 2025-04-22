package com.wangxingxing.wanandroidcompose.ui.main.mine

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.data.bean.CoinInfo
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/22 11:40
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class MineViewModel @Inject constructor() : BaseViewModel<CoinInfo>() {

    /** 获取个人积分 */
    fun fetchPoints() {
        if (App.appViewModel.user.value == null) {
            emitUiState(false, null)
            return
        }

        emitUiState(true)
        launch({
            val response = DataRepository.getUserIntegral()
            handleRequest(response) {
                emitUiState(false, response.data)
            }
        })
    }
}