package ipca.example.shoppinglist.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme


@Composable
fun HomeView (
    navController: NavController,
    modifier: Modifier = Modifier
){

    val viewModel : HomeViewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.fetchCarts()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(uiState.error!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = uiState.carts
                ) { index, item ->

                    Text(
                        text = item.name?:"",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable{
                                navController
                                    .navigate("products/${item.docId}")
                            }
                        ,
                        fontSize = 32.sp
                    )
                }
            }
        }
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                viewModel.creatCart()
            }) {
            Text("Add cart")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    ShoppingListTheme {
        HomeView(
            navController = rememberNavController()
        )
    }
}
