package com.wangxingxing.wanandroidcompose.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.btpj.lib_base.base.BaseActivity
import com.wangxingxing.wanandroidcompose.core.navigation.AppScreen
import com.wangxingxing.wanandroidcompose.core.navigation.NavGraph
import com.wangxingxing.wanandroidcompose.ui.theme.WanAndroidComposeTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * author : 王星星
 * date : 2025/4/8 16:00
 * email : 1099420259@qq.com
 * description :
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            WanAndroidComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    navHostController = rememberNavController()
                    NavGraph(navHostController, innerPadding)
                }

            }
        }
    }

}

