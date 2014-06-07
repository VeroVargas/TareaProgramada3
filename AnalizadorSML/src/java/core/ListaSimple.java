/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;


public class ListaSimple {
		  public NodosListaSimple PrimerNodo;
		  public NodosListaSimple UltimoNodo;
		  String Nombre;
		  
			
		//Constructor construye una lista vacia con un nombre s
		 public ListaSimple (String s)
		{ Nombre = s;
		  PrimerNodo = UltimoNodo =null;
		}

		//Retorna True si Lista Vacía
		 public boolean VaciaLista () {return PrimerNodo == null;}
		 
		 public void imprimir(){
			 if (VaciaLista()){
				 	System.out.println("Lista vacia");
				   }
			 else{
				 NodosListaSimple aux = PrimerNodo;
				 while(aux != null){
					 System.out.print(aux.dat+" ");
					 aux = aux.siguiente;
				 }
			 }
		 }
		// Imprime el contenido de la lista
		 public void Imprimir()
		 { if (VaciaLista()){
		 	System.out.println("Lista vacia");
		   }
		   else{
		   		System.out.print( "La expresión en " + Nombre + " es:  ");
		   		NodosListaSimple Actual = PrimerNodo;
		        while (Actual != null){
		  			  System.out.print(Actual.valor + " ");
		              Actual=Actual.siguiente;
		        }
		              
		      System.out.println();
		      System.out.println();}
		 }
		 
		 public void imprimi()
		 { if (VaciaLista()){
		 	System.out.println("Lista vacia");
		   }
		   else{
		   		System.out.print( "La expresión en " + Nombre + " es:  ");
		   		NodosListaSimple Actual = PrimerNodo;
		        while (Actual != null){
		  			  System.out.print(Actual.dato + " ");
		  			  System.out.print(Actual.tipo + " ");
		              Actual=Actual.siguiente;
		        }
		              
		      System.out.println();
		      System.out.println();}
		 }

		//Constructor construye una lista vacia con un nombre de List
		public ListaSimple(){ this ("Lista");}

		//Inserta un Elemento al Frente de la Lista
		//Si esta vacía PrimerNodo y UltimoNodo se refieren al nuevo nodo. Si no PrimerNodo se refiere al nuevo nodo.
			
		public void InsertaInicio (String dato, String valor, String tipo){
			if (VaciaLista()){
			   PrimerNodo = UltimoNodo = new NodosListaSimple (dato,valor,tipo);
			   //return this;
			}
			else{
			   PrimerNodo = new NodosListaSimple (dato,valor,tipo, PrimerNodo);
			   //return this;
			}
		}


		public void InsertaInicio (String valor, String dato, String tipo, ListaSimple adya){
			if (VaciaLista()){
			   PrimerNodo = UltimoNodo = new NodosListaSimple (dato,valor,tipo);
			}
			else{
			   PrimerNodo = new NodosListaSimple (dato,valor,tipo, PrimerNodo);
			}
		}
		
		public void Insertar (Object a, int posicion){
			if (VaciaLista()){
			   PrimerNodo = UltimoNodo = new NodosListaSimple (a,posicion);
			   //return this;
			}
			else{
				UltimoNodo=UltimoNodo.siguiente =new NodosListaSimple (a,posicion);
			   //return this;
			}
		}

		public void InsertaFinal(String dat){
			  if ( VaciaLista())
			     PrimerNodo = UltimoNodo = new NodosListaSimple (dat);
			  else
			     UltimoNodo=UltimoNodo.siguiente =new NodosListaSimple (dat);
			}
		
		public void InsertaFinal(String variable, String valor){
			  if ( VaciaLista())
			     PrimerNodo = UltimoNodo = new NodosListaSimple (variable, valor);
			  else
			     UltimoNodo=UltimoNodo.siguiente =new NodosListaSimple (variable, valor);
			}
		
		//Elimina un Elemento en una posición dada
		//Si esta vacía PrimerNodo y UltimoNodo se refieren al nuevo nodo.
		//Si no PrimerNodo se refiere al nuevo nodo.

		public Object EliminaMedio (int Posicion)
		{   Object ElementoDel = null;

			if  ( VaciaLista())
				System.out.println( "Nada");

		    else
		   {
		  	 ElementoDel = UltimoNodo.valor; // recuperar la información
			 NodosListaSimple Aux =null;
		     NodosListaSimple Actual = PrimerNodo;
		     int i =1;
		     while (( i != Posicion) & (Actual.siguiente != null))
		     { i++;
		       Aux =Actual;
		       Actual =Actual.siguiente;
		     }
		     if( i ==Posicion)
			 {
			   if (Aux == null)
			   {
		        Actual = PrimerNodo;
		        ElementoDel=PrimerNodo;
		        PrimerNodo = PrimerNodo.siguiente;
		       }
		       else
		       { ElementoDel=Aux.valor;
		         Aux.siguiente = Actual.siguiente;
		       }

		   }

		}
		 return ElementoDel;}

		// << 3.Largo
		public int Largo()
		{
		  NodosListaSimple aux=PrimerNodo;
		  int i=0;
		  while (aux !=null)
		  {
		  	i++;
		  	aux=aux.siguiente;
		  }
		  return i;

		}

		public NodosListaSimple BuscarElemento1(String Elem)
		{
			  NodosListaSimple aux= PrimerNodo;
			  while(aux!=null){
				  if (aux.dato.equals(Elem)){
					return aux;
				  }
				  else
					  aux=aux.siguiente;
			  }
			return aux;
		}
}
