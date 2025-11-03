package ipca.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ipca.example.shoppinglist.ui.home.HomeView
import ipca.example.shoppinglist.ui.login.LoginView
import ipca.example.shoppinglist.ui.products.ProductDetailView
import ipca.example.shoppinglist.ui.products.ProductsView
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

const val TAG = "ShoppingList"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable ("login"){
                            LoginView(
                                navController = navController
                            )
                        }

                        composable ("home"){
                            HomeView(
                                navController = navController
                            )
                        }

                        composable ("products/{cartId}"){
                            val cartId = it.arguments?.getString("cartId")
                            ProductsView(
                                navController = navController,
                                cartId = cartId!!
                            )
                        }

                        composable ("product_detail/{cartId}/{productId}"){
                            val cartId = it.arguments?.getString("cartId")
                            val productId = it.arguments?.getString("productId")
                            ProductDetailView(
                                navController = navController,
                                cartId = cartId!!,
                                productId = productId
                            )
                        }
                        composable ("product_detail/{cartId}"){
                            val cartId = it.arguments?.getString("cartId")
                            ProductDetailView(
                                navController = navController,
                                cartId = cartId!!,
                                productId = null
                            )
                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                val userID = Firebase.auth.currentUser?.uid
                if (userID != null){
                    navController.navigate("home")
                }
            }
        }
    }
}
