package ipca.example.newsapp.models

import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

data class Article (
    var author      : String? = null,
    var title       : String? = null,
    var description : String? = null,
    var url         : String? = null,
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


fun String.encodeUrl() : String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "UTF-8")
}
