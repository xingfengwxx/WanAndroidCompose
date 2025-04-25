package com.wangxingxing.wanandroidcompose.ui.integral.rank

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankj.utilcode.util.StringUtils
import com.btpj.lib_base.ext.navigate
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.core.navigation.Route
import com.wangxingxing.wanandroidcompose.data.bean.CoinInfo

/**
 * author : 王星星
 * date : 2025/4/24 11:44
 * email : 1099420259@qq.com
 * description : 积分排行
 */
@Composable
fun IntegralRankScreen(
    viewModel: IntegralRankViewModel = hiltViewModel()
) {
    val navHostController = LocalNavController.current

    val myCoinInfo by viewModel.myCoinInfo.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMyCoinInfo()
    }

    Column {
        TitleBar(
            title = stringResource(R.string.integral_rank),
            menu = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.clickable {
                        navHostController.navigate(
                            Route.WEB,
                            bundleOf(Const.ParamKey.WEB_TYPE to Const.WebType.Url(
                                name = StringUtils.getString(R.string.txt_integral_rule),
                                link = Const.Config.URL_INTEGRAL_HELP
                            ))
                        )
                    }
                )
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable { navHostController.navigate(Route.INTEGRAL_RANK_RECORD) }
                )
            }
        ) {
            navHostController.popBackStack()
        }

        RefreshList(
            modifier = Modifier.weight(1f),
            uiState = viewModel.uiState.collectAsState().value,
            onRefresh = { viewModel.fetchIntegralRankList() },
            onLoadMore = { viewModel.fetchIntegralRankList(false) },
        ) {
            CoinInfoItem(it)
        }

        if (myCoinInfo != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = myCoinInfo!!.rank,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = myCoinInfo!!.username,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = myCoinInfo!!.coinCount.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun CoinInfoItem(coinInfo: CoinInfo) {
   Card(
       modifier = Modifier
           .fillMaxWidth()
           .padding(horizontal = 10.dp),
       shape = RoundedCornerShape(4.dp),
       colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
       elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
   ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(15.dp)
       ) {
           Text(
               text = coinInfo.rank,
               fontSize = 18.sp,
               fontWeight = FontWeight.Bold
           )
           Text(
               text = coinInfo.username,
               modifier = Modifier
                   .weight(1f)
                   .padding(horizontal = 10.dp),
               fontSize = 16.sp
           )
           Text(
               text = coinInfo.coinCount.toString(),
               fontSize = 16.sp,
               fontWeight = FontWeight.Bold
           )
       }
   }
}