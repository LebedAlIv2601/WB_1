package com.example.wb_1.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.wb_1.R

class ExampleService : Service() {

    //Экземпляр класса MediaPlayer для проигрывания музыки
    private var musicPlayer: MediaPlayer? = null

    //Метод вызывается при попытке других компонентов привязаться к сервису.
    //Возвращаем null,так как не хотим осуществлять привязку.
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    //Метод вызывается при создании сервиса.
    override fun onCreate() {
        super.onCreate()
        musicPlayer = MediaPlayer.create(this, R.raw.solmetal3)
        musicPlayer?.isLooping = false
    }

    //Метод выполняет запуск сервиса.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        musicPlayer?.start()
        return super.onStartCommand(intent, flags, startId)
    }

    //Метод вызывается при уничтожении сервиса.
    override fun onDestroy() {
        super.onDestroy()
        musicPlayer?.stop()
    }
}