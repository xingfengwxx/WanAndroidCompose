package com.wangxingxing.wanandroidcompose.ui.share.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.CusAlertDialog
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.core.navigation.Route
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.theme.Gray_d8d8d8

/**
 * author : 王星星
 * date : 2025/4/25 13:52
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun MyArticleScreen(
    viewModel: MyArticleViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit = {}
) {
    val navHostController = LocalNavController.current

    val needRefresh by App.appViewModel.shareArticleEvent.observeAsState()

    LaunchedEffect(Unit) {
        if (needRefresh == true) {
            viewModel.fetchMyShareArticleList()
        }
    }

    Column {
        TitleBar(
            title = stringResource(R.string.my_share_article),
            menu = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.clickable {
                        navHostController.navigate(Route.ADD_ARTICLE)
                    }
                )
            }
        ) { navHostController.popBackStack() }

        RefreshList(
            uiState = viewModel.uiState.collectAsState().value,
            onRefresh = { viewModel.fetchMyShareArticleList() },
            itemContent = { article ->
                ShareArticleItem(
                    article = article,
                    onDeleteClick = {
                        viewModel.delMyShareArticle(it.id)
                    },
                    onArticleClick = onArticleClick
                )
            }
        )
    }
}

@Composable
fun ShareArticleItem(
    article: Article,
    onDeleteClick: (Article) -> Unit,
    onArticleClick: (Article) -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onArticleClick.invoke(article) },
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
                    text = article.title.toHtml().toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalContentColor.current.copy(alpha = 0.9f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = article.niceDate,
                    fontSize = 13.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Gray_d8d8d8,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {
                        showDeleteDialog = true
                    }
            )
        }
    }

    if (showDeleteDialog) {
        CusAlertDialog(
            content = { Text(stringResource(R.string.txt_del_article_confirm)) },
            onConfirm = { onDeleteClick.invoke(article) }
        ) {
            showDeleteDialog = false
        }
    }
}