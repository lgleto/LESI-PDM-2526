package ipca.example.shoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Cart

data class HomeViewState (
    var carts : List<Cart> = emptyList(),
    var error : String? = null,
    var isLoading : Boolean = false
)

class HomeViewModel : ViewModel() {

    var uiState = mutableStateOf(HomeViewState())
        private set

    val db = Firebase.firestore
    fun fetchCarts() {
        uiState.value = uiState.value.copy(isLoading = true)

        val docRef = db
            .collection("carts")
            .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage
                )
                return@addSnapshotListener
            }

            val carts = mutableListOf<Cart>()
            for (doc in snapshot?.documents?:emptyList()){
                val cart = doc.toObject(Cart::class.java)
                cart?.docId = doc.id
                cart?.let {
                    carts.add(cart)
                }

            }


            uiState.value = uiState.value.copy(
                carts = carts,
                isLoading = false,
                error = null
            )

        }

    }

    fun creatCart(){
        uiState.value = uiState.value.copy(isLoading = true)

        val userID = Firebase.auth.currentUser?.uid

        val user = Cart(
            name = "New cart ${uiState.value.carts.size + 1}",
            owners = listOf<String>(userID!!),
        )

        db.collection("carts")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }
}