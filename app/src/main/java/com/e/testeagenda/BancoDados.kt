package com.e.testeagenda

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class BancoDados  // Deica o contrutor privado para não criar novas instancias
private constructor(context: Context?) : SQLiteOpenHelper(context, DB_NOME, null, DB_VERSAO) {
    // Quando não existir uma base de dados dispara onCreated
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE agenda (id INTEGER primary key, nome_agenda TEXT,nome TEXT, hora TEXT, data_hora DATETIME)")
    }

    // Quando subir uma base da dedos ou aterações no banco de dados
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Log.e("Teste", "Upgrade Disparado");
    }

    // Grava os endereços no banco de dados
    fun adicionarItem(tipo: String?, hora: String?): Long {
        val db = writableDatabase
        db.beginTransaction()
        var calcId: Long = 0
        try {

            // Metodo para armazenar valores de conteúdo
            val valores = ContentValues()
            valores.put("nome_agenda", tipo)
            //valores.put("nome", nome);
            valores.put("hora", hora)

            // Formatar data para adicionar no banco de dados
            val formatar = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale("pt", "BR"))
            val atualData = formatar.format(Date())
            valores.put("data_hora", atualData)

            // Dados para inserção no banco de dados
            calcId = db.insertOrThrow("agenda", null, valores)
            // Completar a transação para o banco de dados
            db.setTransactionSuccessful()
        } catch (aviso: Exception) {
            Log.e("SQLite", aviso.message, aviso)
        } finally {
            db.endTransaction()
        }
        return calcId
    }

    // Editar registros no banco de dados
    fun editarItem(tipo: String, hora: String?, id: Int): Long {
        val db = writableDatabase
        db.beginTransaction()
        var calcId: Long = 0
        try {
            val valores = ContentValues()
            valores.put("nome_agenda", tipo)

            valores.put("hora", hora)

            val dataAtual = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale("pt", "BR"))
                    .format(Calendar.getInstance().time)
            valores.put("data_hora", dataAtual)

            // Passamos o whereClause para verificar o registro pelo ID e TYPE_CALC
            calcId = db.update("agenda", valores, "id = ? and nome_agenda = ?", arrayOf(id.toString(), tipo)).toLong()
            db.setTransactionSuccessful()
        } catch (aviso: Exception) {
            Log.e("SQLite", aviso.message, aviso)
        } finally {
            db.endTransaction()
        }
        return calcId
    }

    fun removerItem(tipo: String, id: Int): Long {
        val db = writableDatabase
        db.beginTransaction()
        var calcId: Long = 0
        try {
            // Passamos o whereClause para verificar o registro pelo ID e TYPE_CALC
            calcId = db.delete("agenda", "id = ? and nome_agenda = ?", arrayOf(id.toString(), tipo)).toLong()
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("Teste", e.message, e)
        } finally {
            db.endTransaction()
        }

        return calcId
    }

    // Busca os dados da lista nos bancos de dados
    fun buscarRegistro(tipo: String): List<Registro> {
        val registros: MutableList<Registro> = ArrayList()
        val sql = "SELECT * FROM agenda WHERE nome_agenda = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, arrayOf(tipo))
        try {
            if (cursor!!.moveToFirst()) {
                do {
                    val registro = Registro()
                    registro.id = cursor.getInt(cursor.getColumnIndex("id"))
                    registro.tipo = cursor.getString(cursor.getColumnIndex("nome_agenda"))
                    registro.hora = cursor.getString(cursor.getColumnIndex("hora"))
                    registro.dataCriada = cursor.getString(cursor.getColumnIndex("data_hora"))
                    registros.add(registro)
                } while (cursor.moveToNext())
                db.setTransactionSuccessful()
            }
        } catch (aviso: Exception) {
            Log.e("SQLite", aviso.message, aviso)
        } finally {
            if (cursor != null && !cursor.isClosed) cursor.close()
        }
        return registros
    }

    companion object {
        private const val DB_NOME = "agenda_barber.db"
        private const val DB_VERSAO = 7
        const val TIPO_AGENDAMENTO = "Agendamento"
        const val CLIENTES_FIDELIDADE = "Clientes"
        private var INSTANCIA: BancoDados? = null
        @JvmStatic
        fun getInstancia(context: Context?): BancoDados? {
            if (INSTANCIA == null) INSTANCIA = BancoDados(context)
            return INSTANCIA
        }

    }
}