package ipca.example.newsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


@Composable
fun ArticlesListView(
    modifier: Modifier = Modifier
) {
    var articles by remember { mutableStateOf<ArrayList<Article>>(arrayListOf()) }
    var status by remember { mutableStateOf<String>("") }

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(
            items = articles,
        ){ index, article ->
            ArticleViewCell(article)
        }
    }

    LaunchedEffect(Unit) {
        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://newsapi.org/v2/everything?q=tesla&from=2025-09-06&sortBy=publishedAt&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                for ((name, value) in response.headers) {
                    println("$name: $value")
                }
                val newsResult = response.body!!.string()
                val jsonResult = JSONObject(newsResult)
                if (jsonResult.getString("status") == "ok") {
                    val articlesJson = jsonResult.getJSONArray("articles")
                    status = "Ok"
                    val articlesList = arrayListOf<Article>()
                    for (i in 0 until articlesJson.length()) {
                        val articleJson = articlesJson.getJSONObject(i)
                        val article = Article.fromJson(articleJson)
                        articlesList.add(article)
                    }
                    status = articlesList.joinToString { it.title + "\n" }
                    articles = articlesList
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesListViewPreview() {
    NewsAppTheme {
        ArticlesListView(
            modifier = Modifier.padding(10.dp)
        )
    }
}