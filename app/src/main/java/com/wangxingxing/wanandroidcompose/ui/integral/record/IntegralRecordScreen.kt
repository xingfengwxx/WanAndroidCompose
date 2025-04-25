package com.wangxingxing.wanandroidcompose.ui.integral.record

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.lib_base.utils.DateUtil
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.data.bean.IntegralRecord

/**
 * author : 王星星
 * date : 2025/4/24 17:28
 * email : 1099420259@qq.com
 * description : 积分记录
 */
@Composable
fun IntegralRecordScreen(
    viewModel: IntegralRecordViewModel = hiltViewModel()
) {
    val navHostController = LocalNavController.current
    Column {
        TitleBar(
            title = stringResource(R.string.integral_record)
        ) {
            navHostController.popBackStack()
        }

        RefreshList(
            uiState = viewModel.uiState.collectAsState().value,
            onRefresh = { viewModel.fetchIntegralRecordPageList() },
            onLoadMore = { viewModel.fetchIntegralRecordPageList(false) }
        ) {
            CoinRecordItem(it)
        }
    }
}

@Composable
fun CoinRecordItem(item: IntegralRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${item.reason}${
                        if (item.desc.contains(stringResource(R.string.txt_integral))) item.desc.subSequence(
                            item.desc.indexOf(stringResource(R.string.txt_integral)),
                            item.desc.length
                        ) else ""
                    }",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalContentColor.current.copy(0.9f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = DateUtil.getLongDateStr(item.date),
                    fontSize = 12.sp,
                    color = LocalContentColor.current.copy(0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = item.coinCount.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}