package ipca.example.shoppinglist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme


@Composable
fun ProductDetailView (
    navController: NavController,
    modifier: Modifier = Modifier,
    cartId : String
){

    val viewModel : ProductDetailViewModel = viewModel()
    val uiState by viewModel.uiState



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
            }
        ) {
            Text("Add")
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ProductDetailViewPreview() {
    ShoppingListTheme {
        ProductDetailView(
            navController = rememberNavController(),
            cartId = "123"
        )
    }
}
