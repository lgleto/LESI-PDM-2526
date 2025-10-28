package ipca.example.shoppinglist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

data class ProductDetailViewState (
    var name : String? = null,
    var qtd : Double? = null,
    var error : String? = null,
    var isLoading : Boolean = false
)

class ProductDetailViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductDetailViewState())
        private set



    val db = Firebase.firestore

    fun updateName(name : String) {
        uiState.value = uiState.value.copy(name = name)
    }

    fun updateQtd(qtd : Double) {
        uiState.value = uiState.value.copy(qtd = qtd)
    }

    fun createProduct(cartId : String){
        uiState.value = uiState.value.copy(isLoading = true)

        var product = Product(
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

    }

}