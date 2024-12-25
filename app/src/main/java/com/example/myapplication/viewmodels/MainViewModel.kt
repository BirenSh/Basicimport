package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.NetworkRepository
import com.example.practice.models.Quotes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val networkRepository: NetworkRepository) :ViewModel() {

    private val _listOfQuote = MutableLiveData<List<Quotes>>()
    val listOfQuote:LiveData<List<Quotes>>
        get() = _listOfQuote

    private val _filteredQuotes = MutableLiveData<List<Quotes>>()
    val filteredQuotes: LiveData<List<Quotes>> get() = _filteredQuotes

    suspend fun getQuotes(){
        val response = networkRepository.getQuotes()
        if (response.isSuccessful && response.body() != null){
            val listOfQuote = response.body()?.quotes
            _listOfQuote.postValue(listOfQuote)
            println("===listOfQuote; ${listOfQuote?.size}")
        }
    }

     fun addQuotes(quotes:List<Quotes>){
        val listQ = _listOfQuote.value.orEmpty().toMutableList()
        listQ.addAll(quotes)
        _listOfQuote.postValue(listQ)
    }

    fun deleteQuote(quote:Quotes){
        val listOfOld = _listOfQuote.value.orEmpty().toMutableList()
        if (quote in listOfOld){
            listOfOld.remove(quote)
        }
        _listOfQuote.postValue(listOfOld)
    }

    fun editQuote(posiiton:Int,quote: Quotes){
        val listOfOld = _listOfQuote.value.orEmpty().toMutableList()
        if (posiiton in listOfOld.indices){
            listOfOld[posiiton] = quote
        }
        _listOfQuote.postValue(listOfOld)
    }

    fun filterQuote(quote:String){
        val fullList = _listOfQuote.value.orEmpty()
        if (quote.isEmpty()){
            _filteredQuotes.postValue(fullList)
        }else{
            val filter = fullList.filter { it.quote.contains(quote,true) }
            _filteredQuotes.postValue(filter)
        }
    }
}