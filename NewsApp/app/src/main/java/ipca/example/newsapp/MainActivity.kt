package ipca.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.newsapp.ui.articles.ArticleDetailView
import ipca.example.newsapp.ui.articles.ArticlesListView
import ipca.example.newsapp.ui.components.MyBottomBar
import ipca.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var navTitle by remember { mutableStateOf("TechCrunch") }
            var isHomeScreen by remember { mutableStateOf(true) }
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = navTitle)
                            },
                            actions = {

                            },
                            navigationIcon = {
                                if (!isHomeScreen)
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        MyBottomBar(navController = navController)
                    }

                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "tech_crunch"
                    ){
                        composable("tech_crunch"){
                            navTitle = "TechCrunch"
                            isHomeScreen = true
                            ArticlesListView(
                                navController = navController,
                                source = "techcrunch"
                            )
                        }
                        composable("bloomberg"){
                            navTitle = "Blooberg"
                            isHomeScreen = true
                            ArticlesListView(
                                navController = navController,
                                source = "bloomberg"
                            )
                        }
                        composable("espn"){
                            navTitle = "ESPN"
                            isHomeScreen = true
                            ArticlesListView(
                                navController = navController,
                                source = "espn"
                            )
                        }
                        composable("article/{url}"){
                            val url = it.arguments?.getString("url")?:""
                            isHomeScreen = false
                            ArticleDetailView(
                                url = url,
                                navController = navController
                            )
                        }
                    }

                }
            }
        }
    }
}

