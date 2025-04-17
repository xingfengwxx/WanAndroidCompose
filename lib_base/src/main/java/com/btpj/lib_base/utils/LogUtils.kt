package com.btpj.lib_base.utils

import android.util.Log
import androidx.annotation.IntDef


/**
 * author : 王星星
 * date : 2022/1/17 15:07
 * email : 1099420259@qq.com
 * description : 日志工具类，解决日志超长不打印的问题
 */
object LogUtils {

    private const val TAG = "wxx"

    const val V = Log.VERBOSE
    const val D = Log.DEBUG
    const val I = Log.INFO
    const val W = Log.WARN
    const val E = Log.ERROR
    const val A = Log.ASSERT

    @IntDef(V, D, I, W, E, A)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class TYPE

    private const val MAX_LOG_LENGTH = 2000

    private const val SEGMENT_SIZE = 3 * 1024

    fun v(msg: String?, tag: String? = TAG) {
        log(V, tag, msg)
    }

    fun d(msg: String?, tag: String? = TAG) {
        log(D, tag, msg)
    }

    fun i(msg: String?, tag: String? = TAG) {
        log(I, tag, msg)
    }

    fun w(msg: String?, tag: String? = TAG) {
        log(W, tag, msg)
    }

    fun e(msg: String?, tag: String = TAG) {
        log(E, tag, msg)
    }

    private fun log(@TYPE type: Int, tag: String?, msg: String?, tr: Throwable? = null) {
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        // 把4*1024的MAX字节打印长度改为2001字符数
        val maxStrLength = 2001 - tag?.length!!
        //大于4000时
        var str = msg
        while (str?.length!! > maxStrLength) {
            realLog(type, tag, str?.substring(0, maxStrLength), tr)
            str = str.substring(maxStrLength)
        }
        //剩余部分
        realLog(type, tag, str, tr)
    }

    private fun realLog(@TYPE type: Int, tag: String?, msg: String?, tr: Throwable? = null) {
        when (type) {
            V -> Log.v(tag, msg.toString())
            D -> Log.d(tag, msg.toString())
            I -> Log.i(tag, msg.toString())
            W -> {
                if (tr == null) {
                    Log.w(tag, msg.toString())
                } else {
                    Log.w(tag, msg.toString(), tr)
                }
            }
            E -> {
                if (tr == null) {
                    Log.e(tag, msg.toString())
                } else {
                    Log.e(tag, msg.toString(), tr)
                }
            }
        }
    }

}