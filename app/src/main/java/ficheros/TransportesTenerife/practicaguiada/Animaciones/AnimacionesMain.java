package ficheros.TransportesTenerife.practicaguiada.Animaciones;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.example.practicaguiada.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AnimacionesMain {

    /*
    Este método, junto con el de abajo, se encargan de mostrar y ocultar el botón de ver la información
    cuando pulsa en una parada en el mapa
     */
    public static boolean animarBotonParadas(Button boton, boolean activar){
        if(activar){
            boton.setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();;
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(boton,"translationY",300f,0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(boton,"alpha",0,100)));
            set.start();
            return true;
        }else{
            AnimatorSet set = new AnimatorSet();;
            set.playTogether(Glider.glide(Skill.CircEaseOut,400,ObjectAnimator.ofFloat(boton,"translationY",0f,500f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(boton,"alpha",100,0)));
            set.start();
            return false;
        }
    }


    /*
    Animación para MapsActivity, muestra y oculta el Constraint Layout que tiene los botones
    para cambiar entre los diferentes tipos de capas en el mapa
     */
    public static boolean alterarCapas(LinearLayout layouCapas, ImageButton botonCapas, boolean capasActivo){

        if(!capasActivo){
            layouCapas.setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut, 150,ObjectAnimator.ofFloat(botonCapas,"rotation",0f,180)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layouCapas,"translationY",-400f,0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layouCapas,"alpha",0,100)));
            set.start();
            return true;
        }else{
            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut, 150,ObjectAnimator.ofFloat(botonCapas,"rotation",180,0)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layouCapas,"alpha",100,0)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layouCapas,"translationY",0f,-400f)));
            set.start();
            return false;
        }
    }

    /*
        Animación para MapsActivity, muestra y oculta el Constraint Layout que tiene los botones
        para cambiar entre los diferentes tipos de capas en el mapa
     */
    public static boolean animarBotonLimpiar(ConstraintLayout texto, boolean capasActivo){
        if(!capasActivo){
            texto.setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(texto,"translationY",-400f,0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(texto,"alpha",0,100)));
            set.start();
            return true;
        }else{
            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(texto,"alpha",100,0)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(texto,"translationY",0f,-400f)));
            set.start();
            return false;
        }
    }



    /*
    Esta animación oculta el Componente para cambiar el texto, y luego lo vuelve a mostrar
     */
    public static void cambiarTexto(TextView v, String nuevoTexto){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(Glider.glide(Skill.CircEaseOut, 200,ObjectAnimator.ofFloat(v,"alpha",100,0))) ;
        set.playTogether(Glider.glide(Skill.CircEaseOut, 500,ObjectAnimator.ofFloat(v,"translationX",0,500)));
        set.start();
        v.setText(nuevoTexto);
        set.playTogether(Glider.glide(Skill.CircEaseOut, 200,ObjectAnimator.ofFloat(v,"alpha",0,100))) ;
        set.playTogether(Glider.glide(Skill.CircEaseOut, 500,ObjectAnimator.ofFloat(v,"translationX",500f,0)));
        set.start();
    }


    /*
    Animaciones para los fragments del maps activity
     */
    public static boolean animarLayoutFragments(FrameLayout layoutFragments, ConstraintLayout layoutOpciones,
                                                ConstraintLayout layoutInferior, boolean activo, View contexto){
        if(!activo){
            layoutFragments.setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutFragments,"translationY",1000f,0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutOpciones,"translationY",
                    0f,-layoutOpciones.getHeight())));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutInferior,"translationY",
                    0f,layoutInferior.getHeight())));

            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layoutFragments,"alpha",0,100)));
            set.start();
            return true;
        }else{

            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layoutFragments,"alpha",100,0)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutOpciones,"translationY",
                    -layoutOpciones.getHeight(),0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutInferior,"translationY",
                    layoutInferior.getHeight(),0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layoutFragments,"translationY",0f,2000)));
            set.start();

            return false;
        }
    }

    public static boolean animarLayoutOpciones(FrameLayout layoutOpciones, ConstraintLayout layoutInferior,
                                               ConstraintLayout constraintOpciones, boolean activo){
        if(!activo){
            layoutOpciones.setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutOpciones,"translationY",-layoutOpciones.getHeight(),0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutInferior,"translationY",
                    0f,layoutInferior.getHeight())));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(constraintOpciones,"translationY",
                    0f,-constraintOpciones.getHeight())));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layoutOpciones,"alpha",0,100)));
            set.start();
            return true;
        }else{

            AnimatorSet set = new AnimatorSet();
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layoutOpciones,"alpha",100,0)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(layoutInferior,"translationY",
                    layoutInferior.getHeight(),0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,100,ObjectAnimator.ofFloat(constraintOpciones,"translationY",
                    -constraintOpciones.getHeight(),0f)));
            set.playTogether(Glider.glide(Skill.CircEaseOut,150,ObjectAnimator.ofFloat(layoutOpciones,"translationY",0f,-layoutOpciones.getHeight())));
            set.start();

            return false;
        }
    }








}


