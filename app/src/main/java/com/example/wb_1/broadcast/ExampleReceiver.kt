package com.example.wb_1.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class ExampleReceiver : BroadcastReceiver() {

    //Метод BroadcastReceiver, вызываемый при приходе широковещательного сообщения
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Airplane mode changed", Toast.LENGTH_LONG).show()
    }
}