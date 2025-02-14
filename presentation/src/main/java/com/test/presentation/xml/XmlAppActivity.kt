package com.test.presentation.xml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.presentation.databinding.ActivityXmlAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class XmlAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityXmlAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityXmlAppBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}