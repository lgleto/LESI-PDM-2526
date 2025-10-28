package ipca.example.shoppinglist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

data class ProductsViewState (
    var products : List<Product> = emptyList(),
    var error : String? = null,
    var isLoading : Boolean = false
)

class ProductsViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductsViewState())
        private set

    var cartId : String? = null

    val db = Firebase.firestore
    fun fetchProducts(cartId : String) {
        uiState.value = uiState.value.copy(isLoading = true)
        this.cartId = cartId
        val docRef = db
            .collection("carts")
            .document(cartId)
            .collection("products")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage
                )
                return@addSnapshotListener
            }

            val products = mutableListOf<Product>()
            for (doc in snapshot?.documents?:emptyList()){
                val product = doc.toObject(Product::class.java)
                product?.docId = doc.id
                product?.let {
                    products .add(product)
                }
            }

            uiState.value = uiState.value.copy(
                products = products,
                isLoading = false,
                error = null
            )

        }

    }

}