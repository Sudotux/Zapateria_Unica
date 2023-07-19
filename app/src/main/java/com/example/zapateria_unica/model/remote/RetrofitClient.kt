package com.example.zapateria_unica.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://prueba-mariocanedo.vercel.app/shoes/"

        fun getRetrofit(): ZapateriaApi {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ZapateriaApi::class.java)
        }

    }

}