package ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas.listaEstacionariosAdapter;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.EstacionarioBusqueda;
import ficheros.TransportesTenerife.practicaguiada.SQL.Consultas;
import ficheros.TransportesTenerife.practicaguiada.SQL.SQL;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;
import com.example.practicaguiada.R;

import java.util.ArrayList;


public class FragmentFavoritos extends Fragment {


    public static ArrayList<EstacionarioBusqueda> listaGuardados;
    public static RecyclerView listaFavoritos;
    private Button botonCerrarFavoritos;

    public FragmentFavoritos() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_favoritos, container, false);
        listaFavoritos = v.findViewById(R.id.listaNoticias);
        listaFavoritos.setLayoutManager(new LinearLayoutManager(v.getContext()));
        botonCerrarFavoritos = v.findViewById(R.id.botonCerrarFavoritos);
        botonCerrarFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.comportamientoDeCerrado();
            }
        });
        recuperarFavoritos(v.getContext());
        return v;
    }


    public static void recuperarFavoritos(Context contexto){
        listaGuardados = Consultas.recogerEstacionariosUser( new SQL(contexto));
        listaEstacionariosAdapter adapter = new listaEstacionariosAdapter(listaGuardados);
        listaFavoritos.setAdapter(adapter);
    }

}