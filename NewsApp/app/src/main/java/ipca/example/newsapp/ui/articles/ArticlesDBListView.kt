package ipca.example.newsapp.ui.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.models.encodeUrl
import ipca.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


@Composable
fun ArticlesListView(
    modifier: Modifier = Modifier,
    navController: NavController,
    source: String = "",
    onArticleClick: (Article) -> Unit = {}
) {

    val viewModel : ArticlesListViewModel = viewModel()
    val uiState by viewModel.uiState

    ArticlesListViewContent(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
        onArticleClick = onArticleClick
    )

    LaunchedEffect(Unit) {
        viewModel.fetchArticles(source)
    }
}

@Composable
fun ArticlesListViewContent(
    modifier: Modifier = Modifier,
    uiState: ArticlesListState,
    navController: NavController,
    onArticleClick: (Article) -> Unit = {}
) {

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(uiState.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center)
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(
                    items = uiState.articles,
                ) { index, article ->
                    ArticleViewCell(article){
                        onArticleClick(article)
                        navController.navigate("article/${article.url?.encodeUrl()}")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArticlesListViewPreview() {
    NewsAppTheme {
        ArticlesListViewContent(
            modifier = Modifier.padding(10.dp),
            uiState = ArticlesListState(
                articles = listOf(
                    Article(
                        title = "Title 1",
                        description = "Description 1"),
                    Article(
                        title = "Title 2",
                        description = "Description 2")

                ),
                isLoading = false,
                error = "No internet connection"
            ),
            navController = rememberNavController()

        )
    }
}