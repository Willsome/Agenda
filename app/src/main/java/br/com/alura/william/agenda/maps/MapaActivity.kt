package br.com.alura.william.agenda.maps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.william.agenda.R
import com.google.android.gms.maps.SupportMapFragment

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
