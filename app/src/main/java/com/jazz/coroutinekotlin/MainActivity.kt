package com.jazz.coroutinekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //blokingExample()
        //suspendExample()
        //dispatcher()
        launch()
        //exampleJob()
        Thread.sleep(5000)
        //asyncAwait()
        //deferred()
        //println(measureTimeMillis { asyncAwait() }.toString())
        //println(measureTimeMillis { deferred() }.toString())
        //println(measureTimeMillis { withContextIO() }.toString())
        //cancelCoroutine()
    }

    public fun longTaskWithMessage(message: String){
        Thread.sleep(4000)
        println(message+ Thread.currentThread().name)
    }

    public fun blokingExample()
    {
        println(LocalDateTime.now().toString()+" Tarea1:"+ Thread.currentThread().name)
        longTaskWithMessage(LocalDateTime.now().toString()+" Tarea2:")
        println(LocalDateTime.now().toString()+" Tarea3:"+  Thread.currentThread().name)
    }

    suspend fun delayCoroutine(message: String)
    {
        delay(timeMillis = 4000)
        println(message+ Thread.currentThread().name)
    }

    //Tarea Sincrona
    fun suspendExample()
    {
        println(LocalDateTime.now().toString()+" Tarea1:"+ Thread.currentThread().name)
        runBlocking {
            delayCoroutine(LocalDateTime.now().toString()+" Tarea2:")
        }
        println(LocalDateTime.now().toString()+" Tarea3:"+  Thread.currentThread().name)
    }

    fun suspendExample2() = runBlocking {
        println(LocalDateTime.now().toString()+" Tarea1:"+ Thread.currentThread().name)
        delayCoroutine(LocalDateTime.now().toString()+" Tarea2:")
        println(LocalDateTime.now().toString()+" Tarea3:"+  Thread.currentThread().name)
    }

    fun dispatcher()
    {
        runBlocking {
            println("Hilo de ejecución 1: ${Thread.currentThread().name} ")
        }

        runBlocking(Dispatchers.Unconfined) {
            println("Hilo de ejecución 2: ${Thread.currentThread().name} ")
        }

        runBlocking(Dispatchers.Default) {
            println("Hilo de ejecución 3: ${Thread.currentThread().name} ")
        }

        runBlocking(Dispatchers.IO) {
            println("Hilo de ejecución 4: ${Thread.currentThread().name} ")
        }

        runBlocking(newSingleThreadContext("HiloTest")) {
            println("Hilo de ejecución 5: ${Thread.currentThread().name} ")
        }

        runBlocking(Dispatchers.Main) {
            println("Hilo de ejecución 6: ${Thread.currentThread().name} ")
        }
    }

    //Tarea Asincrona
    fun launch()
    {
        println(LocalDateTime.now().toString()+" Tarea1:"+ Thread.currentThread().name)
        GlobalScope.launch {
            delayCoroutine(LocalDateTime.now().toString()+" Tarea2:")
        }
        println(LocalDateTime.now().toString()+" Tarea3:"+  Thread.currentThread().name)
    }

    fun exampleJob()
    {
        println(LocalDateTime.now().toString()+" Tarea1:"+ Thread.currentThread().name)
        val job =GlobalScope.launch {
            delayCoroutine(LocalDateTime.now().toString()+" Tarea2:")
        }
        println(LocalDateTime.now().toString()+" Tarea3:"+  Thread.currentThread().name)
        job.cancel()
    }

    suspend fun calculateHard():Int{
        val duracion:Long=2000
        delay(duracion)
        return duracion.toInt()
    }

    fun asyncAwait()= runBlocking {
        //println(System.currentTimeMillis().toString())
        val num1:Int = async{calculateHard()}.await()
        //println(System.currentTimeMillis().toString())
        val num2:Int= async{calculateHard()}.await()
        //println(System.currentTimeMillis().toString())
        val resultado=num1+num2
        //println(resultado.toString())
    }

    fun deferred()= runBlocking {
        //println(System.currentTimeMillis().toString())
        val numero1: Deferred<Int> = async { calculateHard() }
        //println(System.currentTimeMillis().toString())
        val numero2: Deferred<Int> = async { calculateHard() }
        //println(System.currentTimeMillis().toString())
        val resultado=numero1.await()+numero2.await()
        //println(resultado.toString())
    }

    fun withContextIO() = runBlocking {
        val numero1= withContext(Dispatchers.IO){ calculateHard() }
        val numero2= withContext(Dispatchers.IO){ calculateHard() }
        val resultado: Int = numero1+numero2
        println(resultado.toString())
    }

    fun cancelCoroutine()
    {
        runBlocking {
            val job:Job = launch {
                repeat(1000)
                {
                    i->
                    println("job: $i")
                    delay(500L)
                }
            }
            delay(1400)
            job.cancel()
            println("main: termina la espera")
        }
    }

}