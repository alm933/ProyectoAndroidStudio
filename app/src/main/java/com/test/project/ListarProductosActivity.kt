package com.test.project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.OkHttpClient
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.security.auth.callback.Callback


class ListarProductosActivity : AppCompatActivity() {

    private lateinit var rvProductos: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var productoList: MutableList<Producto>
    lateinit var btnProceder : Button
    private val dataManager = DataManager()
    lateinit var TotalFinal :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_productos)




        rvProductos = findViewById(R.id.rvProductos)
        rvProductos.layoutManager = LinearLayoutManager(this)
        productoList = mutableListOf()
        productoAdapter = ProductoAdapter(productoList)
        rvProductos.adapter = productoAdapter

        TotalFinal = findViewById<TextView>(R.id.txtTotalFinal)



        btnProceder = findViewById(R.id.btnProcederC)

        btnProceder.setOnClickListener {

            moverDatosTablaVentas();
            Handler().postDelayed({
                eliminarDatosTablaCompras()
                mostrarDialogo()
            }, 2000) //
        }




        obtenerDatos()

        obtenerTotalCompras()





    }

    private fun obtenerTotalCompras() {


        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.19/TotalAPagar.php" // Reemplaza con la URL de tu servidor y archivo PHP

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Aquí obtienes la respuesta del servidor PHP
                TotalFinal.text = "Total Compras: $response" // Actualiza el TextView con el total
            },
            { error ->
                // Manejar errores de la solicitud
                error.printStackTrace()
            }
        )
        requestQueue.add(stringRequest)
    }


    private fun mostrarDialogo() {
        val builder = AlertDialog.Builder(this@ListarProductosActivity)
        builder.setTitle("Gracias por su compra")
        builder.setMessage("¡Su compra ha sido exitosa!")




        builder.setPositiveButton("Aceptar") { dialog, _ ->
            // Cuando se presiona Aceptar en el diálogo, iniciamos el nuevo Activity
            val intent = Intent(this@ListarProductosActivity, store::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        builder.setCancelable(false) // Evita que el usuario cierre el diálogo al presionar fuera de él

        val dialog = builder.create()
        dialog.show()
    }



    private fun eliminarDatosTablaCompras() {
        Thread {
            try {
                val url = URL("http://192.168.0.19/EliminartablaVenta.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                // Verificar si la solicitud fue exitosa (código 200)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // La solicitud fue exitosa, puedes realizar acciones adicionales si es necesario
                    // Por ejemplo, actualización de la interfaz de usuario
                    runOnUiThread { // Actualizar la interfaz de usuario después de eliminar los datos
                        // Por ejemplo, mostrar un mensaje de éxito
                        Toast.makeText(
                            this@ListarProductosActivity,
                            "Datos de tabla compras eliminados",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Manejar errores o problemas con la solicitud
                    // Por ejemplo, mostrar un mensaje de error
                    runOnUiThread {
                        Toast.makeText(
                            this@ListarProductosActivity,
                            "Error al eliminar datos de tabla compras",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                connection.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread { // Manejar errores de conexión
                    Toast.makeText(this@ListarProductosActivity, "Error de conexión", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.start()
    }

    private fun moverDatosTablaVentas() {
        Thread {
            try {
                val url = URL("http://192.168.0.19/deCompraaVenta.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                // Verificar si la solicitud fue exitosa (código 200)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // La solicitud fue exitosa, puedes realizar acciones adicionales si es necesario
                    // Por ejemplo, actualización de la interfaz de usuario
                    runOnUiThread { // Actualizar la interfaz de usuario después de mover los datos
                        // Por ejemplo, mostrar un mensaje de éxito
                        Toast.makeText(
                            this@ListarProductosActivity,
                            "Datos movidos a tabla ventas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Manejar errores o problemas con la solicitud
                    // Por ejemplo, mostrar un mensaje de error
                    runOnUiThread {
                        Toast.makeText(
                            this@ListarProductosActivity,
                            "Error al mover datos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                connection.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread { // Manejar errores de conexión
                    Toast.makeText(this@ListarProductosActivity, "Error de conexión", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.start()
    }








    private fun obtenerDatos() {
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.19/verificadorCompra.php" // Reemplaza con tu URL PHP

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    for (i in 0 until response.length()) {
                        val productoObject = response.getJSONObject(i)
                        val nombre = productoObject.getString("nombre_producto")
                        val precio = productoObject.getDouble("precio")
                        val cantidad = productoObject.getInt("cantidad")
                        val imageFile = productoObject.getString("http")


                        val producto = Producto(nombre, precio, cantidad , imageFile)


                        Log.d("---->","" + nombre +" " + precio +" "+ cantidad + " "+imageFile)
                        productoList.add(producto)
                    }
                    productoAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }


}
