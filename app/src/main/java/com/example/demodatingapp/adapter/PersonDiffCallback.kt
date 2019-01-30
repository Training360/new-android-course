package com.example.demodatingapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.demodatingapp.model.PersonModel

class PersonDiffCallback : DiffUtil.ItemCallback<PersonModel>() {
    override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return oldItem == newItem
    }
}