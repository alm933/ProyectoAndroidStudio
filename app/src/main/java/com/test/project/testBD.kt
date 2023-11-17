package com.test.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class testBD : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_bd)


        //val btnMostrarDatos: Button = findViewById(R.id.btnMostrarDatos)

        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.19/pacientes.php"
        val stringRequest = StringRequest(Request.Method.GET,url, Response.Listener { response ->
            val jsonArray = JSONArray(response)

            val jsonObject = JSONObject(jsonArray.getString(0))
            var text = jsonObject.get("dni")
            tvResultado.text = text.toString()

        },Response.ErrorListener {error->

        })
        queue.add(stringRequest)

    }

}