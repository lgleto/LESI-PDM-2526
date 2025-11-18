package ipca.example.shoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Cart
import ipca.example.shoppinglist.repository.CartRepository
import ipca.example.shoppinglist.repository.ResultWrapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class HomeViewState (
    var carts : List<Cart> = emptyList(),
    var error : String? = null,
    var isLoading : Boolean = false
)
@HiltViewModel
class HomeViewModel @Inject constructor(
    val cartRepository: CartRepository
)
    : ViewModel() {

    var uiState = mutableStateOf(HomeViewState())
        private set
    fun fetchCarts() {
        cartRepository.fetchCarts().onEach { result ->
            when(result){
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        carts = result.data?:emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is ResultWrapper.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

    fun creatCart(){


        val userID = Firebase.auth.currentUser?.uid

        val cart = Cart(
            name = "New cart ${uiState.value.carts.size + 1}",
            owners = listOf<String>(userID!!),
        )

        cartRepository.creatCart(cart).onEach { result ->
            when(result){
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                }
                is ResultWrapper.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }

        }.launchIn(viewModelScope)

    }
}