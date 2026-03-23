package com.example.mentalhealth.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mentalhealth.databinding.FragmentPlaceholderBinding

class CommunityFragment : Fragment() {

    private var _binding: FragmentPlaceholderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textTitle.setText(com.example.mentalhealth.R.string.nav_community)
        binding.textBody.setText(com.example.mentalhealth.R.string.placeholder_community)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
