package com.example.zapateria_unica.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zapateria_unica.model.local.entities.Zapato
import com.example.zapateria_unica.model.local.entities.ZapatoDetalle

//dao compuesto (Zapato y ZapatoDetalle)
@Dao
interface ZapateriaDao {

    /** Zapato **/

    //insert one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZapato(zapato: Zapato)

    //insert many
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZapatos(zapatos: List<Zapato>)

    //select many
    @Query("SELECT * FROM zapato_table ORDER BY id ASC")
    fun getZapatos(): LiveData<List<Zapato>>

    //update one
    @Update
    suspend fun updateZapato(zapato: Zapato)

    //delete one
    @Delete
    suspend fun deleteZapato(zapato: Zapato)


    /** ZapatoDetalle **/

    //insert one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZapatoDetalle(zapatoDetalle: ZapatoDetalle)

    //insert many
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZapatoDetalles(zapatoDetalles: List<ZapatoDetalle>)

    //select one by id
    @Query("SELECT * FROM zapato_detalle_table WHERE id = :id ORDER BY id ASC LIMIT 1")
    fun getZapatoDetalleById(id: Int): LiveData<ZapatoDetalle>?

    //select one by id and return as list of size 1
    @Query("SELECT * FROM zapato_detalle_table WHERE id = :id ORDER BY id ASC LIMIT 1")
    fun getZapatoDetalleByIdAsList(id: Int): LiveData<List<ZapatoDetalle>>

    //select many
    @Query("SELECT * FROM zapato_detalle_table ORDER BY id ASC")
    fun getZapatoDetalles(): LiveData<List<ZapatoDetalle>>

    //delete one by id
    @Query("DELETE FROM zapato_detalle_table WHERE id = :id")
    suspend fun deleteZapatoDetalleById(id: Int)

    //delete many
    @Query("DELETE FROM zapato_detalle_table")
    suspend fun deleteAllZapatoDetalles()

    //update one
    @Update
    suspend fun updateZapatoDetalle(zapatoDetalle: ZapatoDetalle)

    //delete one
    @Delete
    suspend fun deleteZapatoDetalle(zapatoDetalle: ZapatoDetalle)

}