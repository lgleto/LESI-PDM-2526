package ipca.example.shoppinglist.ui.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.shoppinglist.models.Product
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ProductViewCell(
    modifier: Modifier = Modifier,
    product: Product,
    onClick : () -> Unit = {},
    onCheck : (Boolean) -> Unit = {}
){

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.name ?: "",
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                fontSize = 32.sp
            )
            Text(
                text = product.qtd?.toString() ?: "",
                modifier = Modifier
                    .padding(16.dp),
                fontSize = 32.sp
            )
            Checkbox(
                checked = product.checked ?: false,
                onCheckedChange = {
                    //product.checked = it
                    onCheck(it)
                }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductViewCellPreview(){
    ShoppingListTheme {
        ProductViewCell(
            product = Product(
                name = "Product 1",
                qtd = 1.0,
                checked = false
            )
        )
    }
}