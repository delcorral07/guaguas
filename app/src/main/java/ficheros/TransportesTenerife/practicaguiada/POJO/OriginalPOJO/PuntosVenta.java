package ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PuntosVenta implements Serializable
{

    @SerializedName("DIRECCION")
    @Expose
    private String direccion;
    @SerializedName("LATITUD")
    @Expose
    private String latitud;
    @SerializedName("LONGITUD")
    @Expose
    private String longitud;
    @SerializedName("NOMBRE_CLIENTE")
    @Expose
    private String nombreCliente;
    @SerializedName("POBLACION")
    @Expose
    private String poblacion;
    @SerializedName("PROVINCIA")
    @Expose
    private String provincia;
    @SerializedName("TELEFONO1")
    @Expose
    private String telefono1;
    @SerializedName("TELEFONO2")
    @Expose
    private String telefono2;
    private final static long serialVersionUID = 6497711098217304476L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PuntosVenta() {
    }

    /**
     *
     * @param latitud
     * @param longitud
     * @param nombreCliente
     * @param direccion
     * @param telefono1
     * @param telefono2
     * @param poblacion
     * @param provincia
     */
    public PuntosVenta(String direccion, String latitud, String longitud, String nombreCliente, String poblacion, String provincia, String telefono1, String telefono2) {
        super();
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombreCliente = nombreCliente;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public PuntosVenta withDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public PuntosVenta withLatitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public PuntosVenta withLongitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public PuntosVenta withNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        return this;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public PuntosVenta withPoblacion(String poblacion) {
        this.poblacion = poblacion;
        return this;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public PuntosVenta withProvincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public PuntosVenta withTelefono1(String telefono1) {
        this.telefono1 = telefono1;
        return this;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public PuntosVenta withTelefono2(String telefono2) {
        this.telefono2 = telefono2;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.latitud == null)? 0 :this.latitud.hashCode()));
        result = ((result* 31)+((this.longitud == null)? 0 :this.longitud.hashCode()));
        result = ((result* 31)+((this.nombreCliente == null)? 0 :this.nombreCliente.hashCode()));
        result = ((result* 31)+((this.direccion == null)? 0 :this.direccion.hashCode()));
        result = ((result* 31)+((this.telefono1 == null)? 0 :this.telefono1 .hashCode()));
        result = ((result* 31)+((this.telefono2 == null)? 0 :this.telefono2 .hashCode()));
        result = ((result* 31)+((this.poblacion == null)? 0 :this.poblacion.hashCode()));
        result = ((result* 31)+((this.provincia == null)? 0 :this.provincia.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PuntosVenta) == false) {
            return false;
        }
        PuntosVenta rhs = ((PuntosVenta) other);
        return (((((((((this.latitud == rhs.latitud)||((this.latitud!= null)&&this.latitud.equals(rhs.latitud)))&&((this.longitud == rhs.longitud)||((this.longitud!= null)&&this.longitud.equals(rhs.longitud))))&&((this.nombreCliente == rhs.nombreCliente)||((this.nombreCliente!= null)&&this.nombreCliente.equals(rhs.nombreCliente))))&&((this.direccion == rhs.direccion)||((this.direccion!= null)&&this.direccion.equals(rhs.direccion))))&&((this.telefono1 == rhs.telefono1)||((this.telefono1 != null)&&this.telefono1 .equals(rhs.telefono1))))&&((this.telefono2 == rhs.telefono2)||((this.telefono2 != null)&&this.telefono2 .equals(rhs.telefono2))))&&((this.poblacion == rhs.poblacion)||((this.poblacion!= null)&&this.poblacion.equals(rhs.poblacion))))&&((this.provincia == rhs.provincia)||((this.provincia!= null)&&this.provincia.equals(rhs.provincia))));
    }

}