package com.wangxingxing.wanandroidcompose.ui.main.wechat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.bean.Classify
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/21 17:28
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class WeChatViewModel @Inject constructor() : BaseViewModel<Unit>() {

    /** 项目标题列表LiveData */
    private val _authorTitleListLiveData = MutableLiveData<List<Classify>>()
    val authorTitleListLiveData: LiveData<List<Classify>> = _authorTitleListLiveData

    /** 请求公众号作者标题列表 */
    fun fetchAuthorTitleList() {
        launch({
            handleRequest(
                DataRepository.getAuthorTitleList()
            )
            { _authorTitleListLiveData.value = it.data!! }
        })
    }
}