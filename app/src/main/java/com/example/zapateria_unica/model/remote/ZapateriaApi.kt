package com.example.zapateria_unica.model.remote

import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDC
import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDetalleDC
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ZapateriaApi {

    @GET
    suspend fun fetchZapatos(): Response<List<ZapatoDC>>

    @GET("/{id}")
    suspend fun fetchZapatoDetalle(@Path("id") id: Int) : Response<ZapatoDetalleDC>

}