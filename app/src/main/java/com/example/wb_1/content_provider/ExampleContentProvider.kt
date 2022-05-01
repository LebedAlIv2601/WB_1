package com.example.wb_1.content_provider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import java.lang.IllegalArgumentException

class ExampleContentProvider : ContentProvider() {

    companion object{

        //Определение URL
        const val URL = "content://com.example.wb_1.provider_example/users"

        //Парсинг URI контента
        val contentUri = Uri.parse(URL)

        //Код uri
        const val uriCode = 1

        //Создание объекта UriMatcher для помощи в соспоставлении URI
        var uriMatcher: UriMatcher? = null

        //Значения для базы данных
        private val values: HashMap<String, String>? = null

        init {

            // Инициализация UriMatcher
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            // Добавление URI для доступа ко всей таблице
            uriMatcher!!.addURI(
                "com.example.wb_1.provider_example",
                "users",
                uriCode
            )

            // Добавление URI для доступа к определенной строке таблицы
            uriMatcher!!.addURI(
                "com.example.wb_1.provider_example",
                "users/*",
                uriCode
            )
        }
    }


    // Создание объекта базы данных
    private var db: SQLiteDatabase? = null

    // Создание базы данных
    private class DatabaseHelper (context: Context?) : SQLiteOpenHelper(
        context,
        "Users",
        null,
        1
    ) {
        // создание таблицы в базе данных
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(" CREATE TABLE " + "Users"
                        + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + " name TEXT NOT NULL);")
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
            // Удаление старой таблицы и создание новой
            db.execSQL("DROP TABLE IF EXISTS Users")
            onCreate(db)
        }
    }

    //Метод для удаления данных из таблицы, возвращает количество удаленных строк
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

        //Удаление данных из таблицы по URI, получение количества удаленных строк
        val count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.delete("Users", selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        //Регистрация изменений в ContentResolver
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    //Метод возвращает MIME тип данных для данного URI контента
    override fun getType(uri: Uri): String? {
        return when (uriMatcher!!.match(uri)){
            uriCode -> "vnd.android.cursor.dir/users"
            else -> throw IllegalArgumentException("Unsupported uri: $uri")
        }
    }


    //Метод добавления новых данных в таблицу
    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        //Добавляем в базу данных новые данные
        val rowID = db!!.insert("Users", "", values)

        //Возрват URI добавленного контента и регистрация изменений в ContentResolver
        if (rowID > 0) {
            val _uri = ContentUris.withAppendedId(contentUri, rowID)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }
        throw SQLiteException("Failed to add a record into $uri")
    }

    //Метод, вызываемый при создании ContentProvider для его инициализации
    override fun onCreate(): Boolean {
        db = DatabaseHelper(context).writableDatabase
        return db != null
    }

    //Метод для извлечения данных из таблицы по переданным параметрам
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        //параметр для сортировки выдаваемых данных
        var mSortOrder = sortOrder

        //Builder для запроса к таблице
        val qb = SQLiteQueryBuilder()
        qb.tables = "Users"

        //Определение projectionMap для записи туда данных
        when (uriMatcher!!.match(uri)) {
            uriCode -> qb.projectionMap = values
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        //Если параметр для сортировки не указан, то указываем свой по умолчанию
        if (mSortOrder == null || mSortOrder === "") {
            mSortOrder = "id"
        }

        //Получаем данные в виде объекта Cursor
        val data = qb.query(
            db, projection, selection, selectionArgs, null,
            null, mSortOrder
        )

        //Установка слушателя для просмотра изменений URI контента
        data.setNotificationUri(context!!.contentResolver, uri)

        return data
    }


    //Метод для изменения данных в таблице
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        //Обновление данных в таблице по URI и получение количества обновленных строк
        val count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.update("Users", values, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        //Регистрация изменений в ContentResolver
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
}