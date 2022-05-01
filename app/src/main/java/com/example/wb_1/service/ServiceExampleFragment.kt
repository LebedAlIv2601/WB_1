package com.example.wb_1.service

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wb_1.databinding.FragmentServiceExampleBinding

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
                activity?.startService(Intent(context, ExampleService::class.java))
            }

            buttonStopMusic.setOnClickListener {
                activity?.stopService(Intent(context, ExampleService::class.java))
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}