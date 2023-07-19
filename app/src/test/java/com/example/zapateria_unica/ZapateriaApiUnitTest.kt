package com.example.zapateria_unica

import com.example.zapateria_unica.model.remote.ZapateriaApi
import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDC
import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDetalleDC
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ZapateriaApiUnitTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var zapateriaApi: ZapateriaApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        zapateriaApi = retrofit.create(ZapateriaApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    //fetching de datos zapatos
    @Test
    fun fetchZapatos_success_response() = runBlocking {
        // Configura la respuesta exitosa del servidor
        val jsonResponse = "[{\"id\": 1, " +
                "\"nombre\": \"zapatilla deportiva\", " +
                "\"origen\": 100000, " +
                "\"imagenLink\": \"https://imgUrl_1\", " +
                "\"marca\": \"Nacional\", " +
                "\"numero\": 49}]"
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(jsonResponse)
        )

        // Realiza la llamada a fetchZapatos
        val response = zapateriaApi.fetchZapatos()

        // Verifica la respuesta
        assert(response.isSuccessful)
        val zapatos: List<ZapatoDC>? = response.body()
        assert(zapatos != null && zapatos.size == 1)
        assert(zapatos?.get(0)?.id == 1)
        assert(zapatos?.get(0)?.nombre == "zapatilla deportiva")
        assert(zapatos?.get(0)?.numero == 49)
    }

    //fetching de datos zapatoDetalle
    @Test
    fun fetchZapatoDetalle_success_response() = runBlocking {
        // Configurar la respuesta exitosa del servidor
        val jsonResponse = "{\"id\": 1, " +
                "\"nombre\": \"zapatilla deportiva\", " +
                "\"origen\": 100000, " +
                "\"imagenLink\": \"https://imgUrl_1\", " +
                "\"marca\": \"Nacional\", " +
                "\"numero\": 49, " +
                "\"precio\": 90000, " +
                "\"entrega\": true}"
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(jsonResponse)
        )

        // Realizar la llamada a fetchZapatoDetalle
        val response = zapateriaApi.fetchZapatoDetalle(1)

        // Verificar la respuesta
        assert(response.isSuccessful)
        val zapatoDetalle: ZapatoDetalleDC? = response.body()
        assert(zapatoDetalle != null)
        assert(zapatoDetalle?.id == 1)
        assert(zapatoDetalle?.precio == 90000)
        assert(zapatoDetalle?.numero == 49)
    }
}