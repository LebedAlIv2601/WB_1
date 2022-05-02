package com.example.wb_1.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wb_1.R
import com.example.wb_1.databinding.FragmentBroadcastReceiverExampleBinding

/**
 * Экран, предназначенный для демонстрации работы BroadcastReceiver.
 * Функциональность экрана заключается в следующем:
 * 1) Есть возможность зарегистрировать BroadcastReceiver для прослушивания события "Изменение
 * состояния режима полета", нажав на кнопку "Register Broadcast".
 * 2) Есть возможность снять с регистрации BroadcastReceiver для прекращения прослушивания события
 * "Изменение состояния режима полета", нажав на кнопку "Unregister Broadcast".
 *
 * Функционал BroadcastReceiver чаще всего используется приложениями для получения каких-либо
 * сообщений от системы, например, сообщений о низком заряде батареи или об отсутсвии подключения
 * к сети. Примеры таких приложений: игровые приложения с мультиплеером для отслеживания состояния
 * сети и батареи для предупреждения игрока.
 */

class BroadcastReceiverExampleFragment : Fragment() {

    private var binding: FragmentBroadcastReceiverExampleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBroadcastReceiverExampleBinding
            .inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receiver = ExampleReceiver()

        binding?.buttonRegisterBroadcast?.setOnClickListener {
            if(binding?.registerBroadcastTextView?.text
                == getString(R.string.register_broadcast)) {

                binding?.registerBroadcastTextView?.text = getString(R.string.unregister_broadcast)
                // Регистрация BroadcastReceiver для прослушивания события "Изменение состояния
                // режима полета"
                activity?.registerReceiver(receiver, IntentFilter(
                    Intent.ACTION_AIRPLANE_MODE_CHANGED))
                Toast.makeText(context, "Receiver registered", Toast.LENGTH_SHORT).show()
            } else {
                binding?.registerBroadcastTextView?.text = getString(R.string.register_broadcast)
                // Снятие регистрации BroadcastReceiver для прослушивания события
                // "Изменение состояния режима полета"
                activity?.unregisterReceiver(receiver)
                Toast.makeText(context, "Receiver unregistered", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}