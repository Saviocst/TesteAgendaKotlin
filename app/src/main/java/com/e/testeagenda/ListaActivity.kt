// Depois de organizar o banco de dados, uma nova activity
package com.e.testeagenda

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.testeagenda.adapter.AdaptadorLista
import com.e.testeagenda.model.EventoDeClicar2


class ListaActivity : AppCompatActivity(), EventoDeClicar2 {

    private val dados: MutableList<Registro> = ArrayList()

    private lateinit var rvLista: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        val agrupar = intent.extras
        if (agrupar == null) finish()
        if (BuildConfig.DEBUG && agrupar == null) {
            error("Assertion failed")
        }

        val tipo = agrupar?.getString("tipo")

        //val rvLista = findViewById<RecyclerView>(R.id.lista_recycler_agenda)
        rvLista = findViewById(R.id.lista_recycler_agenda)

        val db = BancoDados.getInstancia(this)

        dados.addAll(db!!.buscarRegistro(tipo!!))

        val adaptador = AdaptadorLista(dados, this)

        rvLista.adapter = adaptador

        rvLista.layoutManager = LinearLayoutManager(this)



    }


    override fun aoClicar(id: Int, tipo: String?) {
        if (tipo == BancoDados.TIPO_AGENDAMENTO) {
            val alertedit = AlertDialog.Builder(this@ListaActivity)
                    .setMessage(getString(R.string.editar_registro))
                    .setNegativeButton(R.string.sim) { _: DialogInterface?, _: Int ->
                        val intent = Intent(this@ListaActivity, AgendaActivity::class.java)
                        intent.putExtra("editarId", id)
                        startActivity(intent)
                    }
                    .setPositiveButton(R.string.não) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                    .create()
            alertedit.show()
        }
    }

    override fun aoClicarSegurar(position: Int, tipo: String?, id: Int) {
        val alertD = AlertDialog.Builder(this@ListaActivity)
                .setMessage(getString(R.string.delete_message))
                .setPositiveButton(R.string.não) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                .setNegativeButton(R.string.sim) { _: DialogInterface?, _: Int ->
                    val bancoDados = BancoDados.getInstancia(this@ListaActivity)
                    val calcId = bancoDados?.removerItem(tipo!!, id)


                    if (calcId!! > 0) {
                        //Toast.makeText(this@ListaActivity, R.string.calc_removed, Toast.LENGTH_LONG).show()
                        dados.removeAt(position)

                        Toast.makeText(this@ListaActivity, R.string.confirm_remove, Toast.LENGTH_SHORT).show()

                        rvLista.adapter?.notifyDataSetChanged()

                    }

                }
                .create()
        alertD.show()
    }


}

