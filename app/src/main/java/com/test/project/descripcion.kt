package com.test.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject

class descripcion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion)

        val intent = intent
        val stringRecibido = intent.getStringExtra("clave_string")
        val valorInt: Int = stringRecibido?.toIntOrNull() ?: 0

        val btnRestar = findViewById<Button>(R.id.btnRestar2)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnFinalizarCompra = findViewById<Button>(R.id.btnFinalizar)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)

        val titulo = findViewById<TextView>(R.id.txtTitle)
        val imageViewDes = findViewById<ImageView>(R.id.imageViewDescripccion)
        val des = findViewById<TextView>(R.id.txtDescripcion)
        val pre = findViewById<TextView>(R.id.txtPrecio)
        val cantidadmax = findViewById<TextView>(R.id.txtcantidadmax)
        val cantidad = findViewById<TextView>(R.id.txtCantidad)
        cantidad.text = "0"

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.19/productos.php"
        val url2 = "http://192.168.0.19/realizarcompra.php"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val jsonArray = JSONArray(response)
            val jsonObject = JSONObject(jsonArray.getString(valorInt))

            val textNomPro = jsonObject.get("nomPro")
            val textPrePro = jsonObject.get("prePro")
            val txtDesPro = jsonObject.get("desPro")
            val textimage = jsonObject.get("http")
            val txtCantidad = jsonObject.get("Cantidad")

            titulo.text = textNomPro.toString()
            val imageurl = ("http://192.168.0.19/imagenes/"+textimage.toString()+"")

            Glide.with(this)
                .load(imageurl)
                .into(imageViewDes)

            des.text = txtDesPro.toString()
            pre.text = textPrePro.toString()
            cantidadmax.text = "Stock :" + txtCantidad.toString()

            val cantidadString = cantidadmax.text.toString().substringAfter(":").trim()
            val cantidadmaxint: Int = cantidadString.toIntOrNull() ?: 0
            var cantidadint = 0

            btnAgregar.setOnClickListener(){
                if(cantidadint < cantidadmaxint){
                    cantidadint = cantidadint + 1
                    cantidad.text = "" + cantidadint
                }
                if(cantidadint == cantidadmaxint){
                    Toast.makeText(this,"Se llego a la cantidad maxima en este producto",Toast.LENGTH_SHORT).show()
                }
            }

            btnRestar.setOnClickListener() {
                if (cantidadint > 0) {
                    cantidadint = cantidadint - 1
                    cantidad.text = cantidadint.toString()
                    if (cantidadint == cantidadmaxint) {
                        Toast.makeText(this, "Se llegó a la cantidad máxima en este producto", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "No se pueden retirar más items", Toast.LENGTH_SHORT).show()
                }
            }

            btnFinalizarCompra.setOnClickListener {
                val cantidadFinal = cantidad.text.toString().toIntOrNull() ?: 0
                val textoPrecio = pre.text.toString()
                val precioString = textoPrecio.substringAfter("$").trim()
                val preciodouble: Double = precioString.toDoubleOrNull() ?: 0.0
                val total = cantidadFinal.toDouble() * preciodouble

                val request = object : StringRequest(Method.POST, url2,
                    Response.Listener<String> { response ->
                        Toast.makeText(this, "Compra registrada correctamente", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, "Error al registrar la compra", Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["dniPaciente"] = "987654321"
                        params["idProducto"] = stringRecibido.toString()
                        params["cantidad"] = cantidadFinal.toString()
                        params["precio"] = preciodouble.toString()
                        params["total"] = total.toString()
                        return params
                    }
                }
                Volley.newRequestQueue(this).add(request)
                val intent = Intent(this, ListarProductosActivity::class.java)
                startActivity(intent)
            }

            btnContinuar.setOnClickListener {
                val cantidadFinal = cantidad.text.toString().toIntOrNull() ?: 0
                val textoPrecio = pre.text.toString()
                val precioString = textoPrecio.substringAfter("$").trim()
                val preciodouble: Double = precioString.toDoubleOrNull() ?: 0.0
                val total = cantidadFinal.toDouble() * preciodouble

                val request = object : StringRequest(Method.POST, url2,
                    Response.Listener<String> { response ->
                        Toast.makeText(this, "Compra registrada correctamente", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, "Error al registrar la compra", Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["dniPaciente"] = "987654321"
                        params["idProducto"] = stringRecibido.toString()
                        params["cantidad"] = cantidadFinal.toString()
                        params["precio"] = preciodouble.toString()
                        params["total"] = total.toString()
                        return params
                    }
                }
                Volley.newRequestQueue(this).add(request)
                val intent = Intent(this, store::class.java)
                startActivity(intent)
            }
        }, { error ->
            Log.e("Volley Error", error.toString())
        })
        queue.add(stringRequest)
    }
}
