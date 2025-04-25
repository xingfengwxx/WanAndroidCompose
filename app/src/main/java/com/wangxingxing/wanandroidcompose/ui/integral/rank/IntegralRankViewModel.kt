package com.wangxingxing.wanandroidcompose.ui.integral.rank

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.Const.Config.PAGE_SIZE
import com.wangxingxing.wanandroidcompose.data.bean.CoinInfo
import com.wangxingxing.wanandroidcompose.data.local.UserManager
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/24 11:45
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class IntegralRankViewModel @Inject constructor() : BaseViewModel<List<CoinInfo>>() {

    /** 我的积分信息 */
    private val _myCoinInfo = MutableStateFlow<CoinInfo?>(null)
    val myCoinInfo = _myCoinInfo.asStateFlow()

    private val coinInfoList = ArrayList<CoinInfo>()
    private var currentPage = 1

    /**
     * 获取积分排行榜列表
     *
     * @param isRefresh
     */
    fun fetchIntegralRankList(isRefresh: Boolean = true) {
        emitUiState(isRefresh, coinInfoList)
        launch({
            if (isRefresh) {
                coinInfoList.clear()
                currentPage = 1
            }

            handleRequest(DataRepository.getIntegralRankPageList(currentPage++, PAGE_SIZE)) {
                coinInfoList.addAll(it.data.datas)
                if (!it.data.over) {
                    emitUiState(
                        data = coinInfoList,
                        showLoadingMore = false,
                        noMoreData = true
                    )
                    return@handleRequest
                }
                currentPage++
                emitUiState(data = coinInfoList, showLoadingMore = true)
            }
        })
    }

    /**
     * 获取我的积分信息
     */
    fun fetchMyCoinInfo() {
        //  未登录时无需请求
        if (!UserManager.isLogin()) return

        launch({
            handleRequest(DataRepository.getUserIntegral()) {
                _myCoinInfo.value = it.data
            }
        })
    }
}