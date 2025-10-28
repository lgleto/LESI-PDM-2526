package ipca.example.shoppinglist

data class Cart (
    var docId : String? = null,
    var name : String? = null,
    var owners : List<String>? = null,
)