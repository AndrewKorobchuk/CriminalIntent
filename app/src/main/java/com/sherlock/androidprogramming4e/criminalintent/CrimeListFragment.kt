@file:Suppress("DEPRECATION")

package com.sherlock.androidprogramming4e.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherlock.androidprogramming4e.criminalintent.databinding.FragmentCrimeListBinding

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment(){

    private lateinit var crimeRecyclerView: RecyclerView

    private var _binding: FragmentCrimeListBinding? = null
    private val binding
        get() = _binding!!

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentCrimeListBinding.inflate(inflater,container,false)
        crimeRecyclerView =  binding.crimeRecyclerView
        /**
         * RecyclerView не отображает элементы на самом экране.
         * Он передает эту задачу объекту LayoutManager.
         * LayoutManager располагает каждый элемент, а также определяет, как работает прокрутка.
         * Поэтому если RecyclerView пытается сделать что-то подобное при наличии LayoutManager,
         * он сломается.
         */
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}