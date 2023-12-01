package com.test.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class EditActivity : AppCompatActivity() {

    private lateinit var editTextNombre: TextView
    private lateinit var editTextCantidad: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        editTextNombre = findViewById(R.id.editNombre)
        editTextCantidad = findViewById(R.id.editCant)

        val id = intent.getIntExtra("id", -1)
        obtenerProductoPorId(id)
    }

    private fun obtenerProductoPorId(id: Int) {
        val url = "http://192.168.0.19/obtener_compra.php?id=$id"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Manejar la respuesta JSON aquí
                val producto = response.getJSONObject("producto")
                val nombre = producto.getString("nombre")
                val cantidad = producto.getInt("cantidad")

                // Llenar los campos con los datos del producto
                editTextNombre.text = nombre
                editTextCantidad.text = cantidad.toString()
            },
            { error ->
                // Manejar errores aquí
                Log.e("Volley", "Error al obtener producto: ${error.message}")
            })

        // Agregar la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(request)
    }
}
