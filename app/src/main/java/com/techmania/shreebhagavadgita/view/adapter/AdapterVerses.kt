package com.techmania.shreebhagavadgita.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.techmania.shreebhagavadgita.databinding.ItemViewVersesBinding

class AdapterVerses(val onVersesItemVClicked: (String, Int) -> Unit) : RecyclerView.Adapter<AdapterVerses.VerseViewHolder>() {
    class VerseViewHolder(val binding: ItemViewVersesBinding) : ViewHolder(binding.root)


    val diffUtil =object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerseViewHolder {
       return  VerseViewHolder(ItemViewVersesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: VerseViewHolder, position: Int) {
        val verse = differ.currentList[position]
        holder.binding.tvVerseNumber.text = "Verse ${position+1}"
        holder.binding.tvVerse.text = verse

        holder.binding.ll.setOnClickListener {
            onVersesItemVClicked(verse,position+1)
        }
    }
}