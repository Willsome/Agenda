package br.com.alura.william.agenda.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

class CarregadorDeFoto {

    fun carregaBitmap(caminhoFoto: String): Bitmap {
        val exifInterface = ExifInterface(caminhoFoto)
        val orientacao: String = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)
        val codigoOrientacao: Int = Integer.parseInt(orientacao)

        lateinit var fotoRotacionada: Bitmap
        when (codigoOrientacao) {
            // Rotaciona 0° no sentido horário
            ExifInterface.ORIENTATION_NORMAL -> fotoRotacionada = abreFotoERotaciona(caminhoFoto, 0)

            // Rotaciona 90° no sentido horário
            ExifInterface.ORIENTATION_ROTATE_90 -> fotoRotacionada = abreFotoERotaciona(caminhoFoto, 90)

            // Rotaciona 180° no sentido horário
            ExifInterface.ORIENTATION_ROTATE_180 -> fotoRotacionada = abreFotoERotaciona(caminhoFoto, 180)

            // Rotaciona 270° no sentido horário
            ExifInterface.ORIENTATION_ROTATE_270 -> fotoRotacionada = abreFotoERotaciona(caminhoFoto, 270)
        }

        return fotoRotacionada
    }

    fun abreFotoERotaciona(caminhoFoto: String, angulo: Int): Bitmap {
        // Abre o bitmap a partir do caminho da foto
        val bitmap: Bitmap = BitmapFactory.decodeFile(caminhoFoto)

        // Prepara a operação de rotação com o ângulo da foto
        val matrix = Matrix()
        matrix.postRotate(angulo.toFloat())

        // Cria um novo bitmap a partir do original já com a rotação aplicada
        return Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
        )
    }
}