package com.wangxingxing.wanandroidcompose.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankj.utilcode.util.StringUtils
import com.btpj.lib_base.ext.navigate
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.ui.widgets.CusAlertDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.App
import com.wangxingxing.wanandroidcompose.Const
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.core.navigation.Route
import com.wangxingxing.wanandroidcompose.data.local.UserManager

/**
 * author : 王星星
 * date : 2025/4/23 10:06
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel()) {
    val navHostController = LocalNavController.current

    val showLogoutBtn by viewModel.showLogoutBtn.observeAsState()
    val haveNewVersion by viewModel.haveNewVersion.observeAsState()
    val cacheSize by viewModel.cacheSize.observeAsState()
    val appVersionName by viewModel.appVersionName.observeAsState()

    var showClearCacheDialog by remember { mutableStateOf(false) }
    var showIntroDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.start()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar(title = stringResource(R.string.title_setting)) { navHostController.popBackStack() }

        SettingItem(
            label = stringResource(R.string.txt_clear_cache),
            value = cacheSize
        ) {
            showClearCacheDialog = true
        }

        SettingItem(
            label = stringResource(R.string.txt_version),
            value = appVersionName,
            showRedCircle = haveNewVersion
        ) {
            viewModel.checkAppUpdate(isManual = true)
        }

        SettingItem(
            label = stringResource(R.string.txt_author),
            value = stringResource(R.string.txt_author_name)
        ) {
            showIntroDialog = true
        }

        SettingItem(
            label = stringResource(R.string.txt_project_source_code)
        ) {
            navHostController.navigate(
                Route.WEB,
                bundleOf(
                    Const.ParamKey.WEB_TYPE to Const.WebType.Url(
                        name = StringUtils.getString(R.string.txt_project_source_code),
                        link = Const.Config.URL_PROJECT_SOURCE_CODE
                    )
                )
            )
        }

        if (showLogoutBtn == true) {
            Button(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = { showLogoutDialog = true }
            ) {
                Text(stringResource(R.string.txt_logout))
            }
        }

        if (showClearCacheDialog) {
            CusAlertDialog(
                content = {
                    Text(stringResource(R.string.txt_confirm_clear_cache))
                },
                confirmText = stringResource(R.string.txt_clear),
                onConfirm = { viewModel.clearAllCache() }
            ) {
                showClearCacheDialog = false
            }
        }

        if (showIntroDialog) {
            CusAlertDialog(
                titleText = stringResource(R.string.txt_contact_author),
                content = {
                    Text(stringResource(R.string.txt_contact_way))
                },
                showCancel = false
            ) {
                showIntroDialog = false
            }
        }

        if (showLogoutDialog) {
            CusAlertDialog(
                content = {
                    Text(stringResource(R.string.txt_confirm_logout))
                },
                onConfirm = {
                    // 手动清除cookie
                    RetrofitManager.cookieJar.clear()
                    UserManager.logout()
                    App.appViewModel.emitUser(null)
                    navHostController.popBackStack()
                }
            ) {
                showLogoutDialog = false
            }
        }
    }
}

@Composable
fun SettingItem(
    label: String,
    value: String? = null,
    showBottomLine: Boolean = true,
    showRedCircle: Boolean? = false,
    onItemClick: (() -> Unit)? = null
) {
    Column(
        Modifier.clickable(
            enabled = onItemClick != null
        ) {
            onItemClick?.invoke()
        }) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label)
            Spacer(modifier = Modifier.weight(1f))
            if (value != null) {
                Text(text = value)
            }
            if (showRedCircle == true) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = LocalContentColor.current.copy(0.5f),
                modifier = Modifier.size(30.dp)
            )
        }
        if (showBottomLine) {
            HorizontalDivider(thickness = Dp.Hairline)
        }
    }
}