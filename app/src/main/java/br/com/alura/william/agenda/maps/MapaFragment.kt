package br.com.alura.william.agenda.maps

import android.location.Geocoder
import android.os.Bundle
import br.com.alura.william.agenda.dao.AlunoDao
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapaFragment : SupportMapFragment(), OnMapReadyCallback {

    private var googleMaps: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMapAsync(this)
    }

    override fun onMapReady(googleMaps: GoogleMap?) {

        this.googleMaps = googleMaps!!

        val posicao = pegaCoordenadaDoEndereco("Rua Vergueiro 3185, Vila Mariana, São Paulo")
        if (posicao != null) {
            centralizaEm(posicao)
        }

        val dao = AlunoDao(context)
        val alunos = dao.buscaAlunos()
        for (aluno in alunos) {
            val coordenada = pegaCoordenadaDoEndereco(aluno.endereco)
            if (coordenada != null) {
                val marcador = MarkerOptions()
                marcador.position(coordenada)
                marcador.title(aluno.nome)
                marcador.snippet(aluno.nota.toString())
                googleMaps.addMarker(marcador)
            }
        }
        dao.close()

        Localizador(context!!, googleMaps)
    }

    fun centralizaEm(coordenada: LatLng) {
        if (googleMaps != null) {
            val update = CameraUpdateFactory.newLatLngZoom(coordenada, 17F)
            googleMaps!!.moveCamera(update)
        }
    }

    private fun pegaCoordenadaDoEndereco(endereco: String?): LatLng? {
        try {
            val resultados = Geocoder(context)
                    .getFromLocationName(
                            endereco,
                            1
                    )

            if (!resultados.isEmpty()) {
                val posicao = LatLng(resultados[0].latitude, resultados[0].longitude)
                return posicao
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}