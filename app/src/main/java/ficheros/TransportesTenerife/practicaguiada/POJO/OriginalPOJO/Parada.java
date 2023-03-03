package ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Parada implements Serializable, Comparable<String>{

    @SerializedName("stop_id")
    @Expose
    private String stop_id;
    @SerializedName("stop_name")
    @Expose
    private String stop_name;
    @SerializedName("stop_lat")
    @Expose
    private double stop_lat;
    @SerializedName("stop_lon")
    @Expose
    private double stop_lon;
    @SerializedName("stop_url")
    @Expose
    private String stop_url;
    private final static long serialVersionUID = -4343595744049060061L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Parada() {
    }

    public Parada(String stop_id, String stop_name){
        this.stop_id = stop_id;
        this.stop_name = stop_name;
    }

    public Parada(String stop_id){
        this.stop_id = stop_id;
    }

    /**
     *
     * @param stop_id
     * @param stop_url
     * @param stop_lon
     * @param stop_name
     * @param stop_lat
     */
    public Parada(String stop_id, String stop_name, double stop_lat, double stop_lon, String stop_url) {
        super();
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.stop_url = stop_url;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public Parada withNumero(String numero) {
        this.stop_id = numero;
        return this;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public Parada withNombre(String nombre) {
        this.stop_name = nombre;
        return this;
    }

    public double getStop_lat() {
        return stop_lat;
    }

    public void setStop_lat(double stop_lat) {
        this.stop_lat = stop_lat;
    }

    public Parada withLat(double lat) {
        this.stop_lat = lat;
        return this;
    }

    public double getStop_lon() {
        return stop_lon;
    }

    public void setStop_lon(double stop_lon) {
        this.stop_lon = stop_lon;
    }

    public Parada withLang(double stop_lang) {
        this.stop_lon = stop_lang;
        return this;
    }

    public String getStop_url() {
        return stop_url;
    }

    public void setStop_url(String stop_url) {
        this.stop_url = stop_url;
    }

    public Parada withEnlace(String enlace) {
        this.stop_url = enlace;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.stop_url == null)? 0 :this.stop_url.hashCode()));
        result = ((result* 31)+((this.stop_id == null)? 0 :this.stop_id.hashCode()));
        result = ((result* 31)+((int)(Double.doubleToLongBits(this.stop_lon)^(Double.doubleToLongBits(this.stop_lon)>>> 32))));
        result = ((result* 31)+((this.stop_name == null)? 0 :this.stop_name.hashCode()));
        result = ((result* 31)+((int)(Double.doubleToLongBits(this.stop_lat)^(Double.doubleToLongBits(this.stop_lat)>>> 32))));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Parada) == false) {
            return false;
        }
        Parada rhs = ((Parada) other);
        return ((((((this.stop_url == rhs.stop_url)||((this.stop_url != null)&&this.stop_url.equals(rhs.stop_url)))&&((this.stop_id == rhs.stop_id)||((this.stop_id != null)&&this.stop_id.equals(rhs.stop_id))))&&(Double.doubleToLongBits(this.stop_lon) == Double.doubleToLongBits(rhs.stop_lon)))&&((this.stop_name == rhs.stop_name)||((this.stop_name != null)&&this.stop_name.equals(rhs.stop_name))))&&(Double.doubleToLongBits(this.stop_lat) == Double.doubleToLongBits(rhs.stop_lat)));
    }

    @Override
    public int compareTo(String o) {
        if(this.stop_id.equals(o)){
            return 0;
        }else{
            return -1;
        }
    }
}