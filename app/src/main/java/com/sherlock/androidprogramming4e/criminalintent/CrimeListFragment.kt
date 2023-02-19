@file:Suppress("DEPRECATION")

package com.sherlock.androidprogramming4e.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherlock.androidprogramming4e.criminalintent.databinding.FragmentCrimeListBinding

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment(){

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

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
        updateUI()
        return binding.root
    }

    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
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


    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view){
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>(){
        /**
         * Функция Adapter.onCreateViewHolder(...) отвечает за создание представления на дисплее,
         * оборачивает его в холдер и возвращает результат.
         * В этом случае вы наполняете list_item_view.xml
         * и передаете полученное представление в новый экземпляр CrimeHolder.
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        /**
         * Когда утилизатору нужно знать, сколько элементов в наборе данных поддерживают его
         * (например, когда он впервые создается),
         * он будет просить свой адаптер вызвать Adapter.getItemCount().
         * Функция getItemCount()возвращает количество элементов в списке преступлений,
         * отвечая на запрос утилизатора.
         */
        override fun getItemCount() = crimes.size

        /**
         * Функция Adapter.onBindViewHolder(holder: CrimeHolder, position: Int)
         * отвечает за заполнение данного холдера holder преступлением из данной позиции position
         */
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }
    }
}