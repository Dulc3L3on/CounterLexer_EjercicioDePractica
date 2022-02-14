//Código de usuario
package Backend.Analizadores;
import Backend.Objetos.Resultado;
import java.util.ArrayList;
//el static en el import, se coloca para la clase symb del parser, por lo mismo que ahí declara un enum...
//pero igual busca y lee para saber la respuesta real... aunque pienso que si se debe al enum...

%%
//Configuración
%class CounterLexer
%unicode
%line
%column
%public
%caseless
%standalone/*esta directiva se vuelve necesaria addla cuando se especifica o no el standAlone, para informar que el tipo de retorno de yytext() no será el nombre por defecto de la clase, sino el que se colocó en %class...
      o dicho de mejor manera, esta directiva se debe colocar cuando se use %class y el lexer no sea implementado con CUP [es decir el %standalone no esté presente... xD*/

//macros para palabras reconocidas
vocalSinTilde = [aeiou]//en lugar de crear estas macros, bien hubieras podido utilizar las classes predefinidas -> letter y digit, pero para que tuviera más uso el caseless, fue que hice esto xD
vocalTildada = [áéíóúÁÉÍÓÚ]//no cuenta las vocales tildadas sin importar que haya listado en forma minus y mayus...
vocal = {vocalSinTilde}|{vocalTildada}
letra = [a-z] //incluye la ñ, pero no la muestra, porque es el caso simi  a lo de las tildes...
digito = [0-9]
alfanumerico = {letra}|{digito}

//macros auxiliares
finDeLinea = \r|\n|\r\n
tabulacion = [ \t\f]/*el espacio antes de \t es OBLIGATORIO, para que acepte espacios en blanco xD*/
espacioEnBlanco = {finDeLinea} | {tabulacion}

//macros para errores
simbolos = "*"|"+"|"-"|"/"|"("|")"|","|"|"|"!"|\"|"#"|"$"|"%"|"&"|"<"|">"|"="|"?"|"¡"|"¿"|"_"|"."|":"|";"|"^"|"{"|"}"  //solo hace falta add más simbolos xD :,v xD
incorrecto = (({letra}|{digito})*{simbolos})+

%{ 
    int numeroVocales;
    int[] totalesPorTipo = {0,0,0,0,0,0,0};//0-4 -> vocales, 5 -> enteros, 6 -> errores
    ArrayList<Resultado> resultados = new ArrayList();
    ArrayList<Resultado> enteros = new ArrayList();
    ArrayList<Resultado> errores = new ArrayList();
    
    private void addResultadoParcialVocales(){
        resultados.add(new Resultado(yytext(), yyline+1, yycolumn+1));        
    }

    private void completarResultadoVocales(){
        if(numeroVocales < 5){
            resultados.get(resultados.size()-1).setTipo(numeroVocales);
            totalesPorTipo[numeroVocales-1] += 1;    
        }else{
            resultados.remove(resultados.size()-1);
        }     

        numeroVocales = 0;             
    }

    private void addResultadoDeEnteros(){
        enteros.add(new Resultado(5, yytext(), yyline+1, yycolumn+1));

        totalesPorTipo[5] += 1;
    }

    private void addError(){
        errores.add(new Resultado(6, yytext(), yyline+1, yycolumn+1));

        totalesPorTipo[6] += 1;
    }   

    public ArrayList<Resultado> getListaResultadosVocales(){
        return resultados;
    }

    public ArrayList<Resultado> getListaResultadosEnteros(){
        return enteros;
    }

    public ArrayList<Resultado> getListaErrores(){
        return errores;
    }

    public int[] getCantidadResultadosTotalesPorTipo(){
        return totalesPorTipo;
    }   
%}

%state COUNTER, ERROR

%%
//Reglas Léxicas

{digito}+ {addResultadoDeEnteros();}

<YYINITIAL>{
    {alfanumerico}+     {addResultadoParcialVocales();yypushback(yylength());yybegin(COUNTER);}
    {incorrecto}       {addError();yypushback(yylength());yybegin(ERROR);}
}

<COUNTER>{
    {vocal}            {numeroVocales++;}
    {espacioEnBlanco}  {completarResultadoVocales();yybegin(YYINITIAL);}
                    //no se muestra el conteo porque no coloqué el símbolo que represetna el fin del archivo, pero no se cuál es TuT, lo mimom va a suceder con error, es decir no retornará a YYINITIAL por la misma situación...
}

<ERROR>{
    {espacioEnBlanco}  {yybegin(YYINITIAL);}//hago esto para que no vuelva a coincidir con {incorrecto} aunque bien podría haber creado un estado para entero
                                            //y que en donde está actualmente COUNTER solito, se add ENTERO (o tendría que add a ENTERO COUNTER? xD), para que 
                                            //tampoco después de haber hecho la regresión se coincida en ese caso ocn alfa#
                                            //crear ese estado de ENTERO lo único que haría es cambiar las reglas léxicas de lugar xD
}

