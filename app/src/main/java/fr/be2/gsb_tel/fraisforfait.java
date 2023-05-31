package fr.be2.gsb_tel;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class fraisforfait extends menu {
    EditText txtQte1;
    Spinner listeForfait1 ;
    String[] valeurs;
    TextView mSomme;
    CalendarView calendar;
    SQLHelper database;
    TextView maDate;
    Float montantCalcule;
    DatePickerDialog picker;
    Calendar calendrier = Calendar.getInstance(); //on declare une classe d'un calendrier qui existe deja
    int jj=calendrier.get(Calendar.DAY_OF_MONTH);
    int mm=calendrier.get(Calendar.MONTH);
    int aaaa=calendrier.get(Calendar.YEAR);

    /**
     * Méthode onCreate : Cette méthode est appelée lorsque l'activité est créée. Dans cette
     * méthode, le contenu de l'interface utilisateur est défini en utilisant la méthode
     * setContentView. De plus, certaines variables sont initialisées et des écouteurs
     * d'événements sont définis pour certains éléments de l'interface utilisateur.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fraisforfait);
        afficherUser();
        txtQte1=findViewById(R.id.Quantite_FAF);
        listeForfait1=findViewById(R.id.TypedeForfait_FAF);
        valeurs=getResources().getStringArray(R.array.ValeurForfait);
        mSomme=findViewById(R.id.txtsomme);
        //calendar= findViewById(R.id.calendrier);
        database= new SQLHelper(this);
        maDate = findViewById(R.id.txtDateFrais);

        txtQte1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                // This is where we'll check the user input
                Integer q1 = Integer.parseInt(String.valueOf("0"+txtQte1.getText()));
                //String f1 = listeForfait1.getSelectedItem().toString();
                int posF1 = listeForfait1.getSelectedItemPosition();
                Float s1 = q1 * Float.parseFloat(valeurs[posF1]);

                mSomme.setText(s1.toString());
            }
        });
    }

    /**
     * Méthode ShowCal : Cette méthode est appelée lorsqu'un bouton est cliqué pour afficher un
     * DatePickerDialog, permettant à l'utilisateur de sélectionner une date. Une fois la date
     * sélectionnée, elle est affichée dans un TextView.
     *
     * @param vv
     */
    public void ShowCal(View vv)
    {
        picker = new DatePickerDialog(fraisforfait.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        maDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        //date qu'on recupere une fois qu'on a selectionne
                    }
                },aaaa, mm, jj);//date qui s'affiche sur le calendrier
        picker.show();
    }

    /**
     * Méthode Monclick : Cette méthode est appelée lorsqu'un bouton est cliqué, en particulier le
     * bouton "ajouterff". Dans cette méthode, plusieurs vérifications sont effectuées pour valider
     * les données saisies par l'utilisateur. Si toutes les validations sont réussies, les données
     * sont insérées dans la base de données à l'aide de la méthode insertData de l'objet database.
     *
     * @param v
     */
    public void Monclick(View v){

        if (v.getId()==R.id.ajouterff) {
                if (txtQte1.getText().toString().trim().length() == 0 || listeForfait1.getSelectedItem().toString().length() == 0
                        || maDate.getText().toString().trim().length() == 0) {
                    //teste si le champ quantite est renseigné ou si le champ type n'est pas vide
                    // et qu'on a selectionne l'une des 4 possibilités et si la date est renseignée
                    afficherMessage("Erreur!", "Champ vide");
                    return;
                }else if (maDate.getText().toString().trim().length()>10 || maDate.getText().toString().trim().length()<8 ) {
                    //test sur la validité du champ date
                    afficherMessage("Erreur!", "Date invalide");
                    return;
                } else if (Integer.parseInt(txtQte1.getText().toString())<1){ //teste si la quantite est au moins 1
                    afficherMessage("Erreur!", "Quantité invalide");
                    return;
                }else {
                    Integer quantite = Integer.parseInt(String.valueOf(txtQte1.getText()));
                    String forfait = listeForfait1.getSelectedItem().toString();
                    String dateForfait = maDate.getText().toString();
                    int posForfait = listeForfait1.getSelectedItemPosition();
                    montantCalcule= quantite * Float.parseFloat(valeurs[posForfait]);
                    if (database.insertData(forfait, quantite, dateForfait, montantCalcule, forfait)) {
                        afficherMessage("Succès !", "Valeur ajoutée, " + "montant = " + montantCalcule +"€");
                        return;
                    }
                }

        }
    }

}
