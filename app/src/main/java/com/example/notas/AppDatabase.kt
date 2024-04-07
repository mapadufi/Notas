package com.example.notas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notas.dao.NotaDao
import com.example.notas.model.Nota

@Database(entities = [Nota::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun notasDao(): NotaDao

    companion object{
        private const val DATABASE_NOME = "DB_NOTAS"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NOME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}