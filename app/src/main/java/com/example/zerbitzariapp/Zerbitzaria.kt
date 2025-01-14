package com.example.zerbitzariapp

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
/*import io.ktor.client.plugins.contentnegotiation.**/
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
/*import io.ktor.client.plugins.contentnegotiation.json*/

// Clase de datos que será serializada a JSON
@Serializable
data class Zerbitzaria(
    val id: Int,
    val izena: String
)

/*// Cliente HTTP configurado para trabajar con JSON
val client = HttpClient(Android) {
    install(ContentNegotiation) {
        json(Json { prettyPrint = true })
    }
}

fun insertZerbitzariaEskaeran(id: Int, text: String, onSuccess: () -> Unit) {
    val requestBody = Zerbitzaria(id, text)

    // Realiza la solicitud POST
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = client.post<String>("http://localhost/insertEskaera.php") {
                contentType(ContentType.Application.Json) // Tipo de contenido JSON
                setBody(requestBody) // Establecer el cuerpo de la solicitud
            }

            // Aquí puedes procesar la respuesta que se envía desde el servidor
            println("Respuesta del servidor: $response")

            // Si la inserción fue exitosa, ejecuta el callback
            onSuccess()

        } catch (e: Exception) {
            // Manejo de errores
            println("Error al enviar la solicitud: ${e.localizedMessage}")
        }
    }
}
*/
