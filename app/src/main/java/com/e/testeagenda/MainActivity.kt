package com.e.testeagenda

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.testeagenda.MainActivity.AdaptadorPrincipal.MainViewHolder
import com.e.testeagenda.model.EventoDeClicar
import com.e.testeagenda.model.ItemPrincipal
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), EventoDeClicar {

    lateinit var rvAgenda: RecyclerView

    private var itemNovos = ArrayList<ItemPrincipal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvAgenda = findViewById(R.id.recycle_principal)

        /*val itemNovos: MutableList<ItemPrincipal> = ArrayList()
        itemNovos.add(ItemPrincipal(1, R.drawable.ic_baseline_ad_units_24_agenda, R.string.agendar, Color.TRANSPARENT))
        itemNovos.add(ItemPrincipal(2, R.drawable.ic_baseline_article_24_lista, R.string.lista, Color.TRANSPARENT))
        itemNovos.add(ItemPrincipal(3, R.drawable.ic_baseline_clientes_vip, R.string.clientes_vip, Color.TRANSPARENT))*/

        createdList()

        rvAgenda.layoutManager = (GridLayoutManager(this, 2))
        val adaptador = AdaptadorPrincipal(this, itemNovos)
        rvAgenda.adapter = adaptador



    }

    private inner class AdaptadorPrincipal(private var listener: EventoDeClicar, val itensNovos: List<ItemPrincipal>) : RecyclerView.Adapter<MainViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            return MainViewHolder(layoutInflater.inflate(R.layout.item_principal, parent, false))
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCorrente = itensNovos[position]

            // bind Criado para rodar os componentes separados
            holder.bind(itemCorrente)
        }

        override fun getItemCount(): Int {
            return itensNovos.size
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: ItemPrincipal) {
                val editText = itemView.findViewById<TextView>(R.id.text_agenda)
                val editImagem = itemView.findViewById<ImageView>(R.id.img_icon_agenda)
                val botaoAgenda = itemView.findViewById<LinearLayout>(R.id.btn_agenda)
                botaoAgenda.setOnClickListener { listener.click(item.id) }

                // Montar o adaptador
                editText.setText(item.nome)
                editImagem.setImageResource(item.drawable)
                botaoAgenda.setBackgroundColor(item.cor)
            }
        }
    }

    private fun createdList(){
        for (item in 1..1){
            itemNovos.add(ItemPrincipal(1, R.drawable.ic_baseline_ad_units_24_agenda, R.string.agendar, Color.TRANSPARENT))
            itemNovos.add(ItemPrincipal(2, R.drawable.ic_baseline_article_24_lista, R.string.lista, Color.TRANSPARENT))
            itemNovos.add(ItemPrincipal(3, R.drawable.ic_baseline_clientes_vip, R.string.clientes_vip, Color.TRANSPARENT))
        }
    }

    override fun click(id: Int) {
        when (id) {
            1 -> startActivity(Intent(this@MainActivity, AgendaActivity::class.java))
            2 -> {
                // Buscando a Activity com os  dados do Banco de Dados
                val intent = Intent(this@MainActivity, ListaActivity::class.java)
                intent.putExtra("tipo", BancoDados.TIPO_AGENDAMENTO)
                startActivity(intent)
            }
            3 -> startActivity(Intent(this@MainActivity, ActivityClientesVip::class.java))}
    }
}