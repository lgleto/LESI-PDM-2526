package ipca.example.helloworld

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text


@Composable
fun Greet(
    modifier: Modifier = Modifier,
    text : String = "john"
) {

    var name by remember { mutableStateOf("Olá, $text!") }
    var greet by remember { mutableStateOf(text) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = name,
            onValueChange = {
                name = it
            }
        )
        Button({
            greet = "Olá, $name!"
        }) {
            Text("Cumprimentar")
        }
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = greet)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetPreview() {
    Greet(text = "shdsghgd")
}

