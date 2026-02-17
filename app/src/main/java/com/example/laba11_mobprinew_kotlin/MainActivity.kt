package com.example.laba11_mobprinew_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.laba11_mobprinew_kotlin.screens.PostListPagingScreen
import com.example.laba11_mobprinew_kotlin.screens.PostListScreen
import com.example.laba11_mobprinew_kotlin.ui.theme.Laba11_MobPriNew_KotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laba11_MobPriNew_KotlinTheme {
                TaskApp()
            }
        }
    }
}


@Composable
fun TaskApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val currEntry by navController.currentBackStackEntryAsState()
                val currScreen = currEntry?.destination?.route
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "1") },
                    label = { Text("PostListPaging") },
                    selected = currScreen == "1",
                    onClick = { navController.navigate("1") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "2") },
                    label = { Text("PostListScreen") },
                    selected = currScreen == "2",
                    onClick = { navController.navigate("2") }
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "1",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("1") { PostListPagingScreen() }
            composable("2") { PostListScreen() }
        }
    }
}
