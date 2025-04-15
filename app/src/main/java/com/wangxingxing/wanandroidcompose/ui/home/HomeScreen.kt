package com.wangxingxing.wanandroidcompose.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme

/**
 * author : 王星星
 * date : 2025/4/8 14:24
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WanAndroidComposeTheme {
        HomeScreen(navHostController = NavHostController(LocalContext.current), name ="Android")
    }
}