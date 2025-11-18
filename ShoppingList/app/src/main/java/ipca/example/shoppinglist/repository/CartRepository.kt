package ipca.example.shoppinglist.repository

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val db : FirebaseFirestore
){

    fun fetchCarts() : Flow<ResultWrapper<List<Cart>>> = flow {
        try {
            emit(ResultWrapper.Loading())
            val docRef = db
                .collection("carts")
                .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)


            docRef
                .snapshotFlow()
                .collect()
                {
                    val carts = mutableListOf<Cart>()
                    for (doc in it.documents ?: emptyList()) {
                        val cart = doc.toObject(Cart::class.java)
                        cart?.docId = doc.id
                        cart?.let {
                            carts.add(cart)
                        }
                    }

                    emit(ResultWrapper.Success(carts.toList()))
                }


        }catch (e:Exception){
            emit(ResultWrapper.Error(e.localizedMessage?:"Unexpected Error"))
        }
    }.flowOn(Dispatchers.IO)

    fun creatCart(cart : Cart) : Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("carts")
                .add(cart)
                .await()

            emit(ResultWrapper.Success(Unit))
        }catch (e:Exception){
            emit(ResultWrapper.Error(e.localizedMessage?:"Unexpected Error"))
        }
    }.flowOn(Dispatchers.IO)

}
