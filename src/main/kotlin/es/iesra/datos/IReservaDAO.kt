package es.iesra.datos

import es.iesra.dominio.Reserva

interface IReservaDAO {
    fun guardar(reserva: Reserva)
    fun obtenerTodas(): List<Reserva>
    fun eliminar(id: Int)
    fun actualizar(Reserva: Reserva)
}