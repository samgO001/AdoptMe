package com.example.adoptme.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.adoptme.data.model.Item
import com.example.adoptme.ui.theme.BrownDark
import com.example.adoptme.ui.theme.BrownPrimary
import com.example.adoptme.ui.theme.OrangeAccent
import com.example.adoptme.ui.theme.OrangeDark
import com.example.adoptme.ui.theme.StatsYellow
import com.example.adoptme.ui.viewmodel.ItemViewModel

@Composable
fun HomeScreen(
    userEmail: String?,
    viewModel: ItemViewModel,
    onExplorePets: () -> Unit,
    onExploreShelters: () -> Unit
) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val userName = userEmail?.substringBefore("@")?.replaceFirstChar { it.uppercase() } ?: "Amigo"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeroSection(userName = userName, onExplorePets = onExplorePets)

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            MissionCard()

            StatsSection()

            HowItWorksSection()

            QuickActionsSection(
                onExplorePets = onExplorePets,
                onExploreShelters = onExploreShelters
            )

            FeaturedPetsSection(
                items = items,
                isLoading = isLoading,
                onExplorePets = onExplorePets
            )

            StoryCard()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HeroSection(userName: String, onExplorePets: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BrownPrimary, OrangeDark)
                )
            )
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "AdoptMe",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Conectando patas con corazones",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¡Hola, $userName!",
                color = StatsYellow,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Encuentra a tu nuevo\nmejor amigo",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Somos el puente entre refugios de adopción y personas que buscan dar amor a una mascota.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onExplorePets,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = BrownPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Explorar mascotas", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun MissionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(StatsYellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = OrangeDark)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = "Nuestra misión",
                    fontWeight = FontWeight.Bold,
                    color = BrownDark,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Facilitamos adopciones responsables conectando refugios verificados con familias comprometidas.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun StatsSection() {
    Column {
        Text(
            text = "Impacto AdoptMe",
            fontWeight = FontWeight.Bold,
            color = BrownDark,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(value = "+120", label = "Mascotas\nadoptadas", modifier = Modifier.weight(1f))
            StatCard(value = "+90", label = "Familias\nfelices", modifier = Modifier.weight(1f))
            StatCard(value = "+50", label = "Refugios\naliados", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = StatsYellow.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontWeight = FontWeight.Bold, color = BrownPrimary, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = BrownDark,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
private fun HowItWorksSection() {
    Column {
        Text(
            text = "¿Cómo funciona?",
            fontWeight = FontWeight.Bold,
            color = BrownDark,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        StepItem(
            number = "1",
            title = "Explora mascotas",
            description = "Navega perfiles de peluditos disponibles en refugios aliados."
        )
        StepItem(
            number = "2",
            title = "Conócelos en persona",
            description = "Agenda una visita y crea conexión con tu futuro compañero."
        )
        StepItem(
            number = "3",
            title = "Adopta con confianza",
            description = "Te acompañamos en todo el proceso de adopión responsable."
        )
    }
}

@Composable
private fun StepItem(number: String, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(OrangeAccent),
            contentAlignment = Alignment.Center
        ) {
            Text(text = number, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.SemiBold, color = BrownDark, fontSize = 15.sp)
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun QuickActionsSection(
    onExplorePets: () -> Unit,
    onExploreShelters: () -> Unit
) {
    Column {
        Text(
            text = "Acciones rápidas",
            fontWeight = FontWeight.Bold,
            color = BrownDark,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                icon = Icons.Default.Pets,
                title = "Mascotas",
                subtitle = "Ver disponibles",
                onClick = onExplorePets,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Default.LocationOn,
                title = "Refugios",
                subtitle = "Lugares aliados",
                onClick = onExploreShelters,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BrownPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = StatsYellow, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = subtitle, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = OrangeAccent,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun FeaturedPetsSection(
    items: List<Item>,
    isLoading: Boolean,
    onExplorePets: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mascotas destacadas",
                fontWeight = FontWeight.Bold,
                color = BrownDark,
                fontSize = 18.sp
            )
            OutlinedButton(onClick = onExplorePets) {
                Text("Ver todas", fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = OrangeAccent)
                }
            }
            items.isEmpty() -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null, tint = OrangeAccent)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Pronto habrá mascotas esperando un hogar. ¡Vuelve pronto!",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            else -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(end = 4.dp)
                ) {
                    items(items.take(5)) { item ->
                        FeaturedPetCard(item = item, onClick = onExplorePets)
                    }
                }
            }
        }
    }
}

@Composable
private fun FeaturedPetCard(item: Item, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            AsyncImage(
                model = item.avatar,
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    color = BrownDark,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun StoryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BrownPrimary.copy(alpha = 0.08f))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "\"Luna llegó a mi vida y todo cambió\"",
                fontWeight = FontWeight.SemiBold,
                color = BrownDark,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "— Sofía, adoptante feliz",
                color = OrangeDark,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
