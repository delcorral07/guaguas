package ficheros.TransportesTenerife.practicaguiada.POJO.TiempoRestante;

import java.util.ArrayList;

public class Llegadas {

    public Llegadas(){
        this.entradas = new ArrayList<>();
    }

    private ArrayList<Llegada> entradas;

    public ArrayList<Llegada> getEntradas (){
        return entradas;
    }

    public void setEntradas(ArrayList<Llegada> entradas)
    {
        this.entradas = entradas;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [entradas = "+ entradas +"]";
    }
}

