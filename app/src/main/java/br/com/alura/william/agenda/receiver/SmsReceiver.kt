package br.com.alura.william.agenda.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.dao.AlunoDao

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val dao = AlunoDao(context)

        val pdus = intent?.getSerializableExtra("pdus") as Array<Any>
        val pdu = pdus[0] as ByteArray

        val formato = intent?.getSerializableExtra("format")

        val sms = android.telephony.gsm.SmsMessage.createFromPdu(pdu)

        val telefone = sms.displayOriginatingAddress

        if (dao.isAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS de Aluno!", Toast.LENGTH_SHORT).show()
            MediaPlayer.create(context, R.raw.msg).start()
        }
        dao.close()
    }
}