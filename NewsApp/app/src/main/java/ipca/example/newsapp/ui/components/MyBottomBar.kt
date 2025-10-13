package ipca.example.newsapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun MyBottomBar(
    navController: NavController
){
    BottomAppBar {
        NavigationBarItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "TechCrunch"
                )
            },
            label = {
                Text("TechCrunch")
            },
            onClick = {
                navController.navigate("tech_crunch")
            }
        )
        NavigationBarItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Blooberg"
                )
            },
            label = {
                Text("Blooberg")
            },
            onClick = {
                navController.navigate("bloomberg")
            }
        )
        NavigationBarItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "ESPN"
                )
            },
            label = {
                Text("ESPN")
            },
            onClick = {
                navController.navigate("espn")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview(){
    NewsAppTheme {
        MyBottomBar(navController = rememberNavController())
    }
}