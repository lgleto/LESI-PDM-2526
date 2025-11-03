package ipca.example.shoppinglist.ui.products

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Product

data class ProductDetailViewState (
    var name : String? = null,
    var qtd : Double? = null,
    var error : String? = null,
    var isLoading : Boolean = false
)

class ProductDetailViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductDetailViewState())
        private set

    var productId : String? = null

    val db = Firebase.firestore

    fun updateName(name : String) {
        uiState.value = uiState.value.copy(name = name)
    }

    fun updateQtd(qtd : Double) {
        uiState.value = uiState.value.copy(qtd = qtd)
    }

    fun createProduct(cartId : String){
        uiState.value = uiState.value.copy(isLoading = true)

        if (productId == null) {

            val product = Product(
                name = uiState.value.name,
                qtd = uiState.value.qtd,
                checked = false
            )

            db.collection("carts")
                .document(cartId)
                .collection("products")
                .add(product)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

        }else{
            db.collection("carts")
                .document(cartId)
                .collection("products")
                .document(productId!!)
                .update(
                    mapOf(
                        "name" to uiState.value.name,
                        "qtd" to uiState.value.qtd)
                )
        }
    }

    fun fetchProduct(cartId : String, productId : String?) {
        this.productId = productId
        if (productId == null){
            return
        }
        uiState.value = uiState.value.copy(isLoading = true)
        db.collection("carts")
            .document(cartId)
            .collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)
                uiState.value = uiState.value.copy(
                    name = product?.name,
                    qtd = product?.qtd,
                    isLoading = false,
                    error = null
                )
            }



    }

}