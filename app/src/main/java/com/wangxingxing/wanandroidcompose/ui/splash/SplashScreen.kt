package com.wangxingxing.wanandroidcompose.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wangxingxing.wanandroidcompose.R

/**
 * author : 王星星
 * date : 2025/4/14 17:18
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    // 加载 Lottie 动画
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.anim_splash)
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE, // 无限循环播放
            modifier = Modifier
                .size(200.dp) // 设置动画大小
                .align(Alignment.Center) // 居中显示
        )
    }
}