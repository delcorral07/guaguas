package ficheros.TransportesTenerife.practicaguiada.POJO.Avisos;

public class Avisos {

    private String titulo;
    private String fechas;
    private String url;

    public Avisos(){}

    public Avisos(String fechas, String titulo, String url){
        this.url = url;
        this.titulo = titulo;
        this.fechas = fechas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    @Override
    public String toString() {
        return "Avisos{" +
                "titulo='" + titulo + '\'' +
                ", fechas='" + fechas + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
