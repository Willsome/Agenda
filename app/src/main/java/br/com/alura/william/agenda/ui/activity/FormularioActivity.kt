package br.com.alura.william.agenda.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.alura.william.agenda.BuildConfig
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.dao.AlunoDao
import br.com.alura.william.agenda.helper.AlunoHelper
import br.com.alura.william.agenda.model.Aluno
import kotlinx.android.synthetic.main.activity_formulario.*
import java.io.File

class FormularioActivity : AppCompatActivity() {

    lateinit var helper: AlunoHelper

    private val CODIGO_CAMERA_INTENT_RESULT: Int = 123

    private lateinit var caminhoFoto: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        helper = AlunoHelper(this)

        val aluno = intent.getSerializableExtra("aluno") as Aluno?
        if (aluno != null) {
            helper.preencheFormulario(aluno)
        }

        bt_formulario_foto.setOnClickListener {
            caminhoFoto = getExternalFilesDir(null)!!.toString() + "/" + System.currentTimeMillis() + ".jpg"
            val arquivoFoto = File(caminhoFoto)
            val intentTiraFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            intentTiraFoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto)) // Android < 7
            intentTiraFoto.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            arquivoFoto
                    )
            )
            startActivityForResult(intentTiraFoto, CODIGO_CAMERA_INTENT_RESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODIGO_CAMERA_INTENT_RESULT && resultCode == Activity.RESULT_OK) {
            helper.carregaFoto(caminhoFoto)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formulario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_formulario_salvar -> {

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
