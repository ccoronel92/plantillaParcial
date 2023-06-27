import losSimpson.conductoresPrincipales
import java.time.DayOfWeek

//PUNTO 1

open class Programa(var titulo: String,
                    var conductoresPrincipales: MutableList<String>,
                    var presupuestoBase: Double,
                    var sponsorsPublicidad: MutableSet<String>,
                    //var dia:String = "Lunes",
                    var dia: DayOfWeek = DayOfWeek.MONDAY, //String no es buena idea si quiero trabajar con los días después
                    var duracion: Int,
                    //var restricciones:MutableSet<Restriccion>), --Al programa no le interesa saber las restricciones que se le aplican
                    var ratings: MutableList<Double>) { //cambio nombre para que quede más claro

    //var ratingsEnLista=5 --No me sirve de nada esto

    //Estos métodos me sirven para conseguir la información necesaria en Restricciones
    fun ultimosCincoRatings() = ratings.takeLast(5) //Conocemos los últimos 5 ratings
    fun promedioCincoRatings() = ultimosCincoRatings().average()
    fun cantidadConductores() = conductoresPrincipales.count()
    fun tieneConductor(conductorEstrella: String) = conductoresPrincipales.contains(conductorEstrella)


    // FIXME: por qué implementar los setters? está en todos lados, eso viene con Kotlin y escribir el setter lleva tiempo
    //fun setTitulo(nuevoTitulo:String){titulo=nuevoTitulo}
    //fun agregarConductor(conductor: String){conductoresPrincipales.add(conductor)}
    //fun quitarConductor(conductor: String){conductoresPrincipales.remove(conductor)}
    //fun setPresupuesto(nuevoPresupuesto:Double){presupuestoBase=nuevoPresupuesto}
    fun agregarSponsor(sponsor: String){sponsorsPublicidad.add(sponsor)}
    fun quitarSponsor(sponsor: String){sponsorsPublicidad.remove(sponsor)}
    //fun setDia(nuevoDia: String){dia=nuevoDia}
    //fun setDuracion(nuevaDuracion: Int){duracion=nuevaDuracion}

    fun agregarRating(nuevoRating: Double) {
        //validarCantidadRating(ultimosRatings) --No hay que validar nada
        ratings.add(nuevoRating)
    }

    fun agregarConductor(conductor: String){conductoresPrincipales.add(conductor)}
    fun primerConductor() = conductoresPrincipales.first()

    fun palabraDelTituloEnPosicion(titulo: String, posicion: Int): String {
        val palabras = titulo.split(" ")
        return if (palabras.count() == 2) palabras[posicion] else "Programa Sin Nombre"
    }

    fun cambiarAlsiguienteDia() {
        dia= diaSiguiente()
    }

    fun diaSiguiente() = dia.plus(1)

    fun primeraMitadConductores(mitadCantidadConnductores: Int) = conductoresPrincipales.take(mitadCantidadConnductores).toMutableList()
    fun conductoresRestantes(conductoresRestantes: Int) = conductoresPrincipales.takeLast(conductoresRestantes).toMutableList()

    // FIXME: esto no está pedido
    //fun validarCantidadRating(ultimosRatings: MutableList<Double>){
    //    if(ultimosRatings.count()==ratingsEnLista){
    //        ultimosRatings.clear()
    //    }
    //}

    //fun cambiarRatingsEnLista(nuevaCantidad: Int){ratingsEnLista=nuevaCantidad} --No sirve de nada

}

 abstract class Restriccion{ //cambio a clase porque tiene un parametro

    // FIXME: no hay que hacer esto, simplemente necesitamos saber si la condición se cumple o no
    //  justamente por eso se separa la restricción de la acción

    //Había entendido mal la consigna (:

    //fun aplicarRestriccion(programa: Programa){
    //    if(condicionDeRestringido(programa)){
    //    programa.restricciones.add(this)}
    //}

    lateinit var accionAsociada: Accion

    abstract fun cumpleCondicion(programa: Programa): Boolean //Le cambio el nombre para que se entienda mejor

    fun ejecutarAccionAsociada(programa: Programa) {accionAsociada.ejecutarAccion(programa)}
    //fun ejecutar(programa:Programa) --Esto no va acá

}

 class promedioRating: Restriccion() {

   private var ratingPromedioNecesario=5 //Cambio nombre para que se entienda más

    //override fun cumpleCondicion(programa:Programa): Boolean = programa.ultimosRatings.average()>ratingPromedio
    override fun cumpleCondicion(programa:Programa) = programa.promedioCincoRatings() > ratingPromedioNecesario

    //fun setRatingPromedio(nuevoPromedio: Int){ratingPromedio=nuevoPromedio} --No sirve de nada el setter

    // FIXME: la idea era que se pudiera configurar el día, pero más allá de eso estás confundiendo la
    //  restricción con la acción
    //override fun ejecutar(programa:Programa){programa.setDia(nuevoDia="martes")}
    //override fun ejecutar(programa:Programa){} //A implementar
}

class maximoConductores: Restriccion() {

    private var maximoConductores=3

    override fun cumpleCondicion(programa:Programa): Boolean = programa.cantidadConductores() > maximoConductores

    //fun setMaximoConductores(nuevoMaximo: Int){maximoConductores=nuevoMaximo} --No me sirve el setter

    //override fun ejecutar(programa:Programa){} //A implementar

}

class conductoresEstrella: Restriccion() {

    private var conductorEstrella= "Pinky"

    //override fun cumpleCondicion(programa:Programa): Boolean = programa.conductoresPrincipales.containscon(conductorEstrella)
    override fun cumpleCondicion(programa:Programa): Boolean = programa.tieneConductor(conductorEstrella)

    //fun agregarConductorEstrella(conductor: String){conductorEstrella=conductor} --No sirve el setter

    //override fun ejecutar(programa:Programa){} //A implementar

}

class excedePresupuesto: Restriccion() {

    private var presupuestoMaximo= 10000.00

    override fun cumpleCondicion(programa:Programa): Boolean = programa.presupuestoBase> presupuestoMaximo

    //fun setPresupuestoMaximo(nuevoValor: Double){presupuestoMaximo=nuevoValor}--No sirve el setter

    //override fun ejecutar(programa:Programa){} //A implementar

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

abstract class RestriccionCombinada(var restricciones:MutableSet<Restriccion>): Restriccion() {

    override fun cumpleCondicion(programa: Programa): Boolean = condicionEspecifica(programa)

    //override fun ejecutar(programa: Programa) {}

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
//object grillaDeProgramacion{
//     lateinit var programas: MutableSet<Programa>
//}

// //Es más útil tener un administrador que tenga acceso a la grilla de programación, lo dejo en el punto 3


// FIXME: se usa en singular: Accion
//abstract class Acciones{

interface Accion{ //no tiene variables, puede ser interface
        abstract fun ejecutarAccion(programa:Programa)
}

object partirPrograma:Accion{

    override fun ejecutarAccion(programa: Programa) {
        programaPartidoBuilder.build(programa)
    }

}

class quitarPrograma:Accion{

    override fun ejecutarAccion(programa: Programa) {
        // FIXME: falta delegar
        //encargado.grillaDeProgramacion.remove(programa)
        //encargado.grillaDeProgramacion.add(losSimpson)
        encargado.reemplazarPrograma(programa)
    }

}

class fusionarPrograma:Accion{

    override fun ejecutarAccion(programa: Programa) {}//TODO:A Implementar

}

class moverDia:Accion{

    override fun ejecutarAccion(programa: Programa) {
        programa.cambiarAlsiguienteDia()
    }

}

object programaPartidoBuilder {

    lateinit var primerPrograma: Programa
    lateinit var segundoPrograma: Programa
    lateinit var programasGenerados: MutableList<Programa>

    fun conductoresPrincipales(programa:Programa){

        var mitadCantidadConnductores = (programa.cantidadConductores()/2)
        var conductoresRestantes = programa.cantidadConductores()-mitadCantidadConnductores

        primerPrograma.conductoresPrincipales = programa.primeraMitadConductores(mitadCantidadConnductores)
        segundoPrograma.conductoresPrincipales = programa.conductoresRestantes(conductoresRestantes)

    }

    fun presupuesto(programa:Programa){

        var mitadPresupuesto = programa.presupuestoBase/2

        primerPrograma.presupuestoBase = mitadPresupuesto
        segundoPrograma.presupuestoBase = mitadPresupuesto

    }

    fun sponsors(programa:Programa){

        var sponsors = programa.sponsorsPublicidad

        primerPrograma.sponsorsPublicidad = sponsors
        segundoPrograma.sponsorsPublicidad = sponsors
    }

    fun duracion(programa:Programa){

        var duracion = programa.duracion/2

        primerPrograma.duracion = duracion
        segundoPrograma.duracion = duracion

    }

    fun titulos(programa:Programa){


        var primeraPalabra = programa.palabraDelTituloEnPosicion(programa.titulo,0)
        var segundaPalabra = programa.palabraDelTituloEnPosicion(programa.titulo,1)

        primerPrograma.titulo = "{$primeraPalabra} en el Aire!"
        segundoPrograma.titulo = "$segundaPalabra"

    }

    fun dias(programa:Programa){

        var dia = programa.dia

        primerPrograma.dia=dia
        segundoPrograma.dia=dia

    }

    fun build(programa: Programa):MutableList<Programa>{

        conductoresPrincipales(programa)
        presupuesto(programa)
        sponsors(programa)
        duracion(programa)
        titulos(programa)
        dias(programa)

        programasGenerados.add(primerPrograma)
        programasGenerados.add(segundoPrograma)

        return programasGenerados

    }

}


object losSimpson: Programa("Los Simpson", mutableListOf("conductor"),1000.00,
    mutableSetOf("Unsam"),DayOfWeek.MONDAY,250,mutableListOf(1.2,2.5,1.6,1.6,1.7))


//PUNTO 3

// FIXME: falta implementar lo que ocurre al agregar un programa

open class AdministradorDeProgramacion(var grillaDeProgramacion: MutableList<Programa>) {

    lateinit var restricciones: MutableList<Restriccion>
    lateinit var programasArevisar: MutableSet<Programa>

    fun agregarAgrilla(programa: Programa){grillaDeProgramacion.add(programa)}
    fun quitarDeGrilla(programa: Programa){grillaDeProgramacion.remove(programa)}

    fun agregarRestriccion(restriccion: Restriccion) {restricciones.add(restriccion)}
    fun quitarRestriccion(restriccion: Restriccion){restricciones.remove(restriccion)}

    fun agregarProgramaArevisar(programa: Programa){programasArevisar.add(programa)}
    fun quitarProgramaArevisar(programa: Programa){programasArevisar.remove(programa)}

    fun reemplazarPrograma(programa: Programa){
        losSimpson.dia=programa.dia //seteo el día, que sea el mismo que el programa reemplazado
        losSimpson.duracion=programa.duracion //al igual que la duracion
        quitarDeGrilla(programa)
        agregarAgrilla(losSimpson)
    }

    fun definirAccion(restriccion: Restriccion,accion: Accion) {restriccion.accionAsociada=accion}

    fun procesoDeRevision(){

        for (programa in programasArevisar) {

            for (restriccion in restricciones) {

                if(restriccion.cumpleCondicion(programa)){
                    restriccion.ejecutarAccionAsociada(programa)
                    break
                    }

            }

        }

    }

    }

object encargado :AdministradorDeProgramacion(grillaDeProgramacion = mutableListOf(losSimpson))

//object encargado{

 //   lateinit var programasArevisar: MutableSet<Programa>

    // FIXME: es un misplaced method, no debería estar acá
   // fun definirRestricciones(programa:Programa, restriccion: Restriccion){
        //programa.restricciones.add(restriccion)
    //}

    //fun ejecutarRestricciones(programa: Programa){
        // FIXME: delegar al programa
        //programa.restricciones.forEach{it.ejecutar(programa)}
 //   }

    //fun agregarProgramaArevisar(programa:Programa){
    //    programasArevisar.add(programa)
    //}

    //fun procesoDeRevision(){
    //    programasArevisar.forEach{this.ejecutarRestricciones(it)}
    //}
//}

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
