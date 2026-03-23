package com.example.mentalhealth.ui.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mentalhealth.MentalHealthApplication
import com.example.mentalhealth.R
import com.example.mentalhealth.databinding.DialogAddJournalBinding
import com.example.mentalhealth.databinding.FragmentJournalBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: JournalViewModel by viewModels {
        val app = requireActivity().application as MentalHealthApplication
        JournalViewModelFactory(app.journalRepository)
    }

    private lateinit var adapter: JournalEntriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = JournalEntriesAdapter { entry ->
            viewModel.deleteEntry(entry.id)
        }
        binding.recyclerJournal.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.entries.collect { list ->
                    adapter.submitList(list)
                    val empty = list.isEmpty()
                    binding.textEmpty.visibility = if (empty) View.VISIBLE else View.GONE
                    binding.recyclerJournal.visibility = if (empty) View.GONE else View.VISIBLE
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            showAddEntryDialog()
        }
    }

    private fun showAddEntryDialog() {
        val dialogBinding = DialogAddJournalBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.journal_dialog_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.journal_dialog_save, null)
            .setNegativeButton(android.R.string.cancel) { d, _ -> d.dismiss() }
            .create()

        dialog.setOnShowListener {
            val positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positive.setOnClickListener {
                val title = dialogBinding.inputTitle.text?.toString()
                val body = dialogBinding.inputBody.text?.toString().orEmpty()
                if (body.isBlank()) {
                    dialogBinding.inputBodyLayout.error =
                        getString(R.string.journal_error_body_required)
                    return@setOnClickListener
                }
                dialogBinding.inputBodyLayout.error = null
                viewModel.addEntry(title, body)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
