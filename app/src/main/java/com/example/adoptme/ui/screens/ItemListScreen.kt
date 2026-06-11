package com.example.adoptme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.adoptme.R
import com.example.adoptme.ui.viewmodel.ItemViewModel

@Composable
fun ItemListScreen(
    viewModel: ItemViewModel,
    onEditItem: (String) -> Unit,
    onAddItem: () -> Unit
) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItem) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.title_add_pet))
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
                items.isEmpty() -> Text(stringResource(R.string.msg_no_data), modifier = Modifier.align(Alignment.Center))
                else -> {
                    LazyColumn {
                        items(items) { item ->
                            ListItem(
                                leadingContent = {
                                    AsyncImage(
                                        model = item.avatar,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp).clip(CircleShape),
                                        contentScale = ContentScale.Crop,
                                        error = null // Puedes poner un placeholder aquí
                                    )
                                },
                                headlineContent = { Text(item.name) },
                                supportingContent = { Text(item.description) },
                                trailingContent = {
                                    Row {
                                        item.id?.let { id ->
                                            IconButton(onClick = { onEditItem(id) }) {
                                                Icon(Icons.Default.Edit, contentDescription = null)
                                            }
                                            IconButton(onClick = { viewModel.deleteItem(id) }) {
                                                Icon(Icons.Default.Delete, contentDescription = null)
                                            }
                                        }
                                    }
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
