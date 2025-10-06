package ipca.example.newsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleViewCell(
    article : Article,
    modifier: Modifier = Modifier
){
    Card (
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(12.dp), // Apply rounded corners

    ){
        Column (
            modifier = Modifier.padding(8.dp),

            ){
            Text(text = article.title ?: "",
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp)
            Text(text = article.description ?: "",
                modifier = Modifier.padding(bottom = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleViewCellPreview(){
    NewsAppTheme {
        ArticleViewCell(
            article = Article(
                "",
                title = "Title akjsfhaskjfas ",
                description = "Description",
                url = "",
                urlToImage = "",
                publishedAt = ""
            )
        )
    }
}