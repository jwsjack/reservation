package com.tp1.e_cebanu.tp1.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.models.User;

import static com.tp1.e_cebanu.tp1.R.id.tvNumber3;

/*
* Java# version 1.8
*
* @name       TP_1
* @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
* @author     EUGENIU CEBANU / matricule: 20025851
* @author     jwsjack3@gmail.com
* @version    1
* @date       2017-02-20
* @description Contrôleur pour la page Profile - Gérer toutes les opérations sur la page Profile
*/
public class ProfileActivity extends AppCompatActivity {

    private User liu = null;
    private String messFooter = "";
    private ImageView user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        liu = AppService.getLiu();
        messFooter = getResources().getString(R.string.messageWelcome);
        if (liu.getId() != 0) {
            //change l'image par rapport le role
            if (liu.isSuperAdmin()) {
                user_image = (ImageView) findViewById(R.id.user_image);
                user_image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.superadmin));
            }

            messFooter = getResources().getString(R.string.activity_title_profile) + " : " + liu.getNom();
            //Définir les valeurs d'utilisateur
            //email:
            TextView tvNumber3 = (TextView) findViewById(R.id.tvNumber3);
            //tvNumber3.setText(liu.getEmail());
            tvNumber3.setText(liu.getLogin()+".reservation@gmail.com");
            TextView tvNumber4 = (TextView) findViewById(R.id.tvNumber4);
            //tvNumber4.setText(liu.getEmail());
            tvNumber4.setText(liu.getLogin()+".reservation@gmail.com");
            //address:
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, messFooter, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Poignée barre d'action article clique ici(Handle action bar item clicks here.)
        // La barre d'action va gérer automatiquement les clics sur le Home/Up button,
        // aussi longtemps que vous spécifiez une activité parent dans AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // terminer l'activité
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
