package ipca.example.shoppinglist.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ipca.example.shoppinglist.TAG

data class LoginState (
    var email : String? = null,
    var password : String? = null,
    var error : String? = null,
    var isLoading : Boolean = false
)

class LoginViewModel : ViewModel() {

    var uiState = mutableStateOf(LoginState())
        private set

    fun updateEmail(email : String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun updatePassword(password : String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess:()->Unit) {
        uiState.value = uiState.value.copy(isLoading = true)

        if (uiState.value.email.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Email is required")
            return
        }

        if (uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Password is required")
            return
        }

        var auth: FirebaseAuth
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(
            uiState.value.email!!,
            uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = null)
                    onLoginSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = "Wrong password or no internet connection")
                }
            }
    }

}