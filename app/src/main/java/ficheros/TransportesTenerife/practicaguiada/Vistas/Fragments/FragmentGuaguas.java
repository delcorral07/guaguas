package ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments;

import static ficheros.TransportesTenerife.practicaguiada.SQL.Consultas.insertarGuagua;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
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

import ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas.listaParadasAdapter;
import ficheros.TransportesTenerife.practicaguiada.Animaciones.AnimacionesMain;
import ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido.Itinerario;
import ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido.Puntos;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;
import ficheros.TransportesTenerife.practicaguiada.Data.DatosOnline;
import com.example.practicaguiada.R;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Maps;

import ficheros.TransportesTenerife.practicaguiada.SQL.Consultas;
import ficheros.TransportesTenerife.practicaguiada.SQL.SQL;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Navegador;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGuaguas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGuaguas extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private String numeroGuagua;
    private String descripcion;
    private TextView textoDescripcionLinea, textoTituloLinea;
    private Button botonSentido,botonMapa, botonHorario, botonCerrarGuaguas;
    private RecyclerView listaParadas;
    private ToggleButton botonFavorito;
    public final String URL = "https://movil.titsa.com/horariosmovil.php?IdLinea=";
    private boolean esUnSentido = false;
    private ProgressDialog dialog;
    private List<Puntos> listadoElegido;
    private boolean itinerarioCargado = false;
    private ProgressBar progreso;
    private String[] descripcionCompleta;
    private boolean primeraCarga = true;


    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentGuaguas() {
        // Required empty public constructor
    }

    public FragmentGuaguas(String codGuagua, String descripcion) {
        this.numeroGuagua = codGuagua;
        this.descripcion = descripcion;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGuaguas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGuaguas newInstance(String param1, String param2) {
        FragmentGuaguas fragment = new FragmentGuaguas();
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

        View v =  inflater.inflate(R.layout.fragment_guaguas, container, false);
        initComponents(v);
        textoDescripcionLinea.setText("Cargando recorrido...");
        eventos(v);
        comprobarGuagua(v);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MapsActivity.fragmentGuagua.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            if(MapsActivity.comprobarConexion(MapsActivity.botonQR.getContext())){
                                try{
                                    MapsActivity.puntosGuaguaIda = DatosOnline.itinerarioIda(numeroGuagua,0).getParadasOnline();
                                    MapsActivity.puntosGuaguaVenida = DatosOnline.itinerarioVenida(numeroGuagua,0).getParadasOnline();
                                    comprobarItinerario(v);
                                    progreso.setVisibility(View.GONE);
                                    listaParadas.setVisibility(View.VISIBLE);
                                    MapsActivity.guaguaAnterior = numeroGuagua;
                                    botonMapa.setEnabled(true);
                                }catch (NullPointerException ex){
                                    AlertDialog.Builder mensaje = new AlertDialog.Builder(MapsActivity.fragmentGuagua.getContext());
                                    mensaje.setTitle("No se ha podido recuperar la informacion");
                                    mensaje.setMessage("Parece que la información no está disponible. Vuelve a intentarlo en unos segundos");
                                    MapsActivity.guaguaAnterior = String.valueOf(Math.random()*500+1);
                                    mensaje.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MapsActivity.abrirFragmentGuagua(numeroGuagua,descripcion);
                                        }
                                    });
                                    mensaje.setNegativeButton("Cerrar",null);
                                    mensaje.create().show();
                                }
                            }else{
                                AlertDialog.Builder mensaje = new AlertDialog.Builder(MapsActivity.fragmentGuagua.getContext());
                                mensaje.setTitle("No hay conexión a internet");
                                mensaje.setMessage("Parece que no hay conexión a internet. Vuelve a intentarlo en unos segundos");
                                mensaje.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MapsActivity.abrirFragmentGuagua(numeroGuagua,descripcion);
                                    }
                                });
                                mensaje.setNegativeButton("Cerrar",null);
                                mensaje.create().show();
                            }
                    }
                });
            }
        }).start();

        return v;
    }



    public void initComponents(View v){
        textoTituloLinea = v.findViewById(R.id.textoTituloLinea);
        textoDescripcionLinea = v.findViewById(R.id.textoDescripcionLinea);
        listaParadas = v.findViewById(R.id.listaParadasGuagua);
        botonSentido = v.findViewById(R.id.botonIda);
        botonMapa = v.findViewById(R.id.botonMostrarMapa);
        botonHorario = v.findViewById(R.id.botonHorario);
        botonFavorito = v.findViewById(R.id.botonGuaguaFavorita);
        botonCerrarGuaguas = v.findViewById(R.id.botonCerrarGuaguas);
        botonMapa.setEnabled(false);
        progreso = v.findViewById(R.id.progreso);
        textoTituloLinea.setText(numeroGuagua);
    }


    public void sentidoTitulo(boolean esIda){
        if(descripcion.contains("->")){
            descripcionCompleta = descripcion.split("->");
        }else{
            descripcionCompleta = descripcion.split("-");
        }
        String descripcionTratada = "";
        if(esIda){
            for(int i = 0 ; i < descripcionCompleta.length ; i ++){
                descripcionCompleta[i] = descripcionCompleta[i].replace("-"," - ");
                if(i== descripcionCompleta.length-1){
                    descripcionTratada+=descripcionCompleta[i].trim();
                }else{
                    descripcionTratada+=descripcionCompleta[i].trim()+" -> ";
                }
            }
        }else{
            for(int i = descripcionCompleta.length-1 ; i >= 0 ; i--){
                descripcionCompleta[i] = descripcionCompleta[i].replace("-"," - ");
                if(i==0){
                    descripcionTratada+=descripcionCompleta[i].trim();
                }else{
                    descripcionTratada+=descripcionCompleta[i].trim()+" -> ";
                }
            }
        }
        textoDescripcionLinea.setText(descripcionTratada);
    }




    public void eventos(View v){
        botonSentido.setOnClickListener(this);
        botonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.colocarMarcadoresLinea(listadoElegido);
                MapsActivity.puntosList = listadoElegido;
                MapsActivity.layoutFragmentsActivo =  AnimacionesMain.animarLayoutFragments(MapsActivity.layouFragments, MapsActivity.layoutOpciones,
                        MapsActivity.layoutInferior, MapsActivity.layoutFragmentsActivo, v.findViewById(R.id.layoutFragments));
                if(!MapsActivity.botonLimpiarActivo) {
                    MapsActivity.botonLimpiarActivo = AnimacionesMain.animarBotonLimpiar(MapsActivity.layoutBotonLimpiar,
                            MapsActivity.botonLimpiarActivo);
                }

            }
        });
        botonHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Navegador.class);
                i.putExtra("link",URL+numeroGuagua);
                startActivity(i);
            }
        });
        botonFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarGuagua(v);
            }
        });
        listaParadas.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
        botonCerrarGuaguas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.comportamientoDeCerrado();
            }
        });
    }

    @Override
    public void onClick(View v) {
        MapsActivity.itinerarioIda = !MapsActivity.itinerarioIda;
        sentidoTitulo(MapsActivity.itinerarioIda);

        comprobarItinerario(v);
    }

    public void comprobarItinerario(View v) {
        listaParadas.setLayoutManager(new LinearLayoutManager(v.getContext()));
        if(MapsActivity.puntosGuaguaIda == null || MapsActivity.puntosGuaguaVenida == null){
            esUnSentido = true;
        }
        if(esUnSentido){
            if(!primeraCarga){
                Toast.makeText(MapsActivity.layouFragments.getContext(), "Esta línea es de un solo sentido", Toast.LENGTH_SHORT).show();
            }
            try{
                listaParadasAdapter adapter = new listaParadasAdapter(MapsActivity.puntosGuaguaIda);
                listadoElegido = MapsActivity.puntosGuaguaIda;
                listaParadas.setAdapter(adapter);
            }catch (NullPointerException ex){
                listaParadasAdapter adapter = new listaParadasAdapter(MapsActivity.puntosGuaguaVenida);
                listadoElegido = MapsActivity.puntosGuaguaVenida;
                listaParadas.setAdapter(adapter);
            }
            textoDescripcionLinea.setText(descripcion);
        }else{
            if (MapsActivity.itinerarioIda) {
                listaParadasAdapter adapter = new listaParadasAdapter(MapsActivity.puntosGuaguaIda);
                listadoElegido = MapsActivity.puntosGuaguaIda;
                listaParadas.setAdapter(adapter);
            }else{
                listaParadasAdapter adapter = new listaParadasAdapter(MapsActivity.puntosGuaguaVenida);
                listadoElegido = MapsActivity.puntosGuaguaVenida;
                listaParadas.setAdapter(adapter);
            }
            sentidoTitulo(MapsActivity.itinerarioIda);
        }
        primeraCarga = false;
    }

    public void comprobarGuagua(View v){
        boolean guardada = Consultas.comprobarGuagua(numeroGuagua,descripcion, new SQL(v.getContext()));
        if(guardada){
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradafav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonFavorito.setText(content);
            botonFavorito.setTextOn(content);
            botonFavorito.setTextOff(content);
        }else{
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradanofav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonFavorito.setText(content);
            botonFavorito.setTextOn(content);
            botonFavorito.setTextOff(content);
        }
    }

    public void cambiarSigno(boolean modo, View v){
        if(modo){
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradafav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonFavorito.setText(content);
            botonFavorito.setTextOn(content);
            botonFavorito.setTextOff(content);
        }else{
            ImageSpan imageSpan = new ImageSpan(v.getContext(), R.drawable.paradanofav);
            SpannableString content = new SpannableString("X");
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            botonFavorito.setText(content);
            botonFavorito.setTextOn(content);
            botonFavorito.setTextOff(content);
        }
    }


    public void guardarGuagua(View v){
        if(!Consultas.comprobarGuagua(numeroGuagua,descripcion, new SQL(v.getContext()))){
            if(insertarGuagua(numeroGuagua,descripcion, new SQL(v.getContext()))){
                Toast.makeText(v.getContext(), "Se ha guardado la línea "+numeroGuagua+" como favorita",
                        Toast.LENGTH_SHORT).show();
                cambiarSigno(true,v);
            }
        }else{
            if(Consultas.eliminarGuagua(numeroGuagua, new SQL(v.getContext()))){
                Toast.makeText(v.getContext(), "Se ha eliminado la línea "+numeroGuagua+" de favoritos", Toast.LENGTH_SHORT).show();
                cambiarSigno(false,v);
            }
        }
    }
}