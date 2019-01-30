package com.example.demodatingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demodatingapp.databinding.ListItemPersonBinding
import com.example.demodatingapp.fragment.ListFragmentDirections
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
            bind(person, createOnClickListener(position))
            itemView.tag = person
        }
    }

    private fun createOnClickListener(index: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction = ListFragmentDirections.navigationToDetail(index)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(private val binding: ListItemPersonBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PersonModel, clickListener: View.OnClickListener) {
            binding.apply {
                person = item
                this.clickListener = clickListener
                binding.personPhoto.setImageResource(item.galleryImages.first())
                binding.listItemHeader.binding.person = item
                executePendingBindings()
            }
        }
    }
}
