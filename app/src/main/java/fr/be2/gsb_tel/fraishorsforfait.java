package fr.be2.gsb_tel;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class fraishorsforfait extends menu {

    //declaration des variables
    Button btnAjouter, btnModifier, btnSupprimer;
    EditText libelle;
    EditText montant;
    TextView date;
    SQLHelper database; //variable qui permet d'accéder à la classe SQLHelper pr pouvoir accéder à ses méthodes.
    Calendar calendrier = Calendar.getInstance(); //on declare une classe d'un calendrier qui existe deja
    int jj=calendrier.get(Calendar.DAY_OF_MONTH);
    int mm=calendrier.get(Calendar.MONTH);
    int aaaa=calendrier.get(Calendar.YEAR);

    /**
     * Méthode onCreate : Cette méthode est appelée lorsque l'activité est créée. Dans cette
     * méthode, le contenu de l'interface utilisateur est défini en utilisant la méthode
     * setContentView. De plus, la variable database est initialisée en créant une instance
     * de la classe SQLHelper. La méthode init() est appelée pour relier les boutons aux objets
     * graphiques correspondants.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fraishorsforfait);
        afficherUser();
        database=new SQLHelper(this); //j'instancie la classe SQLHelper avec la variable database
        init();
    }

    /**
     * Permet de relier les boutons avec leur objet graphique
     */
    public void init(){
        btnAjouter=findViewById(R.id.main_button_10);
        libelle=findViewById(R.id.libelle_FHF);
        montant=findViewById(R.id.Montant_FHF);
        date=findViewById(R.id.txtdatefrais);
        DatePickerDialog picker;
    }

    /**
     * Affiche un calendrier avec les dates à jour
     * @param vv
     */
    public void ShowCal(View vv)
    {
        DatePickerDialog picker = new DatePickerDialog(fraishorsforfait.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, aaaa, mm, jj);
        picker.show();
    }

    /**
     * Méthode MyClick : Cette méthode est appelée lorsqu'un bouton est cliqué, en particulier le
     * bouton "btnAjouter". Dans cette méthode, plusieurs vérifications sont effectuées pour valider
     * les données saisies par l'utilisateur. Si toutes les validations sont réussies, les données
     * sont insérées dans la base de données à l'aide de la méthode insertData de la bdd
     *
     * Ajoute le libelle, la date et le montant d'un frais hors forfait
     * @param view
     */
    public void MyClick(View view) {
        if (view.getId() == R.id.main_button_10) {
            String libelle1 = libelle.getText().toString();
            double montant1 = Double.parseDouble(montant.getText().toString());//conversion d'un text
            // en string et d'un string en double
            String date1 = date.getText().toString();
            if (libelle1.trim().length() == 0 || montant1 == 0 || date1.length() == 0) { //test si les champs
                // libelle, montant et date sont renseignes
                afficherMessage("Erreur !", "Champs vides.");
                return;
            } else {
                database.insertData("Hors Forfait", null, date1, montant1, libelle1);
                afficherMessage("Succès !", "Informations ajoutées.");
                return;
            }
        }
    }
    }