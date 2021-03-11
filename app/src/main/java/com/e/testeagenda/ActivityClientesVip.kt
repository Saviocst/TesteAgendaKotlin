package com.e.testeagenda

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.e.testeagenda.BancoDados.Companion.getInstancia

class ActivityClientesVip : AppCompatActivity() {
    private var nomeCliente: EditText? = null
    private var numeroCliente: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes_vip)
        nomeCliente = findViewById(R.id.nome_cliente)
        numeroCliente = findViewById(R.id.numero_cel)
        val btnCadastro = findViewById<Button>(R.id.btn_cadastro)
        btnCadastro.setOnClickListener { v: View? -> cadastrar(v) }
    }

    fun cadastrar(v: View?) {
        if (!validar()) {
            Toast.makeText(this@ActivityClientesVip, R.string.mensagem_erro, Toast.LENGTH_LONG).show()
        }
        val nome = nomeCliente!!.text.toString()
        val numero = numeroCliente!!.text.toString()
        //int numero = Integer.parseInt(numeroCliente.getText().toString());
        val resposta = cadastroCliente(nome, numero)
        val telaResp = AlertDialog.Builder(this@ActivityClientesVip)
                .setTitle(getString(R.string.confirma_cadastro))
                .setMessage(resposta)
                .setPositiveButton(android.R.string.ok) { dialog: DialogInterface?, which: Int -> }
                .setNegativeButton(R.string.salvar) { dialog: DialogInterface?, which: Int ->
                    val bancoDados = getInstancia(this@ActivityClientesVip)
                    var editarId = 0

                    // verifica se tem ID vindo da tela anterior quando é UPDATE
                    if (intent.extras != null) editarId = intent.extras!!.getInt("editarId", 0)
                    val calcId: Long
                    // verifica se é update ou create
                    calcId = if (editarId > 0) {
                        bancoDados!!.editarItem(BancoDados.CLIENTES_FIDELIDADE, resposta, editarId)
                    } else {
                        bancoDados!!.adicionarItem(BancoDados.CLIENTES_FIDELIDADE, resposta)
                    }
                    if (calcId > 0) {
                        Toast.makeText(this@ActivityClientesVip, R.string.concluido, Toast.LENGTH_LONG).show()
                        abrirLista()
                    }
                }
                .create()
        telaResp.show()
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

    private fun abrirLista() {
        val intent = Intent(this@ActivityClientesVip, ListaActivity::class.java)
        intent.putExtra("tipo", BancoDados.CLIENTES_FIDELIDADE)
        startActivity(intent)
    }

    // Validar os campos
    private fun validar(): Boolean {
        return (!nomeCliente!!.text.toString().startsWith("0")
                && !nomeCliente!!.text.toString().isEmpty()
                && !numeroCliente!!.text.toString().startsWith("0")
                && !numeroCliente!!.text.toString().isEmpty())
    }

    private fun cadastroCliente(nome: String, numero: String): String {
        return "Cliente: $nome\nNúmero(cel): $numero"
        //"Cliente: "+ nome + "\n" +
    }
}