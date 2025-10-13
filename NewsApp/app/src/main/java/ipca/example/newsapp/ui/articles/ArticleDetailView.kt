package ipca.example.newsapp.ui.articles

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleDetailView(
    modifier: Modifier = Modifier,
    url: String,
    navController: NavController
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
            }
        },
        update = {
            it.loadUrl(url)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailViewPreview(){
    NewsAppTheme {
        ArticleDetailView(
            url = "https://www.google.com",
            navController = rememberNavController()
        )

    }
}