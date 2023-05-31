package fr.be2.gsb_tel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class menu extends AppCompatActivity {
    Intent intent;
    EditText codeVisiteur;
    Context context;


    private static final String monFichier = "GSB_PREF_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        afficherUser();
        secure();
    }

    public void click_menu(View v){
        String msg= " ";
int id = v.getId();

        if (id== R.id.main_button_1) {
                intent = new Intent(menu.this, fraisforfait.class);
        }
        else if (id== R.id.main_button_2) {
            intent = new Intent(menu.this, fraishorsforfait.class);
        }
        else if (id== R.id.main_button_3) {
            intent = new Intent(menu.this, consultationFrais.class);
        }
        else if (id== R.id.main_button_4) {
            intent = new Intent(menu.this, parametres.class);
        }
        else if (id== R.id.main_button_15) {
            getSharedPreferences(monFichier, Context.MODE_PRIVATE).edit().clear().commit();
            intent = new Intent(menu.this, Connexion.class);

        }
        startActivity(intent);

        // Toast.makeText();


    }
    public void fermer(View v){

        this.finish();
    }
    public void secure(){
        String cvisiteur= getSharedPreferences("GSB_PREF_USER", MODE_PRIVATE).getString("CodeVisiteur","pas authentifie");
        if (cvisiteur.equals("pas authentifie")) {
            Intent intent = new Intent(menu.this,Connexion.class);
            startActivity(intent);
            this.finish();
        }}
    public void afficherMessage(String titre, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this); //classe qui constuit une boite de dialogue
        builder.setCancelable(true); //pr que la boite de dialogue soit refermable
        builder.setTitle(titre);
        builder.setMessage(message);
        builder.show();

    }

    public void afficherUser(){
        TextView bienvenue = findViewById(R.id.bienvenue);
        String nomUser = getSharedPreferences(monFichier, MODE_PRIVATE).getString("Nom", "");
        nomUser += " " + getSharedPreferences(monFichier, MODE_PRIVATE).getString("Prenom", "");
        bienvenue.setText(nomUser);
    }
}

