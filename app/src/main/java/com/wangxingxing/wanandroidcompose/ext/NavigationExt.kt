package com.wangxingxing.wanandroidcompose.ext

import android.os.Bundle
import androidx.navigation.NavController
import com.wangxingxing.wanandroidcompose.core.navigation.Route
import com.wangxingxing.wanandroidcompose.data.local.UserManager

/**
 * author : 王星星
 * date : 2025/4/24 11:14
 * email : 1099420259@qq.com
 * description : Navigation的扩展函数(添加登录判断)
 */
fun NavController.navigateNeedLogin(route: String, args: Bundle? = null) {
    if (!UserManager.isLogin()) {
        navigate(Route.LOGIN)
        return
    }

    graph.findNode(route)?.let {
        navigate(it.id, args)
    }
}