package com.btpj.lib_base.data.bean

import com.google.gson.annotations.SerializedName

/**
 * 接口返回外层封装实体
 *
 * @author LTP  2022/3/22
 */
data class ApiResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)