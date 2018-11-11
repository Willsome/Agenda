package br.com.alura.william.agenda.web

import java.io.PrintStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class WebClient {

    fun post(json: String): String {

        try {

            val url = URL("https://www.caelum.com.br/mobile")
            val httpURLConnection = url.openConnection() as HttpURLConnection

            httpURLConnection.setRequestProperty("Accept", "application/json")
            httpURLConnection.setRequestProperty("Content-type", "application/json")

            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true

            val saida = PrintStream(httpURLConnection.outputStream)
            saida.println(json)

            httpURLConnection.connect()

            val resposta = Scanner(httpURLConnection.inputStream).next()

            return resposta

        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}