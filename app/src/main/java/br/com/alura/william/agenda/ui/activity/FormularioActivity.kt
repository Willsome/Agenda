package br.com.alura.william.agenda.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.dao.AlunoDao
import br.com.alura.william.agenda.helper.AlunoHelper
import br.com.alura.william.agenda.model.Aluno

class FormularioActivity : AppCompatActivity() {

    lateinit var helper: AlunoHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        helper = AlunoHelper(this)

        val aluno = intent.getSerializableExtra("aluno") as Aluno?
        if (aluno != null) {
            helper.preencheFormulario(aluno)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_formulario, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_formulario_ok -> {

                val aluno = helper.pegaAluno()

                if (aluno.id != null) {
                    AlunoDao(this).altera(aluno)
                    Toast.makeText(
                            this,
                            "Aluno alterado !",
                            Toast.LENGTH_SHORT
                    ).show()
                } else {
                    AlunoDao(this).insere(aluno)
                    Toast.makeText(
                            this,
                            "Aluno adicionado !",
                            Toast.LENGTH_SHORT
                    ).show()
                }

                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
