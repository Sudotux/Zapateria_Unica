package com.example.zapateria_unica.model

import com.example.zapateria_unica.model.local.entities.Zapato
import com.example.zapateria_unica.model.local.entities.ZapatoDetalle
import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDC
import com.example.zapateria_unica.model.remote.dataclasses.ZapatoDetalleDC

class Mapper {

    companion object {
        fun fromZapatoDCToZapato(zapatosDC: List<ZapatoDC>): List<Zapato> {
            return zapatosDC.map {
                Zapato(
                    id = it.id,
                    nombre = it.nombre,
                    origen = it.origen,
                    imagenLink = it.imagenLink,
                    marca = it.marca,
                    numero = it.numero
                )
            }
        }

        fun fromZapatoDetalleDCToZapatoDetalle(zapatoDetalleDC: ZapatoDetalleDC): ZapatoDetalle {
            return ZapatoDetalle(
                id = zapatoDetalleDC.id,
                nombre = zapatoDetalleDC.nombre,
                origen = zapatoDetalleDC.origen,
                imagenLink = zapatoDetalleDC.imagenLink,
                marca = zapatoDetalleDC.marca,
                numero = zapatoDetalleDC.numero,
                precio = zapatoDetalleDC.precio,
                entrega = zapatoDetalleDC.entrega
            )
        }

    }

}