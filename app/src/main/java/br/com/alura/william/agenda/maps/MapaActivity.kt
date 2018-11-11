package br.com.alura.william.agenda.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.alura.william.agenda.R

class MapaActivity : AppCompatActivity() {

    private val REQUEST_PERMISSOES = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_mapa, MapaFragment())
                .commit()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissoes = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permissoes, REQUEST_PERMISSOES)
            }
        }
    }
}
