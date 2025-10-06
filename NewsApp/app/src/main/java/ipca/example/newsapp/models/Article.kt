package ipca.example.newsapp.models

import org.json.JSONObject

data class Article (
    var author : String?,
    var title : String?,
    var description : String?,
    var url : String?,
    var urlToImage : String?,
    var publishedAt : String?,
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

