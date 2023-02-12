package com.sherlock.androidprogramming4e.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()

            /**
             * Функция FragmentManager.beginTransaction() создает и возвращает экземпляр
             * FragmentTransaction. Класс FragmentTransaction использует динамичный интерфейс:
             * функции, настраивающие FragmentTransaction,
             * возвращают FragmentTransaction вместо Unit,
             * что позволяет объединять их вызовы в цепочку.
             * Таким образом, выделенный код в приведенном выше листинге означает:
             * «Создать новую транзакцию фрагмента, включить в нее одну операцию add,
             * а затем закрепить».
             */

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}