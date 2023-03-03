package ficheros.TransportesTenerife.practicaguiada.POJO.TiempoRestante;


public class Llegada
{
    private String denominacion;

    private String hora;

    private String destinoLinea;

    private String idTrayecto;

    private String minutosParaLlegar;

    private String codigoParada;

    private String linea;

    public String getDenominacion ()
    {
        return denominacion;
    }

    public void setDenominacion (String denominacion)
    {
        this.denominacion = denominacion;
    }

    public String getHora ()
    {
        return hora;
    }

    public void setHora (String hora)
    {
        this.hora = hora;
    }

    public String getDestinoLinea ()
    {
        return destinoLinea;
    }

    public void setDestinoLinea (String destinoLinea)
    {
        this.destinoLinea = destinoLinea;
    }

    public String getIdTrayecto ()
    {
        return idTrayecto;
    }

    public void setIdTrayecto (String idTrayecto)
    {
        this.idTrayecto = idTrayecto;
    }

    public String getMinutosParaLlegar ()
    {
        return minutosParaLlegar;
    }

    public void setMinutosParaLlegar (String minutosParaLlegar)
    {
        this.minutosParaLlegar = minutosParaLlegar;
    }

    public String getCodigoParada ()
    {
        return codigoParada;
    }

    public void setCodigoParada (String codigoParada)
    {
        this.codigoParada = codigoParada;
    }

    public String getLinea ()
    {
        return linea;
    }

    public void setLinea (String linea)
    {
        this.linea = linea;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [denominacion = "+denominacion+", hora = "+hora+", destinoLinea = "+destinoLinea+", idTrayecto = "+idTrayecto+", minutosParaLlegar = "+minutosParaLlegar+", codigoParada = "+codigoParada+", linea = "+linea+"]";
    }
}
