package com.wangxingxing.wanandroidcompose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.LoadingDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.LocalNavController
import com.wangxingxing.wanandroidcompose.core.navigation.Route
import com.wangxingxing.wanandroidcompose.data.local.UserManager

/**
 * author : 王星星
 * date : 2025/4/22 16:11
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val navHostController = LocalNavController.current

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        username = UserManager.getLastUserName()
    }

    if (showLoadingDialog) {
        LoadingDialog(loadingText = stringResource(R.string.txt_login_loading)) { showLoadingDialog = false }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(title = stringResource(R.string.login)) {
            navHostController.popBackStack()
        }

        Image(
            painter = painterResource(R.drawable.ic_logo),
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
            onValueChange = { username = it },
            label = { Text(text = stringResource(id = R.string.user_name))},
            colors = TextFieldDefaults.colors()
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(id = R.string.password)) },
            colors = TextFieldDefaults.colors(),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp, end = 20.dp),
            enabled = username.trim().isNotEmpty() && password.trim().isNotEmpty(),
            onClick = {
                showLoadingDialog = true
                viewModel.login(
                    username.trim(),
                    password.trim(),
                    errorBlock = {
                        showLoadingDialog = false
                    },
                    successBlock = {
                        // 登录成功后的操作
                        showLoadingDialog = false
                        navHostController.popBackStack()
                    }
                )
            }
        ) {
            Text(text = stringResource(id = R.string.login))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, top = 10.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = stringResource(R.string.register),
                modifier = Modifier
                    .clickable {
                        navHostController.navigate(Route.REGISTER)
                    },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}