package com.example.zapateria_unica.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zapateria_unica.databinding.ItemCellFirstBinding
import com.example.zapateria_unica.model.local.entities.Zapato

//"First" por FirstFragment
class FirstAdapter(private var datalist: List<Zapato>) :
    RecyclerView.Adapter<FirstAdapter.FirstViewHolder>() {

    val selectedItemId = MutableLiveData<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstViewHolder {
        val binding: ItemCellFirstBinding = ItemCellFirstBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return FirstViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: FirstViewHolder, position: Int) {
        val zapato: Zapato = datalist.get(position)

        Glide.with(holder.itemView).load(zapato.imagenLink).into(holder.ivFoto)
        holder.tvNombre.text = zapato.nombre

        //Dado que falta la propiedad precio en la lista del primer endpoint, se reemplaza por
        //el unico entero luego del id, como reemplazo. En SecondFragment se muestra el precio real.
        holder.tvPrecio.text = zapato.numero.toString()

        holder.itemView.setOnClickListener {
            selectedItemId.value = zapato.id
        }
    }

    fun setData(list: List<Zapato>) {
        datalist = list
        notifyDataSetChanged()
    }

    class FirstViewHolder(binding: ItemCellFirstBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivFoto: ImageView = binding.ivFoto
        val tvNombre: TextView = binding.tvNombre
        val tvPrecio: TextView = binding.tvPrecio
    }

}