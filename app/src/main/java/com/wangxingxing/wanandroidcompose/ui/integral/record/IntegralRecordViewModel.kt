package com.wangxingxing.wanandroidcompose.ui.integral.record

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.Const.Config.PAGE_SIZE
import com.wangxingxing.wanandroidcompose.data.bean.IntegralRecord
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/24 17:30
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class IntegralRecordViewModel @Inject constructor() : BaseViewModel<List<IntegralRecord>>() {

    private val integralRecordList = ArrayList<IntegralRecord>()
    private var currentPage = 0

    /**
     * 获取积分记录列表
     *
     * @param isRefresh 是否刷新
     */
    fun fetchIntegralRecordPageList(isRefresh: Boolean = true) {
        emitUiState(isRefresh, integralRecordList)
        launch({
            if (isRefresh) {
                integralRecordList.clear()
                currentPage = 0
            }

            handleRequest(DataRepository.getIntegralRecordPageList(currentPage++, PAGE_SIZE)) {
                integralRecordList.addAll(it.data.datas)
                if (it.data.over) {
                    emitUiState(
                        data = integralRecordList,
                        showLoadingMore = false,
                        noMoreData = true
                    )
                    return@handleRequest
                }

                currentPage++
                emitUiState(data = integralRecordList, showLoadingMore = true)
            }
        })
    }
}