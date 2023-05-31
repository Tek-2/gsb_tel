package fr.be2.gsb_tel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Connexion extends AppCompatActivity {
    EditText codeVisiteur;
    EditText email;
    EditText codeVerif;
    LinearLayout verification;
    Integer codeAleatoire;
    private static final String monFichier = "GSB_PREF_USER";

    /**
     * Méthode onCreate : Cette méthode est appelée lorsque l'activité est créée.
     * Elle configure le layout de l'activité en utilisant le fichier XML "connexion.xml"
     * et initialise les variables EditText et LinearLayout en récupérant les références des
     * éléments correspondants dans le layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        codeVisiteur = findViewById(R.id.Codevisiteur);
        email = findViewById(R.id.email);
        verification = findViewById(R.id.linear);
        codeVerif= findViewById(R.id.codeVerif);

    }

    /**
     * Méthode click_code : Cette méthode est appelée lorsqu'un bouton est cliqué. Elle génère un
     * code aléatoire à l'aide de la classe Random et l'affiche dans un toast. Ensuite, elle rend
     * le LinearLayout "verification" visible.
     *
     * @param v
     */
    public void click_code(View v){
        Random r = new Random();
        int min = 1000;
        int max = 9999;
        codeAleatoire = r.nextInt((max - min) + 1) + min;
        Toast.makeText(this,"code="+codeAleatoire.toString(),Toast.LENGTH_SHORT).show();
        verification.setVisibility(View.VISIBLE);


    }

    /**
     * Méthode Valide_code : Cette méthode est appelée lorsque le bouton "Valider" est cliqué.
     * Elle compare le code aléatoire généré précédemment avec le code saisi par l'utilisateur.
     * Si les codes correspondent, un message toast "Réussite" est affiché et les valeurs des champs
     * "codeVisiteur" et "email" sont enregistrées dans les préférences partagées à l'aide de la clé
     * "CodeVisiteur" et "email". Enfin, elle crée une intention pour démarrer une nouvelle activité
     * "menu" et lance cette intention.
     *
     * @param v
     */
    public void Valide_code (View v){
        String codeAleatoireStr = codeAleatoire.toString();
        String codeverifStr = codeVerif.getText().toString();
        if (codeAleatoireStr.equals(codeverifStr)){
            Toast.makeText(this, "Réussite", Toast.LENGTH_SHORT).show();
            getSharedPreferences(monFichier, MODE_PRIVATE)
                    .edit()
                    .putString("CodeVisiteur", codeVisiteur.getText().toString())
                    .putString("email", email.getText().toString())
                    .apply();
            Intent intent = new Intent(Connexion.this,menu.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Méthode fermer : Cette méthode est appelée lorsque le bouton "Fermer" est cliqué.
     * Elle termine l'activité en cours.
     *
     * @param v
     */
    public void fermer(View v){

        this.finish();
    }
}


