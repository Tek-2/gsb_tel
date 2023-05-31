package fr.be2.gsb_tel;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class consultationFrais extends menu {
    private SQLHelper database;
    private SimpleCursorAdapter dataAdapter;

    /**
     * Méthode onCreate : Cette méthode est appelée lorsque l'activité est créée. Elle configure le
     * layout de l'activité en utilisant le fichier XML "consultationfrais.xml". Elle initialise la
     *base de données SQLHelper et ouvre la connexion avec la base de données. Ensuite, elle appelle
     *la méthode "displayListView" pour générer le ListView à partir des données de la base de données.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultationfrais);
        afficherUser();
        database = new SQLHelper(this);
        database.open();
        //Générer le ListView a partir de SQLite Database
        displayListView();

    }

    /**
     * Méthode displayListView : Cette méthode est responsable de l'affichage des données de frais
     * dans le ListView. Elle récupère un curseur (Cursor) contenant les données de frais à partir
     * de la base de données. Elle spécifie les colonnes de la base de données que l'on souhaite lier
     * à des éléments spécifiques dans le fichier XML. Ensuite, elle crée un SimpleCursorAdapter en
     * utilisant le curseur, les colonnes et les éléments spécifiques du XML pour définir l'adaptateur.
     * Elle associe l'adaptateur au ListView et configure un écouteur d'événements sur le ListView
     * pour gérer les clics sur les éléments de la liste. Lorsqu'un élément est cliqué, elle récupère
     * l'ID du frais correspondant, l'affiche dans un toast et supprime les données de frais correspondantes
     * de la base de données.
     */
    private void displayListView() {

        Cursor cursor = database.fetchAllFrais();

        // Les colonnes que l’on veut lier
        String[] columns = new String[]{
                SQLHelper.ID_FRAIS,
                SQLHelper.DATE_FRAIS,
                SQLHelper.MONTANT,
                SQLHelper.DATE_SAISIE,
                SQLHelper.QUANTITE,
                SQLHelper.LIBELLE
        };

        // Les éléments defnis dans le XML auxquels les données sont liées
        int[] to = new int[]{
                R.id.idFrais,
                R.id.dateFrais,
                R.id.montant,
                R.id.dateActu,
                R.id.txtQte,
                R.id.libelleFrais,
        };
        //On créer l'adaptateur à l'aide du curseur pointant sur les données souhaitées  ainsi que les informations de mise en page
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.frais_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = findViewById(R.id.listView1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // On obtient le curseur, positionne sur la ligne correspondante dans le jeu de résultats
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                //  //On obtient l'id du frais selectionné
                String monID =
                        cursor.getString(cursor.getColumnIndexOrThrow("ID_FRAIS"));
                Toast.makeText(getApplicationContext(),
                        monID, Toast.LENGTH_SHORT).show();
                database.deleteData(Integer.parseInt(monID));
            }
        });

        /**
         * Méthode addTextChangedListener : Cette méthode configure un TextWatcher pour le champ de
         * texte (EditText) "myFilter". Le TextWatcher est utilisé pour écouter les modifications
         * du texte dans le champ de texte et filtrer les données affichées dans le ListView en
         * fonction du texte saisi.
         *
         */

        // Attribuer l’adapter au ListView
        listView.setAdapter(dataAdapter);

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        /**
         * Méthode runQuery : Cette méthode est utilisée pour exécuter une requête de filtrage sur
         * la base de données en fonction du texte de contrainte spécifié. Elle renvoie un curseur
         * (Cursor) contenant les données filtrées.
         *
         */
        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return database.fetchFrais(constraint.toString());
            }
        });
    }

}