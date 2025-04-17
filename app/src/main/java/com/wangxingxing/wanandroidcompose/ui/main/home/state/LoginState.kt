package com.wangxingxing.wanandroidcompose.ui.main.home.state

import com.wangxingxing.wanandroidcompose.data.bean.User

/**
 * author : 王星星
 * date : 2025/4/8 14:39
 * email : 1099420259@qq.com
 * description :
 */
// 状态密封类示例
sealed interface LoginState {
    object Idle : LoginState
    object Loading : LoginState
    data class Success(val user: User) : LoginState
    data class Error(val message: String) : LoginState
}