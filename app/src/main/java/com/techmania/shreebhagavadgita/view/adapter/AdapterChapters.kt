package com.techmania.shreebhagavadgita.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.techmania.shreebhagavadgita.databinding.ItemViewChaptersBinding
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel
import kotlin.reflect.KFunction1

class AdapterChapters(
    val onChapterIVClicked: (ChaptersItem) -> Unit,
    val onFavClicked: (ChaptersItem) -> Unit,
    val onFavFilledClicked: KFunction1<ChaptersItem, Unit>,
    val viewModel: MainViewModel,
    val showSaveButton : Boolean,

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

        val keys = viewModel.getAllSavedChaptersSP()

        if(!showSaveButton){
            holder.binding.apply {
                ivFavorite.visibility = View.GONE
                ivFavoriteFilled.visibility = View.GONE
            }

        }
        else{
            if(keys.contains(chapter.chapter_number.toString())){
                holder.binding.apply {
                    ivFavorite.visibility = View.GONE
                    ivFavoriteFilled.visibility = View.VISIBLE
                }
            }
            else{
                holder.binding.apply {
                    ivFavorite.visibility = View.VISIBLE
                    ivFavoriteFilled.visibility = View.GONE
                }

            }
        }

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
            ivFavorite.setOnClickListener {
                ivFavoriteFilled.visibility = View.VISIBLE
                ivFavorite.visibility = View.GONE
                onFavClicked(chapter) }

            ivFavoriteFilled.setOnClickListener {
                ivFavorite.visibility = View.VISIBLE
                ivFavoriteFilled.visibility = View.GONE
                onFavFilledClicked(chapter)
                 }

        }

    }


}