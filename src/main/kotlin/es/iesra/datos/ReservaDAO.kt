package es.iesra.datos

import es.iesra.dominio.Reserva
import java.io.File

class ReservaDAO(private val file: File) : IReservaDAO {
    init{
        if (!file.exists()){
            file.parentFile?.mkdirs()
            file.createNewFile()
        }
    }

    override fun guardar(reserva: Reserva) {
        val linea = when (reserva) {

            is es.iesra.dominio.ReservaHotel -> {
                "HOTEL|${reserva.id}|${reserva.descripcion}|${reserva.ubicacion}|${reserva.numeroNoches}|${reserva.fechaCreacion}"
            }

            is es.iesra.dominio.ReservaVuelo -> {
                "VUELO|${reserva.id}|${reserva.descripcion}|${reserva.origen}|${reserva.destino}|${reserva.horaVuelo}|${reserva.fechaCreacion}"
            }

            else -> throw IllegalArgumentException("Tipo de reserva no soportado")
        }

        file.appendText(linea + "\n")
    }

    override fun obtenerTodas(): List<Reserva> {

        if (!file.exists()) return emptyList()

        return file.readLines()
            .filter { it.isNotBlank() }
            .map { linea ->

                val partes = linea.split("|")

                when (partes[0]) {

                    "HOTEL" -> es.iesra.dominio.ReservaHotel.creaInstancia(
                        descripcion = partes[2],
                        ubicacion = partes[3],
                        numeroNoches = partes[4].toInt()
                    )

                    "VUELO" -> es.iesra.dominio.ReservaVuelo.creaInstancia(
                        descripcion = partes[2],
                        origen = partes[3],
                        destino = partes[4],
                        horaVuelo = partes[5]
                    )

                    else -> throw IllegalArgumentException("Tipo desconocido: ${partes[0]}")
                }
            }
    }

    override fun eliminar(id: Int) {
        TODO("Aún no implementado")
}
    override fun actualizar(reserva: Reserva) {
        TODO("Aún no implementado")

    }    }