package com.wangxingxing.wanandroidcompose.ui.login.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.blankj.utilcode.util.ToastUtils
import com.btpj.lib_base.ui.widgets.LoadingDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.data.local.UserManager

/**
 * author : 王星星
 * date : 2025/4/28 14:15
 * email : 1099420259@qq.com
 * description :
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val navHostController = LocalNavController.current

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordSure by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordSureVisibility by remember { mutableStateOf(false) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showLoadingDialog) {
        LoadingDialog(loadingText = stringResource(R.string.txt_register_loading)) { showLoadingDialog = false }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(
            title = stringResource(R.string.register),
        ) { navHostController.popBackStack() }

        AsyncImage(
            model = R.drawable.ic_logo,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 48.dp)
                .size(100.dp)
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = username,
            onValueChange = { username = it},
            label = { Text(stringResource(R.string.user_name))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.4f)
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            value = password,
            onValueChange = { password = it},
            label = { Text(stringResource(R.string.password))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.4f)
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
                }
            }
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            value = passwordSure,
            onValueChange = { passwordSure = it},
            label = { Text(stringResource(R.string.password_sure))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.4f)
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
                }
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp, end = 20.dp),
            enabled = username.trim().isNotEmpty() && password.trim().isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(0.8f)
            ),
            onClick = {
                if (password.length < 6) {
                    ToastUtils.showShort(R.string.toast_password_length_limit)
                    return@Button
                }
                if (password != passwordSure) {
                    ToastUtils.showShort(R.string.toast_password_disagree)
                    return@Button
                }

                // 执行注册操作
                showLoadingDialog = true
                viewModel.register(
                    userName = username,
                    pwd = password,
                    pwdSure = passwordSure,
                    errorBlock = { showLoadingDialog = false}
                ) {
                    // 注册成功后的操作
                    showLoadingDialog = false
                    UserManager.saveLastUserName(username)
                    navHostController.popBackStack()
                }
            }
        ) {
            Text(stringResource(R.string.register))
        }
    }
}