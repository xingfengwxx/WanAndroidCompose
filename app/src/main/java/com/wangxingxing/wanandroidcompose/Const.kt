package com.wangxingxing.wanandroidcompose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * author : 王星星
 * date : 2025/4/18 9:36
 * email : 1099420259@qq.com
 * description :
 */
object Const {

    object Config {
        const val BUGLY_APP_ID = "d60b18d191"

        const val PAGE_SIZE = 10

        const val URL_PROJECT_SOURCE_CODE = "https://github.com/xingfengwxx/WanAndroidCompose"
        const val URL_WAN_ANDROID = "https://www.wanandroid.com/"
        const val URL_INTEGRAL_HELP = "https://www.wanandroid.com/blog/show/2653"
    }

    object ParamKey {
        const val WEB_TYPE = "webType"
        const val COLLECTED_FLAG = "collectedFlag"
        const val URL = "url"
    }

    sealed class ArticleType {
        object LatestProject : ArticleType()        // 最新项目
        object Project : ArticleType()    // 项目列表
        object Square : ArticleType()    // 广场 - 广场
        object Ask : ArticleType()    // 广场 - 每日一问
        object Wechat : ArticleType()    // 公众号
        object Collect : ArticleType()    // 我收藏的文章
        object Search : ArticleType()    // 搜索到的文章
        object SystemDetails : ArticleType()    // 搜索到的文章
    }

    @Parcelize
    sealed class WebType(open var link: String) : Parcelable {
        data class OnSiteArticle(val articleId: Int, override var link: String) : WebType(link)
        data class OutSiteArticle(
            val articleId: Int, val title: String, val author: String, override var link: String
        ) : WebType(link)

        data class Url(val id: Int? = null, val name: String, override var link: String) : WebType(link)
    }
}