package com.example.wb_1.service

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wb_1.databinding.FragmentServiceExampleBinding

/**
 * Экран, предназначенный для демонстрации работы Service.
 * Функциональность экрана заключается в следующем:
 * 1) Есть возможность запустить воспроизведение музыки, нажав на кнопку "Start". Музыка будет
 * проигрываться в фоновом режиме.
 * 2) Есть возможность остановить воспроизведение музыки, нажав на кнопку "Stop".
 *
 * Функционал Service используется приложениями для выполнения фоновых операций, не требующих
 * непосредственного участия пользователя, например, проигрывание музыки, подгрузка файлов из сети.
 * Примеры приложений: Яндекс.Музыка (проигрывание музыки), приложения для занятий спортом
 * (подсчет различных показателей в фоновом режиме).
 */

class ServiceExampleFragment : Fragment() {

    private var binding: FragmentServiceExampleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceExampleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            buttonStartMusic.setOnClickListener {
                //Запуск сервиса
                activity?.startService(Intent(context, ExampleService::class.java))
            }

            buttonStopMusic.setOnClickListener {
                //Остановка сервиса
                activity?.stopService(Intent(context, ExampleService::class.java))
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}