package br.com.alura.william.agenda.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.alura.william.agenda.model.Aluno

class AlunoDao(context: Context?) : SQLiteOpenHelper(context, "AndroidDb", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE Alunos(" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "endereco TEXT," +
                "telefone TEXT," +
                "site TEXT," +
                "nota REAL," +
                "caminhoFoto TEXT" +
                ");"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        /*val sql = "DROP TABLE IF EXISTS Alunos;"
        db?.execSQL(sql)
        onCreate(db)*/
        var sql = ""
        when (oldVersion) {
            1 -> {
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT;"
                db?.execSQL(sql)
            }
        }
    }

    fun insere(aluno: Aluno): Unit {
        writableDatabase.insert("Alunos", null, createContentValues(aluno))
    }

    fun buscaAlunos(): List<Aluno> {

        val sql = "SELECT * FROM Alunos;"

        val alunos = mutableListOf<Aluno>()

        val cursor = readableDatabase.rawQuery(sql, null)
        while (cursor.moveToNext()) {

            val aluno = Aluno()
            aluno.id = cursor.getLong(cursor.getColumnIndex("id"))
            aluno.nome = cursor.getString(cursor.getColumnIndex("nome"))
            aluno.endereco = cursor.getString(cursor.getColumnIndex("endereco"))
            aluno.telefone = cursor.getString(cursor.getColumnIndex("telefone"))
            aluno.site = cursor.getString(cursor.getColumnIndex("site"))
            aluno.nota = cursor.getDouble(cursor.getColumnIndex("nota"))
            aluno.caminhoFoto = cursor.getString(cursor.getColumnIndex("caminhoFoto"))

            alunos.add(aluno)
        }
        cursor.close()

        return alunos
    }

    fun deleta(aluno: Aluno) {
        val params = arrayOf(aluno.id.toString())
        writableDatabase.delete("Alunos", "id = ?", params)
    }

    fun altera(aluno: Aluno) {
        val params = arrayOf(aluno.id.toString())
        writableDatabase.update("Alunos", createContentValues(aluno), "id = ?", params)
    }

    private fun createContentValues(aluno: Aluno): ContentValues {
        val contentValues = ContentValues()
        contentValues.put("id", aluno.id)
        contentValues.put("nome", aluno.nome)
        contentValues.put("endereco", aluno.endereco)
        contentValues.put("telefone", aluno.telefone)
        contentValues.put("site", aluno.site)
        contentValues.put("nota", aluno.nota)
        contentValues.put("caminhoFoto", aluno.caminhoFoto)
        return contentValues
    }

    fun isAluno(telefone: String): Boolean {
        val sql = "SELECT * FROM Alunos WHERE telefone = ?;"
        val cursor = readableDatabase.rawQuery(sql, arrayOf(telefone))
        val resultado = cursor.count
        cursor.close()
        return resultado > 0;
    }
}