package com.example.wb_1.content_provider

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wb_1.R
import com.example.wb_1.databinding.FragmentContentProviderExampleBinding

/**
 * Экран, предназначенный для демонстрации работы ContentProvider и ContentResolver.
 * Функциональность экрана заключается в следующем:
 * 1) Есть возможность вставить в БД записи о новых пользователях через ContentProvider, вписав имя
 * нового пользователя в поле EditText и нажав на кнопку "Insert Data".
 * 2) Есть возможность получить данные из БД с помощью ContentResolver, нажав на кнопку "Get Data".
 * Данные будут выведены в текстовом поле.
 *
 * Функционал ContentProvider и ContentResolver используется приложениями для доступа к данным
 * других приложений, например, записанным в телефоне контактам. Примеры таких приложений:
 * GetContacts, VK.
 */

class ContentProviderExampleFragment : Fragment() {

    private var binding: FragmentContentProviderExampleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentProviderExampleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            buttonGetData.setOnClickListener{
                getData()
            }

            buttonInsertData.setOnClickListener {
                insertData()
            }

        }
    }

    private fun insertData() {
        //Создаем экземпляр класса ContentValues для добавления в БД
        val values = ContentValues()

        // Кладем в values значение из поля ввода текста
        values.put("name", binding?.dataEditText?.getText().toString())

        // Вставка данных в БД через URI
        activity?.contentResolver?.insert(
            Uri.parse("content://com.example.wb_1.provider_example/users"),
            values)

        // Высвечиваем Toast с уведомлением о вставке новой записи в БД
        Toast.makeText(context, "New Record Inserted", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("Range")
    private fun getData() {

        //Создаем объект класса Cursor для доступа к данным из БД
        val cursor = activity?.contentResolver?.query(
            Uri.parse("content://com.example.wb_1.provider_example/users"),
            null, null, null, null)

        //Записываем каждую строку в StringBuilder и выводим на TextView в случае успеха
        if (cursor!!.moveToFirst()) {
            val dataString = StringBuilder()
            while (!cursor.isAfterLast) {
                dataString.append("""
                    ${cursor.getString(cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}
                    
                    """.trimIndent())
                cursor.moveToNext()
            }
            binding?.dataTextView?.text = dataString
        } else {
            binding?.dataTextView?.text = getString(R.string.no_records_found)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}