package com.test.presentation.xml.fragments

import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    fun getNavController() = try {
        findNavController()
    } catch (_: Exception) {
        null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setBackListener(onBack: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBack.invoke()
        }
    }
}