package ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas.tiempoParadasAdapter;
import ficheros.TransportesTenerife.practicaguiada.POJO.TiempoRestante.Llegada;
import ficheros.TransportesTenerife.practicaguiada.SQL.Consultas;
import ficheros.TransportesTenerife.practicaguiada.SQL.SQL;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;
import ficheros.TransportesTenerife.practicaguiada.Data.DatosOnline;
import com.example.practicaguiada.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHorarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHorarios extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COD_PARADA = "param1";
    private static final String NOMBRE_PARADA = "param2";
    private String numeroParada, nombreParada;
    public TextView textoNumeroParada, textoNombreParada;
    private Button botonCerrarHorarios;
    private ToggleButton botonGuardarParada;
    private RecyclerView listaLineas;
    ArrayList<Llegada> llegadas;
    private ProgressBar progreso;
    private SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHorarios() {
        // Required empty public constructor
    }

    public FragmentHorarios(String codParada, String nombreParada) {
        this.numeroParada = codParada;
        this.nombreParada = nombreParada;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHorarios.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHorarios newInstance(String param1, String param2) {
        FragmentHorarios fragment = new FragmentHorarios();
        Bundle args = new Bundle();
        args.putString(COD_PARADA, param1);
        args.putString(NOMBRE_PARADA, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_horarios, container, false);
        textoNumeroParada = v.findViewById(R.id.textoNumeroParada);
        textoNombreParada = v.findViewById(R.id.textoNombreParada);
        listaLineas = v.findViewById(R.id.listaLineas);;
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh);
        botonGuardarParada = v.findViewById(R.id.botonParadaFavorita);
        textoNombreParada.setText(nombreParada);
        textoNumeroParada.setText(numeroParada);
        progreso = v.findViewById(R.id.progreso1);
        botonCerrarHorarios = v.findViewById(R.id.botonCerrarHorarios);
        new Thread(new Runnable() {
            @Override
            public void run() {
                cargarDatos(v);
            }
        }).start();
        swipeRefreshLayout.bringToFront();
        botonGuardarParada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarParada(v);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                cargarDatos(v);
            }
        });
        botonCerrarHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.comportamientoDeCerrado();
            }
        });

        comprobarParada(v);
        return v;
    }

    public void cargarDatos(View v){
            MapsActivity.fragmentHorario.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        if(MapsActivity.comprobarConexion(MapsActivity.botonCapas.getContext())){
                            try{
                                llegadas = DatosOnline.tiempo(numeroParada).getEntradas();
                                System.out.println("No da null");
                                progreso.setVisibility(View.GONE);
                                listaLineas.setVisibility(View.VISIBLE);
                                listaLineas.setLayoutManager(new LinearLayoutManager(MapsActivity.fragmentHorario.getContext()));
                                tiempoParadasAdapter adaptador = new tiempoParadasAdapter(llegadas);
                                listaLineas.setAdapter(adaptador);
                            }catch(NullPointerException ex) {
                                System.out.println(ex.getMessage());
                                System.out.println(ex.getCause());
                                AlertDialog.Builder mensaje = new AlertDialog.Builder(MapsActivity.fragmentHorario.getContext());
                                mensaje.setTitle("No se ha podido recuperar la informacion");
                                mensaje.setMessage("Parece que la información no está disponible. Vuelve a intentarlo en unos segundos");
                                mensaje.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MapsActivity.abrirFragmentParada(numeroParada, nombreParada);
                                    }
                                });
                                mensaje.setNegativeButton("Cerrar", null);
                                mensaje.create().show();
                            }

                        }else{
                            AlertDialog.Builder mensaje = new AlertDialog.Builder(MapsActivity.fragmentHorario.getContext());
                            mensaje.setTitle("No hay conexión a internet");
                            mensaje.setMessage("Parece que no hay conexión a internet. Vuelve a intentarlo en unos segundos");
                            mensaje.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MapsActivity.abrirFragmentParada(numeroParada,nombreParada);
                                }
                            });
                            mensaje.setNegativeButton("Cerrar",null);
                            mensaje.create().show();
                        }
                }
            });
        }




    public void guardarParada(View v){
        if(!Consultas.comprobarParadaRegistrada(numeroParada,nombreParada, new SQL(v.getContext()))){
            if(Consultas.insertarParada(numeroParada,nombreParada, new SQL(v.getContext()))){
                Toast.makeText(v.getContext(), "Se ha guardado la parada "+numeroParada+" como favorita",
                        Toast.LENGTH_SHORT).show();
                cambiarSigno(true,v);
            }
        }else{
            if(Consultas.eliminarParada(numeroParada, new SQL(v.getContext()))){
                Toast.makeText(v.getContext(), "Se ha eliminado la parada "+numeroParada+" de favoritos", Toast.LENGTH_SHORT).show();
                cambiarSigno(false,v);
            }
        }
    }


    public void comprobarParada(View v){
        boolean guardada = Consultas.comprobarParadaRegistrada(numeroParada,nombreParada, new SQL(v.getContext()));
        if(guardada){
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradafav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonGuardarParada.setText(content);
            botonGuardarParada.setTextOn(content);
            botonGuardarParada.setTextOff(content);
        }else{
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradanofav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonGuardarParada.setText(content);
            botonGuardarParada.setTextOn(content);
            botonGuardarParada.setTextOff(content);
        }
    }

    public void cambiarSigno(boolean modo, View v){
        if(modo){
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradafav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonGuardarParada.setText(content);
            botonGuardarParada.setTextOn(content);
            botonGuardarParada.setTextOff(content);
        }else{
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradanofav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonGuardarParada.setText(content);
            botonGuardarParada.setTextOn(content);
            botonGuardarParada.setTextOff(content);
        }
    }

}