package com.example.mentalhealth.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.mentalhealth.MentalHealthApplication
import com.example.mentalhealth.R
import com.example.mentalhealth.data.theme.ThemePreference
import com.example.mentalhealth.databinding.FragmentProfileBinding
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var suppressThemeCallback = false
    private var themeCheckedListener: MaterialButtonToggleGroup.OnButtonCheckedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireActivity().application as MentalHealthApplication

        themeCheckedListener = MaterialButtonToggleGroup.OnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked || suppressThemeCallback) return@OnButtonCheckedListener
            val pref = when (checkedId) {
                R.id.button_theme_light -> ThemePreference.LIGHT
                R.id.button_theme_dark -> ThemePreference.DARK
                else -> ThemePreference.SYSTEM
            }
            lifecycleScope.launch {
                app.themeRepository.setPreference(pref)
            }
        }
        binding.themeToggle.addOnButtonCheckedListener(themeCheckedListener!!)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                app.themeRepository.preference.collect { pref ->
                    val id = when (pref) {
                        ThemePreference.SYSTEM -> R.id.button_theme_system
                        ThemePreference.LIGHT -> R.id.button_theme_light
                        ThemePreference.DARK -> R.id.button_theme_dark
                    }
                    suppressThemeCallback = true
                    binding.themeToggle.check(id)
                    suppressThemeCallback = false
                }
            }
        }

        binding.buttonLogout.setOnClickListener {
            lifecycleScope.launch {
                app.sessionRepository.setLoggedIn(false)
                val options = NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .setLaunchSingleTop(true)
                    .build()
                findNavController().navigate(R.id.loginFragment, null, options)
            }
        }
    }

    override fun onDestroyView() {
        themeCheckedListener?.let { binding.themeToggle.removeOnButtonCheckedListener(it) }
        themeCheckedListener = null
        _binding = null
        super.onDestroyView()
    }
}
