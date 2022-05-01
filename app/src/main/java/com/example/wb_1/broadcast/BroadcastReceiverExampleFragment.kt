package com.example.wb_1.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wb_1.NEW_MESSAGE_SENT
import com.example.wb_1.R
import com.example.wb_1.databinding.FragmentBroadcastReceiverExampleBinding

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
                activity?.registerReceiver(receiver, IntentFilter(
                    Intent.ACTION_AIRPLANE_MODE_CHANGED))
                Toast.makeText(context, "Receiver registered", Toast.LENGTH_SHORT).show()
            } else {
                binding?.registerBroadcastTextView?.text = getString(R.string.register_broadcast)
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