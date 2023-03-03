package ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas;


import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.abrirFragmentParada;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido.Puntos;
import com.example.practicaguiada.R;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;

import java.util.ArrayList;
import java.util.List;

//Lista de puntos de los recorridos de la página de tista (Ida y vuelta)
public class listaParadasAdapter extends RecyclerView.Adapter<listaParadasAdapter.ItinerarioPuntosholder> {

    private List<Puntos> listaPuntos;

    public listaParadasAdapter(List<Puntos> listaPuntos) {
        if(listaPuntos == null){
            this.listaPuntos = new ArrayList<Puntos>();
        }
        this.listaPuntos = listaPuntos;
    }

    @NonNull
    @Override
    public ItinerarioPuntosholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseno_lista_paradas,null,true);
        return new ItinerarioPuntosholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItinerarioPuntosholder holder, int position) {
        holder.numeroParada.setText(listaPuntos.get(position).getCodigo());
        holder.nombreParada.setText(new StringBuilder().append(position+1+". ")
                .append(tiempoParadasAdapter.formatearTexto(
                        listaPuntos.get(position).getNombre())).toString());
    }

    @Override
    public int getItemCount() {
        try{
            int n = listaPuntos.size();
            return n;
        }catch (NullPointerException e){}
        return 0;
    }

    public class ItinerarioPuntosholder extends RecyclerView.ViewHolder {

        TextView numeroParada, nombreParada;
        public ItinerarioPuntosholder(@NonNull View itemView) {
            super(itemView);
            this.numeroParada = itemView.findViewById(R.id.textoParadaNumero);
            this.nombreParada = itemView.findViewById(R.id.textoParadaNombre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapsActivity.paradaDesdeGuagua = true;
                    abrirFragmentParada(listaPuntos.get(getLayoutPosition()).getCodigo()
                            ,listaPuntos.get(getLayoutPosition()).getNombre());
                }
            });
        }
    }
}
