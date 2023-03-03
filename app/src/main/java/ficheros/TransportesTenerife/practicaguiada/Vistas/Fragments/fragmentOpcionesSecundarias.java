package ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments;

import static com.example.practicaguiada.R.color.textoNegroDia;

import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.botonInfoParada;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.botonVerTiempo;
import static ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity.obtenerUbicacionActual;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas.tiempoParadasAdapter;
import ficheros.TransportesTenerife.practicaguiada.Animaciones.AnimacionesMain;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.Parada;
import ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido.Puntos;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Navegador;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.PuntosVenta;
import com.example.practicaguiada.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentOpcionesSecundarias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentOpcionesSecundarias extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button botonCerrar;
    private ImageButton botonParadaCercana, botonPlanifViaje, botonNoticias, botonPuntosVenta,
    botonCambiarTema;
    private LinearLayout layoutParadaCercana, layoutPlanifViaje,layoutNoticias, layoutPuntosVenta,
    layoutCambiarTema;
    private Dialog dialogoBuscarParada, dialogoPlanificarViaje, dialogoPuntosVenta;
    private EditText txDirOrigen, txDirDestino;
    private FragmentManager managerOpciones;
    private FragmentTransaction transactionOpciones;


    //Widgets del dialog buscarParadasCercanas
    private Button btDialogoBuscarParadas, btDialogoCerrarDialogo, btDialogoPlanifViaje,
    btDialogoCerrarPlanif, btDialogoCerrarVenta, btDialogoBuscarVenta;
    private Spinner spDialogoDistanciaElegida, spMunicipioOrigen, spMunicipioDestino,
        spVentas;
    private RadioButton radioTodosPuntos, radioPuntosMunicipio, radioPuntosCerca;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentOpcionesSecundarias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentOpcionesSecundarias.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentOpcionesSecundarias newInstance(String param1, String param2) {
        fragmentOpcionesSecundarias fragment = new fragmentOpcionesSecundarias();
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
        View v = inflater.inflate(R.layout.fragment_opciones_secundarias, container, false);
        botonCerrar = v.findViewById(R.id.botonCerrar);
        layoutParadaCercana = v.findViewById(R.id.layoutParadaCercana);
        layoutPlanifViaje = v.findViewById(R.id.layoutPlanifViaje);
        botonPlanifViaje = v.findViewById(R.id.botonPlanifViaje);
        botonParadaCercana = v.findViewById(R.id.botonParadaCercana);
        botonNoticias = v.findViewById(R.id.botonNoticias);
        layoutNoticias = v.findViewById(R.id.layoutNoticias);
        botonPuntosVenta = v.findViewById(R.id.btPuntosVenta);
        layoutPuntosVenta = v.findViewById(R.id.clPuntosVenta);
        managerOpciones = MapsActivity.manager;
        inicializarDialogos(v);
        eventos();

        return v;
    }

    public void inicializarDialogos(View v){
        //Inicialiazamos los referente al diálogo de la parada más cercana
        dialogoBuscarParada = new Dialog(v.getContext());
        dialogoBuscarParada.setContentView(R.layout.dialogo_paradas_cercanas);
        dialogoBuscarParada.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btDialogoBuscarParadas = dialogoBuscarParada.findViewById(R.id.btDialogBuscarParada);
        btDialogoCerrarDialogo = dialogoBuscarParada.findViewById(R.id.btDialogoCancelarBusqueda);
        spDialogoDistanciaElegida = dialogoBuscarParada.findViewById(R.id.spDialogoDistanciaElegida);
        cambiarColorSpinner(spDialogoDistanciaElegida);

        //Inicializamos lo referente al diálogo de planificar ruta
        dialogoPlanificarViaje = new Dialog(v.getContext());
        dialogoPlanificarViaje.setContentView(R.layout.dialogo_planificador_rutas);
        dialogoPlanificarViaje.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btDialogoCerrarPlanif = dialogoPlanificarViaje.findViewById(R.id.btCerrarEstab);
        btDialogoPlanifViaje = dialogoPlanificarViaje.findViewById(R.id.btDirecEstab);
        spMunicipioDestino = dialogoPlanificarViaje.findViewById(R.id.spDialogoMunicipioDestino);
        spMunicipioOrigen = dialogoPlanificarViaje.findViewById(R.id.spDialogMunicipioOrigen);
        txDirOrigen = dialogoPlanificarViaje.findViewById(R.id.txDialogoDireccionOrigen);
        txDirDestino = dialogoPlanificarViaje.findViewById(R.id.txDialogoDireccionDestino);
        cambiarColorSpinner(spMunicipioOrigen);
        cambiarColorSpinner(spMunicipioDestino);
        dialogoVentas(v);


    }

    public void dialogoVentas(View v){
        //Inicializamos los componentes del diálogo de puntos de venta
        dialogoPuntosVenta = new Dialog(v.getContext());
        dialogoPuntosVenta.setContentView(R.layout.dialogo_puntos_venta);
        dialogoPuntosVenta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btDialogoBuscarVenta = dialogoPuntosVenta.findViewById(R.id.btDialogBuscarVenta);
        btDialogoCerrarVenta = dialogoPuntosVenta.findViewById(R.id.btDialogCancelarVenta);
        radioPuntosCerca = dialogoPuntosVenta.findViewById(R.id.rdPuntosCerca);
        radioPuntosMunicipio = dialogoPuntosVenta.findViewById(R.id.rdPuntosMunicipios);
        radioTodosPuntos = dialogoPuntosVenta.findViewById(R.id.rdTodosPuntos);
        spVentas = dialogoPuntosVenta.findViewById(R.id.spinnerDialogoVentas);
        spVentas.setEnabled(false);
        btDialogoBuscarVenta.setEnabled(false);
        radioPuntosCerca.setOnClickListener(this);
        radioPuntosMunicipio.setOnClickListener(this);
        radioTodosPuntos.setOnClickListener(this);
        btDialogoCerrarVenta.setOnClickListener(this);
        btDialogoBuscarVenta.setOnClickListener(this);
        cambiarColorSpinner(spVentas);
    }

    public void cambiarColorSpinner(Spinner sp){
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(textoNegroDia); /* if you want your item to be white */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void cargarFragmentNoticias(View v){
        FragmentNoticias fragmentNuevo = new FragmentNoticias();
        transactionOpciones = managerOpciones.beginTransaction();
        transactionOpciones.replace(R.id.layoutFragmentOpciones, fragmentNuevo);
        transactionOpciones.addToBackStack(null);
        transactionOpciones.commitAllowingStateLoss();
        MapsActivity.fragmentNoticiasActivo = true;
    }

    //==============================================================================================
    //====================== PLANIFICADOR DE VIAJES ================================================
    //==============================================================================================

    public void calcularRutaViaje(View v) {
        if (String.valueOf(spMunicipioOrigen.getSelectedItem()).equals("Selecciona un municipio") |
                String.valueOf(spMunicipioDestino.getSelectedItem()).equals("Selecciona un municipio")) {
            AlertDialog.Builder mensaje = new AlertDialog.Builder(v.getContext());
            mensaje.setTitle("Falta marcar los municipios");
            mensaje.setMessage("Tienes que marcar tanto el municipio de origen como el de destino");
            mensaje.setPositiveButton("¡Entendido!", null);
            mensaje.create().show();
        } else {
            String link = "http://maps.google.com/maps?saddr=";
            String origen ="", destino="", dirOrigen="", dirDestino="";
            if (!(txDirOrigen.getText().toString().equals(""))) {
                origen += txDirOrigen.getText().toString().trim()+",";
            }
            origen += String.valueOf(spMunicipioOrigen.getSelectedItem());

            if (!(txDirDestino.getText().toString().equals(""))) {
                destino += txDirDestino.getText().toString().trim() + ",";
            }
            destino += String.valueOf(spMunicipioDestino.getSelectedItem());
            origen+=",Tenerife,España";
            destino+=",Tenerife,España";
            link+=origen+"&daddr="+destino+"&dirflg=r";
            System.out.println(link);
            Intent intentNavegador = new Intent(v.getContext(), Navegador.class);
            intentNavegador.putExtra("link", link);
            startActivity(intentNavegador);
        }
    }

    //==============================================================================================
    //====================== PARADAS CERCANAS ======================================================
    //==============================================================================================

    public void calcularMostrarParadas(View v) {
        if (ActivityCompat.checkSelfPermission(MapsActivity.fragmentOpciones.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.fragmentOpciones.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MapsActivity.fragmentOpciones.getContext(), "No se han concedido permisos de ubicación", Toast.LENGTH_SHORT).show();
        } else {
            if (MapsActivity.isLocationEnabled(v.getContext())) {

                obtenerUbicacionActual();
                if(MapsActivity.ubicacionActual == null){
                    Toast.makeText(MapsActivity.fragmentOpciones.getContext(), "Obteniendo ubicación, inténtalo en unos segundos...", Toast.LENGTH_SHORT).show();
                }else{
                    String distancia = String.valueOf(spDialogoDistanciaElegida.getSelectedItem());
                    if (distancia.contains("Elige una distancia")) {
                        AlertDialog.Builder mensaje = new AlertDialog.Builder(v.getContext());
                        mensaje.setTitle("Falta elegir una distancia");
                        mensaje.setMessage("Elige el rango en el que quieres buscar las paradas");
                        mensaje.setPositiveButton("¡Entendido!", null);
                        mensaje.create().show();
                    } else {
                        int zl = 1;
                        int dist = Integer.parseInt(distancia.substring(0, distancia.indexOf(" ")));
                        if (dist == 1) {
                            dist = 1000;
                        } else if (dist == 2) {
                            dist = 2000;
                            zl = 2;
                        } else if (dist == 3) {
                            dist = 3000;
                            zl = 3;
                        } else if (dist == 5) {
                            dist = 5000;
                            zl = 5;
                        }
                        MapsActivity.mMap.clear();
                        Circle circle = MapsActivity.mMap.addCircle(
                                new CircleOptions()
                                        .center(MapsActivity.ubicacionActual)
                                        .radius(dist)
                                        .strokeColor(Color.BLACK)
                                        .fillColor(0x30ff0000)
                                        .strokeWidth(2));
                        MapsActivity.puntosList = new ArrayList<Puntos>();
                        for (Parada parada : MapsActivity.paradas) {
                            if (checkDistance(MapsActivity.ubicacionActual.latitude,
                                    MapsActivity.ubicacionActual.longitude, parada.getStop_lat(),
                                    parada.getStop_lon(), Float.parseFloat(String.valueOf(circle.getRadius())))) {
                                MapsActivity.mMap.addMarker(new MarkerOptions().position(new LatLng(parada.getStop_lat(), parada.getStop_lon())).title(parada.getStop_name())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntoguagua)));
                                MapsActivity.puntosList.add(new Puntos(parada.getStop_name(), parada.getStop_id(), " "));

                            }
                        }
                        dialogoBuscarParada.dismiss();
                        MapsActivity.fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(MapsActivity.layoutFragmentOpciones,
                                MapsActivity.layoutInferior, MapsActivity.layoutOpciones, true);
                        if (!MapsActivity.botonLimpiarActivo) {
                            MapsActivity.botonLimpiarActivo = AnimacionesMain.animarBotonLimpiar(MapsActivity.layoutBotonLimpiar,
                                    MapsActivity.botonLimpiarActivo);
                        }
                        switch (zl) {
                            case 1:
                                MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 14));
                                break;
                            case 2:
                                MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 13));
                                break;
                            case 3:
                                MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 12));
                                break;
                            case 5:
                                MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 11));
                                break;
                        }
                    }
                }

            } else {
                AlertDialog.Builder mensaje = new AlertDialog.Builder(v.getContext());
                mensaje.setTitle("Falta activar la ubicación");
                mensaje.setMessage("Para utilizar esta funcionalidad debes tener la ubicación activada");
                mensaje.setPositiveButton("¡Entendido!", null);
                mensaje.create().show();

            }
        }
    }

    private boolean checkDistance(double currentLocLat, double currentLocLong, double placeLocLat, double placeLocLong, float radio) {
        float[] results = new float[3];
        Location.distanceBetween(currentLocLat, currentLocLong, placeLocLat, placeLocLong, results);
        return (results[0] <= radio);
    }

    //==============================================================================================
    //====================== PUNTOS DE VENTA =======================================================
    //==============================================================================================

    private void cambiarEstadoDialogo(int n) {
        btDialogoBuscarVenta.setEnabled(true);
        if(n == radioTodosPuntos.getId() && spVentas.isEnabled()){
            spVentas.setEnabled(false);
        }else if(n == radioPuntosCerca.getId()){
            spVentas.setEnabled(true);
            spVentas.setAdapter(ArrayAdapter
                    .createFromResource(MapsActivity.layoutOpciones.getContext(),
                    R.array.distancias, android.R.layout.simple_spinner_dropdown_item));
        }else if(n == radioPuntosMunicipio.getId()){
            spVentas.setEnabled(true);
            spVentas.setAdapter(ArrayAdapter
                    .createFromResource(MapsActivity.layoutOpciones.getContext(),
                    R.array.poblaciones, android.R.layout.simple_spinner_dropdown_item));
        }

    }

    private void buscarPuntosVenta(View v) {
        MapsActivity.puntosVentaActivos = true;
        if(radioTodosPuntos.isChecked()){
            MapsActivity.colocarPuntosVenta(MapsActivity.puntosVentas);
            dialogoPuntosVenta.dismiss();
            MapsActivity.fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(MapsActivity.layoutFragmentOpciones,
                    MapsActivity.layoutInferior, MapsActivity.layoutOpciones, true);
            if (!MapsActivity.botonLimpiarActivo) {
                MapsActivity.botonLimpiarActivo = AnimacionesMain.animarBotonLimpiar(MapsActivity.layoutBotonLimpiar,
                        MapsActivity.botonLimpiarActivo);
            }
            MapsActivity.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(MapsActivity.Tenerife,9,0,0)));
        }else if(radioPuntosCerca.isChecked()){
            if (ActivityCompat.checkSelfPermission(MapsActivity.fragmentOpciones.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.fragmentOpciones.getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MapsActivity.fragmentOpciones.getContext(), "No se han concedido permisos de ubicación", Toast.LENGTH_SHORT).show();
            } else {
                if(MapsActivity.isLocationEnabled(v.getContext())){
                    String distancia = String.valueOf(spVentas.getSelectedItem());
                    if (distancia.contains("Elige una distancia")) {
                        AlertDialog.Builder mensaje = new AlertDialog.Builder(v.getContext());
                        mensaje.setTitle("Falta elegir una distancia");
                        mensaje.setMessage("Elige el rango en el que quieres buscar las paradas");
                        mensaje.setPositiveButton("¡Entendido!", null);
                        mensaje.create().show();
                    } else {
                        obtenerUbicacionActual();
                        if(MapsActivity.ubicacionActual == null){
                            Toast.makeText(MapsActivity.fragmentOpciones.getContext(), "Obteniendo ubicación, inténtalo en unos segundos...", Toast.LENGTH_SHORT).show();
                        }else{
                            calcularVentasCercanas(v, distancia);
                            dialogoPuntosVenta.dismiss();
                            MapsActivity.fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(MapsActivity.layoutFragmentOpciones,
                                    MapsActivity.layoutInferior, MapsActivity.layoutOpciones, true);
                            if (!MapsActivity.botonLimpiarActivo) {
                                MapsActivity.botonLimpiarActivo = AnimacionesMain.animarBotonLimpiar(MapsActivity.layoutBotonLimpiar,
                                        MapsActivity.botonLimpiarActivo);
                            }
                        }
                    }
                }else{
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(v.getContext());
                    mensaje.setTitle("Falta activar la ubicación");
                    mensaje.setMessage("Para utilizar esta funcionalidad debes tener la ubicación activada");
                    mensaje.setPositiveButton("¡Entendido!",null);
                    mensaje.create().show();
                }

            }
        }else{
            String municipio = String.valueOf(spVentas.getSelectedItem());
            if (municipio.contains("Selecciona un municipio")) {
                AlertDialog.Builder mensaje = new AlertDialog.Builder(v.getContext());
                mensaje.setTitle("Falta elegir un municipio");
                mensaje.setMessage("Tienes que elegir un municipio para poder buscar las ventas");
                mensaje.setPositiveButton("¡Entendido!", null);
                mensaje.create().show();
            }else {
                calcularVentasMunicipio(municipio);
                dialogoPuntosVenta.dismiss();
                MapsActivity.fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(MapsActivity.layoutFragmentOpciones,
                        MapsActivity.layoutInferior, MapsActivity.layoutOpciones, true);
                if (!MapsActivity.botonLimpiarActivo) {
                    MapsActivity.botonLimpiarActivo = AnimacionesMain.animarBotonLimpiar(MapsActivity.layoutBotonLimpiar,
                            MapsActivity.botonLimpiarActivo);
                }
            }
        }
        if (botonVerTiempo) {
            //Oculta el botón de ver parada
            botonVerTiempo = AnimacionesMain.animarBotonParadas(botonInfoParada, false);
        }
    }

    public void calcularVentasMunicipio(String municipio){
        MapsActivity.mMap.clear();
        ArrayList<PuntosVenta> puntosRef = new ArrayList<PuntosVenta>();
        for (PuntosVenta venta : MapsActivity.puntosVentas) {
            if (tiempoParadasAdapter.formatearTexto(municipio.toLowerCase()).contains(tiempoParadasAdapter.formatearTexto(venta.getPoblacion().toLowerCase()))) {
                MapsActivity.mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(venta.getLatitud()),
                        Double.parseDouble(venta.getLongitud()))).title(venta.getNombreCliente())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_ventas_mapa)));
                puntosRef.add(venta);
            }
        }
        PuntosVenta puntoCentral = puntosRef.get(puntosRef.size()/2);
        puntosRef.clear();
        MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                Double.parseDouble(puntoCentral.getLatitud()),
                Double.parseDouble(puntoCentral.getLongitud())), 10));
    }

    public void calcularVentasCercanas(View v, String distancia) {
            int zl = 1;
            int dist = Integer.parseInt(distancia.substring(0, distancia.indexOf(" ")));
            if (dist == 1) {
                dist = 1000;
            } else if (dist == 2) {
                dist = 2000;
                zl = 2;
            } else if(dist == 3){
                dist = 3000;
                zl = 3;
            }else if(dist == 5){
                dist = 5000;
                zl = 5;
            }
            MapsActivity.mMap.clear();
            Circle circle = MapsActivity.mMap.addCircle(
                    new CircleOptions()
                            .center(MapsActivity.ubicacionActual)
                            .radius(dist)
                            .strokeColor(Color.BLACK)
                            .fillColor(0x30ff0000)
                            .strokeWidth(2));

            for (PuntosVenta venta : MapsActivity.puntosVentas) {
                if (checkDistance(MapsActivity.ubicacionActual.latitude,
                        MapsActivity.ubicacionActual.longitude, Double.parseDouble(venta.getLatitud()),
                        Double.parseDouble(venta.getLongitud()), Float.parseFloat(String.valueOf(circle.getRadius())))) {
                    MapsActivity.mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(venta.getLatitud()),
                            Double.parseDouble(venta.getLongitud()))).title(venta.getNombreCliente())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_ventas_mapa)));
                }
            }
            switch (zl){
                case 1:
                    MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 14));
                    break;
                case 2:
                    MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 13));
                    break;
                case 3:
                    MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 12));
                    break;
                case 5:
                    MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.ubicacionActual, 11));
                    break;

            }

        }



    //==============================================================================================
    //====================== EVENTOS ===============================================================
    //==============================================================================================


    public void eventos() {
        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(MapsActivity.layoutFragmentOpciones,
                        MapsActivity.layoutInferior, MapsActivity.layoutOpciones, true);
            }
        });
        layoutParadaCercana.setOnClickListener(this);
        botonParadaCercana.setOnClickListener(this);
        btDialogoCerrarDialogo.setOnClickListener(this);
        btDialogoBuscarParadas.setOnClickListener(this);
        botonPlanifViaje.setOnClickListener(this);
        btDialogoCerrarPlanif.setOnClickListener(this);
        btDialogoPlanifViaje.setOnClickListener(this);
        layoutPlanifViaje.setOnClickListener(this);
        txDirOrigen.setOnClickListener(this);
        txDirDestino.setOnClickListener(this);
        botonNoticias.setOnClickListener(this);
        layoutNoticias.setOnClickListener(this);
        btDialogoCerrarVenta.setOnClickListener(this);
        btDialogoBuscarVenta.setOnClickListener(this);
        layoutPuntosVenta.setOnClickListener(this);
        botonPuntosVenta.setOnClickListener(this);
        radioPuntosCerca.setOnClickListener(this);
        radioPuntosMunicipio.setOnClickListener(this);
        radioTodosPuntos.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int n = v.getId();
        if (n == layoutParadaCercana.getId() | n == botonParadaCercana.getId()) { //Abrir el dialogo de las paradas
            dialogoBuscarParada.show();
        } else if (n == btDialogoBuscarParadas.getId()) { //Iniciar la búsqueda de las paradas
            calcularMostrarParadas(v);
        } else if (n == btDialogoCerrarDialogo.getId()) { //Cerrar el diálogo de las paradas
            dialogoBuscarParada.dismiss();
        } else if (n == botonPlanifViaje.getId() | n == layoutPlanifViaje.getId()) { //Abrir el diálogo planificador de rutas
            dialogoPlanificarViaje.show();
        } else if (btDialogoPlanifViaje.getId() == n) { //Planificar rutas
            calcularRutaViaje(v);
        } else if (btDialogoCerrarPlanif.getId() == n) { //Cerrar el diálogo planificador de rutas
            dialogoPlanificarViaje.dismiss();
        } else if (n == txDirOrigen.getId()) { //Evento para limpiar el EditText de la direccion origen
            txDirOrigen.setText("");
        } else if (n == txDirDestino.getId()) { //Evento para limpiar el EditText de la direccion destino
            txDirDestino.setText("");
        } else if (n == layoutNoticias.getId() | n == botonNoticias.getId()) { //Activar el fragment de las últimas noticias
            cargarFragmentNoticias(v);
        } else if (n == layoutPuntosVenta.getId() | n == botonPuntosVenta.getId()) { //Activar el diálogo de buscar puntos de venta
            dialogoPuntosVenta.show();
        } else if(n == radioPuntosCerca.getId() | n == radioPuntosMunicipio.getId() |
                n == radioTodosPuntos.getId()){ //Evento para determinar el tipo de búsqueda de puntos de venta
            cambiarEstadoDialogo(n);
        } else if(n == btDialogoBuscarVenta.getId()){ //Buscar los puntos de venta según el escodigo
            buscarPuntosVenta(v);
        } else if(n == btDialogoCerrarVenta.getId()){ //Cerrar el diálogo de busqueda de puntos de venta y resetear
            dialogoPuntosVenta.dismiss();
            dialogoVentas(v);
        }
    }


}