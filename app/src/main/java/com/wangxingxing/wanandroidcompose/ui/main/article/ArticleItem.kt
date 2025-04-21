package com.wangxingxing.wanandroidcompose.ui.main.article

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.btpj.lib_base.ext.toHtml
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.data.bean.Article
import com.wangxingxing.wanandroidcompose.ui.theme.Green_669900
import com.wangxingxing.wanandroidcompose.ui.theme.Red_FF4A57

/**
 * author : 王星星
 * date : 2025/4/18 17:12
 * email : 1099420259@qq.com
 * description :
 */
@Composable
fun ArticleItem(
    article: Article,
    articleViewModel: ArticleViewModel,
    onArticleClick: (Article) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onArticleClick(article) },
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = article.author.ifEmpty { article.shareUser },
                        fontSize = 13.sp,
                        color = LocalContentColor.current.copy(alpha = 0.8f)
                    )

                    if (article.type == 1) Text(
                        text = stringResource(R.string.isTop),
                        color = Red_FF4A57,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .border(1.dp, Red_FF4A57)
                            .padding(horizontal = 4.dp)
                    )

                    if (article.fresh) Text(
                        text = stringResource(R.string.isNew),
                        color = Red_FF4A57,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .border(1.dp, Red_FF4A57)
                            .padding(horizontal = 4.dp)
                    )

                    if (article.tags.isNotEmpty()) Text(
                        text = article.tags[0].name,
                        color = Green_669900,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .border(1.dp, Green_669900)
                            .padding(horizontal = 4.dp)
                    )
                }
                Text(
                    text = article.niceDate,
                    fontSize = 13.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f)
                )
            }
            Text(
                text = article.title.toHtml().toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = LocalContentColor.current.copy(alpha = 0.9f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${article.superChapterName}.${article.chapterName}".toHtml().toString(),
                    fontSize = 13.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f)
                )
                Icon(
                    imageVector = if (article.collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Red_FF4A57,
                    modifier = Modifier.clickable {
                        articleViewModel.handleCollect(article)
                   }
                )
            }
        }
    }
}