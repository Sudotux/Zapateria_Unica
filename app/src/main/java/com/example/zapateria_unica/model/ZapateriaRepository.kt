package com.example.zapateria_unica.model

import android.util.Log
import androidx.lifecycle.LiveData

import com.example.zapateria_unica.model.local.ZapateriaDao
import com.example.zapateria_unica.model.local.entities.Zapato
import com.example.zapateria_unica.model.local.entities.ZapatoDetalle
import com.example.zapateria_unica.model.remote.RetrofitClient
import com.example.zapateria_unica.model.remote.ZapateriaApi
import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDC
import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDetalleDC
import retrofit2.Response

class ZapateriaRepository(private val zapateriaDao: ZapateriaDao) {

    private val zapateriaApi: ZapateriaApi = RetrofitClient.getRetrofit()
    val zapatos: LiveData<List<Zapato>> = zapateriaDao.getZapatos()
    val zapatoDetalles: LiveData<List<ZapatoDetalle>> = zapateriaDao.getZapatoDetalles()

    //zapatos
    suspend fun fetchZapatos() {
        try {
            val response: Response<List<ZapatoDC>> = zapateriaApi.fetchZapatos()
            when (response.code()) {
                in 200..299 -> response.body().let {
                    if (it != null) {
                        zapateriaDao.insertZapatos(Mapper.fromZapatoDCToZapato(it))
                    }
                }
                else -> Log.d("REPO", "${response.code()} --- ${response.errorBody()}")
            }
        } catch (t: Throwable) {
            Log.e("REPO", "${t.message}")
        }
    }

    //zapatoDetalle
    suspend fun fetchZapatoDetalle(id: Int) {
        try {
            val response: Response<ZapatoDetalleDC> = zapateriaApi.fetchZapatoDetalle(id)
            when (response.code()) {
                in 200..299 -> response.body().let {
                    if (it != null) {
                        zapateriaDao.insertZapatoDetalle(Mapper.fromZapatoDetalleDCToZapatoDetalle(it))
                    }
                }
                else -> Log.d("REPO", "${response.code()} --- ${response.errorBody()}")
            }
        } catch (t: Throwable) {
            Log.e("REPO", "${t.message}")
        }
    }

    fun getZapatoDetalleById(id: Int): LiveData<ZapatoDetalle>? {
        return zapateriaDao.getZapatoDetalleById(id)
    }

    suspend fun deleteAllZapatoDetalles() {
        zapateriaDao.deleteAllZapatoDetalles()
    }

}