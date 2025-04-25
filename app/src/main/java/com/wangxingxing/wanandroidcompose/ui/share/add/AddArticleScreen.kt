package com.wangxingxing.wanandroidcompose.ui.share.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.LoadingDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/4/25 14:37
 * email : 1099420259@qq.com
 * description :
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddArticleScreen(
    viewModel: AddArticleViewModel = hiltViewModel(),
) {
    val navHostController = LocalNavController.current

    var title by rememberSaveable { mutableStateOf("") }
    var link by rememberSaveable { mutableStateOf("") }
    var showLoadingDialog by remember { mutableStateOf(false) }

    val bottomSheetState =
        androidx.compose.material.rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    if (showLoadingDialog) {
        LoadingDialog(loadingText = stringResource(R.string.txt_commiting)) { showLoadingDialog = false }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(
            title = stringResource(R.string.share_article),
            menu = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.HelpOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                bottomSheetState.show()
                            }
                        }
                )
            }
        ) { navHostController.popBackStack() }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
            value = title,
            onValueChange = { title = it},
            label = { Text(stringResource(R.string.article_title))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                    0.4f
                )
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            value = link,
            label = { Text(text = stringResource(id = R.string.article_link)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                    0.4f
                )
            ),
            onValueChange = { link = it })

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 20.dp, end = 20.dp),
            enabled = title.trim().isNotEmpty() && link.trim().isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(0.6f)
            ),
            onClick = {
                // 执行提交操作
                showLoadingDialog = true
                viewModel.addArticle(
                    title = title,
                    link = link,
                    errorCallback = { showLoadingDialog = false },
                    successCallback = {
                        // 分享成功后的操作
                        showLoadingDialog = false
                        App.appViewModel.emitShareArticleSuccess()
                        navHostController.popBackStack()
                    }
                )
            }
        ) {
            Text(stringResource(R.string.share))
        }
    }

    BottomSheetTip(bottomSheetState = bottomSheetState)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetTip(bottomSheetState: ModalBottomSheetState) {
    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetState = bottomSheetState,
        sheetContent = {
            // 底部弹窗的内容
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(R.string.txt_tips),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(R.string.txt_tips_share),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            }
        }
    ) { }
}