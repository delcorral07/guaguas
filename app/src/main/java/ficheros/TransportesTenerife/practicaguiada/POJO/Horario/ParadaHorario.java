package ficheros.TransportesTenerife.practicaguiada.POJO.Horario;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParadaHorario implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("descripcion_larga")
    @Expose
    private String descripcionLarga;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    private final static long serialVersionUID = -549555045612613416L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ParadaHorario() {
    }

    /**
     *
     * @param descripcion
     * @param descripcionLarga
     * @param lng
     * @param id
     * @param lat
     */
    public ParadaHorario(String id, String descripcion, String descripcionLarga, String lat, String lng) {
        super();
        this.id = id;
        this.descripcion = descripcion;
        this.descripcionLarga = descripcionLarga;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParadaHorario withId(String id) {
        this.id = id;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ParadaHorario withDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public ParadaHorario withDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public ParadaHorario withLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public ParadaHorario withLng(String lng) {
        this.lng = lng;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.descripcion == null)? 0 :this.descripcion.hashCode()));
        result = ((result* 31)+((this.descripcionLarga == null)? 0 :this.descripcionLarga.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.lng == null)? 0 :this.lng.hashCode()));
        result = ((result* 31)+((this.lat == null)? 0 :this.lat.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ParadaHorario) == false) {
            return false;
        }
        ParadaHorario rhs = ((ParadaHorario) other);
        return ((((((this.descripcion == rhs.descripcion)||((this.descripcion!= null)&&this.descripcion.equals(rhs.descripcion)))&&((this.descripcionLarga == rhs.descripcionLarga)||((this.descripcionLarga!= null)&&this.descripcionLarga.equals(rhs.descripcionLarga))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.lng == rhs.lng)||((this.lng!= null)&&this.lng.equals(rhs.lng))))&&((this.lat == rhs.lat)||((this.lat!= null)&&this.lat.equals(rhs.lat))));
    }

}