package com.e.testeagenda

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AgendaActivity : AppCompatActivity() {

    private var editNome: EditText? = null
    private var editHora: EditText? = null
    private var diaAgendamento: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        editNome = findViewById(R.id.txt_nome_agenda)
        editHora = findViewById(R.id.txt_hora_agenda)
        diaAgendamento = findViewById(R.id.dias_de_agendamento)

        val botaoAgenda = findViewById<Button>(R.id.btn_agenda)
        botaoAgenda.setOnClickListener { clicar() }
    }

    private fun clicar() {
        if (!valid()) {
            Toast.makeText(this@AgendaActivity, R.string.erro, Toast.LENGTH_LONG).show()
            return
        }
        val sNome = editNome?.text.toString()
        val sHora = editHora?.text.toString()

        val resp = agendaOrg(sNome, sHora)
        val respostaFinal = diaSemana(resp)
        val telaResp = AlertDialog.Builder(this@AgendaActivity)
                .setTitle(getString(R.string.confirma_agenda))
                .setMessage(respostaFinal)
                .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int -> }
                .setNegativeButton(R.string.salvar) { _: DialogInterface?, _: Int ->
                    val bancoDados = BancoDados.getInstancia(this@AgendaActivity)
                    var editarId = 0

                    // verifica se tem ID vindo da tela anterior quando é UPDATE
                    if (intent.extras != null) editarId = intent.extras!!.getInt("editarId", 0)
                    // verifica se é update ou create
                    val calcId: Long = if (editarId > 0) {
                        bancoDados!!.editarItem(BancoDados.TIPO_AGENDAMENTO, respostaFinal, editarId)
                    } else {
                        bancoDados!!.adicionarItem(BancoDados.TIPO_AGENDAMENTO, respostaFinal)
                    }
                    if (calcId > 0) {
                        Toast.makeText(this@AgendaActivity, R.string.concluido, Toast.LENGTH_LONG).show()
                        abrirLista()
                    }
                }
                .create()
        telaResp.show()

        // Esconder Teclado
        val esconder = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        esconder.hideSoftInputFromWindow(editNome?.windowToken, 0)
        esconder.hideSoftInputFromWindow(editHora?.windowToken, 0)
    }

    // Criar opção de menu no apk Parte 1
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // Escutar eventos de click no Menu Parte 2
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Caso tenha mais de um botão de menu usa o switch
        if (item.itemId == R.id.menu_agenda) {
            abrirLista()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Forma organizada de mostrar a lista de arquivos salvos
    private fun abrirLista() {
        val intent = Intent(this@AgendaActivity, ListaActivity::class.java)
        intent.putExtra("tipo", BancoDados.TIPO_AGENDAMENTO)
        startActivity(intent)
    }

    // Resultado e organização do agendamento
    // Tirei o strig nome
    private fun agendaOrg(nome: String, hora: String): String {
        return "Nome: $nome \nHora: $hora"

    }

    // Metodo para selecionar o dia do aendamento
    private fun diaSemana(dias: String): String {
        return when (diaAgendamento?.selectedItemPosition) {
            0 -> "$dias\nDia agendado: Domingo"
            1 -> "$dias\nDia agendado: Segunda-Feira"
            2 -> "$dias\nDia agendado: Terça-Feira"
            3 -> "$dias\nDia agendado: Quarta-Feira"
            4 -> "$dias\nDia agendado: Quinta-Feira"
            5 -> "$dias\nDia agendado: Sexta-Feira"
            6 -> "$dias\nDia agendado: Sabado"
            else -> "$dias\nDia agendado: Dia não definido"
        }
    }

    private fun valid(): Boolean {
        return (editNome?.text.toString().isNotEmpty()
                && editHora?.text.toString().isNotEmpty())
    }
}