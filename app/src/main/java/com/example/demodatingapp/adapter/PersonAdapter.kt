package com.example.demodatingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demodatingapp.databinding.ListItemPersonBinding
import com.example.demodatingapp.model.PersonModel

class PersonAdapter: ListAdapter<PersonModel, PersonAdapter.ViewHolder>(PersonDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = getItem(position)
        holder.apply {
            bind(person)
            itemView.tag = person
        }
    }

    class ViewHolder(private val binding: ListItemPersonBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PersonModel) {
            binding.apply {
                person = item
                binding.personPhoto.setImageResource(item.galleryImages.first())
                binding.listItemHeader.binding.person = item
                executePendingBindings()
            }
        }
    }
}
