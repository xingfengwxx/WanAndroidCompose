package com.wangxingxing.wanandroidcompose.ui.main.home

import com.wangxingxing.wanandroidcompose.data.bean.Banner
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import com.wangxingxing.wanandroidcompose.ui.main.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/8 14:27
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ArticleViewModel() {
    private val _bannerList = MutableStateFlow<List<Banner>>(emptyList())
    val bannerList = _bannerList.asStateFlow()

  /** 请求首页轮播图 */
  fun fetchBanners() {
    launch({
      handleRequest(DataRepository.getBanner()) {
        _bannerList.value = it.data
      }
    })
  }

}