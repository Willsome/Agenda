package br.com.alura.william.agenda.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.william.agenda.R

class MapaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_mapa, MapaFragment())
                .commit()
    }
}
