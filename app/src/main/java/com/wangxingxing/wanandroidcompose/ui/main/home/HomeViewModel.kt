package com.wangxingxing.wanandroidcompose.ui.main.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wanandroidcompose.ui.main.home.state.HomeUiState
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/4/8 14:27
 * email : 1099420259@qq.com
 * description :
 */

// 状态管理示例
class HomeViewModel : ViewModel() {
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            // 调用 UseCase
        }
    }
    fun updateUiState(uiState: HomeUiState) {
        _uiState.value = uiState
    }
}