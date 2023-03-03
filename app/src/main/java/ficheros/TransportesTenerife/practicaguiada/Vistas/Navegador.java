package ficheros.TransportesTenerife.practicaguiada.Vistas;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaguiada.R;

public class Navegador extends AppCompatActivity {

    private WebView web;
    private boolean webCargada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webCargada = false;
        setContentView(R.layout.activity_navegador_horario);
        web = findViewById(R.id.navegador);
        String URL = getIntent().getStringExtra("link");
        web.loadUrl(URL);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(webCargada){
            finish();
        }
        webCargada=true;
    }
}