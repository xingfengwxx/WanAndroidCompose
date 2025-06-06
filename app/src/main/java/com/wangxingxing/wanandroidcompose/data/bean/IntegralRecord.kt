package com.wangxingxing.wanandroidcompose.data.bean

import com.btpj.lib_base.ui.widgets.ProvideItemKey

/**
 * 积分记录
 * @author LTP  2022/4/12
 */
data class IntegralRecord(
    var coinCount: Int,
    var date: Long,
    var desc: String,
    var id: Int,
    var type: Int,
    var reason: String,
    var userId: Int,
    var userName: String
) : ProvideItemKey {

    override fun provideKey(): Int {
        return id
    }
}