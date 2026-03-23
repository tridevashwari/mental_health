package com.example.mentalhealth.ui.journal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalhealth.data.local.JournalEntry
import com.example.mentalhealth.databinding.ItemJournalEntryBinding
import java.text.DateFormat
import java.util.Date

class JournalEntriesAdapter(
    private val onDelete: (JournalEntry) -> Unit,
) : ListAdapter<JournalEntry, JournalEntriesAdapter.Holder>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemJournalEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return Holder(binding, onDelete)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: ItemJournalEntryBinding,
        private val onDelete: (JournalEntry) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: JournalEntry) {
            val title = entry.title
            if (title.isNullOrBlank()) {
                binding.textTitle.visibility = android.view.View.GONE
            } else {
                binding.textTitle.visibility = android.view.View.VISIBLE
                binding.textTitle.text = title
            }
            binding.textBody.text = entry.body
            val fmt = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
            binding.textDate.text = fmt.format(Date(entry.createdAtEpochMs))
            binding.buttonDelete.setOnClickListener { onDelete(entry) }
        }
    }

    private object Diff : DiffUtil.ItemCallback<JournalEntry>() {
        override fun areItemsTheSame(oldItem: JournalEntry, newItem: JournalEntry) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: JournalEntry, newItem: JournalEntry) =
            oldItem == newItem
    }
}
