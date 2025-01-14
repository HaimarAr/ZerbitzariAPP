package com.example.zerbitzariapp

import android.net.http.HttpResponseCache.install
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zerbitzariapp.ui.theme.ZerbitzariAppTheme
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.entity.ContentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray



@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZerbitzariAppTheme {
                NavigationHandler()
            }
        }
    }
}


fun fetchTables(onResult: (List<String>) -> Unit) {
    val client = OkHttpClient()
    val url = "http://10.0.2.2/getMesas.php" // Cambia esto si usas un dispositivo físico, usa la IP de tu PC en lugar de 10.0.2.2
    val request = Request.Builder()
        .url(url)
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val jsonData = response.body?.string()

                val tableList = mutableListOf<String>()

                if (!jsonData.isNullOrEmpty()) {
                    val jsonArray = JSONArray(jsonData)
                    for (i in 0 until jsonArray.length()) {
                        val table = jsonArray.getJSONObject(i).getString("id") // Aquí asumes que el JSON tiene un campo "id"
                        tableList.add(table)
                    }
                }

                // Log de las mesas obtenidas
                Log.d("FetchTables", "Table List: $tableList")

                // Llamar a onResult en el hilo principal
                withContext(Dispatchers.Main) {
                    onResult(tableList)
                }
            } else {
                Log.e("FetchTables", "Error: ${response.code}")
            }
        } catch (e: Exception) {
            // Manejo de excepciones
            e.printStackTrace()
            Log.e("FetchTables", "Request failed: ${e.message}")
        }
    }

}

fun fetchProduktua(onResult: (List<Produktua>) -> Unit) {
    val client = OkHttpClient()
    val url = "http://10.0.2.2/get.php"

    val request = Request.Builder()
        .url(url)
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val jsonData = response.body?.string()

                val produktuak = mutableListOf<Produktua>()

                if (!jsonData.isNullOrEmpty()) {
                    val jsonArray = JSONArray(jsonData)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val produktua = Produktua(
                            id = jsonObject.getInt("id"),
                            izena = jsonObject.getString("izena"),
                            deskribapena = jsonObject.getString("deskribapena"),
                            prezioa = jsonObject.getDouble("prezioa").toFloat(),
                            erosketaPrezioa = jsonObject.getDouble("erosketaPrezioa").toFloat(),
                            kantitatea = jsonObject.getInt("kantitatea"),
                            kantitateMinimoa = jsonObject.getInt("kantitateMinimoa"),
                            mota = jsonObject.getInt("mota")
                        )
                        produktuak.add(produktua)
                    }
                }

                // Log de los productos obtenidos
                Log.d("FetchProduktua", "Product List: $produktuak")

                // Llamar a onResult en el hilo principal
                withContext(Dispatchers.Main) {
                    onResult(produktuak)
                }
            } else {
                Log.e("FetchProduktua", "Error: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FetchProduktua", "Request failed: ${e.message}")
        }
    }
}

fun fetchErabiltzaileak(onResult: (List<Zerbitzaria>) -> Unit) {
    val client = OkHttpClient()
    val url = "http://10.0.2.2/getZerbitzariak.php" // Cambia esto si usas un dispositivo físico, usa la IP de tu PC en lugar de 10.0.2.2
    val request = Request.Builder()
        .url(url)
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val jsonData = response.body?.string()

                val zerbitzariak = mutableListOf<Zerbitzaria>()

                if (!jsonData.isNullOrEmpty()) {
                    val jsonArray = JSONArray(jsonData)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val zerbitzaria = Zerbitzaria(
                            id = jsonObject.getInt("id"),
                            izena = jsonObject.getString("izena")
                        )
                        zerbitzariak.add(zerbitzaria)
                    }
                }

                // Llamar a onResult en el hilo principal
                withContext(Dispatchers.Main) {
                    onResult(zerbitzariak)
                }
            } else {
                Log.e("FetchErabiltzaileak", "Error: ${response.code}")
            }
        } catch (e: Exception) {
            // Manejo de excepciones
            e.printStackTrace()
            Log.e("FetchErabiltzaileak", "Request failed: ${e.message}")
        }
    }
}

sealed class Screen {
    object CharlieApp : Screen()
    object MainScreen : Screen()
    object MahaiakAukeratu : Screen()
    object KomandaAukeratu : Screen()
    object EntranteakScreen : Screen()
    object KomandaScreen : Screen()
    object LehenPlateraScreen: Screen()
    object EdariakScreen: Screen()
    object PostreakScreen: Screen()

}

@Composable
fun NavigationHandler() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.CharlieApp) }

    when (currentScreen) {
        is Screen.CharlieApp ->
            CharlieApp(onNavigateToMainScreen = { currentScreen = Screen.MainScreen })

        is Screen.MainScreen ->
            MainScreen(onNavigateToMahaiakAukeratu = { currentScreen = Screen.MahaiakAukeratu })

        is Screen.MahaiakAukeratu ->
            MahaiakAukeratu(onNavigateToKomandaAukeratu = { currentScreen = Screen.KomandaAukeratu })

        is Screen.KomandaAukeratu ->
            KomandaAukeratuScreen(
                onNavigateToEntranteak = { currentScreen = Screen.EntranteakScreen },
                onNavigateToLehenPlatera = { currentScreen = Screen.LehenPlateraScreen },
                onNavigateToPostreak = { currentScreen = Screen.PostreakScreen },
                onNavigateToEdariak = { currentScreen = Screen.EdariakScreen },
                onNavigateToKomanda = { currentScreen = Screen.KomandaScreen }
            )

        is Screen.EntranteakScreen ->
            EntranteakScreen(onNavigateToKomandaAukeratu = { currentScreen = Screen.KomandaAukeratu })

        is Screen.LehenPlateraScreen ->
            LehenPlateraScreen(onNavigateToKomandaAukeratu = { currentScreen = Screen.KomandaAukeratu })

        is Screen.PostreakScreen ->
            PostreakScreen(onNavigateToKomandaAukeratu = { currentScreen = Screen.KomandaAukeratu })

        is Screen.EdariakScreen ->
            EdariakScreen(onNavigateToKomandaAukeratu = { currentScreen = Screen.KomandaAukeratu })

        is Screen.KomandaScreen -> KomandaScreen()
    }
}


@Composable
fun ProduktuaListScreen(mota: Int, title: String, onNavigateToKomandaAukeratu: () -> Unit) {
    var produktuak by remember { mutableStateOf<List<Produktua>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val selectedQuantities = remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    // Fetch data filtered by mota
    LaunchedEffect(Unit) {
        fetchProduktua { result ->
            produktuak = result.filter { it.mota == mota }
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                border = BorderStroke(2.dp, Color.Black),
                onClick = { /* Handle Atzera */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
            ) {
                Text(text = "Atzera", color = Color.Black)
            }

            Button(
                border = BorderStroke(2.dp, Color.Black),
                onClick = { /* Handle Txat */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
            ) {
                Text(text = "Txat", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = title, color = Color.Black)

        if (isLoading) {
            CircularProgressIndicator(color = Color(0xFFFF6600))
        } else {
            produktuak.forEach { produktua ->
                KomandaItemRow(
                    itemName = "${produktua.izena}",
                    initialQuantity = 0,
                    onQuantityChanged = { quantity ->
                        // Update the quantity of the product in the map
                        selectedQuantities.value = selectedQuantities.value.toMutableMap().apply {
                            put(produktua.id, quantity)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Handle inserting to the database
                onNavigateToKomandaAukeratu()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
        ) {
            Text(text = "Komanda bukatu", color = Color.Black)
        }
    }
}

@Composable
fun EntranteakScreen(onNavigateToKomandaAukeratu: () -> Unit) {
    ProduktuaListScreen(
        mota = 1,
        title = "Entranteak",
        onNavigateToKomandaAukeratu = onNavigateToKomandaAukeratu
    )
}

@Composable
fun LehenPlateraScreen(onNavigateToKomandaAukeratu: () -> Unit) {
    ProduktuaListScreen(
        mota = 2,
        title = "Lehen Platerak",
        onNavigateToKomandaAukeratu = onNavigateToKomandaAukeratu
    )
}

@Composable
fun PostreakScreen(onNavigateToKomandaAukeratu: () -> Unit) {
    ProduktuaListScreen(
        mota = 3,
        title = "Postreak",
        onNavigateToKomandaAukeratu = onNavigateToKomandaAukeratu
    )
}

@Composable
fun EdariakScreen(onNavigateToKomandaAukeratu: () -> Unit) {
    ProduktuaListScreen(
        mota = 4,
        title = "Edariak",
        onNavigateToKomandaAukeratu = onNavigateToKomandaAukeratu
    )
}

@Composable
fun CharlieApp(onNavigateToMainScreen: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Acción de cerrar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Itxi", color = Color.Black)
                }
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Acción del chat */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Txat", color = Color.Black)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = onNavigateToMainScreen,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Zerbitzaria", fontSize = 16.sp, color = Color.Black)
                }
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Acción para Sukaldaria */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Sukaldaria", fontSize = 16.sp, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun MainScreen(onNavigateToMahaiakAukeratu: () -> Unit) {
    // Estado para almacenar los nombres de los botones obtenidos
    var zerbitzariak by remember { mutableStateOf<List<Zerbitzaria>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Lanza la carga de datos cuando la pantalla se crea
    LaunchedEffect(Unit) {
        // Llamar a la función fetchMesas
        fetchErabiltzaileak { result ->
            zerbitzariak = result
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Acción de Atzera */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Atzera", color = Color.Black)
                }
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Acción de Txat */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Txat", color = Color.Black)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Si los datos aún están cargando, muestra un indicador de carga
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                // Mostrar los botones dinámicamente
                zerbitzariak.forEach { Zerbitzaria ->

                    ZerbitzariaButton(id = Zerbitzaria.id, text = Zerbitzaria.izena, onNavigateToMahaiakAukeratu = onNavigateToMahaiakAukeratu)
                }
            }
        }
    }
}

@Composable
fun MahaiakAukeratu(onNavigateToKomandaAukeratu: () -> Unit) {
    val tableList = remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        fetchTables { fetchedTables ->
            tableList.value = fetchedTables
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Acción de Atzera */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Atzera", color = Color.Black)
                }
                Button(
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Acción de Txat */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
                ) {
                    Text("Txat", color = Color.Black)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            tableList.value.chunked(2).forEach { rowTables ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)  // Asegura que haya espacio en los laterales
                ) {
                    rowTables.forEach { table ->
                        Box(
                            modifier = Modifier.weight(1f)  // Hace que los botones ocupen la mitad del ancho
                        ) {
                            MahaiaButton(
                                text = table,
                                function = onNavigateToKomandaAukeratu


                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MahaiaButton(text: String, function: () -> Unit) {
    Button(
        onClick = function,
        modifier = Modifier
            .padding(16.dp)  // Ajusta el padding
            .clip(CircleShape)  // Hace el botón redondo (si lo prefieres redondo)
            .size(100.dp)  // Hace que el botón sea cuadrado (puedes ajustar el valor)
            .height(100.dp),  // Define la altura y el ancho igual
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600))
    ) {
        Text(text, color = Color.Black)
    }
}


@Composable
fun KomandaAukeratuScreen(
    onNavigateToEntranteak: () -> Unit,
    onNavigateToLehenPlatera: () -> Unit,
    onNavigateToPostreak: () -> Unit,
    onNavigateToEdariak: () -> Unit,
    onNavigateToKomanda: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                border = BorderStroke(2.dp, Color.Black),
                onClick = { /* Acción de Atzera */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
            ) {
                Text(text = "Atzera", color = Color.Black)
            }

            Button(
                border = BorderStroke(2.dp, Color.Black),
                onClick = { /* Acción de Txat */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
            ) {
                Text(text = "Txat", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToEntranteak,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
        ) {
            Text("Entranteak", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToLehenPlatera,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
        ) {
            Text("1. Platera", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToPostreak,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
        ) {
            Text("Postreak", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToEdariak,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
        ) {
            Text("Edaria", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToKomanda,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
        ) {
            Text("Komanda", color = Color.Black)
        }
    }
}

@Composable
fun ZerbitzariaButton( id: Int, text: String, onNavigateToMahaiakAukeratu: () -> Unit) {
    Button(
        border = BorderStroke(2.dp, Color.Black),
        onClick = onNavigateToMahaiakAukeratu, /*insertZerbitzariaEskaeran(id, text),*/
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600)),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(60.dp)
    ) {
        Text(text, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun KomandaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                border = BorderStroke(2.dp, Color.Black),

                onClick = { /* Handle Atzera */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
            ) {
                Text(text = "Atzera", color = Color.Black)
            }

            Button(
                border = BorderStroke(2.dp, Color.Black),

                onClick = { /* Handle Txat */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
            ) {
                Text(text = "Txat", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "KOMANDA",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List of items
        val items = listOf(
            "HAMBURGUESA DE POLLO",
            "HAMBURGUESA COMPLETA",
            "HAMBURGUESA COMPLETA",
            "HAMBURGUESA COMPLETA",
            "HAMBURGUESA COMPLETA",
            "HAMBURGUESA COMPLETA",
            "HAMBURGUESA COMPLETA"
        )

        items.forEach { item ->

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Button
        Button(
            border = BorderStroke(2.dp, Color.Black),

            onClick = { /* Handle Komanda bukatu */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600)),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(text = "Komanda bukatu", color = Color.Black)
        }
    }
}

@Composable
fun KomandaItemRow(itemName: String, initialQuantity: Int, onQuantityChanged: (Int) -> Unit) {
    var quantity by remember { mutableStateOf(initialQuantity) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = itemName, color = Color.Black)
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (quantity > 0) quantity-- }) {
                Text("-", color = Color.Red, fontSize = 24.sp)
            }
            Text(quantity.toString(), color = Color.Black)
            IconButton(onClick = { quantity++ }) {
                Icon(Icons.Default.Add, contentDescription = "Increment", tint = Color.Green)
            }
        }
    }

    // Notify the parent when the quantity changes
    LaunchedEffect(quantity) {
        onQuantityChanged(quantity)
    }
}

@Preview(showBackground = true)
@Composable
fun KomandaAukeratuScreenPreview() {
    ZerbitzariAppTheme {
        KomandaAukeratuScreen(
            onNavigateToEntranteak = {},
            onNavigateToLehenPlatera = {},
            onNavigateToPostreak = {},
            onNavigateToEdariak = {},
            onNavigateToKomanda = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MahaiakAukeratuPreview() {
    ZerbitzariAppTheme {
        MahaiakAukeratu(onNavigateToKomandaAukeratu = {})
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ZerbitzariAppTheme {
        MainScreen(onNavigateToMahaiakAukeratu = {})
    }
}

@Preview(showBackground = true)
@Composable
fun KomandaScreenPreview() {
    ZerbitzariAppTheme {
        KomandaScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun EntranteakScreenPreview() {
    ZerbitzariAppTheme {
        EntranteakScreen(onNavigateToKomandaAukeratu = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CharlieAppPreview() {
    ZerbitzariAppTheme {
        CharlieApp(onNavigateToMainScreen = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LehenPlateraScreenPreview() {
    ZerbitzariAppTheme {
        LehenPlateraScreen(onNavigateToKomandaAukeratu = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PostreakScreenPreview() {
    ZerbitzariAppTheme {
        PostreakScreen(onNavigateToKomandaAukeratu = {})
    }
}

@Preview(showBackground = true)
@Composable
fun EdariakScreenPreview() {
    ZerbitzariAppTheme {
        EdariakScreen(onNavigateToKomandaAukeratu = {})
    }
}


