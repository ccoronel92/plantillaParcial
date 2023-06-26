import java.time.DayOfWeek

//PUNTO 1

open class Programa(var titulo: String,
                    var conductoresPrincipales: MutableSet<String>,
                    var presupuestoBase: Double,
                    var sponsorsPublicidad: MutableSet<String>,
                    //var dia:String = "Lunes",
                    var dia: DayOfWeek = DayOfWeek.MONDAY, //String no es buena idea si quiero trabajar con los días después
                    var duracion: Int,
                    //var restricciones:MutableSet<Restriccion>), --Al programa no le interesa saber las restricciones que se le aplican
                    var ultimosRatings: MutableList<Double>) {

    //var ratingsEnLista=5 --No me sirve de nada esto

    //Estos métodos me sirven para conseguir la información necesaria en Restricciones
    fun ultimosCincoRatings() = ultimosRatings.takeLast(5) //Conocemos los últimos 5 ratings
    fun promedioCincoRatings() = ultimosCincoRatings().average()
    fun cantidadConductores() = conductoresPrincipales.count()
    fun tieneConductor(conductorEstrella: String) = conductoresPrincipales.contains(conductorEstrella)


    // FIXME: por qué implementar los setters? está en todos lados, eso viene con Kotlin y escribir el setter lleva tiempo
    //fun setTitulo(nuevoTitulo:String){titulo=nuevoTitulo}
    //fun agregarConductor(conductor: String){conductoresPrincipales.add(conductor)}
    //fun quitarConductor(conductor: String){conductoresPrincipales.remove(conductor)}
    //fun setPresupuesto(nuevoPresupuesto:Double){presupuestoBase=nuevoPresupuesto}
    //fun agregarSponsor(sponsor: String){sponsorsPublicidad.add(sponsor)}
    //fun quitarSponsor(sponsor: String){sponsorsPublicidad.remove(sponsor)}
    //fun setDia(nuevoDia: String){dia=nuevoDia}
    //fun setDuracion(nuevaDuracion: Int){duracion=nuevaDuracion}

    fun agregarRating(nuevoRating: Double) { //?? Funciona igual para settear las listas?
        //validarCantidadRating(ultimosRatings) --No hay que validar nada
        ultimosRatings.add(nuevoRating)
    }

    // FIXME: esto no está pedido
    //fun validarCantidadRating(ultimosRatings: MutableList<Double>){
    //    if(ultimosRatings.count()==ratingsEnLista){
    //        ultimosRatings.clear()
    //    }
    //}

    //fun cambiarRatingsEnLista(nuevaCantidad: Int){ratingsEnLista=nuevaCantidad} --No sirve de nada

}

interface Restriccion{

    // FIXME: no hay que hacer esto, simplemente necesitamos saber si la condición se cumple o no
    //  justamente por eso se separa la restricción de la acción

    //Había entendido mal la consigna (:

    //fun aplicarRestriccion(programa: Programa){
    //    if(condicionDeRestringido(programa)){
    //    programa.restricciones.add(this)}
    //}

    fun cumpleCondicion(programa: Programa): Boolean //Le cambio el nombre para que se entienda mejor

    fun ejecutar(programa:Programa)

}

object promedioRating:Restriccion {

   private var ratingPromedioNecesario=5 //Cambio nombre para que se entienda más

    //override fun cumpleCondicion(programa:Programa): Boolean = programa.ultimosRatings.average()>ratingPromedio
    override fun cumpleCondicion(programa:Programa): Boolean = programa.promedioCincoRatings() > ratingPromedioNecesario

    //fun setRatingPromedio(nuevoPromedio: Int){ratingPromedio=nuevoPromedio} --No sirve de nada el setter

    // FIXME: la idea era que se pudiera configurar el día, pero más allá de eso estás confundiendo la
    //  restricción con la acción
    //override fun ejecutar(programa:Programa){programa.setDia(nuevoDia="martes")}
    override fun ejecutar(programa:Programa){} //A implementar
}

object maximoConductores:Restriccion {

    private var maximoConductores=3

    override fun cumpleCondicion(programa:Programa): Boolean = programa.cantidadConductores() > maximoConductores

    //fun setMaximoConductores(nuevoMaximo: Int){maximoConductores=nuevoMaximo} --No me sirve el setter

    override fun ejecutar(programa:Programa){} //A implementar

}

object conductoresEstrella:Restriccion {

    private var conductorEstrella= "Pinky"

    //override fun cumpleCondicion(programa:Programa): Boolean = programa.conductoresPrincipales.containscon(conductorEstrella)
    override fun cumpleCondicion(programa:Programa): Boolean = programa.tieneConductor(conductorEstrella)

    //fun agregarConductorEstrella(conductor: String){conductorEstrella=conductor} --No sirve el setter

    override fun ejecutar(programa:Programa){} //A implementar

}

object excedePresupuesto:Restriccion {

    private var presupuestoMaximo= 10000.00

    override fun cumpleCondicion(programa:Programa): Boolean = programa.presupuestoBase> presupuestoMaximo

    //fun setPresupuestoMaximo(nuevoValor: Double){presupuestoMaximo=nuevoValor}--No sirve el setter

    override fun ejecutar(programa:Programa){} //A implementar

}

// FIXME: la idea no es que implementes las restricciones, son solo de ejemplo, lo que necesitamos es que puedas
//  generar una lista de restricciones genéricas a las que apliquemos un AND / OR
//object ratingYconductor: Restriccion {

//    override fun cumpleCondicion(programa:Programa): Boolean = promedioRating.cumpleCondicion(programa)||maximoConductores.cumpleCondicion(programa)

//    override fun ejecutar(programa:Programa){} //A implementar

//}


// FIXME: lo mismo acá
//object conductorYpresupuesto: Restriccion {

//    override fun cumpleCondicion(programa:Programa): Boolean = conductoresEstrella.cumpleCondicion(programa)&&excedePresupuesto.cumpleCondicion(programa)

//    override fun ejecutar(programa:Programa){} //A implementar

//}

abstract class RestriccionCombinada(var restricciones:MutableSet<Restriccion>): Restriccion {

    override fun cumpleCondicion(programa: Programa): Boolean = condicionEspecifica(programa)

    override fun ejecutar(programa: Programa) {}

    abstract fun condicionEspecifica(programa: Programa): Boolean

}

class cumpleTodasLasRestricciones(restricciones: MutableSet<Restriccion>) : RestriccionCombinada(restricciones) {

    override fun condicionEspecifica(programa: Programa) = restricciones.all{it.cumpleCondicion(programa)}

}

class cumpleAlgunaRestriccion(restricciones: MutableSet<Restriccion>) : RestriccionCombinada(restricciones) {

    override fun condicionEspecifica(programa: Programa) = restricciones.any{it.cumpleCondicion(programa)}

}


//PUNTO 2
// FIXME: falta implementar más acciones (se pedían 3 de 4)

// FIXME: me suena a un objeto que tiene poca responsabilidad, es un objeto anémico
object grillaDeProgramacion{
     lateinit var programas: MutableSet<Programa>
}


// FIXME: se usa en singular: Accion
abstract class Acciones{
    abstract fun ejecutarAccion(programa:Programa)
}

class partirPrograma(programa: Programa):Acciones(){
    override fun ejecutarAccion(programa: Programa) {
    }
}

class quitarPrograma(programa: Programa):Acciones(){
    override fun ejecutarAccion(programa: Programa) {
        // FIXME: falta delegar
        grillaDeProgramacion.programas.remove(programa)
        grillaDeProgramacion.programas.add(losSimpson)
    }

}


object losSimpson: Programa("Los Simpson", mutableSetOf("conductor"),1000.00,
    mutableSetOf("Unsam"),"Lunes",250,mutableListOf(1.2,2.5,1.6,1.6,1.7),mutableSetOf())

//PUNTO 3

// FIXME: falta implementar lo que ocurre al agregar un programa
object encargado{

    lateinit var programasArevisar: MutableSet<Programa>

    // FIXME: es un misplaced method, no debería estar acá
    fun definirRestricciones(programa:Programa, restriccion: Restriccion){
        programa.restricciones.add(restriccion)
    }

    fun ejecutarRestricciones(programa: Programa){
        // FIXME: delegar al programa
        programa.restricciones.forEach{it.ejecutar(programa)}
    }

    fun agregarProgramaArevisar(programa:Programa){
        programasArevisar.add(programa)
    }

    fun procesoDeRevision(){
        programasArevisar.forEach{this.ejecutarRestricciones(it)}
    }
}

//
//Las ideas de diseño que se me surgieron fueron:
//
//Template method: En la parte de Acciones, ya que todas las acciones deben ejecutar ciertos procesos, cree una clase abstracta y los redefini dependiendo
//cada una de ellas
//
//Strategy: En la parte de Restricciones, donde todas comparten la aplicación de la restricción y la ejecución, mientras que redefinen el método de las
//condiciones a ejecutarse, esto ayuda a encapsular un poco el algoritmo
//
//Composite Pattern: En la parte de Restricciones que se combinan, ambas aplican dos de las restricciones simultáneamente
//
//Builder: En la parte de Acciones, cuando se deben partir los programas y fusionarse se debería aplicar un Builder, no lo hice porque me enredé mucho y no
//me salió, pero se debería encarar así
//
//Command Pattern: La parte de la ejecución de las restricciones, donde la encargada tiene que ejecutar todas las restricciones de un programa y estas
// mismas ejecutarse cada una por su cuenta
//
//Decidí esta forma de diseñar la solución ya que no se repite código, es flexible a cambios y es más simple
