package com.wangxingxing.wanandroidcompose.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.bean.HotSearch
import com.wangxingxing.wanandroidcompose.data.local.CacheManager
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/27 10:19
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel<Unit>() {

    /** 搜索历史数据 */
    private val _searchHistoryData = MutableLiveData<ArrayDeque<String>>()
    val searchHistoryData: LiveData<ArrayDeque<String>> = _searchHistoryData

    private val _hotSearchList = MutableLiveData<List<HotSearch>?>()
    val hotSearchList: LiveData<List<HotSearch>?> = _hotSearchList

    /**
     * 获取历史搜索记录
     *
     */
    fun fetchSearchHistoryData() {
        launch({
            _searchHistoryData.value = CacheManager.getSearchHistoryData()
        })
    }

    /**
     * 获取热搜搜索
     *
     */
    fun fetchHotSearchList() {
        launch({
            handleRequest(DataRepository.getHotSearchList()) {
                _hotSearchList.value = it.data
            }
        })
    }

    /**
     * 清空历史
     *
     */
    fun clearHistory() {
        _searchHistoryData.value = ArrayDeque()
    }

    /**
     * 处理搜索点击事件
     *
     * @param searchText 搜索内容
     */
    fun handleSearchClick(searchText: String) {
        _searchHistoryData.value?.apply {
            if (contains(searchText)) {
                //当搜索历史中包含该数据时 删除
                remove(searchText)
            } else if (size > 10) {
                //如果集合的size 有10个以上了，删除最后一个
                removeLast()
            }
            //添加新数据到第一条
            addFirst(searchText)
            _searchHistoryData.value = this
        }
    }

    /**
     * 删除历史搜索记录
     *
     * @param item 删除的数据
     */
    fun handleDelHistoryItem(item: String) {
        _searchHistoryData.value?.remove(item)
        _searchHistoryData.value = _searchHistoryData.value
    }

    /**
     * 处理历史搜索记录点击事件（主要是将点击的移到第一个）
     *
     * @param item 点击的数据
     */
    fun handleHistoryItemClick(item: String) {
        _searchHistoryData.value?.remove(item)
        _searchHistoryData.value?.addFirst(item)
    }
}