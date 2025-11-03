package ipca.example.shoppinglist.ui.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.models.Product
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme


@Composable
fun ProductDetailView (
    navController: NavController,
    modifier: Modifier = Modifier,
    cartId : String,
    productId: String?
){

    val viewModel : ProductDetailViewModel = viewModel()
    val uiState by viewModel.uiState


    LaunchedEffect(productId) {
        viewModel.fetchProduct(cartId, productId)
    }


    Column {
        TextField(
            value = uiState.name ?: "",
            label = { Text("Name") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updateName(it)
            }
        )
        TextField(
            value = uiState.qtd?.toString() ?: "",
            label = { Text("Qtd") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updateQtd(it.toDouble())
            }
        )
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                viewModel.createProduct(cartId)
                navController.popBackStack()
            }
        ) {
            Text(if (productId == null) "Create" else "Update")
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ProductDetailViewPreview() {
    ShoppingListTheme {
        ProductDetailView(
            navController = rememberNavController(),
            cartId = "123",
            productId = null
        )
    }
}
