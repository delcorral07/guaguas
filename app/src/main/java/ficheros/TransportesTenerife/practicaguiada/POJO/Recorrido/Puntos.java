package ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Puntos implements Serializable, Comparable<String>
{

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    private final static long serialVersionUID = 8194658519632977367L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Puntos() {
    }

    /**
     *
     * @param codigo
     * @param tipo
     * @param nombre
     */
    public Puntos(String nombre, String codigo, String tipo) {
        super();
        this.nombre = nombre;
        this.codigo = codigo;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Puntos withNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Puntos withCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Puntos withTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.codigo == null)? 0 :this.codigo.hashCode()));
        result = ((result* 31)+((this.tipo == null)? 0 :this.tipo.hashCode()));
        result = ((result* 31)+((this.nombre == null)? 0 :this.nombre.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Puntos) == false) {
            return false;
        }
        Puntos rhs = ((Puntos) other);
        return ((((this.codigo == rhs.codigo)||((this.codigo!= null)&&this.codigo.equals(rhs.codigo)))&&((this.tipo == rhs.tipo)||((this.tipo!= null)&&this.tipo.equals(rhs.tipo))))&&((this.nombre == rhs.nombre)||((this.nombre!= null)&&this.nombre.equals(rhs.nombre))));
    }

    @Override
    public int compareTo(String o) {
        if(this.codigo.equals(o)){
            return 0;
        }else{
            return -1;
        }
    }
}