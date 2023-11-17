package com.test.project


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide

class store : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val imageViewChao = findViewById<ImageView>(R.id.imageView4)
        val imageUrlChao = "http://192.168.0.19/imagenes/chao.jpg"

        //Bismutol
        val imageViewBismutol =findViewById<ImageView>(R.id.imageView5)
        val imageUrlBismutol = "http://192.168.0.19/imagenes/bismutol.jpg"

        //Paracetamol
        val imageViewPanadol = findViewById<ImageView>(R.id.imageView6)
        val imageUrlPanadol = "http://192.168.0.19/imagenes/panadolniños.png"


        val imageViewParacetamol = findViewById<ImageView>(R.id.imageView7)
        val imageUrlParacetamol  = "http://192.168.0.19/imagenes/paracetamol.jpg"





        Glide.with(this)
            .load(imageUrlParacetamol)
            .into(imageViewParacetamol  )



        Glide.with(this)
            .load(imageUrlPanadol)
            .into(imageViewPanadol)





        Glide.with(this)
            .load(imageUrlChao)
            .into(imageViewChao)





        Glide.with(this)
            .load(imageUrlBismutol)
            .into(imageViewBismutol)




        //Cambio de activity usando imageView

        imageViewChao.setOnClickListener {
            // Aquí dentro se define la acción al hacer clic en la ImageView

            val miString = "0"

            val intent = Intent(this, descripcion::class.java)
            intent.putExtra("clave_string", miString)
            startActivity(intent)

        }

        imageViewBismutol.setOnClickListener {
            // Aquí dentro se define la acción al hacer clic en la ImageView

            val miString = "1"

            val intent = Intent(this, descripcion::class.java)
            intent.putExtra("clave_string", miString)
            startActivity(intent)

        }


        imageViewPanadol.setOnClickListener {
            // Aquí dentro se define la acción al hacer clic en la ImageView

            val miString = "2"

            val intent = Intent(this, descripcion::class.java)
            intent.putExtra("clave_string", miString)
            startActivity(intent)

        }

        imageViewParacetamol.setOnClickListener {
            // Aquí dentro se define la acción al hacer clic en la ImageView

            val miString = "3"

            val intent = Intent(this, descripcion::class.java)
            intent.putExtra("clave_string", miString)
            startActivity(intent)

        }

    }








}