package ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas.listaEstacionariosAdapter;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.EstacionarioBusqueda;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;

import com.example.practicaguiada.R;

import org.apache.poi.sl.draw.geom.Context;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListado extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText textoBusqueda;
    private RecyclerView listadoBusqueda;
    private Button botonCerrarListado;
    private Context contexto;
    private String mParam1;
    private String mParam2;

    public FragmentListado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentListado.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListado newInstance(String param1, String param2) {
        FragmentListado fragment = new FragmentListado();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listado, container, false);
        listadoBusqueda = v.findViewById(R.id.listaNoticias);
        this.textoBusqueda = v.findViewById(R.id.editBuscador);
        botonCerrarListado = v.findViewById(R.id.botonCerrarListado);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Activity activiy = (Activity) v.getContext();
                activiy.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        while(!MapsActivity.mapaDatosCargados){}
                        listadoBusqueda.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        listaEstacionariosAdapter adapter = new listaEstacionariosAdapter(MapsActivity.listaEstacionarios);
                        listadoBusqueda.setAdapter(adapter);
                    }
                });
            }
        }).start();

        eventos();
        return v;
    }



    private void eventos(){
        textoBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override

            public void afterTextChanged(Editable s) {
                texto(s.toString());
            }
        });
        textoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textoBusqueda.setText("");
            }
        });
        botonCerrarListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.comportamientoDeCerrado();
            }
        });
    }
    private void texto(String s){
        ArrayList<EstacionarioBusqueda> nuevaLista = new ArrayList<EstacionarioBusqueda>();
        try{
            for(EstacionarioBusqueda es : MapsActivity.listaEstacionarios){
                if(!es.isEsGuagua()){
                    if(es.getCodigo().toLowerCase().contains(s.toLowerCase()) ||
                            es.getNombre().toLowerCase().contains(s.toLowerCase())){
                        nuevaLista.add(es);
                        listaEstacionariosAdapter adapter = new listaEstacionariosAdapter(nuevaLista);
                        listadoBusqueda.setAdapter(adapter);
                    }
                }else{
                    if(es.getCodigo().toLowerCase().contains(s.toLowerCase())){
                        nuevaLista.add(es);
                        listaEstacionariosAdapter adapter = new listaEstacionariosAdapter(nuevaLista);
                        listadoBusqueda.setAdapter(adapter);
                    }
                }
            }

        }catch (NullPointerException e){}

    }
}