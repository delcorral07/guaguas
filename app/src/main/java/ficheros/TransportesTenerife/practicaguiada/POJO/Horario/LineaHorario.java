package ficheros.TransportesTenerife.practicaguiada.POJO.Horario;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class LineaHorario implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("tiempo")
    @Expose
    private String tiempo;
    @SerializedName("destino")
    @Expose
    private String destino;
    private final static long serialVersionUID = 3152159704204436082L;

    /**
     * No args constructor for use in serialization
     *
     */
    public LineaHorario() {
    }

    /**
     *
     * @param descripcion
     * @param tiempo
     * @param id
     * @param destino
     */
    public LineaHorario(String id, String descripcion, String tiempo, String destino) {
        super();
        this.id = id;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.destino = destino;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LineaHorario withId(String id) {
        this.id = id;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LineaHorario withDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public LineaHorario withTiempo(String tiempo) {
        this.tiempo = tiempo;
        return this;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LineaHorario withDestino(String destino) {
        this.destino = destino;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.descripcion == null)? 0 :this.descripcion.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.destino == null)? 0 :this.destino.hashCode()));
        result = ((result* 31)+((this.tiempo == null)? 0 :this.tiempo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LineaHorario) == false) {
            return false;
        }
        LineaHorario rhs = ((LineaHorario) other);
        return (((((this.descripcion == rhs.descripcion)||((this.descripcion!= null)&&this.descripcion.equals(rhs.descripcion)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.destino == rhs.destino)||((this.destino!= null)&&this.destino.equals(rhs.destino))))&&((this.tiempo == rhs.tiempo)||((this.tiempo!= null)&&this.tiempo.equals(rhs.tiempo))));
    }


}