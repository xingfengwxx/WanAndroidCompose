package com.wangxingxing.wanandroidcompose

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
}