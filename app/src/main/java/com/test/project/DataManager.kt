package com.test.project

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DataManager {
    fun moverDatos() {
        Thread {
            try {
                // Realiza una solicitud para obtener los datos de la primera tabla
                val urlObtenerDatos = URL("http://192.168.0.19/verificadorCompra.php")
                val conexionObtenerDatos = urlObtenerDatos.openConnection() as HttpURLConnection
                conexionObtenerDatos.requestMethod = "GET"

                val respuestaObtenerDatos = BufferedReader(InputStreamReader(conexionObtenerDatos.inputStream))
                val datosJson = StringBuilder()
                var linea: String?
                while (respuestaObtenerDatos.readLine().also { linea = it } != null) {
                    datosJson.append(linea)
                }
                respuestaObtenerDatos.close()

                // Parsea los datos obtenidos si es un JSON
                // val datos = parsearJson(datosJson.toString())

                // Realiza una solicitud para agregar datos a la segunda tabla
                val urlAgregarDatos = URL("http://192.168.0.19/deCompraaVenta.php")
                val conexionAgregarDatos = urlAgregarDatos.openConnection() as HttpURLConnection
                conexionAgregarDatos.requestMethod = "POST"
                conexionAgregarDatos.doOutput = true
                conexionAgregarDatos.setRequestProperty("Content-Type", "application/json")

                // Aquí deberías pasar los datos obtenidos a través de la conexión para agregarlos a la segunda tabla
                // val datosComoJson = convertirDatosAJson(datos)
                // val outputStream = DataOutputStream(conexionAgregarDatos.outputStream)
                // outputStream.writeBytes(datosComoJson)
                // outputStream.flush()
                // outputStream.close()

                // Realiza una solicitud para eliminar datos de la primera tabla
                val urlEliminarDatos = URL("http://192.168.0.19/EliminartablaVenta,php")
                val conexionEliminarDatos = urlEliminarDatos.openConnection() as HttpURLConnection
                conexionEliminarDatos.requestMethod = "DELETE"

                val respuestaEliminarDatos = BufferedReader(InputStreamReader(conexionEliminarDatos.inputStream))
                val respuestaString = StringBuilder()
                var lineaEliminarDatos: String?
                while (respuestaEliminarDatos.readLine().also { lineaEliminarDatos = it } != null) {
                    respuestaString.append(lineaEliminarDatos)
                }
                respuestaEliminarDatos.close()

                // Realiza las acciones necesarias en la interfaz de usuario después de mover los datos
                // runOnUiThread { /* Actualiza la interfaz de usuario */ }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}