package com.test.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var txtuser:EditText
    lateinit var txtpass:EditText
    lateinit var btnIngresar:Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtuser = findViewById(R.id.txtUser)
        txtpass = findViewById(R.id.txtPss)
        btnIngresar=findViewById(R.id.btnIngresar)


        btnIngresar.setOnClickListener{

            val user = txtuser.text.toString()
            val pass = txtpass.text.toString()

            if(user=="user" && pass=="pass"){
                Toast.makeText(this,"Entraste",Toast.LENGTH_SHORT).show()
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