package ipca.example.calculator

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.calculator.ui.theme.CalculatorTheme


@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    label : String,
    onNumPressed : (String) -> Unit
){
    Button(
        modifier = modifier
            .size(90.dp)
            .padding(8.dp),
        onClick = {
            onNumPressed(label)
        }
    ) {
        Text(
            label,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorButtonPreview(){
    CalculatorTheme {
        CalculatorButton(
            modifier = Modifier,
            label = "7",
            onNumPressed = {}
        )
    }
}