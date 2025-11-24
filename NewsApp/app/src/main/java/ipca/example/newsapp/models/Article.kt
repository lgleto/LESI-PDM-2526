package ipca.example.newsapp.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

@Entity
data class Article (
    var author      : String? = null,
    var title       : String? = null,
    var description : String? = null,
    @PrimaryKey
    var url         : String = "",
    var urlToImage  : String? = null,
    var publishedAt : String? = null,
){
    companion object{
        fun fromJson(json : JSONObject) : Article {
            return Article(
                json.getString("author"),
                json.getString("title"),
                json.getString("description"),
                json.getString("url"),
                json.getString("urlToImage"),
                json.getString("publishedAt"),
            )
        }
    }
}

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Query("SELECT * FROM article WHERE url = :url")
    fun loadByUrl(url: String): Article?

    // insert & update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}


fun String.encodeUrl() : String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "UTF-8")
}

