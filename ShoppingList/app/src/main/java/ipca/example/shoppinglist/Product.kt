package ipca.example.shoppinglist


data class Product (
    var docId : String? = null,
    var name : String? = null,
    var qtd : Double? = null,
    var checked : Boolean? = null
)