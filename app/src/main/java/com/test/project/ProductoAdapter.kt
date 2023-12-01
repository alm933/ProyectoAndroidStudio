package com.test.project

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide




class ProductoAdapter(private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fila, parent, false)
        return ProductoViewHolder(itemView)
    }






    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val productoActual = listaProductos[position]
        holder.nombre.text = productoActual.nombre
        holder.precio.text = productoActual.precio.toString()
        holder.cantidad.text = productoActual.cantidad.toString()

        val imageUrl =
            "http://192.168.0.19/imagenes/${productoActual.imageFileName}" // URL de la imagen utilizando el nombre del archivo


//        Log.d("ProductoAdapter", "Nombre: ${productoActual.nombre}, Precio: ${productoActual.precio}, Cantidad: ${productoActual.cantidad} ")


        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.chao)
            .error(R.drawable.icon_blood_drop)
            .into(holder.imagen)

    }

    override fun getItemCount() = listaProductos.size

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.filaNombre)
        val precio: TextView = itemView.findViewById(R.id.textView13)
        val cantidad: TextView = itemView.findViewById(R.id.textView15)
        val imagen: ImageView = itemView.findViewById(R.id.filaImage)





    }
}
