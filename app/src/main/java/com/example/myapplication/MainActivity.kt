package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodels.MainViewModel
import com.example.practice.models.Quotes
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()
    lateinit var  quoteAdapter : QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        quoteAdapter = QuoteAdapter {selectedQuote->
            mainViewModel.editQuote(selectedQuote,Quotes("",1,"changed"))
        }

        binding.recyclerView.adapter = quoteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//
        lifecycleScope.launch {
            mainViewModel.getQuotes()
        }

        mainViewModel.listOfQuote.observe(this){
            quoteAdapter.fetchQuotes(it)
            binding.quoteSize.text = "total Quote: ${it.size}"
        }

        lifecycleScope.launch {
            delay(5000)
            val listOfQuote = mutableListOf(
                Quotes(author = "", id = 0, quote = "test1"),
                Quotes(author = "", id = 1, quote = "test2"),
                Quotes(author = "", id = 2, quote = "test3")
            )
            mainViewModel.addQuotes(listOfQuote)
        }



    }
}