package ipca.example.newsapp.ui.articles

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.example.newsapp.models.AppDatabase
import ipca.example.newsapp.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ArticlesDBListState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ArticlesDBListViewModel : ViewModel() {

    var uiState = mutableStateOf(ArticlesDBListState())
        private set

    fun fetchArticles(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
        val articles = AppDatabase
            .getInstance(context)
            ?.articleDao()
            ?.getAll()
            viewModelScope.launch(Dispatchers.Main) {
                uiState.value = uiState.value.copy(
                    articles = articles ?: emptyList()
                )
            }

        }
    }

}
