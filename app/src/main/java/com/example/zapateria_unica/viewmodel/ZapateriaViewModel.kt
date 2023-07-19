package com.example.zapateria_unica.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.zapateria_unica.model.ZapateriaRepository
import com.example.zapateria_unica.model.local.ZapateriaDataBase
import com.example.zapateria_unica.model.local.entities.Zapato
import com.example.zapateria_unica.model.local.entities.ZapatoDetalle

import kotlinx.coroutines.launch

class ZapateriaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ZapateriaRepository =
        ZapateriaRepository(ZapateriaDataBase.getDataBase(application).getZapateriaDao())

    private val zapatos: LiveData<List<Zapato>> = repository.zapatos
    private val zapatoDetalles: LiveData<List<ZapatoDetalle>> = repository.zapatoDetalles
    private var zapatoDetalle: LiveData<ZapatoDetalle>? = null

    init {
        fetchZapatos()
    }

    fun fetchZapatos() = viewModelScope.launch {
        repository.fetchZapatos()
    }

    fun fetchZapatoDetalle(id: Int) = viewModelScope.launch {
        repository.fetchZapatoDetalle(id)
    }

    fun getZapatoDetalleById(id: Int) = viewModelScope.launch {
        zapatoDetalle = repository.getZapatoDetalleById(id)
    }

    fun deleteAllZapatoDetalles() = viewModelScope.launch {
        repository.deleteAllZapatoDetalles()
    }

    fun getZapatos(): LiveData<List<Zapato>> {
        return zapatos
    }

    fun getZapatoDetalles(): LiveData<List<ZapatoDetalle>> {
        return zapatoDetalles
    }

    fun getZapatoDetalle(): LiveData<ZapatoDetalle>? {
        return zapatoDetalle
    }

}