package com.wangxingxing.wanandroidcompose.ui.main.project

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme

/**
 * author : 王星星
 * date : 2025/4/8 14:24
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun MineScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Mine",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MineScreenPreview() {
    WanAndroidComposeTheme {
        MineScreen()
    }
}