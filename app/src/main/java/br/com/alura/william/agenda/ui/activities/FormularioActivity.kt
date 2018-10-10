package br.com.alura.william.agenda.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.alura.william.agenda.R
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        bt_formulario_salvar.setOnClickListener {
            Toast.makeText(
                    this,
                    "Botão clicado !",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
}
