package com.techmania.shreebhagavadgita.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.techmania.shreebhagavadgita.databinding.ItemViewChaptersBinding
import com.techmania.shreebhagavadgita.models.ChaptersItem

class AdapterChapters(
    val onChapterIVClicked: (ChaptersItem) -> Unit,
   val onFavClicked: (ChaptersItem) -> Unit
) : RecyclerView.Adapter<AdapterChapters.ChapterViewHolder>() {

    class ChapterViewHolder(val binding:ItemViewChaptersBinding): ViewHolder(binding.root)

    val diffUtil = object :DiffUtil.ItemCallback<ChaptersItem>(){
        override fun areItemsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ =AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
       return ChapterViewHolder(ItemViewChaptersBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
       val chapter = differ.currentList[position]

        holder.binding.apply {
            tvChapterNumber.text = "Chapter ${chapter.chapter_number}"
            tvChapterTitle.text= chapter.name_translated
            tvChapterDescription.text = chapter.chapter_summary
            tvChapterDescriptionHindi.text = chapter.chapter_summary_hindi
            tvVersesCount.text = chapter.verses_count.toString()
        }

         holder.binding.ll.setOnClickListener{
             onChapterIVClicked(chapter)
         }

        holder.binding.apply {
            ivFavorite.setOnClickListener { onFavClicked(chapter) }
        }

    }


}