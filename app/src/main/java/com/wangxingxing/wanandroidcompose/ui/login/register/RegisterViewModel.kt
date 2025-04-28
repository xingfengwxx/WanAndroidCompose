package com.wangxingxing.wanandroidcompose.ui.login.register

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.local.UserManager
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/28 14:11
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class RegisterViewModel @Inject constructor() : BaseViewModel<String>() {

    fun register(
        userName: String,
        pwd: String,
        pwdSure: String,
        errorBlock: () -> Unit = {},
        successCall: () -> Unit = {}
    ) {
        launch({
            handleRequest(
                DataRepository.register(userName, pwd, pwdSure),
                errorBlock = {
                    errorBlock()
                    false
                },
                successBlock = {
                    UserManager.saveLastUserName(userName)
                    successCall()
                })
        })
    }
}