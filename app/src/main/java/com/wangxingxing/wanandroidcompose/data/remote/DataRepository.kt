package com.wangxingxing.wanandroidcompose.data.remote

import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.http.RetrofitManager
import com.wangxingxing.wanandroidcompose.data.bean.*
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * author : 王星星
 * date : 2025/4/18 10:07
 * email : 1099420259@qq.com
 * description : 数据仓库
 */
object DataRepository : Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun login(username: String, pwd: String): ApiResponse<User> {
        return service.login(username, pwd)
    }

    override suspend fun register(
        username: String,
        pwd: String,
        pwdSure: String
    ): ApiResponse<Any?> {
        return service.register(username, pwd, pwdSure)
    }

    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        return service.getBanner()
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        return service.getArticleTopList()
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int,
        categoryId: Int?
    ): ApiResponse<PageResponse<Article>> {
        return service.getArticlePageList(pageNo, pageSize, categoryId)
    }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return service.collectArticle(id)
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return service.unCollectArticle(id)
    }

    override suspend fun unCollectArticleInCollectPage(id: Int, originId: Int): ApiResponse<Any?> {
        return service.unCollectArticleInCollectPage(id, originId)
    }

    override suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>> {
        return service.getProjectTitleList()
    }

    override suspend fun getNewProjectPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getNewProjectPageList(pageNo, pageSize)
    }

    override suspend fun getProjectPageList(
        @Path(value = "pageNo") pageNo: Int,
        @Query(value = "page_size") pageSize: Int,
        @Query(value = "cid") categoryId: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getProjectPageList(pageNo, pageSize, categoryId)
    }

    override suspend fun getSquarePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getSquarePageList(pageNo, pageSize)
    }

    override suspend fun getAskPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getAskPageList(pageNo, pageSize)
    }

    override suspend fun getTreeList(): ApiResponse<List<Structure>> {
        return service.getTreeList()
    }

    override suspend fun getNavigationList(): ApiResponse<List<Navigation>> {
        return service.getNavigationList()
    }

    override suspend fun getAuthorTitleList(): ApiResponse<List<Classify>> {
        return service.getAuthorTitleList()
    }

    override suspend fun getAuthorArticlePageList(
        authorId: Int,
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getAuthorArticlePageList(authorId, pageNo, pageSize)
    }

    override suspend fun getUserIntegral(): ApiResponse<CoinInfo> {
        return service.getUserIntegral()
    }

    override suspend fun getIntegralRankPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<CoinInfo>> {
        return service.getIntegralRankPageList(pageNo, pageSize)
    }

    override suspend fun getIntegralRecordPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<IntegralRecord>> {
        return service.getIntegralRecordPageList(pageNo, pageSize)
    }

    override suspend fun getCollectArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return service.getCollectArticlePageList(pageNo, pageSize)
    }

    override suspend fun getCollectUrlList(): ApiResponse<List<CollectUrl>> {
        return service.getCollectUrlList()
    }

    override suspend fun getMyShareArticlePageList(pageNo: Int, pageSize: Int): ApiResponse<Share> {
        return service.getMyShareArticlePageList(pageNo, pageSize)
    }

    override suspend fun addArticle(title: String, link: String): ApiResponse<Any?> {
        return service.addArticle(title, link)
    }

    override suspend fun deleteShareArticle(id: Int): ApiResponse<Any?> {
        return service.deleteShareArticle(id)
    }

    override suspend fun collectOutSiteArticle(
        title: String,
        author: String,
        link: String
    ): ApiResponse<Any?> {
        return service.collectOutSiteArticle(title, author, link)
    }

    override suspend fun collectUrl(name: String, link: String): ApiResponse<CollectUrl?> {
        return service.collectUrl(name, link)
    }

    override suspend fun unCollectUrl(id: Int): ApiResponse<Any?> {
        return service.unCollectUrl(id)
    }

    override suspend fun getOtherAuthorArticlePageList(
        id: Int,
        page: Int
    ): ApiResponse<OtherAuthor> {
        return service.getOtherAuthorArticlePageList(id, page)
    }

    override suspend fun getHotSearchList(): ApiResponse<List<HotSearch>> {
        return service.getHotSearchList()
    }

    override suspend fun getSearchDataByKey(
        pageNo: Int,
        pageSize: Int,
        searchKey: String
    ): ApiResponse<PageResponse<Article>> {
        return service.getSearchDataByKey(pageNo, pageSize, searchKey)
    }
}