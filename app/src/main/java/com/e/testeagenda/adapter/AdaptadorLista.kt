package com.e.testeagenda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.testeagenda.*



class AdaptadorLista(private val dados: List<Registro>, private var listner: ListaActivity) : RecyclerView.Adapter<MainViewHolderLista>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MainViewHolderLista {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.lista_agenda_personalizada, viewGroup, false)
        return MainViewHolderLista(view)
    }

    override fun onBindViewHolder(mainViewHolderLista: MainViewHolderLista, i: Int) {
        val dadosCorrente = dados[i]

        mainViewHolderLista.bind(dadosCorrente, listner)
    }

    override fun getItemCount(): Int = dados.size

}
