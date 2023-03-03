package ficheros.TransportesTenerife.practicaguiada.Vistas.Fragments;

import static ficheros.TransportesTenerife.practicaguiada.Data.DatosOnline.avisos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ficheros.TransportesTenerife.practicaguiada.AdaptadoresVistas.listaAvisosAdapter;
import ficheros.TransportesTenerife.practicaguiada.Vistas.MapsActivity;
import com.example.practicaguiada.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNoticias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNoticias extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView listadoNoticias;
    private Button botonCerrarNoticias;
    private SwipeRefreshLayout refreshNoticias;
    public static boolean noticiasCargadas = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentNoticias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNoticias.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNoticias newInstance(String param1, String param2) {
        FragmentNoticias fragment = new FragmentNoticias();
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
        View v = inflater.inflate(R.layout.fragment_noticias, container, false);
        listadoNoticias = v.findViewById(R.id.listaNoticias);
        refreshNoticias = v.findViewById(R.id.refreshNoticias);
        refreshNoticias.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNoticias.setRefreshing(false);
                listadoNoticias.setLayoutManager(new LinearLayoutManager(v.getContext()));
                listaAvisosAdapter adapter = new listaAvisosAdapter(avisos());
                listadoNoticias.setAdapter(adapter);
            }
        });
        botonCerrarNoticias = v.findViewById(R.id.botonCerrarNoticias);
        botonCerrarNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.comportamientoDeCerrado();
            }
        });
        listadoNoticias.setLayoutManager(new LinearLayoutManager(v.getContext()));
        listaAvisosAdapter adapter = new listaAvisosAdapter(avisos());
        listadoNoticias.setAdapter(adapter);


        return v;
    }


}