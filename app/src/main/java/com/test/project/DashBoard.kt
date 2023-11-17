package com.test.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        val txtApellido = findViewById<TextView>(R.id.txtApellidos)
        val txtDni = findViewById<TextView>(R.id.txtDni)
        val txtDireccion = findViewById<TextView>(R.id.txtDireccion)
        val txtContactoEmergencia = findViewById<TextView>(R.id.txtContactoEmergencia)
        val txtEdad = findViewById<TextView>(R.id.txtEdad)
        val txtSangre = findViewById<TextView>(R.id.txtTipoSandgre)
        val txtEnfermedadesCronicas = findViewById<TextView>(R.id.txtEnfermedadesCronicas)
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)
        val txtTelefono = findViewById<TextView>(R.id.txtTelefono)
        val txtA :TextView = findViewById(R.id.txtEnfermedadesCronicas)
        var car = findViewById<ImageView>(R.id.imageCar)


        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.19/pacientes.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val jsonArray = JSONArray(response)

            val jsonObject = JSONObject(jsonArray.getString(1))

            val textDni = jsonObject.get("dni")
            val textNombre = jsonObject.get("nombres")
            val textApellido = jsonObject.get("apellido")
            val textDireccion = jsonObject.get("direccion")
            val textCorreo = jsonObject.get("correo")
            val textEdad = jsonObject.get("edad")
            val textTelefono = jsonObject.get("telefono")
            val textSangre = jsonObject.get("grupoSanguineo")
            //val texta= jsonObject.get("allergies")
            // val textCE = jsonObject.get("ec")
            //  var textEnfermedadesCronicas = jsonObject.get("ec")


            txtDni.text = textDni.toString()
            txtNombre.text = textNombre.toString()
            txtApellido.text = textApellido.toString()
            txtDireccion.text = textDireccion.toString()
            txtCorreo.text = textCorreo.toString()
            txtEdad.text = textEdad.toString()
            txtTelefono.text = textTelefono.toString()
            txtSangre.text = textSangre.toString()
            txtContactoEmergencia.text = "CARGO"
            //txtEnfermedadesCronicas.text = textEnfermedadesCronicas.toString()
            //txtA.text =texta.toString()
            /*


*/


        }, { error ->
            Log.e("Volley Error", error.toString())
        }
        )
        queue.add(stringRequest)


        car.setOnClickListener {
            // Aquí dentro se define la acción al hacer clic en la ImageView



            val intent = Intent(this, store::class.java)
            startActivity(intent)

        }


    }
}