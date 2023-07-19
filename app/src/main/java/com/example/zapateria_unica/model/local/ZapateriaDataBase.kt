package com.example.zapateria_unica.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zapateria_unica.model.local.entities.Zapato
import com.example.zapateria_unica.model.local.entities.ZapatoDetalle

@Database(entities = [Zapato::class, ZapatoDetalle::class], version = 1, exportSchema = false)
abstract class ZapateriaDataBase : RoomDatabase() {

    abstract fun getZapateriaDao(): ZapateriaDao

    companion object {
        private const val DB_NAME: String = "zapateria_database"

        @Volatile
        private var INSTANCE: ZapateriaDataBase? = null

        fun getDataBase(context: Context): ZapateriaDataBase {
            val tempInstance: ZapateriaDataBase? = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance: ZapateriaDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    ZapateriaDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}