package com.wangxingxing.wanandroidcompose.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.lib_base.utils.CommonUtil
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.data.local.CacheManager

/**
 * author : 王星星
 * date : 2025/4/18 10:25
 * email : 1099420259@qq.com
 * description : 搜索
 */
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    toSearchResultScreen: (String) -> Unit
) {
    Column {
        TitleContent(viewModel) { toSearchResultScreen(it) }
        HotSearchContent(viewModel) { toSearchResultScreen(it) }
        SearchHistoryContent(viewModel) { toSearchResultScreen(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleContent(
    viewModel: SearchViewModel = hiltViewModel(),
    onSearchClick: (String) -> Unit
) {
    val navHostController = LocalNavController.current

    var searchText by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(contentAlignment = Alignment.Center) {
        val searchAble = searchText.trim().isNotEmpty()
        TitleBar(
            menu = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary.copy(if (searchAble) 1f else 0.4f),
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(enabled = searchAble) {
                            viewModel.handleSearchClick(searchText)
                            onSearchClick(searchText)
                        }
                )
            }
        ) { navHostController.popBackStack() }

        TextField(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .focusRequester(focusRequester),
            value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.input_search_key_to_search),
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary, // 设置文本颜色
                cursorColor = MaterialTheme.colorScheme.onPrimary.copy(0.5f), // 设置光标颜色
                containerColor = MaterialTheme.colorScheme.primary // 设置背景颜色
            )
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HotSearchContent(
    viewModel: SearchViewModel = hiltViewModel(),
    onHotSearchClick: (String) -> Unit
) {
    val hotSearchList by viewModel.hotSearchList.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchHotSearchList()
    }

    Text(
        text = stringResource(R.string.txt_hot_search),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
    )
    FlowRow(modifier = Modifier.padding(horizontal = 10.dp)) {
        hotSearchList?.forEach {
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onHotSearchClick(it.name) }
                    .background(LocalContentColor.current.copy(0.1f), RoundedCornerShape(8.dp))
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                text = it.name,
                color = CommonUtil.randomColor(),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SearchHistoryContent(
    viewModel: SearchViewModel = hiltViewModel(),
    onHistoryClick: (String) -> Unit
) {
    val searchHistoryList by viewModel.searchHistoryData.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSearchHistoryData()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.txt_history_search),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Text(
            text = stringResource(R.string.clear),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable { viewModel.clearHistory() }
        )
    }
    searchHistoryList?.apply {
        CacheManager.saveSearchHistoryData(this)
        forEach {
            SearchHistoryItem(
                item = it,
                onDelClick = { item ->
                    viewModel.handleDelHistoryItem(item)
                }
            ) { historyItem ->
                viewModel.handleHistoryItemClick(historyItem)
                onHistoryClick(historyItem)
            }
        }
    }
}

@Composable
fun SearchHistoryItem(
    item: String,
    onDelClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(12.dp)
    ) {
        Text(
            text = item,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.clickable { onDelClick(item) }
        )
    }
}