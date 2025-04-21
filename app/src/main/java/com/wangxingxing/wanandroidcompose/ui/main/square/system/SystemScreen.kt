package com.wangxingxing.wanandroidcompose.ui.main.square.system

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.RefreshList
import com.wangxingxing.wanandroidcompose.data.bean.Structure
import com.wangxingxing.wanandroidcompose.ui.main.project.StructureItem

/**
 * author : 王星星
 * date : 2025/4/21 16:29
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SystemScreen(
    lazyListState: LazyListState,
    viewModel: SystemViewModel = hiltViewModel(),
    onStructureClick: (Structure, Int) -> Unit
) {
    RefreshList(
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 10.dp),
        uiState = viewModel.uiState.collectAsState().value,
        lazyListState = lazyListState,
        onRefresh = { viewModel.fetchSystemList() },
        itemContent = { StructureItem(it, onStructureClick) }
    )
}