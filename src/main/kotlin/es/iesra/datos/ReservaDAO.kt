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
        file.appendText(reserva.toString() + "\n")
    }

    override fun obtenerTodas(): List<Reserva> {
       TODO("Aún no implementado")
    }

    override fun eliminar(id: Int) {
        TODO("Aún no implementado")
}
    override fun actualizar(reserva: Reserva) {
        TODO("Aún no implementado")

    }    }