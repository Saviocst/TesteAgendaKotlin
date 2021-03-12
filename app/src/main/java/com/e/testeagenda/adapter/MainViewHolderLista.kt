package com.e.testeagenda.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.testeagenda.R
import com.e.testeagenda.Registro
import com.e.testeagenda.model.EventoDeClicar
import com.e.testeagenda.model.EventoDeClicar2

class MainViewHolderLista(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(dado: Registro, eventoDeClicar: EventoDeClicar2) {
        val nomeCliente = itemView.findViewById<TextView>(R.id.nome_cliente)
        val horaDiaCliente = itemView.findViewById<TextView>(R.id.hora_cliente)
        val dataCliente = itemView.findViewById<TextView>(R.id.data_cliente)

        horaDiaCliente.text = dado.hora
        dataCliente.text = dado.dataCriada

        itemView.setOnClickListener { view: View? -> eventoDeClicar.aoClicar(dado.id, dado.tipo) }

        // listener para ouvir evento de long-click (segurar touch - EXCLUIR)
        itemView.setOnLongClickListener { view: View? ->
            eventoDeClicar.aoClicarSegurar(adapterPosition, dado.tipo, dado.id)
            false
        }
    }


}


