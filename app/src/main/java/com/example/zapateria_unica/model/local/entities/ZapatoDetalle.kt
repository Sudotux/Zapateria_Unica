package com.example.zapateria_unica.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "zapato_detalle_table")
data class ZapatoDetalle(
    @NotNull
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val origen: String,
    val imagenLink: String,
    val marca: String,
    val numero: Int,
    val precio: Int,
    val entrega: Boolean
)
