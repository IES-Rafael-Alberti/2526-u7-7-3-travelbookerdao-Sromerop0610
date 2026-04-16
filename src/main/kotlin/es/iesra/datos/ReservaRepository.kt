package es.iesra.datos

import es.iesra.dominio.Reserva

class ReservaRepository(private val dao: IReservaDAO) : IReservaRepository {

    override fun agregar(reserva: Reserva): Boolean {
        dao.guardar(reserva)
        return true
    }

    override fun obtenerTodas(): List<Reserva> {
        return dao.obtenerTodas()
    }
}