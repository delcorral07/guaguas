package ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ficheros.TransportesTenerife.practicaguiada.POJO.TiempoRestante.Llegada;
import com.example.practicaguiada.R;

import java.util.ArrayList;
import java.util.List;


public class tiempoParadasAdapter extends RecyclerView.Adapter<tiempoParadasAdapter.LineaHorarioViewHolder> {

    private final String GAUAGUA_VACIA = "Por ahora no pasa ninguna guagua por aquí";

    /*
    Este constructor lo generamos nosotros siempre
     */
    private final List<Llegada> listaHorarios;

    public tiempoParadasAdapter(ArrayList<Llegada> listaHorarios){
        if(listaHorarios.size() == 0){
            Llegada nuevaLlegada = new Llegada();
            nuevaLlegada.setLinea(GAUAGUA_VACIA);
            nuevaLlegada.setMinutosParaLlegar("");
            nuevaLlegada.setDenominacion("");
            nuevaLlegada.setDestinoLinea("");
            listaHorarios.add(nuevaLlegada);
        }
        this.listaHorarios = listaHorarios;
    }
    //Esta clase nos ayuda a establecer el diseño de cada uno de los elementos e la lsita
    @NonNull
    @Override
    public LineaHorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseno_lista_horario,null,false);
        return new LineaHorarioViewHolder(v);
    }

    /*
    En esta clase asignamos el valor a cada uno de los elementos del layout de la clase
     */
    @Override
    public void onBindViewHolder(@NonNull LineaHorarioViewHolder holder, int position) {
        if(listaHorarios.get(0).getLinea().equals(GAUAGUA_VACIA)){
            holder.textoTextoLinea.setText("");
            holder.textoPieMinutosLinea.setText("");
            holder.textoDescripcion.setText("");
            holder.textoNumero.setText("");
            holder.textoMinutos.setText("");
            holder.textoDescripcion.setText("");
            holder.textoTextoLinea.setTextSize(13);
            holder.textoTextoLinea.setText(GAUAGUA_VACIA);

        }else{
            holder.textoNumero.setText(listaHorarios.get(position).getLinea());
            holder.textoMinutos.setText(listaHorarios.get(position).getMinutosParaLlegar());
            holder.textoDescripcion.setText(formatearTexto(listaHorarios.get(position).getDenominacion())+" -> "+listaHorarios.get(position).getDestinoLinea());
        }

    }

    public static String formatearTexto(String descripcion){
        String res = descripcion.trim();
        if(res.contains("&Ntilde;")){
            res = res.replace("&Ntilde;","Ñ");
        }
        if(res.contains("&aacute;")){
            res = res.replace("&aacute;","á");
        }
        if(res.contains("&eacute;")){
            res = res.replace("&eacute;","é");
        }
        if(res.contains("&oacute;")){
            res = res.replace("&oacute;","ó");
        }
        if(res.contains("&uacute;")){
            res = res.replace("&uacute;","ú");
        }
        if(res.contains("&iacute;")){
            res = res.replace("&iacute;","í");
        }
        if(res.contains("&Aacute;")){
            res = res.replace("&Aacute;","Á");
        }
        if(res.contains("&Eacute;")){
            res = res.replace("&Eacute;","É");
        }
        if(res.contains("&Iacute;")){
            res = res.replace("&Iacute;","Í");
        }
        if(res.contains("&Oacute;")){
            res = res.replace("&Oacute;","Ó");
        }
        if(res.contains("&Uacute;")){
            res = res.replace("&Uacute;","Ú");
        }
        if(res.contains("&Uuml;")){
            res = res.replace("&Uuml;","Ü");
        }
        if(res.contains("&uuml;")){
            res = res.replace("&uuml;","ü");
        }
//        if(res.contains("Ú")){
//            res = res.replace("Ú","Ú");
//        }
//        if(res.contains("Ó")){
//            res = res.replace("Ó","");
//        }
//        if(res.contains("Í")){
//            res = res.replace("Í","i");
//        }
//        if(res.contains("É")){
//            res = res.replace("É","e");
//        }
//        if(res.contains("Á")){
//            res = res.replace("Á","a");
//        }
//        if(res.contains("á")){
//            res = res.replace("á","a");
//        }
//        if(res.contains("é")){
//            res = res.replace("é","e");
//        }
//        if(res.contains("í")){
//            res = res.replace("í","i");
//        }
//        if(res.contains("ó")){
//            res = res.replace("ó","o");
//        }
//        if(res.contains("ú")){
//            res = res.replace("ú","u");
//        }

        return res;
    }

    /*
    Aquí se muestra el tamaño de la lista
     */
    @Override
    public int getItemCount() {
        return listaHorarios.size();
    }

    /*
    Esta clase se genra de la implementación de las diferentes clases, es el que asigna valores
    a los componentes
     */
    public static class LineaHorarioViewHolder extends RecyclerView.ViewHolder {
        TextView textoNumero, textoDescripcion, textoMinutos, textoTextoLinea, textoPieMinutosLinea;
        public LineaHorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textoNumero = itemView.findViewById(R.id.textoListaCodigo);
            textoDescripcion = itemView.findViewById(R.id.textoListaDescripcion);
            textoMinutos = itemView.findViewById(R.id.textoMinutosLinea);
            textoTextoLinea = itemView.findViewById(R.id.textoListaTipo);
            textoPieMinutosLinea = itemView.findViewById(R.id.textoPieMinutosLinea);
        }
    }
}


