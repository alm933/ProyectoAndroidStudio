package com.test.project
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.test.project.Producto
import com.test.project.ProductoAdapter
import org.json.JSONException

class ListarProductosActivity : AppCompatActivity() {

    private lateinit var rvProductos: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var productoList: MutableList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_productos)

        rvProductos = findViewById(R.id.rvProductos)
        rvProductos.layoutManager = LinearLayoutManager(this)
        productoList = mutableListOf()
        productoAdapter = ProductoAdapter(productoList)
        rvProductos.adapter = productoAdapter

        obtenerDatos()
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
