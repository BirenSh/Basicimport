package com.example.myapplication.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentQuoteBinding
import com.example.myapplication.viewmodels.MainViewModel
import com.example.practice.models.Quotes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuoteFragment : Fragment() {
    lateinit var binding: FragmentQuoteBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val quoteAdapter: QuoteAdapter = QuoteAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observer()
        setupClick()
    }

    private fun setupClick() {
        // on click of item edit
        quoteAdapter.onClickCallback = {
            mainViewModel.editQuote(it, Quotes("", 1, "selected"))
        }

        // to search the item
        searchItem()


        // move to next fragment
        binding.quoteSize.setOnClickListener {
            val fragment = DetailFragment.newInstance(param1 = "test")
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment).addToBackStack(null)
                .commit()
        }
    }

    private fun searchItem() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.filterQuote(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun observer() {
        mainViewModel.listOfQuote.observe(viewLifecycleOwner) {
            quoteAdapter.fetchQuotes(it)
            updateTotalCount(it.size)
        }

        mainViewModel.filteredQuotes.observe(viewLifecycleOwner) {
            quoteAdapter.fetchQuotes(it)
            updateTotalCount(it.size)
        }
    }

    private fun setupView() {
        if (mainViewModel.listOfQuote.value == null){
            lifecycleScope.launch {
                mainViewModel.getQuotes()
            }
        }

        binding.recyclerView.adapter = quoteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    fun updateTotalCount(size: Int) {
        binding.quoteSize.text = "Total quote: $size"
    }


}