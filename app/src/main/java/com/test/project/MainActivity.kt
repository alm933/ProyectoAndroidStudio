package com.test.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    lateinit var txtuser:EditText
    lateinit var txtpass:EditText
    lateinit var btnIngresar:Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logo = findViewById<ImageView>(R.id.imageLogo)
        val imageUrlLogo = "http://192.168.0.19/imagenes/logo.png"

        Glide.with(this)
            .load(imageUrlLogo)
            .into(logo)


            txtuser = findViewById(R.id.txtUser)
        txtpass = findViewById(R.id.txtPss)
        btnIngresar=findViewById(R.id.btnIngresar)


        btnIngresar.setOnClickListener{

            val user = txtuser.text.toString()
            val pass = txtpass.text.toString()

            if(user=="user" && pass=="pass"){
               // val miString = "1"
                val intent = Intent(this, DashBoard::class.java)
                //intent.putExtra("clave_string", miString)
                startActivity(intent)
            }

            if(user=="" && pass!=null){
                Toast.makeText(this,"Falta colocar el usuario",Toast.LENGTH_SHORT).show()
            }

            if(user!=null && pass==""){
                Toast.makeText(this,"Falta colocar la contraseña",Toast.LENGTH_SHORT).show()
            }
        }
    }
}