package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.SimpleListBinding
import com.example.myapplication.diff.QuoteDiff
import com.example.practice.models.Quotes

class QuoteAdapter() : RecyclerView.Adapter<QuoteAdapter.MyViewHolder>(){
    var onClickCallback:((Int)->Unit)? = null
    private val listOfQuotes = mutableListOf<Quotes>()
    inner class MyViewHolder(private val simpleListBinding: SimpleListBinding)
        :RecyclerView.ViewHolder(simpleListBinding.root){
        fun bind(quotes: Quotes){
            simpleListBinding.textView.text = quotes.quote
            simpleListBinding.textView.setOnClickListener {
                onClickCallback?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = SimpleListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfQuotes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listOfQuotes[position])
    }

    fun fetchQuotes(quotes: List<Quotes>){
        val diffCallback = QuoteDiff(listOfQuotes, quotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listOfQuotes.clear()
        listOfQuotes.addAll(quotes)
        diffResult.dispatchUpdatesTo(this)
    }



}