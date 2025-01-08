package com.example.zerbitzariapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zerbitzariapp.ui.theme.ZerbitzariAppTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZerbitzariAppTheme {
                CharlieApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharlieApp() {
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
            // Descomenta esta parte si tienes un logo válido en drawable
            /*
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Charlie's Burgers Logo",
                modifier = Modifier.size(200.dp)
            )
            */
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Button(
                    border = BorderStroke(2.dp, Color.Black),

                    onClick = { /* Acción para Zerbitzaria */ },
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
fun MainScreen() {
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
            ZerbitzariaButton("Zerbitzaria 1") { /* Acción Zerbitzaria 1 */ }
            ZerbitzariaButton("Zerbitzaria 2") { /* Acción Zerbitzaria 2 */ }

            // Imagen del logo
            /*Image(
                painter = painterResource(id = R.drawable.logo), // Asegúrate de que `logo` exista en res/drawable
                contentDescription = "Charlie's Burgers Logo",
                modifier = Modifier.size(150.dp)
            )*/

            ZerbitzariaButton("Zerbitzaria 3") { /* Acción Zerbitzaria 3 */ }
            ZerbitzariaButton("Zerbitzaria 4") { /* Acción Zerbitzaria 4 */ }
        }
    }
}

@Composable
fun MahaiakAukeratu(){
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
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre filas
        ) {
            // Primera fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp), // Espacio entre botones
                verticalAlignment = Alignment.CenterVertically,
                //Hola


            ) {
                MahaiaButton(
                    text = " 1",
                ) { /* Acción Zerbitzaria 1 */ }

                MahaiaButton(
                    text = " 2",

                ) { /* Acción Zerbitzaria 2 */ }
            }

            // Segunda fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MahaiaButton(
                    text = " 3",
                ) { /* Acción Zerbitzaria 3 */ }

                MahaiaButton(
                    text = " 4",
                ) { /* Acción Zerbitzaria 4 */ }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                MahaiaButton(
                    text = "5",
                    ) { /* Acción Zerbitzaria 3 */ }

                MahaiaButton(
                    text = "6",
                ) { /* Acción Zerbitzaria 4 */ }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MahaiaButton(
                    text = "7",

                ) { /* Acción Zerbitzaria 3 */ }

                MahaiaButton(
                    text = "8",


                ) { /* Acción Zerbitzaria 4 */ }
            }
        }


    }
}

@Composable
fun KomandaAukeratuScreen() {
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

                onClick = { /* Handle Titul */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600))
            ) {
                Text(text = "Titul", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Orange Buttons List
        val buttonLabels = listOf("Entrenam", "1. Fitxa", "Probaak", "Ereduak", "Komanda Ikusi")
        buttonLabels.forEach { label ->
            Button(
                border = BorderStroke(2.dp, Color.Black),

                onClick = { /* Handle $label action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6600)),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(text = label, color = Color.Black)
            }
        }
    }
}

@Composable
fun ZerbitzariaButton(text: String, onClick: () -> Unit) {
    Button(
        border = BorderStroke(2.dp, Color.Black),

        onClick = onClick,
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
fun MahaiaButton(text: String,
                 onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        border = BorderStroke(2.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600)),
        modifier = Modifier
            .size(150.dp)    ) {
        Text(text,  color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun KomandaAukeratuScreenPreview() {
    ZerbitzariAppTheme {
        KomandaAukeratuScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MahaiakAukeratuPreview() {
    ZerbitzariAppTheme {
        MahaiakAukeratu()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ZerbitzariAppTheme {
        MainScreen()
    }
}

@Composable
fun EntranteakScreen() {
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

        Row(            modifier = Modifier.fillMaxWidth(),
        ) {// Title
            Text(
                text = "ENTRANTEAK",
                color = Color.Black
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // List of items
        val items = listOf(
            "HAMBURGUESA DE POLLO" to "biblibiblibliblinsfabufabfuysab\njhfbaubfsbfhjasbfjdsbfkjhsfa\nfiufausfausfnausfjneifjranfjesa\nnfjesa\nnfoesanfoesanfosnfosk",
            "HAMBURGUESA COMPLETA" to "biblibiblibliblinsfabufabfuysab\njhfbaubfsbfhjasbfjdsbfkjhsfa\nfiufausfausfnausfjneifjranfjesa\nnfjesa\nnfoesanfosanfosnfosk"
        )

        items.forEach { (title, description) ->
            ItemRow(title = title, description = description)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Button
        Button(
            border = BorderStroke(2.dp, Color.Black),

            onClick = { /* Handle Komandarekin segi */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6600)),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(text = "Komandarekin segi", color = Color.Black)
        }
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
            KomandaItemRow(itemName = item, quantity = 0)
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
fun KomandaItemRow(itemName: String, quantity: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemName,
            color = Color.Black
        )
        Text(
            text = quantity.toString(),
            color = Color.Black
        )
    }

}



@Preview(showBackground = true)
@Composable
fun KomandaScreenPreview() {
    ZerbitzariAppTheme {
        KomandaScreen()
    }
}


@Composable
fun ItemRow(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, color = Color.Gray)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(

            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Handle decrement */ }) {

                    Text(
                        text = "-",
                        color = Color.Red,
                        fontSize = 24.sp,
                        modifier = Modifier.clickable { /* Handle click */ }
                    )

                }

                Text(
                    text = "0",
                    //style = MaterialTheme.typography.subtitle1,
                    color = Color.Black
                )

                IconButton(onClick = { /* Handle increment */ }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increment",
                        tint = Color.Green
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            Box(

                modifier = Modifier
                    .size(64.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                /*Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "Placeholder Image",
                tint = Color.Gray,
                modifier = Modifier.align(Alignment.Center)
            )*/
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EntranteakScreenPreview() {
    ZerbitzariAppTheme {
        EntranteakScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CharlieAppPreview() {
    ZerbitzariAppTheme {
        CharlieApp()
    }
}
