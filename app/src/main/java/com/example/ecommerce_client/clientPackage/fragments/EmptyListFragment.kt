package com.example.ecommerce_client.clientPackage.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerce_client.R
import com.example.ecommerce_client.databinding.FragmentEmptyListBinding
import com.example.ecommerce_client.databinding.FragmentOrderBinding

class EmptyListFragment : Fragment() {
    private lateinit var binding: FragmentEmptyListBinding
    private var text: String? = "No result"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmptyListBinding.inflate(inflater, container, false)
        binding.actionText.text = text
        return binding.root
    }
    fun setActionText(text: String){
        this.text = text
    }
}