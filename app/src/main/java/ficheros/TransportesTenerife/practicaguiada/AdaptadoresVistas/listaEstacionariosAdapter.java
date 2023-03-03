package ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas;

import static ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments.FragmentFavoritos.recuperarFavoritos;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.fragmentFavoritosActivo;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.fragmentLista;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.hideKeyboard;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.EstacionarioBusqueda;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;
import com.example.practicaguiada.R;
import ficheros.TransportesTenerife.practicaguiada.SQL.Consultas;
import ficheros.TransportesTenerife.practicaguiada.SQL.SQL;

import java.util.List;

public class listaEstacionariosAdapter extends RecyclerView.Adapter<listaEstacionariosAdapter.EstacionariosViewHolder> {

    List<EstacionarioBusqueda> listaEstacionarios;
    private final String TEXTO_EST_VACIO = "Aquí aparecerán tus paradas/guaguas favoritas";

    //A este constructor se le pasa la lista de Objetos a representar
    public listaEstacionariosAdapter(List<EstacionarioBusqueda> listaEstacionarios){
        if(listaEstacionarios.size() == 0){
            EstacionarioBusqueda nEstac = new EstacionarioBusqueda();
            nEstac.setTitulo(TEXTO_EST_VACIO);
            nEstac.setCodigo("");
            nEstac.setNombre("");
            nEstac.setEsGuagua(false);
            listaEstacionarios.add(nEstac);
        }
        this.listaEstacionarios = listaEstacionarios;
    }

    @NonNull
    @Override //En este método se establece el diseño para los elementos
    public EstacionariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseno_lista_estacionarios,
                null,false);
        return new EstacionariosViewHolder(v);
    }

    @Override //Sustituye los Componentes por la información de la lista de objetos
    public void onBindViewHolder(@NonNull EstacionariosViewHolder holder, int position) {
        if(listaEstacionarios.get(0).getTitulo().equals(TEXTO_EST_VACIO)){
            holder.textoListaTipo.setTextSize(13);
        }
        holder.textoListaTipo.setText(listaEstacionarios.get(position).getTitulo());
        holder.textoListaCodigo.setText(listaEstacionarios.get(position).getCodigo());
        holder.textoListaDescripcion.setText(tiempoParadasAdapter.formatearTexto(listaEstacionarios.get(position).getNombre()));
    }

    @Override //Devuelve el tamaño de la lista de elementos
    public int getItemCount() {
        return listaEstacionarios.size();
    }


    public class EstacionariosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textoListaTipo, textoListaCodigo, textoListaDescripcion;
        ConstraintLayout layoutContenedor;

        public EstacionariosViewHolder(@NonNull View itemView) {
            super(itemView);
            if(!listaEstacionarios.get(0).getTitulo().equals(TEXTO_EST_VACIO)){
                itemView.setOnClickListener(this);
                if(fragmentFavoritosActivo) {
                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            dialogoAlerta();
                            return true;
                        }
                    });
                }

            }


            textoListaTipo = itemView.findViewById(R.id.textoListaTipo);
            textoListaCodigo = itemView.findViewById(R.id.textoListaCodigo);
            textoListaDescripcion = itemView.findViewById(R.id.textoListaDescripcion);
            layoutContenedor = itemView.findViewById(R.id.layoutContenedor);
        }


        public void abrirIntentParada(String cod, String nombre){

            MapsActivity.abrirFragmentParada(cod,nombre);
        }

        public void abrirIntentGuagua(String cod, String nombre){
            MapsActivity.abrirFragmentGuagua(cod,nombre);
        }

        @Override
        public void onClick(View v) {
            int n = getLayoutPosition();
            EstacionarioBusqueda es = listaEstacionarios.get(n);
            if(es.isEsGuagua()){
                abrirIntentGuagua(es.getCodigo(),es.getNombre());
            }else{
                abrirIntentParada(es.getCodigo(),es.getNombre());
            }
            hideKeyboard(fragmentLista.getActivity());
        }


        public void dialogoAlerta(){
            int n = getLayoutPosition();
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Se va a eliminar la "+listaEstacionarios.get(n).getTitulo()
            +" "+listaEstacionarios.get(n).getCodigo());
            builder.setMessage("¿Quieres eliminar de favoritos?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(listaEstacionarios.get(n).isEsGuagua()){
                        if(Consultas.eliminarGuagua(listaEstacionarios.get(n).getCodigo(),
                                new SQL(itemView.getContext()))){
                            Toast.makeText(itemView.getContext(), "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if(Consultas.eliminarParada(listaEstacionarios.get(n).getCodigo(),
                                new SQL(itemView.getContext()))){
                            Toast.makeText(itemView.getContext(), "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                    recuperarFavoritos(itemView.getContext());
                }
            });
            builder.setNegativeButton("No", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }


}
