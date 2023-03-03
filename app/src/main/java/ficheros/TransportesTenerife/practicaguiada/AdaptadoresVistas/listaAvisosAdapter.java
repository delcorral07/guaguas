package ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ficheros.TransportesTenerife.practicaguiada.POJO.Avisos.Avisos;
import com.example.practicaguiada.R;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Navegador;

import java.util.List;

public class listaAvisosAdapter extends RecyclerView.Adapter<listaAvisosAdapter.avisosViewHolder>{

    List<Avisos> listaAvisos;

    public listaAvisosAdapter(List<Avisos> listaAvisos){
        this.listaAvisos = listaAvisos;
    }

    @NonNull
    @Override
    public listaAvisosAdapter.avisosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseno_ultimos_avisos,
                null,false);
        return new avisosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull listaAvisosAdapter.avisosViewHolder holder, int position) {
        holder.tituloNoticia.setText(listaAvisos.get(position).getTitulo());
        holder.textoNuevasFechas.setText(listaAvisos.get(position).getFechas());
    }

    @Override
    public int getItemCount() {
        return listaAvisos.size();
    }

    public class avisosViewHolder extends RecyclerView.ViewHolder {

        TextView tituloNoticia, textoNuevasFechas;
        Button botonAbrirNoticias;


        public avisosViewHolder(@NonNull View itemView) {
            super(itemView);


            tituloNoticia = itemView.findViewById(R.id.tvTituloNoticia);
            textoNuevasFechas = itemView.findViewById(R.id.tvTextoFechas);
            botonAbrirNoticias = itemView.findViewById(R.id.botonAbrirNoticias);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirNoticias(v);
                }
            });
            botonAbrirNoticias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirNoticias(v);
                }
            });


        }

        public void abrirNoticias(View itemView){
            String url = listaAvisos.get(getLayoutPosition()).getUrl();
            Intent nuevoIntent = new Intent(itemView.getContext(), Navegador.class);
            nuevoIntent.putExtra("link",url);
            itemView.getContext().startActivity(nuevoIntent);
        }
    }
}
