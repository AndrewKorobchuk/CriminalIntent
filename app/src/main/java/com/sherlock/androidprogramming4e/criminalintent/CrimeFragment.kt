package com.sherlock.androidprogramming4e.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sherlock.androidprogramming4e.criminalintent.databinding.FragmentCrimeBinding

/**
 * контроллер, взаимодействующий с объектами модели и представления.
 * Его задача — выдача подробной информации о конкретном преступлении
 * и ее обновление при модификации пользователем
 */
class CrimeFragment : Fragment() {
    private var _binding: FragmentCrimeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var crime: Crime

    /**
     * Экземпляр фрагмента настраивается во Fragment.onCreate(Bundle?),
     * но создание и настройка представления фрагмента осуществляются
     * в другой функции жизненного цикла фрагмента:
     * onCreateView(LayoutInflater, ViewGroup?, Bundle?).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    /**
     * В функции onCreateView(...) мы явно заполняем представление фрагмента,
     * вызывая LayoutInflater.inflate(...) с передачей идентификатора ресурса макета.
     * Второй параметр определяет родителя представления,
     * что обычно необходимо для правильной настройки виджета.
     * Третий параметр указывает, нужно ли включать заполненное представление в родителя.
     * Мы передаем false, потому что представление будет добавлено в контейнере activity.
     * Представление фрагмента не нужно сразу добавлять в родительское представление —
     * activity обработает этот момент позже.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCrimeBinding.inflate(inflater,container,false)
        binding.crimeDate.apply {
            text = crime.date.toString()
            isEnabled = false
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        /**
         * создаем анонимный класс, который реализует интерфейс слушателя TextWatcher.
         */
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Это пространство оставлено пустым специально
            }

            /**
             * В функции onTextChanged(...) мы вызываем toString() для объекта CharSequence,
             * представляющего ввод пользователя.
             * Эта функция возвращает строку,
             * которая затем используется для задания заголовка Crime.
             */
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // И это
            }
        }

        binding.crimeTitle.addTextChangedListener(titleWatcher)
        binding.crimeSolved.apply {
            setOnCheckedChangeListener { _, isChecked -> crime.isSolved = isChecked }
        }
    }
}