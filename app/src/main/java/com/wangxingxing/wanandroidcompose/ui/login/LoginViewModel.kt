package com.wangxingxing.wanandroidcompose.ui.login

import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.data.local.UserManager
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/22 16:12
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<String>() {

    /**
     * Login
     *
     * @param userName 用户名
     * @param pwd 密码
     * @param errorBlock 错误回调
     * @param successBlock 成功回调
     * @receiver
     * @receiver
     */
    fun login(
        userName: String,
        pwd: String,
        errorBlock: () -> Unit = {},
        successBlock: () -> Unit = {}
    ) {
       launch({
           handleRequest(DataRepository.login(userName, pwd), errorBlock = {
               errorBlock()
               false
           }) {
               UserManager.saveLastUserName(userName)
               UserManager.saveUser(it.data)
               App.appViewModel.emitUser(it.data)
               successBlock.invoke()
           }
       })
    }
}