package com.flickrsearch.ui

import android.content.Context
import android.content.Intent
import android.text.Html
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.flickrsearch.model.FlickrImageResponse

@Composable
fun ResultScreen(
    photo: FlickrImageResponse,
    navController: NavController
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberAsyncImagePainter(photo.imageUrl),
                contentDescription = photo.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(bottom = 16.dp)
            )
            Text(text = "Title: ${photo.title}", fontWeight = FontWeight.Bold)
            Text(text = "Author: ${photo.author}", fontWeight = FontWeight.Bold)
            Text(text = "Published: ${photo.published}", fontWeight = FontWeight.Bold)

            val descriptionText =
                Html.fromHtml(photo.description, Html.FROM_HTML_MODE_COMPACT).toString()
            Text(text = "Description: $descriptionText", fontWeight = FontWeight.Bold)
            Button(onClick = { shareImage(photo, navController.context) },) {
                Text(text = "Share Now")
            }
        }
    }
}

fun shareImage(photo: FlickrImageResponse, context: Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            "Look at this image!\n\nTitle: ${photo.title}\nAuthor: ${photo.author}\nPublished: ${photo.published}\nURL: ${photo.imageUrl}"
        )
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}
