package ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Itinerario implements Serializable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("paradas")
    @Expose
    private List<Puntos> paradasOnline = null;
    private final static long serialVersionUID = -2625000970113435031L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Itinerario() {
    }

    /**
     *
     * @param success
     * @param paradasOnline
     */
    public Itinerario(boolean success, List<Puntos> paradasOnline) {
        super();
        this.success = success;
        this.paradasOnline = paradasOnline;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Itinerario withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<Puntos> getParadasOnline() {
        return paradasOnline;
    }

    public void setParadasOnline(List<Puntos> paradasOnline) {
        this.paradasOnline = paradasOnline;
    }

    public Itinerario withParadasOnline(List<Puntos> paradasOnline) {
        this.paradasOnline = paradasOnline;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+(this.success? 1 : 0));
        result = ((result* 31)+((this.paradasOnline == null)? 0 :this.paradasOnline.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Itinerario) == false) {
            return false;
        }
        Itinerario rhs = ((Itinerario) other);
        return ((this.success == rhs.success)&&((this.paradasOnline == rhs.paradasOnline)||((this.paradasOnline!= null)&&this.paradasOnline.equals(rhs.paradasOnline))));
    }

}



