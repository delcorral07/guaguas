package ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO;

import java.io.Serializable;

public class EstacionarioBusqueda implements Serializable {

    /*
    Esta clase la usaremos en la clase listado. Al usar un recycler view para hacer la búsqueda,
    no podemos simplemente mostrar paradas y guaguas, entonces creamos esta clase para
    poder mostrar información de cada uno de los objetos guagua y parada y en función del valor
    que nos devuelva el booleano, lo trataremos de una manera u otra
     */

    private String codigo;
    private String titulo; //Puede ser título o parada
    private String nombre;
    private boolean esGuagua;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public EstacionarioBusqueda(){}

    public EstacionarioBusqueda(String codigo, String nombre,String titulo, boolean esGuagua) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.esGuagua = esGuagua;
        this.titulo = titulo;
    }



    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsGuagua() {
        return esGuagua;
    }

    public void setEsGuagua(boolean esGuagua) {
        this.esGuagua = esGuagua;
    }
}
