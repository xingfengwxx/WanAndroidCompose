package com.wangxingxing.wanandroidcompose.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.core.navigation.Route

/**
 * author : 王星星
 * date : 2025/4/14 17:18
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun SplashScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    // 加载 Lottie 动画
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.anim_splash)
    )

    // 监听动画进度
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1, // 只播放一次
        isPlaying = true
    )

    // 监听动画是否结束
    LaunchedEffect(progress) {
        if (progress >= 1f) { // 动画播放结束
            navHostController.navigate(Route.HOME) // 跳转到 HomeScreen
        }
    }

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