package ficheros.TransportesTenerife.practicaguiada.POJO.Horario;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Horario implements Serializable
{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("parada")
    @Expose
    private ParadaHorario paradaHorario;
    @SerializedName("lineas")
    @Expose
    private List<LineaHorario> lineas = null;
    private final static long serialVersionUID = 7669674122778059441L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Horario() {
    }

    /**
     *
     * @param lineas
     * @param success
     * @param paradaHorario
     */
    public Horario(boolean success, ParadaHorario paradaHorario, List<LineaHorario> lineas) {
        super();
        this.success = success;
        this.paradaHorario = paradaHorario;
        this.lineas = lineas;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Horario withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ParadaHorario getParada() {
        return paradaHorario;
    }

    public void setParada(ParadaHorario paradaHorario) {
        this.paradaHorario = paradaHorario;
    }

    public Horario withParada(ParadaHorario paradaHorario) {
        this.paradaHorario = paradaHorario;
        return this;
    }

    public List<LineaHorario> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaHorario> lineas) {
        this.lineas = lineas;
    }

    public Horario withLineas(List<LineaHorario> lineas) {
        this.lineas = lineas;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.lineas == null)? 0 :this.lineas.hashCode()));
        result = ((result* 31)+(this.success? 1 : 0));
        result = ((result* 31)+((this.paradaHorario == null)? 0 :this.paradaHorario.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Horario) == false) {
            return false;
        }
        Horario rhs = ((Horario) other);
        return ((((this.lineas == rhs.lineas)||((this.lineas!= null)&&this.lineas.equals(rhs.lineas)))&&(this.success == rhs.success))&&((this.paradaHorario == rhs.paradaHorario)||((this.paradaHorario != null)&&this.paradaHorario.equals(rhs.paradaHorario))));
    }

}