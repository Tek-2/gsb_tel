package fr.be2.gsb_tel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.be2.gsb_tel.SQLHelper;

public class parametres extends menu {

    EditText Nom1, Prenom1, CodeV1, Email1,URL1;
    SQLHelper database;
    Button Valider1;
    private static final String monFichier = "GSB_PREF_USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres);
        database= new SQLHelper(this);
        afficherUser();
        init();

    }

    public void init(){

        //Param.getInt(Param.getColumnIndex("CODEV1"));
        CodeV1= findViewById(R.id.CODEV);
        CodeV1.setText(getSharedPreferences(monFichier, MODE_PRIVATE).getString("CODEVisiteur",""));

        Nom1=findViewById(R.id.NOM);
        Nom1.setText(getSharedPreferences(monFichier, MODE_PRIVATE).getString("NOM",""));

        Prenom1=findViewById(R.id.PRENOM);
        Prenom1.setText(getSharedPreferences(monFichier, MODE_PRIVATE).getString("PrenOM",""));

        Email1=findViewById(R.id.EMAIL);
        Email1.setText(getSharedPreferences(monFichier, MODE_PRIVATE).getString("email",""));

        URL1=findViewById(R.id.URLSERVEUR);
        URL1.setText(getSharedPreferences(monFichier, MODE_PRIVATE).getString("UrlServeur",""));

        Valider1=findViewById(R.id.main_button_11);
    }




    public void MonClickParam(View v ) {

        getSharedPreferences(monFichier, MODE_PRIVATE)
                .edit()
                .putString("CodeVisiteur", CodeV1.getText().toString())
                .putString("email", Email1.getText().toString())
                .putString("Prenom", Prenom1.getText().toString())
                .putString("Nom", Nom1.getText().toString())
                .putString("UrlServeur", URL1.getText().toString())
                .apply();

        intent = new Intent(parametres.this, menu.class);
        //this.finish();
        afficherMessage("Informations enregistrées avec succès", "");
        Handler handler = new Handler();
        int delayMillis = 2000; // Délai de 3 secondes

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cette partie du code sera exécutée après le délai spécifié
                finish();
            }
        }, delayMillis);
    }

}