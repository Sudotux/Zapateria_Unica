package com.example.zapateria_unica

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.zapateria_unica.model.local.ZapateriaDao
import com.example.zapateria_unica.model.local.ZapateriaDataBase
import com.example.zapateria_unica.model.local.entities.Zapato
import com.example.zapateria_unica.model.local.entities.ZapatoDetalle
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class ZapateriaDaoInstrumentedTest {
    private lateinit var zapateriaDao: ZapateriaDao
    private lateinit var db: ZapateriaDataBase

    // Para ejecutar tareas de forma sincrónica en las pruebas
    private val executor = Executors.newSingleThreadExecutor()

    // Regla para ejecutar las tareas de Room en el hilo principal
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ZapateriaDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        zapateriaDao = db.getZapateriaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //insercion y obtencion de zapatos
    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveZapato() = runBlocking {
        val zapato = Zapato(
            id = 1,
            nombre = "zapatilla deportiva",
            origen = "Nigeria",
            imagenLink = "https://imgUrl1",
            marca = "Nacional",
            numero = 39
        )
        zapateriaDao.insertZapato(zapato)
        val retrievedZapato = getValue(zapateriaDao.getZapatos())[0]
        assertEquals(zapato.id, retrievedZapato.id)
        assertEquals(zapato.nombre, retrievedZapato.nombre)
        assertEquals(zapato.numero, retrievedZapato.numero)
    }

    //insercion y obtencion de zapatoDetalles
    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveZapatoDetalle() = runBlocking {
        val zapatoDetalle = ZapatoDetalle(
            id = 1,
            nombre = "zapatilla deportiva",
            origen = "Nigeria",
            imagenLink = "https://imgUrl1",
            marca = "Nacional",
            numero = 39,
            precio = 90000,
            entrega = true
        )
        zapateriaDao.insertZapatoDetalle(zapatoDetalle)
        val retrievedZapatoDetalle = getValue(zapateriaDao.getZapatoDetalles())[0]
        assertEquals(zapatoDetalle.id, retrievedZapatoDetalle.id)
        assertEquals(zapatoDetalle.nombre, retrievedZapatoDetalle.nombre)
        assertEquals(zapatoDetalle.precio, retrievedZapatoDetalle.precio)
        assertEquals(zapatoDetalle.entrega, retrievedZapatoDetalle.entrega)
    }

    // Función para obtener el valor de un LiveData en las pruebas
    @Throws(InterruptedException::class)
    private fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            liveData.observeForever(observer)
        }
        latch.await(2, TimeUnit.SECONDS)
        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }
}