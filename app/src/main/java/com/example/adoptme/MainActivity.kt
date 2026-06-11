package com.example.adoptme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.adoptme.ui.screens.ItemListScreen
import com.example.adoptme.ui.screens.ItemUpsertScreen
import com.example.adoptme.ui.screens.UserListScreen
import com.example.adoptme.ui.screens.UserUpsertScreen
import com.example.adoptme.ui.theme.AdoptMeTheme
import com.example.adoptme.ui.viewmodel.ItemViewModel
import com.example.adoptme.ui.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private var isLoggedIn by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdoptMeTheme {
                MainScreen(
                    isLoggedIn = isLoggedIn,
                    onLogout = {
                        SessionManager.logout(this)
                        isLoggedIn = false
                    },
                    onGoToLogin = {
                        Navigation.openLogin(this)
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isLoggedIn = SessionManager.isLoggedIn(this)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(isLoggedIn: Boolean, onLogout: () -> Unit, onGoToLogin: () -> Unit) {
    val navController = rememberNavController()
    val itemViewModel: ItemViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    // Redirigir a home si se cierra sesión estando en una pantalla protegida
    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute == "items" || currentRoute == "users") {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AdoptMe") },
                actions = {
                    if (isLoggedIn) {
                        IconButton(onClick = onLogout) {
                            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Salir")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Inicio") },
                    selected = currentDestination?.route == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                    label = { Text("Mascotas") },
                    selected = currentDestination?.hierarchy?.any { it.route?.startsWith("items") == true } == true,
                    onClick = {
                        if (isLoggedIn) navController.navigate("items") else onGoToLogin()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Usuarios") },
                    selected = currentDestination?.hierarchy?.any { it.route?.startsWith("users") == true } == true,
                    onClick = {
                        if (isLoggedIn) navController.navigate("users") else onGoToLogin()
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                AndroidView(factory = { context ->
                    val view = LayoutInflater.from(context).inflate(R.layout.activity_main, null)
                    view.findViewById<View>(R.id.bottomNav)?.visibility = View.GONE
                    view
                }, update = { view ->
                    val btnLogin = view.findViewById<Button>(R.id.btnGoLogin)
                    val btnRegister = view.findViewById<Button>(R.id.btnRegister)
                    
                    if (isLoggedIn) {
                        btnLogin?.text = "Gestionar Mascotas"
                        btnLogin?.setOnClickListener { navController.navigate("items") }
                        btnRegister?.visibility = View.GONE
                    } else {
                        btnLogin?.text = "Iniciar Sesión"
                        btnLogin?.setOnClickListener { onGoToLogin() }
                        btnRegister?.visibility = View.VISIBLE
                        btnRegister?.setOnClickListener { Navigation.openRegister(view.context) }
                    }
                })
            }

            composable("items") {
                ItemListScreen(
                    viewModel = itemViewModel,
                    onEditItem = { id -> navController.navigate("items_upsert?id=$id") },
                    onAddItem = { navController.navigate("items_upsert") }
                )
            }
            composable(
                route = "items_upsert?id={id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType; nullable = true })
            ) { backStackEntry ->
                ItemUpsertScreen(
                    viewModel = itemViewModel,
                    itemId = backStackEntry.arguments?.getString("id"),
                    onBack = { navController.popBackStack() }
                )
            }

            composable("users") {
                UserListScreen(
                    viewModel = userViewModel,
                    onEditUser = { id -> navController.navigate("users_upsert?id=$id") },
                    onAddUser = { navController.navigate("users_upsert") }
                )
            }
            composable(
                route = "users_upsert?id={id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType; nullable = true })
            ) { backStackEntry ->
                UserUpsertScreen(
                    viewModel = userViewModel,
                    userId = backStackEntry.arguments?.getString("id"),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
