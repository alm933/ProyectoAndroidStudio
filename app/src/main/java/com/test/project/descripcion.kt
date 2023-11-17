package com.test.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
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

        val titulo = findViewById<TextView>(R.id.txtTitle)
        val imageViewDes = findViewById<ImageView>(R.id.imageViewDescripccion)
        val des = findViewById<TextView>(R.id.txtDescripcion)
        val pre = findViewById<TextView>(R.id.txtPrecio)

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.19/productos.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val jsonArray = JSONArray(response)

            val jsonObject = JSONObject(jsonArray.getString(valorInt))

            val textNomPro = jsonObject.get("nomPro")
            val textPrePro = jsonObject.get("prePro")
            val txtDesPro = jsonObject.get("desPro")
            val textimage = jsonObject.get("http")

            titulo.text = textNomPro.toString()
            val imageurl = ("http://192.168.0.19/imagenes/"+textimage.toString()+"")

            Glide.with(this)
                .load(imageurl)
                .into(imageViewDes)


            des.text = txtDesPro.toString()

            pre.text =textPrePro.toString()



        }, { error ->
            Log.e("Volley Error", error.toString())
        }
        )
        queue.add(stringRequest)

    }
}