package br.com.alura.william.agenda.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.adapter.ListaAlunosAdapter
import br.com.alura.william.agenda.dao.AlunoDao
import br.com.alura.william.agenda.maps.MapaActivity
import br.com.alura.william.agenda.model.Aluno
import br.com.alura.william.agenda.utils.AlunoConverter
import br.com.alura.william.agenda.web.WebClient
import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivity : AppCompatActivity() {

    private val CODIGO_PERMISSAO_SMS = 312
    private val CODIGO_PERMISSAO_LIGACAO = 69

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        pegaPermissaoSMS()

        registerForContextMenu(lv_lista_alunos)

        bt_lista_alunos_novo_aluno.setOnClickListener {
            val vaiProFormulario = Intent(
                    this,
                    FormularioActivity::class.java
            )
            startActivity(vaiProFormulario)
        }
    }

    private fun pegaPermissaoSMS() {
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this@ListaAlunosActivity,
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    CODIGO_PERMISSAO_SMS
            )
        }
    }

    override fun onResume() {
        super.onResume()
        carregaAlunos()
    }

    private fun carregaAlunos() {
        val alunos = AlunoDao(this).buscaAlunos()

        val adapter = ListaAlunosAdapter(
                this,
                alunos
        )

        lv_lista_alunos.adapter = adapter

        lv_lista_alunos.setOnItemClickListener { parent, view, position, id ->
            val aluno = lv_lista_alunos.getItemAtPosition(position) as Aluno
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra("aluno", aluno)
            startActivity(intent)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, menuItem: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        val info = menuInfo as AdapterView.AdapterContextMenuInfo

        val aluno = lv_lista_alunos.getItemAtPosition(info.position) as Aluno

        val menuLigar = menu?.add("Ligar")
        menuLigar?.setOnMenuItemClickListener(
                MenuItem.OnMenuItemClickListener {

                    if (ActivityCompat.checkSelfPermission(
                                    this,
                                    android.Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                                this@ListaAlunosActivity,
                                arrayOf(android.Manifest.permission.CALL_PHONE),
                                CODIGO_PERMISSAO_LIGACAO
                        )

                    } else {
                        val intentLigar = Intent(Intent.ACTION_CALL)
                        intentLigar.data = Uri.parse("tel:" + aluno.telefone)
                        startActivity(intentLigar)
                    }

                    /*val intentLigar = Intent(Intent.ACTION_CALL)
                    intentLigar.data = Uri.parse("tel:" + aluno.telefone)
                    startActivity(intentLigar)*/

                    return@OnMenuItemClickListener false
                }
        )

        val menuIrParaSite = menu?.add("Ir para Site")
        val intentSite = Intent(Intent.ACTION_VIEW)
        var site = aluno.site
        if (!site!!.startsWith("http://")) {
            site = "http://$site"
        }
        intentSite.data = Uri.parse(site)
        menuIrParaSite?.intent = intentSite

        val menuEnviarSMS = menu?.add("SMS")
        val intentSMS = Intent(Intent.ACTION_VIEW)
        intentSMS.data = Uri.parse("sms:" + aluno.telefone)
        menuEnviarSMS?.intent = Intent(Intent.ACTION_VIEW)

        val menuIrParaMapa = menu?.add("Vai Para Mapa")
        val intentMapa = Intent(Intent.ACTION_VIEW)
        intentMapa.data = Uri.parse("geo:0,0?z=14&q=" + aluno.endereco)
        menuIrParaMapa?.intent = Intent(Intent.ACTION_VIEW)

        val menuDeletar = menu?.add("Deletar")
        menuDeletar?.setOnMenuItemClickListener(
                MenuItem.OnMenuItemClickListener {
                    AlertDialog.Builder(this@ListaAlunosActivity)
                            .setTitle("Deletando aluno")
                            .setMessage("Tem certeza que deseja deletar este aluno ?")
                            .setPositiveButton("sim") {
                                dialogInterface, i ->
                                AlunoDao(this).deleta(aluno)
                                carregaAlunos()
                            }
                            .setNegativeButton("não", null)
                            .show()

                    return@OnMenuItemClickListener false
                }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_alunos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.menu_lista_alunos_enviar_notas -> {
                ListaAlunosTask().execute()
            }

            R.id.menu_lista_alunos_baixar_provas -> {
                startActivity(Intent(this, ProvasActivity::class.java))
            }

            R.id.menu_lista_alunos_mapa -> {
                startActivity(Intent(this, MapaActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private inner class ListaAlunosTask : AsyncTask<Void, Void, Any>() {

        override fun onPreExecute() {
            super.onPreExecute()
            pbListaAlunos.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: Void?): Any {
            val dao = AlunoDao(this@ListaAlunosActivity)
            val alunos = dao.buscaAlunos()
            dao.close()
            val json = AlunoConverter().toJSON(alunos)
            val resposta = WebClient().post(json)

            return resposta
        }

        override fun onPostExecute(resposta: Any?) {
            super.onPostExecute(resposta)
            pbListaAlunos.visibility = View.GONE
            Toast.makeText(
                    this@ListaAlunosActivity,
                    resposta as String,
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
}
