/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;


public class NodosListaSimple {
	// datos amigables para que la Clase Lista Tenga un acceso directo
	Object dat;
	String dato;
	String valor;
    String tipo;
    int posicion;
    NodosListaSimple siguiente;

    NodosListaSimple (String var, String tipo)
    {  dato = var;
       this.tipo = tipo;
     siguiente = null;  
     //sig = Prime;
    }

    //Constructor Crea un nodo del Tipo Object y al siguiente nodo de la lista
    NodosListaSimple (String var, String tipo, NodosListaSimple signodo)
    {   dato = var;
    	this.tipo = tipo;
        siguiente = signodo;
    //sig = sigui;
    }
    
    NodosListaSimple (String var)
    {  dato = var;
       siguiente = null;  
       //sig = Prime;
    }

    //Constructor Crea un nodo del Tipo Object y al siguiente nodo de la lista
    NodosListaSimple (String var, NodosListaSimple signodo)
    {   dato = var;
      siguiente = signodo;
      //sig = sigui;
    }
    
NodosListaSimple(Object dat, int pos){
	this.dat = dat;
	this.posicion = pos;
	siguiente = null;
}

NodosListaSimple(Object dat, int pos,NodosListaSimple signodo){
	this.dat = dat;
	this.posicion = pos;
	siguiente = signodo;
}
//Construtor  Crea un nodo del tipo Object
NodosListaSimple (String dato, String valor, String tipo)
 {  this.dato = dato;
	this.valor = valor;
 	this.tipo = tipo;
    siguiente = null;  
    //sig = Prime;
 }

//Constructor Crea un nodo del Tipo Object y al siguiente nodo de la lista
NodosListaSimple (String dato, String valor, String tipo, NodosListaSimple signodo)
{   this.dato = dato;
	this.valor = valor;
	this.tipo = tipo;
   siguiente = signodo;
   //sig = sigui;
}

//Retorna el dato que se encuentra en este nodo
Object getObject() {return valor; }

//Retorna el siguiente nodo
NodosListaSimple getnext() {return siguiente; }
}
