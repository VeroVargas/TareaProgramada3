package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class SML {
   
    public ListaSimple Codigo = new ListaSimple(); // lista guarda el codigo del archivo
    public ListaSimple estatico = new ListaSimple ("Estatico"); // lista que almacena los datos del ambiente estatico
    public ListaSimple dinamico = new ListaSimple ("Dinamico"); // lista que almacena los datos del ambiente dinamico
    public String resultadoValor;//auxiliar para guardar el valor de algun dato
    public String resultadoTipo;//auxiliar para guardar el valor de algun dato

    public String iniciar(String ruta) {
        try{
            FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();
            int pos=0;
            while(linea !=null){
                    StringTokenizer dato = new StringTokenizer(linea," ");
                    while(dato.hasMoreTokens()){
                            Object caracter = dato.nextToken();
                            Codigo.Insertar(caracter, pos);
                            pos++;}
                    linea = br.readLine();}
            NodosListaSimple aux = Codigo.PrimerNodo;
            analizar(aux,Codigo);
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        return imprimirTablas();
    }

    

    /*funcion analizar(aux,Codigo)
     * recibe como parametros la lista del codigo que se va a evaluar
     * y el primer nodo de dicha lista*/
    public void analizar(NodosListaSimple aux,ListaSimple Codigo) throws IOException{
            while(aux!=null){
                    // Verifica si se define una variable
                    if(aux.dat.equals("val")){
                            aux = aux.siguiente;
                            String variable = aux.dat.toString();
                            aux = aux.siguiente;
                            if(aux.siguiente.dat.equals("let")){
                                    aux = aux.siguiente;
                                    int index = eval_let(aux.posicion); // si se presenta un let usa eval_let, para crear una lista con los datos de Let
                                    estatico.InsertaFinal(variable, resultadoTipo);
                                    dinamico.InsertaFinal(variable, resultadoValor);
                                    if(Codigo.Largo()==index+1){ ;break;}
                                    else{
                                            while(aux!=null){
                                                    if(aux.posicion==index){aux = aux.siguiente; break;}
                                                    else aux = aux.siguiente;
                                            }
                                    }
                                    }
                            else if(aux.siguiente.dat.equals("if")){
                                    aux = aux.siguiente;
                                    int index = arregloIf(aux.siguiente.posicion); //si se presenta un if usa arregloIf para separar los datos por if
                                    estatico.InsertaFinal(variable, resultadoTipo);
                                    dinamico.InsertaFinal(variable, resultadoValor);
                                    if(Codigo.Largo()==index+1){ ;break;}
                                    else{
                                            while(aux!=null){
                                                    if(aux.posicion==index){aux = aux.siguiente; break;}
                                                    else aux = aux.siguiente;
                                            }
                                    }
                            }
                            else{
                                    String valor = aux.siguiente.dat.toString();
                                    estatico.InsertaFinal(variable);
                                    dinamico.InsertaFinal(variable, aux.siguiente.dat.toString());
                                    try{
                                            //Verifica si el dato dado es un int
                                            if (Integer.parseInt(valor) %1 == 0){
                                                    NodosListaSimple aux2 = estatico.PrimerNodo;
                                                    while(aux2!=null){
                                                            if(aux2.dato.equals(variable)){ aux2.tipo = "int";break;}
                                                            else aux2 = aux2.siguiente;
                                                    }
                                                    aux = aux.siguiente;
                                            }//Fin del if
                                    }
                                    catch (NumberFormatException e){
                                            //llama a la funcion verificaOper(), para determinar el tipo de dato dado
                                            int band = verificaOper(valor); 
                                            if(band == 1);
                                            else{
                                                    NodosListaSimple aux2 = estatico.PrimerNodo;
                                                    NodosListaSimple aux1 = dinamico.PrimerNodo;
                                                    while(aux2!=null){
                                                            if(aux2.dato.equals(variable)){ aux2.tipo = "int"; aux1 = dinamico.PrimerNodo; break;}
                                                            else{ aux2 = aux2.siguiente;}
                                                    }
                                                    while(aux1!=null){
                                                            if(aux1.dato.equals(variable)){ aux1.tipo = resultadoValor; break;}
                                                            else aux1 = aux1.siguiente;
                                                    }
                                            }
                                            aux = aux.siguiente;
                                    }
                            }
                    }
                    //Si se presenta un let, llama a la funcion eval_let
                    else if(aux.dat.equals("let")){
                            int index = eval_let(aux.posicion);
                            if(Codigo.Largo()==index+1){ ;break;}
                            else{
                                    while(aux!=null){
                                            if(aux.posicion==index){aux = aux.siguiente; break;}
                                            else aux = aux.siguiente;
                                    }
                            }
                    }
                    //Si se presenta un if, llama a la funcion arregloIf
                    else if(aux.dat.equals("if")){
                            int index = arregloIf(aux.posicion);
                    if(Codigo.Largo()==index+1){ ;break;}
                    else{
                            while(aux!=null){
                                    if(aux.posicion==index){aux = aux.siguiente; break;}
                                    else aux = aux.siguiente;
                            }
                    }
                    }
                    else{
                            //para enteros, strings y booleans
                            String dato = aux.dat.toString();
                            try{
                                    int num = Integer.parseInt(dato);
                                    resultadoValor = String.valueOf(num);
                                    resultadoTipo = "int";
                            }
                            catch (NumberFormatException e){
                                    int size = dato.length();
                                    if(size == 1){
                                            NodosListaSimple a = dinamico.BuscarElemento1(dato);
                                            NodosListaSimple a1 = estatico.BuscarElemento1(dato);
                                            if(a!=null && a1!=null){
                                                    resultadoValor = a.tipo;
                                                    resultadoTipo = a1.tipo;
                                            }
                                    }
                                    else{
                                            int numVeri = verificaOper(dato); //verifica el tipo del dato

                                    }
                            }
                            aux = aux.siguiente;
                    }
            }
    }

    /*Funcion arregloIf(pos)
     * recibe la posicion del if en la lista, 
     * crea una lista con los datos del if*/
    public int arregloIf (int pos) throws IOException{
            pos = pos - 1;
            NodosListaSimple index = Codigo.PrimerNodo;
            ListaSimple arreglo = new ListaSimple();
            while(index!=null){
                    if(index.posicion==pos){
                            while(index!=null){
                                    arreglo.Insertar(index.dat, index.posicion); 
                                    index = index.siguiente;}}
                    else index = index.siguiente;
            } int num = eval_if(arreglo); 
            return num;
    }

    /*Funcion eval_if(array)
     * recibe los datos del if como arreglo
     * los separa en expresiones
     * exp1= if; exp2= then; exp3= else
     *determina los valores del if con evaluar(e1,e2,e3)*/
    public int eval_if (ListaSimple arreglo) throws IOException {
            NodosListaSimple aux = arreglo.PrimerNodo;

            //arreglos donde se guardan los datos del if
            ArrayList exp1= new ArrayList();
            ArrayList exp2= new ArrayList();
            ArrayList exp3= new ArrayList();

            while(aux!=null){
                    //Guarda los datos en exp1
                    if (aux.dat.equals("if")){ aux = aux.siguiente;
                            while(!(aux.dat.equals("then"))){ 
                                    exp1.add(aux.dat); 
                                    aux = aux.siguiente;}}
                    //Guarda los datos en exp2
                    else if (aux.dat.equals("then")){
                             aux = aux.siguiente;
                             if(aux.dat.equals("if")){
                                     while(!(aux.dat.equals("else"))){ 
                                             exp2.add(aux.dat); 
                                             aux = aux.siguiente;}
                                     exp2.add(aux.dat); 
                                     aux = aux.siguiente;
                                     while(!(aux.dat.equals("else"))){ 
                                             exp2.add(aux.dat); 
                                             aux = aux.siguiente;}
                             }
                             while(!(aux.dat.equals("else"))){ 
                                     exp2.add(aux.dat); 
                                     aux = aux.siguiente;}}
                    else if(aux.dat.equals("else")){ 
                            aux = aux.siguiente;
                            if(aux.dat.equals("if")){ 
                                    exp3.add(aux.dat);
                                    while(!(aux.dat.equals("else"))){ 
                                            exp3.add(aux.dat); 
                                            aux = aux.siguiente;}
                                    exp3.add(aux.dat);
                            }
                            else if(aux.dat.equals("let")){ 
                                    exp3.add(aux.dat);
                                    while(!(aux.dat.equals("end"))){ 
                                            exp3.add(aux.dat);
                                            aux = aux.siguiente;}
                                    evaluar(exp1,exp2,exp3); 
                                    return aux.posicion;}
                            else{
                                    exp3.add(aux.dat); 
                                    evaluar(exp1,exp2,exp3); 
                                    return aux.posicion; }
                    }
                    else aux = aux.siguiente;
            }return 0;
    }

    /* Funcion auxiliar de eval_if evaluar(expresion1,expresion2,expresion3)
     * determina la condicion del if
     * utiliza a la funcion compara para esto*/
    public void evaluar(ArrayList a1, ArrayList a2, ArrayList a3) throws IOException{
            Iterator<String> exp = a1.iterator();
            //separa para iterar sobre los arreglos
            while(exp.hasNext()){
                    String dato1 = exp.next(); String dato2 = exp.next();
                    if(dato2.equals("<")|dato2.equals(">")|dato2.equals("=")){
                            String dato = exp.next(); int num1,num2;
                            try{ num1 = Integer.parseInt(dato1); //verifica si es un int
                            //Cuando determina que los dos datos ingresandos son int, llama a la funcion compara
                                    try{ num2 = Integer.parseInt(dato); 
                                            compara(num1,num2,dato2,a2,a3);}
                                    catch(NumberFormatException e2){
                                            NodosListaSimple nodoAux1 = estatico.BuscarElemento1(dato), nodoAux2 = dinamico.BuscarElemento1(dato);
                                            if(nodoAux1 != null){ 
                                                    String tipo = nodoAux1.tipo, valor = nodoAux2.tipo;
                                                    num2 = Integer.parseInt(valor);
                                                    compara(num1,num2,dato2,a2,a3);}
                                            else ;}}
                            catch(NumberFormatException e1){
                                    NodosListaSimple nodoAux = estatico.BuscarElemento1(dato1), nodoAux3 = dinamico.BuscarElemento1(dato1);
                                    if(nodoAux!=null){
                                            String tipo = nodoAux.tipo, valor = nodoAux3.tipo;		
                                            if(valor.equals("true")|valor.equals("false")){
                                                    if(dato.equals("false")|dato.equals("true")){
                                                            if(valor.equals(dato)) eval_then(a2);
                                                            else eval_else(a3);
                                                    }
                                                    else{
                                                    NodosListaSimple nodoAux1 = estatico.BuscarElemento1(dato), nodoAux2 = dinamico.BuscarElemento1(dato);
                                                    String tipo1 = nodoAux1.tipo; String valor1 = nodoAux2.tipo;
                                                    if(valor.equals("false")|valor.equals("true")){
                                                            if(tipo.equals(valor)) eval_then(a2);
                                                            else eval_else(a3);
                                                    }
                                                    else ;
                                                    }
                                                    }
                                            try{ num1 = Integer.parseInt(valor);
                                                    try{ num2 = Integer.parseInt(dato); compara(num1,num2,dato2,a2,a3);}
                                                    catch(NumberFormatException e2){
                                                            NodosListaSimple nodoAux1 = estatico.BuscarElemento1(dato), nodoAux2 = dinamico.BuscarElemento1(dato);
                                                            if(nodoAux1 != null){
                                                                    tipo = nodoAux1.tipo;
                                                                    valor = nodoAux2.tipo;
                                                                    num2 = Integer.parseInt(valor);
                                                                    compara(num1,num2,dato2,a2,a3);} else;} }
                                            catch(NumberFormatException e2){;}}	
                                    else{
                                            int b = verificaOper(dato1);
                                            num1 = Integer.parseInt(resultadoValor);
                                            try{ num2 = Integer.parseInt(dato); compara(num1,num2,dato2,a2,a3);}
                                            catch(NumberFormatException e4){
                                                    NodosListaSimple nodoAux1 = estatico.BuscarElemento1(dato), nodoAux2 = dinamico.BuscarElemento1(dato);
                                                    if(nodoAux1 != null){
                                                            String tipo = nodoAux1.tipo;
                                                            String valor = nodoAux2.tipo;
                                                            num2 = Integer.parseInt(valor);
                                                            compara(num1,num2,dato2,a2,a3);} 
                                                    else{b = verificaOper(dato);
                                                    num2 = Integer.parseInt(resultadoValor); compara(num1,num2,dato2,a2,a3);}}
                                    }

                            }}break;}}

    /*Funcion auxliar de eval_if compara(num1,num2,comparador,then,else) 
     * num1 num2 se relacionan mediante comp
     * Compara la condicion del if, y determinar si evaluar el then(e2) o el else(e3)*/
    public  void compara(int num1, int num2, String comp, ArrayList e2, ArrayList e3) throws IOException{
            if(comp.equals("<")){
                    if(num1 < num2) 
                            eval_then(e2);
                    else eval_else(e3); }
            else if(comp.equals(">")){
                    if(num1>num2) 
                            eval_then(e2);
                    else eval_else(e3);}
            else{
                    if(num1==num2) 
                            eval_then(e2);
                    else eval_else(e3);}
    }

    /*Funcion eval_then(num1,num2,expresion2)
     * numeros del if
     * y el arreglo de la condicion*/
    public  void eval_then(ArrayList exp2) throws IOException{
            Iterator<String> datos = exp2.iterator();
            String nombre = datos.next();
            if(exp2.size() == 1){
                    if(nombre.length()==1){
                            try{
                                    int a = Integer.parseInt(nombre);
                                    resultadoValor = String.valueOf(a);
                                    resultadoTipo = "int";
                            }
                            catch(NumberFormatException e){
                                    NodosListaSimple aux = estatico.BuscarElemento1(nombre), 
                                    aux2 = dinamico.BuscarElemento1(nombre);
                                    if(aux != null){
                                            String tipo = aux.tipo, valor = aux2.tipo;
                                            resultadoValor = valor; resultadoTipo = tipo;} 
                                    else ;}}
                    else{
                            try{ 
                                    int a = Integer.parseInt(nombre); 
                                    resultadoValor = String.valueOf(a); 
                                    resultadoTipo = "int";}
                            catch(NumberFormatException e){
                                    int num = verificaOper(nombre);} 
                            } 
            }
            else{
                    if(nombre.equals("let")) System.out.println("funcion vero");
                    else if(nombre.equals("if")){
                            int tamanno = exp2.size();
                            int index = 1;
                            ListaSimple lista = new ListaSimple();
                            lista.Insertar(nombre, 0);
                            while(index!=tamanno){
                                    lista.Insertar(datos.next(), index); 
                                    index++;}
                            eval_if(lista);}
                    else{
                            try{
                                    int numero = Integer.parseInt(nombre);
                                    resultadoValor = String.valueOf(numero);
                                    resultadoTipo = "int";}
                            catch (NumberFormatException e){
                                    int len = nombre.length();
                                    if(len == 1){
                                            NodosListaSimple aux = dinamico.BuscarElemento1(nombre), aux2 = estatico.BuscarElemento1(nombre);
                                            if(aux!=null && aux2!=null){
                                                    resultadoValor = aux.tipo;
                                                    resultadoTipo = aux2.tipo;}}
                                    else{
                                            int ban = verificaOper(nombre);}
                            }
                    }
            }
    }

    /*Funcion eval_else(num1,num2,expresion3)
     * recibe numeros del if
     * y el arreglo con la condicion else*/
    public void eval_else(ArrayList exp3) throws IOException{
            Iterator<String> datos = exp3.iterator();
            String nombre = datos.next();
            if(exp3.size() == 1){
                    if(nombre.length()==1){
                            try{
                                    int a = Integer.parseInt(nombre);
                                    resultadoValor = String.valueOf(a);
                                    resultadoTipo = "int";
                            }
                            catch(NumberFormatException e){
                                    NodosListaSimple aux = estatico.BuscarElemento1(nombre), aux2 = dinamico.BuscarElemento1(nombre);
                                    if(aux != null){
                                            String tipo = aux.tipo, valor = aux2.tipo;
                                            resultadoValor = valor; 
                                            resultadoTipo = tipo;} 
                                    else ;}
                    }
                    else{
                            try{ int a = Integer.parseInt(nombre); 
                                    resultadoValor = String.valueOf(a); 
                                    resultadoTipo = "int";}
                            catch(NumberFormatException e){ 
                                    int ban = verificaOper(nombre);}
                    }
            }
            else{
                    if(nombre.equals("let"));
                    else if(nombre.equals("if")){
                            int len = exp3.size(), index = 1;
                            ListaSimple lista = new ListaSimple();
                            while(index!=len){
                                    lista.Insertar(datos.next(), index);
                                    index++;}
                            eval_if(lista);
                    }
                    else{
                            try{
                                    int numero = Integer.parseInt(nombre);
                                    resultadoValor = String.valueOf(numero);
                                    resultadoTipo = "int";}
                            catch (NumberFormatException e){
                                    int len = nombre.length();
                                    if(len == 1){
                                            NodosListaSimple aux = dinamico.BuscarElemento1(nombre), aux2 = estatico.BuscarElemento1(nombre);
                                            if(aux!=null && aux2!=null){
                                                    resultadoValor = aux.tipo;
                                                    resultadoTipo = aux2.tipo;}
                                            }
                                    else{
                                            int ban = verificaOper(nombre);}
                            }
                    }
            }
    }

    /*Funcion eval_let(pos)
     * resive la posicion del let en codigo
     * divide el let en :
     * Variables: asignaciones antes de "in"
     * Asignaciones: guarda todos los datos hasta "end"
     * listas*/
    public int eval_let(int pos) throws IOException{
            NodosListaSimple auxLet = Codigo.PrimerNodo;
            ListaSimple Let = new ListaSimple();
            while(auxLet!=null){
                    if(auxLet.posicion==pos){
                            while(auxLet!=null){ 
                                    Let.Insertar(auxLet.dat, auxLet.posicion); 
                                    auxLet = auxLet.siguiente;}
                            break;}
                    else auxLet = auxLet.siguiente;}
            auxLet = Let.PrimerNodo;
            ListaSimple Variables = new ListaSimple(), Asignaciones = new ListaSimple();
            while(auxLet!=null){
                    int index = 0;
                    if (auxLet.dat.equals("let")){
                            auxLet = auxLet.siguiente;
                            while(!(auxLet.dat.equals("in"))){ 
                                    Object a = auxLet.dat;
                                    if(a.equals("let")){
                                            while(!(a.equals("end"))){
                                                    Variables.Insertar(a, auxLet.posicion);
                                                    auxLet = auxLet.siguiente; a = auxLet.dat;}}
                                    Variables.Insertar(a,auxLet.posicion); index++;
                                    auxLet = auxLet.siguiente;}}
                    else if (auxLet.dat.equals("in")){
                            auxLet = auxLet.siguiente;
                            while(!(auxLet.dat.equals("end"))){
                                    Object a = auxLet.dat;
                                    Asignaciones.Insertar(a,auxLet.posicion);
                                    index++;
                                    auxLet = auxLet.siguiente;
                             }	}
                    else if (auxLet.dat.equals("end")){
                            NodosListaSimple aux1 = Variables.PrimerNodo;
                            NodosListaSimple aux2 = Asignaciones.PrimerNodo;
                            analizar(aux1,Variables);
                            analizar(aux2,Asignaciones);
                            return auxLet.posicion;}
            }
            return auxLet.posicion;
    }

    /*Funcion tipo()
     * funcion que determina el tipo de dato que se
     * acaba de ingresar en lista dinamico*/
    public  void tipo(){
            NodosListaSimple aux1 = dinamico.PrimerNodo;
            NodosListaSimple aux2 = estatico.PrimerNodo;
            while (aux1!=null && aux2!=null){
                    if (aux1.tipo.equals("true") || aux1.tipo.equals("false")){
                            aux2.tipo = "Boolean";
                            aux2 = aux2.siguiente;
                            aux1 = aux1.siguiente;}
                    else{ 
                            try{ 
                                    if (Integer.parseInt(aux1.tipo) %1 == 0){
                                            aux2.tipo = "int"; 
                                            aux2 = aux2.siguiente; 
                                            aux1 = aux1.siguiente;}	
                            }
                            catch (NumberFormatException e){
                                    aux2.tipo = define(aux1.tipo); 
                                    aux2 = aux2.siguiente; 
                                    aux1 = aux1.siguiente;}
                    }
            }
    }

    /*Funcion verificaOper(dato)
     * recibe como parametro un string
     * dato se analiza para saber si es un string, o esta compuesto
     * retorna un entero como bandera del proceso q siguió*/
    public int verificaOper(String valor) throws IOException{
            ListaSimple listaOper = new ListaSimple();
            int len = valor.length();
            int pos = 0;
            String unificar = "";
            while(pos!=len){
                    String caracter = String.valueOf(valor.charAt(pos));
                    if(caracter.equals("*")|caracter.equals("+")|caracter.equals("-")|caracter.equals("/")){
                            listaOper.InsertaFinal(unificar, caracter);
                            unificar = "";
                            pos++;}
                    else{unificar = unificar + caracter; pos++;}
                    }
            listaOper.InsertaFinal(unificar,null);
            if(listaOper.Largo()==1){
                    int b =letra(unificar);
                    if(b==1){
                            tipo();
                            return 1;}
                    else 
                            return 3;}
            else{
                    calcular(listaOper);
                    return 2;}
    }

    /*Funcion calcular(lista)
     * Determina el tipo de dato de las variables
     * en las lista estatica y dinamico*/
    public void calcular(ListaSimple lista){
            NodosListaSimple aux = lista.PrimerNodo;
            int res = 0;
            while(aux!=null){
                    String dato1,oper,dato2;
                    dato1 = aux.dato;
                    oper = aux.tipo;
                    dato2 = aux.siguiente.dato;
                    res = operaciones(dato1,oper,dato2);
                    aux.siguiente.dato = String.valueOf(res);
                    aux = aux.siguiente;
                    if(aux.tipo==null) break;
            }
            resultadoValor = String.valueOf(res);
            resultadoTipo = "int";
    }

    /* resolver(operando1,operando2,operador)
     * realiza la operacion correspondiente*/
    public static int resolver(int num1,int num2,String oper){
            int resultado;
            if(oper.equals("+")) resultado = num1 + num2;
            else if(oper.equals("-")) resultado = num1 - num2;
            else if(oper.equals("*")) resultado = num1 * num2;
            else resultado = num1 / num2;
            return resultado;
    }

    /*Funcion operaciones(dato1,operador,dato2)
     * resive los parametros como str
     * realiza operaciones de ser nesesario determina el valor de las variables
     * al encontrar los operandos evalua la operacion llamando a resolver()*/
    public int operaciones(String dato1,String oper,String dato2){
            int num1,num2;
            try{
                    num1 = Integer.parseInt(dato1); 
                    try{num2 = Integer.parseInt(dato2); 
                            int result = resolver(num1,num2,oper);
                            return result;}
                    catch(NumberFormatException e1){
                            NodosListaSimple aux = dinamico.BuscarElemento1(dato2);
                            if(aux!=null){
                                    try{
                                            num2 = Integer.parseInt(aux.tipo);
                                            int result = resolver(num1,num2,oper);
                                            return result;}
                                    catch(NumberFormatException e3){;}
                                    }
                            }
            }
            catch(NumberFormatException e2){
                    NodosListaSimple aux = dinamico.BuscarElemento1(dato1);
                    if(aux!=null){
                            try{
                                    num1 = Integer.parseInt(aux.tipo);
                                    try{num2 = Integer.parseInt(dato2);
                                            int result = resolver(num1,num2,oper);
                                            return result;}
                                    catch(NumberFormatException e1){
                                            aux = dinamico.BuscarElemento1(dato2);
                                            if(aux!=null){
                                                    try{num2 = Integer.parseInt(aux.tipo);
                                                            int result = resolver(num1,num2,oper);
                                                            return result;}
                                                    catch(NumberFormatException e3){;}
                                                    }
                                            }
                                    }
                            catch(NumberFormatException e){;}
                    }
            }
            return 0;
    }

    /*  Funcion letra (info)
     * resive un str
     * que verifica si una varible esta en el programa unificarlas*/
    public int letra (String info) throws IOException{	
            if (dinamico.VaciaLista()){
                    System.out.println("Lista vacia");
            }//Fin del if
            else{
                    //Se realizan las busquedas para realizar el cambio del valor en caso que sea necesario
                    NodosListaSimple aux = dinamico.PrimerNodo;
                    NodosListaSimple guia = dinamico.PrimerNodo;
                    while (aux!=null){
                            String var = aux.dato;
                            if (var.equals(info)){
                                    while (guia != null){
                                            if (guia.tipo.equals(info)){
                                                    guia.tipo = aux.tipo;
                                                    break;}
                                            else{
                                                    guia = guia.siguiente;}
                                    }
                                    aux.tipo = aux.tipo;
                                    break;} 
                            else{
                                    aux = aux.siguiente;}
                    }
                    if(aux==null){resultadoTipo = define(info);
                            resultadoValor = info;
                            return 3;}
            }
            return 0;	
    }

    /*Funcion define(dato)
     * Parametro str
     * valida el dato recivido del cod
     * para determinar el tipo de dato 
     * String, tupla, lista, char, boolean*/	
    public static String define(String dato){
            char pc= ';';
            if (dato.charAt(dato.length()-1)==pc);{
                    StringTokenizer tokn = new StringTokenizer(dato,";");
                    dato = tokn.nextToken();
            }
            try {
                    //define si es un int
                    Integer.parseInt(dato);
                    return "int";
            } catch (NumberFormatException nfe){
                    //define si es un bool
                    if (dato.equals("true")||dato.equals("false")){
                            return "boolean";}
                    //define si es un char
                    else if(dato.charAt(0)=='#'){
                            return "char";}
                    //define si es una tupla
                    else if(dato.charAt(0)=='('){
                            dato= eliminarChar(dato,'(',')');
                            StringTokenizer tupla = new StringTokenizer(dato,",");
                            String obtenido= tupla.nextToken();
                            if(obtenido.indexOf("[")==0){
                                    String aux=tupla.nextToken();
                                    while(aux.indexOf("]")<0){
                                            obtenido=obtenido+","+aux;
                                            aux= tupla.nextToken();}
                            }
                            obtenido=define(obtenido);
                            while(tupla.hasMoreTokens()){
                                    String aux2=tupla.nextToken();
                                    if(aux2.indexOf("[")==0){
                                            String aux=tupla.nextToken();
                                            while(aux.indexOf("]")<0){
                                                    aux2=aux2+","+aux;
                                                    aux= tupla.nextToken();}
                                    }
                                    obtenido+="*"+define(aux2);
                            }
                            return obtenido;
                                    }
                    //define si es una lista
                    else if(dato.charAt(0)=='['){
                            dato= eliminarChar(dato,'[',']');
                            StringTokenizer lista = new StringTokenizer(dato,",");
                            String result = define(lista.nextToken());
                            result = "list "+result;
                            return result;}
                    else if(dato.indexOf("let")==0){
                            return "let";
                    }
                    else if(dato.indexOf("if")==0){
                            return "if";
                    }
                    //de lo contrario es un string
                    else{
                            return "string";}

            }
    }

    /*Funcion auxiliar eliminarChar(str,char1,char2)
     * Elimina el ';' al final de cada expresion*/
    public static String eliminarChar(String text,char char1, char char2){
            String res="";
            for(int i=0; i<text.length(); i++){
                    if(text.charAt(i)!=char1&& text.charAt(i)!=char2)
                            res+= text.charAt(i);
            }
            return res;
    }

    /*Funcion imprimirTablas
     * como su nombre lo indica, esta funcion imprime
     * las tablas del ambiente dinamico y estatico*/
    public String imprimirTablas(){
        String resul = "";
            resul = "---- Tabla ambiente estatico ---- <br>";
            NodosListaSimple aux = estatico.PrimerNodo;
            NodosListaSimple aux1 = dinamico.PrimerNodo;
            while(aux!=null){
                    resul += ("	"+aux.dato+" --->  "+aux.tipo+"<br>");
                    aux = aux.siguiente;
            }
           
            resul += ("<br><br>---- Tabla ambiente dinamico ---- <br>");
            while(aux1!=null){
                    resul += ("	"+aux1.dato+" --->  "+aux1.tipo+"<br>");
                    aux1 = aux1.siguiente;
            }
            System.out.println("");
            return resul;
    }
}
