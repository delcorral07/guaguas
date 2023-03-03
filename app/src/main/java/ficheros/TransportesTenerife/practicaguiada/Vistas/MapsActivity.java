package ficheros.TransportesTenerife.practicaguiada.Vistas;



import static ficheros.TransportesTenerife.practicaguiada.Data.lecturaFicheros.deserializarFichero;
import static ficheros.TransportesTenerife.practicaguiada.Data.lecturaFicheros.existeSerializado;
import static ficheros.TransportesTenerife.practicaguiada.Data.lecturaFicheros.serializarArray;
import static ficheros.TransportesTenerife.practicaguiada.Data.lecturaFicheros.tareasDeActualizacion;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.AsyncTaskLoader;

import ficheros.TransportesTenerife.practicaguiada.Animaciones.AnimacionesMain;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.EstacionarioBusqueda;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.Parada;
import ficheros.TransportesTenerife.practicaguiada.POJO.Recorrido.Puntos;
import ficheros.TransportesTenerife.practicaguiada.Data.lecturaFicheros;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.Guagua;
import ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO.PuntosVenta;

import com.example.practicaguiada.R;

import ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments.FragmentFavoritos;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments.FragmentGuaguas;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments.FragmentHorarios;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments.FragmentListado;
import ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments.fragmentOpcionesSecundarias;

import com.example.practicaguiada.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener,
        GoogleMap.OnMapClickListener, Serializable, GoogleMap.OnMarkerClickListener, OnMapsSdkInitializedCallback {

    public static GoogleMap mMap;
    public static FusedLocationProviderClient fusedLocationClient;
    public static final LatLng Tenerife = new LatLng(28.266594174050855, -16.56083408676261);


    /* Componentes Widget de la interfaz MapsActivity */
    public static ImageButton botonCapas, botonSatelite, botonCoche, botonVistaNormal,
            botonAbrirBusqueda, botonQR, botonAbrirLlamada;
    public static Button botonLimpiar, botonInfoParada, botonLlegarVenta, botonCerrarVenta;
    private TextView textoDireccion, textoNombre, textoNumero;

    /* POJOS utilizados en la clase */
    private PuntosVenta puntoVentaSeleccionado;
    public static Parada paradaElegida;
    public static Guagua guaguaElegida;
    public static List<Parada> paradas;
    public static List<Puntos> puntosList;
    public static List<PuntosVenta> puntosVentas;
    public static List<EstacionarioBusqueda> listaEstacionarios;
    public static FrameLayout layouFragments, layoutFragmentOpciones;
    public static ConstraintLayout layoutBotonLimpiar, layoutInferior, layoutOpciones;
    public static FragmentTransaction transaction;
    public static Fragment fragmentLista, fragmentGuagua, fragmentHorario, fragmentFavoritos, fragmentOpciones;
    public static FragmentManager manager;
    public static LatLng ubicacionActual;
    public static String linkNavegador;
    public static LinearLayout layouCapas;
    public Dialog dialogoVenta;
    public static UiModeManager uiModeManager;
    public static String guaguaAnterior;


    /*
    Los itinerarios que tiene una guagua. Se actualizan haciendo llamada a la api
     */
    public static List<Puntos> puntosGuaguaIda;
    public static List<Puntos> puntosGuaguaVenida;

    /* Variables para controlar las animaciones y el flujo de interacción entre fragments */
    public static boolean fragmentHorarioActivo = false, fragmenGuaguaActivo = false,
            botonLimpiarActivo = false, layoutFragmentsActivo = false, fragmentFavoritosActivo = false,
            paradaDesdeGuagua = false, guaguaDesdeFavorito = false, paradaDesdeFavorito = false,
            paradaDesdeMapa = false, itinerarioIda = false, fragmentOpcionesActivo = false,
            fragmentNoticiasActivo = false, puntosVentaActivos = false, capasActivo = false,
            botonVerTiempo = false, mapaDatosCargados = false;

    /*
        TODO:


        DONE:
            - Arreglado bloqueo cuando no hay conexión en las guaguas
            - Arreglado bloqueo cuando no hay conexión en las paradas
            - El texto del recorrido de una línea cambia cuando cambiamos el sentido del recorrido
            - Se ha fijado la orientación en vertical hasta diseñar la interfaz horizontal
            - Se han cambiado los algoritmos de búsqueda para optimizar los tiempos de carga
     */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        com.example.practicaguiada.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        initComponents();
        grantLocationPermises();

        cargarFicheros(this);
        fragmentOpciones = new fragmentOpcionesSecundarias();
        fragmentLista = new FragmentListado();
        getSupportFragmentManager().beginTransaction().add(R.id.layoutFragments, fragmentLista).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layoutFragmentOpciones, fragmentOpciones).commit();
        manager = getSupportFragmentManager();
        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        MapsInitializer.initialize(getApplicationContext(), MapsInitializer.Renderer.LATEST, this);
    }

    @Override
    public void onMapsSdkInitialized(MapsInitializer.Renderer renderer) {
        switch (renderer) {
            case LATEST:
                Log.d("MapsDemo", "The latest version of the renderer is used.");
                break;
            case LEGACY:
                Log.d("MapsDemo", "The legacy version of the renderer is used.");
                break;
        }
    }


    public static boolean comprobarConexion(Context contexto){
        ConnectivityManager cm = (ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return (nInfo != null && nInfo.isAvailable() && nInfo.isConnected());
    }









    /*
    Vincula los objetos java con los elementos de la clase R
    */
    private void initComponents() {
        this.botonCapas = findViewById(R.id.botonCapas);
        this.botonSatelite = findViewById(R.id.botonSatelite);
        this.botonCoche = findViewById(R.id.botonCoche);
        this.botonVistaNormal = findViewById(R.id.botonVistaNormal);
        this.layouCapas = findViewById(R.id.layoutCapas);
        this.botonInfoParada = findViewById(R.id.botonVerInfoParada);
        this.botonAbrirBusqueda = findViewById(R.id.botonAbrirBusqueda);
        this.botonQR = findViewById(R.id.botonQr);
        this.botonLimpiar = findViewById(R.id.botonLimpiar);
        layoutInferior = findViewById(R.id.layoutInferior);
        layouFragments = findViewById(R.id.layoutFragments);
        layoutBotonLimpiar = findViewById(R.id.layoutBotonLimpiar);
        layoutOpciones = findViewById(R.id.layoutOpciones);
        layoutFragmentOpciones = findViewById(R.id.layoutFragmentOpciones);
        botonInfoParada.setVisibility(View.GONE);
        dialogoVenta = new Dialog(this);
        dialogoVenta.setContentView(R.layout.dialogo_info_venta);
        dialogoVenta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        botonCerrarVenta = dialogoVenta.findViewById(R.id.btCerrarEstab);
        botonLlegarVenta = dialogoVenta.findViewById(R.id.btDirecEstab);
        botonAbrirLlamada = dialogoVenta.findViewById(R.id.btAbrirLlamada);
        textoDireccion = dialogoVenta.findViewById(R.id.tvDirEstab);
        textoNombre = dialogoVenta.findViewById(R.id.tvNombreEstb);
        textoNumero = dialogoVenta.findViewById(R.id.tvTelefonoEstab);
        eventos();

    }



    public void cargarFicheros(Context c) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(existeSerializado(c)){
                    System.out.println("\n\n\n\tENTRA A LEERR LOS FICHEROS SERIALIZADOS\n\n");
                    deserializarFichero(c);
                }else{
                    System.out.println("\n\n\n\tENTRA A CARGAR LOS FICHEROS\n\n\n\t");
                    paradas = lecturaFicheros.leerParadas(c);
                    puntosVentas = lecturaFicheros.leerFicheroVentas(c);
                    generarEstacionarios(c);
                    List[] lista = {paradas,puntosVentas,listaEstacionarios};
                    serializarArray(lista,c);
                }
                mapaDatosCargados = true;
            }
        }).start();
    }

    private void generarEstacionarios(Context c){
        listaEstacionarios = new ArrayList<>();
        for(Guagua g : lecturaFicheros.leerGuaguas(c)){
            listaEstacionarios.add(new EstacionarioBusqueda(g.getRouteShortName(),
                    g.getRouteLongName(),"Linea ", true));
        }
        for(Parada p : lecturaFicheros.leerParadas(c)){
            listaEstacionarios.add(new EstacionarioBusqueda(p.getStop_id(),
                    p.getStop_name(),"Parada ",false));
        }
        //De esta manera conseguimos una lista con nombres con la que trabajar en el recicler view
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(Tenerife, 9, 0, 0)));
        mMap.setOnMapClickListener(this);

        mMap.setOnMarkerClickListener(this);
        mMap.setMinZoomPreference(9);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                if(isLocationEnabled(layoutInferior.getContext())){
                    try{
                        cargarParadasCercanasInicial();
                    }catch (NullPointerException ex){
                        System.out.println(ex);
                    }

                }
            }
        });
    }

    private void cargarParadasCercanasInicial() throws NullPointerException{
        mMap.clear();
        runOnUiThread(new Runnable() {
            @Override
            public void run() throws NullPointerException{
                Circle circle = mMap.addCircle(
                        new CircleOptions()
                                .center(MapsActivity.ubicacionActual)
                                .radius(350)
                                .strokeWidth(0));
                MapsActivity.puntosList = new ArrayList<Puntos>();
                for (Parada parada : MapsActivity.paradas) {
                    if (checkDistance(MapsActivity.ubicacionActual.latitude,
                            MapsActivity.ubicacionActual.longitude, parada.getStop_lat(),
                            parada.getStop_lon(), Float.parseFloat(String.valueOf(circle.getRadius())))) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(parada.getStop_lat(), parada.getStop_lon())).title(parada.getStop_name())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntoguagua)));
                        MapsActivity.puntosList.add(new Puntos(parada.getStop_name(), parada.getStop_id(), " "));

                    }
                }
            }
        });
    }
    private boolean checkDistance(double currentLocLat, double currentLocLong, double placeLocLat, double placeLocLong, float radio) {
        float[] results = new float[3];
        Location.distanceBetween(currentLocLat, currentLocLong, placeLocLat, placeLocLong, results);
        return (results[0] <= radio);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /*
    Recoge, si puede, el tipo de trayecto. Esto sirve para mostrar las paradas en el mapa cuando
    este activity se activa desde una activity que no sea login
     */

    public static void colocarMarcadoresLinea(List<Puntos> puntos) {
        mMap.clear();
        if (botonVerTiempo) {
            //Oculta el botón de ver parada
            botonVerTiempo = AnimacionesMain.animarBotonParadas(botonInfoParada, false);
        }
        puntosVentaActivos = false;

        Comparator<Parada> comp = new Comparator<Parada>() {
            @Override
            public int compare(Parada o1, Parada o2) {
                return o1.getStop_id().compareTo(o2.getStop_id());
            }
        };
        Parada p1 = new Parada();
        Parada p2 = new Parada();
        for (int i = 0; i < puntos.size(); i++) {
            try{
                Parada parada = paradas.get(Collections.binarySearch(paradas,new Parada(puntos.get(i).getCodigo()),comp));
                if(i==0){
                    p1 = parada;
                }
                if(i==puntos.size()-1){
                    p2 = parada;
                }
                int icon = 0;
                String label = "";
                switch (puntos.get(i).getTipo()) {
                    case "origen":
                        label = " (Origen)";
                        icon = R.drawable.estacion;
                        break;
                    case "destino":
                        label = " (Destino)";
                        icon = R.drawable.estacion;
                        break;
                    default:
                        label = "";
                        icon = R.drawable.puntoguagua;
                        break;
                }
                mMap.addMarker(new MarkerOptions().position(new LatLng(parada.getStop_lat(), parada.getStop_lon())).title(parada.getStop_name()+label)
                        .icon(BitmapDescriptorFactory.fromResource(icon)));
            }catch (ArrayIndexOutOfBoundsException ex){ }



            LatLng medPos = puntoMedio(p1.getStop_lat(),p1.getStop_lon(),p2.getStop_lat(),p2.getStop_lon());
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(medPos, 5, 0, 0)));
        }

    }

    private static LatLng puntoMedio(double lat1, double long1, double lat2, double long2) {
        return new LatLng((lat1+lat2)/2, (long1+long2)/2);
    }


    public static void colocarPuntosVenta(List<PuntosVenta> puntos) {
        mMap.clear();
        for (int i = 0; i < puntos.size(); i++) {
            for (PuntosVenta p : puntosVentas) {
                if (puntos.get(i).getNombreCliente().equals(p.getNombreCliente())) {
                    mMap.addMarker(new MarkerOptions().position(
                            new LatLng(Double.parseDouble(p.getLatitud()), Double.parseDouble(p.getLongitud())))
                            .title(p.getNombreCliente())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_ventas_mapa)));
                }
            }
        }
    }


//    ----------------------------------------------------------------------------------------------
//    ---------------------------- UBICACION Y PERMISOS  -------------------------------------------
//    ----------------------------------------------------------------------------------------------

    public void grantLocationPermises() {
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(ubicacionActual, 16, 0, 0)));
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        mMap.getUiSettings().setCompassEnabled(false);
                    }
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setCompassEnabled(false);
                mMap.getUiSettings().setAllGesturesEnabled(true);
            }
        }
    }

    public void miUbicacion(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder mensaje = new AlertDialog.Builder(this);
            mensaje.setTitle("Se necesitan permisos de ubicación");
            mensaje.setMessage("Para acceder a todas las funciones de esta aplicación debes activar " +
                    "los permisos de ubicación, así como la propia ubicación");
            mensaje.setPositiveButton("Conceder permisos", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    grantLocationPermises();
                }
            });
            mensaje.setNegativeButton("Cancelar", null);
            mensaje.create().show();
        } else {
            if (isLocationEnabled(view.getContext())) {
                mMap.setMyLocationEnabled(true);
                fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 16)); //animar camara
                            cargarParadasCercanasInicial();
                        }
                    }
                });
            } else {
                AlertDialog.Builder mensaje = new AlertDialog.Builder(this);
                mensaje.setTitle("Falta activar la ubicación");
                mensaje.setMessage("Para utilizar esta funcionalidad debes tener la ubicación activada");
                mensaje.setPositiveButton("¡Entendido!", null);
                mensaje.create().show();
            }
        }
    }

    public static void obtenerUbicacionActual() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(MapsActivity.layoutInferior.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MapsActivity.layoutInferior.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                fusedLocationClient.getLastLocation().addOnSuccessListener(MapsActivity.fragmentLista.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
            }
        }).start();
    }

    @SuppressWarnings("deprecation")
    public static Boolean isLocationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return (mode != Settings.Secure.LOCATION_MODE_OFF);
        }
    }

    public void recogerParadaSeleccionada(String tituloParada, Marker mark) {
        ArrayList<Parada> nombresPuntos = new ArrayList<>();
        for(Parada p : paradas) {
            if (tratarNombre(p.getStop_name()).equals(tratarNombre(tituloParada)))
                nombresPuntos.add(p);
        }
        LatLng direccionMarker = mark.getPosition();
        for(Parada po : nombresPuntos){
            if(direccionMarker.longitude == po.getStop_lon() && direccionMarker.latitude == po.getStop_lat()){
                paradaElegida = po;
            }
        }
        botonInfoParada.setText("Ver parada");

    }

    public void recogerVentaSeleccionada(String tituloPuntoVenta){
        for(PuntosVenta p : puntosVentas){
            if(p.getNombreCliente().equals(tituloPuntoVenta)){
                this.puntoVentaSeleccionado = p;
                break;
            }
        }
        botonInfoParada.setText("Ver establecimiento");
    }

    public String tratarNombre(String tituloParada) {
        if (tituloParada.contains(" (Origen)")) {
            tituloParada = tituloParada.replace(" (Origen)", "");
        }
        if (tituloParada.contains(" (Destino)")) {
            tituloParada = tituloParada.replace(" (Destino)", "");
        }
        if (tituloParada.contains("1.")) {
            tituloParada = tituloParada.replace("1.", "");
        }
        return tituloParada;
    }





//    ----------------------------------------------------------------------------------------------
//    ---------------------------- FRAGMENTS  -------------------------------------------
//    ----------------------------------------------------------------------------------------------


    /**
     * Cierra los fragments previamente abiertos, inicializa el fragment de horarios y lo coloca en
     * el contenedor del layout que sube y baja
     */
    public void abrirIntentParada() {
        if (paradaElegida.getStop_name().contains("INTERCAMBIADOR STA.CRUZ")) {
            fragmentHorario = new FragmentHorarios("9181", "INTERCAMBIADOR STA.CRUZ");
        } else {
            fragmentHorario = new FragmentHorarios(paradaElegida.getStop_id(), paradaElegida.getStop_name());
        }
        transaction = manager.beginTransaction();

        transaction.replace(R.id.layoutFragments, fragmentHorario);
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentHorarioActivo = true;
        layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
                layoutInferior,layoutFragmentsActivo, findViewById(R.id.layoutFragments));
    }

    /**
     * Cierra los fragments previamente abiertos, inicializa el fragment del listado y lo coloca en
     * el contenedor del layout que sube y baja
     */
    public void abrirLayoutListado() {
        if (capasActivo) {
            capasActivo = AnimacionesMain.alterarCapas(layouCapas, botonCapas, true);
       }
        if (fragmentFavoritosActivo || paradaDesdeMapa) {
            if(fragmentLista == null){
                fragmentLista = new FragmentListado();
            }
            transaction = manager.beginTransaction();
            transaction.replace(R.id.layoutFragments, fragmentLista);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
            fragmentFavoritosActivo = false;
            paradaDesdeMapa = false;
        }
        layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
                layoutInferior, layoutFragmentsActivo, findViewById(R.id.layoutFragments));
        paradaDesdeMapa = false;

    }

    /**
     * Cierra los fragments previamente abiertos, inicializa el fragment de paradas y
     * guaguas favortias y lo coloca en el contenedor del layout que sube y baja
     */
    public static void abrirFragmentFavoritos(View v) {
        if(fragmentFavoritos == null){
            fragmentFavoritos = new FragmentFavoritos();
        }
        transaction = manager.beginTransaction();
        transaction.replace(R.id.layoutFragments, fragmentFavoritos);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
        fragmentFavoritosActivo = true;
        if (capasActivo) {
            capasActivo = AnimacionesMain.alterarCapas(layouCapas, botonCapas, true);
        }
        if(!layoutFragmentsActivo){
            layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
                    layoutInferior,false, layouFragments);
        }

    }

    /**
     * Cierra los fragments anteriores e inicializa el fragment con la información de las paradas
     * @param codParada El código (ID) de la parada que se quiere mostrar. Se usa para llamar
     *                  a la API
     * @param nombreParada El nombre que tiene la parada para mostrar
     */
    public static void abrirFragmentParada(String codParada, String nombreParada) {
        if(fragmentFavoritosActivo){
            paradaDesdeFavorito = true;
        }
        paradaElegida = new Parada(codParada,nombreParada);
        fragmentHorario = new FragmentHorarios(codParada, nombreParada);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.layoutFragments, fragmentHorario);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
        fragmentHorarioActivo = true;
    }

    /**
     * Cierra los fragments anteriores, inicializa el fragment de las guaguas y lo coloca
     * en el condenedor
     * @param codGuagua El código de la guagua que se va a buscar. Se utiliza para llamar a la API
     * @param descripcion El recorrido que hace la guagua. Se utiliza para mostrar al usuario como
     *                    información adicional
     */
    public static void abrirFragmentGuagua(String codGuagua, String descripcion) {

        if(fragmentFavoritosActivo){
            guaguaDesdeFavorito = true;
        }
        guaguaElegida = new Guagua(codGuagua, descripcion);
        fragmentGuagua = new FragmentGuaguas(codGuagua, descripcion);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.layoutFragments, fragmentGuagua);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
        fragmenGuaguaActivo = true;
    }

    /**
     * Abre el layout superior con las opciones
     * @param v vista
     */
    public void abrirIntentOpciones(View v){
        fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(layoutFragmentOpciones,
                layoutInferior,layoutOpciones,false);
    }

    /**
     * Muestra el diálogo de venta
     * @param v vista
     */
    public void abrirDialogoVenta(View v){
        textoNumero.setText(puntoVentaSeleccionado.getTelefono1());
        textoNombre.setText(puntoVentaSeleccionado.getNombreCliente());
        textoDireccion.setText(puntoVentaSeleccionado.getDireccion());
        dialogoVenta.show();
    }

    /**
     * Abre el planificador de viajes. COmprueba que la ubicación sea diferente de null
     * para pasarla también como parámetro al google maps y organizar el viaje desde donde estamos
     * hasta donde queremos llegar
     * @param v Vista
     */
    public void abrirBusquedaTienda(View v){
        linkNavegador = "http://maps.google.com/maps?";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            linkNavegador+="&daddr="+puntoVentaSeleccionado.getLatitud()+","+puntoVentaSeleccionado+"&dirflg=w";
            abrirNavegador(linkNavegador,v);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());
                        linkNavegador+="saddr="+ubicacionActual.latitude+","+ubicacionActual.longitude+
                                "&daddr="+puntoVentaSeleccionado.getLatitud()+","+puntoVentaSeleccionado.getLongitud()+"&dirflg=w";
                        abrirNavegador(linkNavegador,v);
                    }else{
                        linkNavegador+="&daddr="+puntoVentaSeleccionado.getLatitud()+","+puntoVentaSeleccionado+"&dirflg=w";
                        abrirNavegador(linkNavegador,v);
                    }
                }
            });
        }

    }

    /**
     * Abre el Activity del navegador
     * @param link Link que se va a cargar en este activity
     * @param v Vista para obtener el c
     */
    public void abrirNavegador(String link, View v){
        System.out.println(link);
        Intent intentNavegador = new Intent(v.getContext(), Navegador.class);
        intentNavegador.putExtra("link", link);
        startActivity(intentNavegador);
    }


//    ----------------------------------------------------------------------------------------------
//    ------------------------------ EVENTOS DE LA APLICACIÓN  -------------------------------------
//    ----------------------------------------------------------------------------------------------

    private void eventos() {
        botonSatelite.setOnClickListener(this);
        botonCoche.setOnClickListener(this);
        botonVistaNormal.setOnClickListener(this);
        botonCapas.setOnClickListener(this);
        botonLlegarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirBusquedaTienda(v);
            }
        });
        botonCerrarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoVenta.dismiss();
            }
        });
        botonAbrirLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel: "+puntoVentaSeleccionado.getTelefono1()));
                startActivity(i);
            }
        });

        botonInfoParada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!puntosVentaActivos){
                    paradaDesdeMapa = true;
                    abrirIntentParada();
                }else{
                    abrirDialogoVenta(v);
                }


            }
        });
        botonAbrirBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLayoutListado();
            }
        });
        botonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                botonLimpiarActivo = AnimacionesMain.animarBotonLimpiar(MapsActivity.layoutBotonLimpiar, botonLimpiarActivo);
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Tenerife, 9));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(Tenerife,9,0,0)));
                if(botonVerTiempo){
                    botonVerTiempo = AnimacionesMain.animarBotonParadas(botonInfoParada, false);
                }
                puntosVentaActivos = false;

            }
        });
        botonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutFragmentsActivo) {
                    layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
                            layoutInferior,true, findViewById(R.id.layoutFragments));
                }
                Intent nuevoIntent = new Intent(v.getContext(), LectorQR.class);
                startActivity(nuevoIntent);

            }
        });


    }

    @Override
    public void onClick(View v) {
        int n = v.getId();
        /*
        Si el usuario tiene el layout contenedor de fragments abierto, lo cierra
        y después anima el layout de capas
         */
        if (layoutFragmentsActivo) {
            layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
                    layoutInferior,true, findViewById(R.id.layoutFragments));

        }
        if (n == botonCoche.getId()) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            capasActivo = AnimacionesMain.alterarCapas(layouCapas, botonCapas, capasActivo);
        } else if (n == botonSatelite.getId()) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            capasActivo = AnimacionesMain.alterarCapas(layouCapas, botonCapas, capasActivo);
        } else if (n == botonVistaNormal.getId()) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            capasActivo = AnimacionesMain.alterarCapas(layouCapas, botonCapas, capasActivo);
        } else if (n == botonCapas.getId()) {
            capasActivo = AnimacionesMain.alterarCapas(layouCapas, botonCapas, capasActivo);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (botonVerTiempo) {
            //Oculta el botón de ver parada
            botonVerTiempo = AnimacionesMain.animarBotonParadas(botonInfoParada, false);
        }
        if (capasActivo) {
            //Oculta el layout de cambiar de capas
            capasActivo = AnimacionesMain.alterarCapas(layouCapas, botonCapas, true);
        }
        if (layoutFragmentsActivo) {
//            //Oculta el layout contenedor de fragments
//            layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
//                    layoutInferior,true, findViewById(R.id.layoutFragments));

            paradaDesdeMapa = false;
        }
        if(fragmentOpcionesActivo) {
            fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(layoutFragmentOpciones,
                    layoutInferior, layoutOpciones,true);
        }
    }

    public static void comportamientoDeCerrado() {
        if (layoutFragmentsActivo) {
            if (paradaDesdeGuagua) { //Cuando entramos a ver una parada desde el fragment de guaguas
                /*
                Toma la guagua elegida (P.E. desde la 120 vemos la parada 9181) y cambia del
                fragment de parada al fragment de guaguas de la línea 120
                 */
                abrirFragmentGuagua(guaguaElegida.getRouteShortName(), guaguaElegida.getRouteLongName());
                paradaDesdeGuagua = false;
            } else if ((paradaDesdeFavorito || guaguaDesdeFavorito) && fragmentFavoritosActivo) {
                /*
                FragmentFavoritoActivo indica que se abrió el fragment de favoritos. Si desde este
                fragment entramos a una guagua/parada, las varaibles paradaDesdeFavorito/guaguaDesdeFavorito
                estarán en true, por lo que en vez de cerrar el contenedor, volverá a inflar el fragment de favoritos
                 */
                abrirFragmentFavoritos(new View(MapsActivity.layouFragments.getContext()));
                paradaDesdeFavorito = false;
                guaguaDesdeFavorito = false;
            } else if (fragmentFavoritosActivo || paradaDesdeMapa) {
                /*
                Si sólo el fragment de favoritos está abierto, oculta el contenedor
                (El usuario entra a favoritos y sale con la misma)
                 */
                layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
                        layoutInferior, true, layouFragments);
            } else if (fragmentHorarioActivo || fragmenGuaguaActivo) {
                /*
                El usario ve una parada/línea desde el fragment de listado. EL programa vuelve
                a inflar el fragment de listado en vez de cerrar el contenedor
                 */
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layoutFragments, fragmentLista).commit();
                fragmentHorarioActivo = false;
                fragmenGuaguaActivo = false;
            } else {
                /*
                Este caso se da cuando el usuario abre el listado (Botón lupa central)
                 */
                layoutFragmentsActivo = AnimacionesMain.animarLayoutFragments(layouFragments, layoutOpciones,
                        layoutInferior, true, layouFragments);
            }
        } else if (fragmentOpcionesActivo) {
            if (fragmentNoticiasActivo) {
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layoutFragmentOpciones, fragmentOpciones).commit();
                fragmentNoticiasActivo = false;
            } else {
                fragmentOpcionesActivo = AnimacionesMain.animarLayoutOpciones(layoutFragmentOpciones,
                        layoutInferior, layoutOpciones, true);
            }
        }
    }

    /*
    Utilizando variables booleanas, determinamos el comportamiento de la navegación del layout
    contenedor de los fragments
     */
    @Override
    public void onBackPressed() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(MapsActivity.layouFragments.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        if(layoutFragmentsActivo | fragmentOpcionesActivo){
            comportamientoDeCerrado();
        }else {
            AlertDialog.Builder mensaje = new AlertDialog.Builder(this);
            mensaje.setTitle("Cerrar Transpotes Tenerife");
            mensaje.setMessage("Va a salir de la aplicación");
            mensaje.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            mensaje.setNegativeButton("Cancelar",null);
            mensaje.create().show();
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!puntosVentaActivos) {
            recogerParadaSeleccionada(marker.getTitle(), marker);
        } else {
            recogerVentaSeleccionada(marker.getTitle());
        }
        if (!botonVerTiempo) {
            botonVerTiempo = AnimacionesMain.animarBotonParadas(botonInfoParada, true);
        }
        return false;
    }
}
