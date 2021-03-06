package com.tp1.e_cebanu.tp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.activities.MainActivity;
import com.tp1.e_cebanu.tp1.authenticator.AuthenticatorActivity;
import static com.tp1.e_cebanu.tp1.util.UIUtils.verifierAuthentification;

/*
* Java# version 1.8
*
* @name       TP_1
* @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
* @author     EUGENIU CEBANU / matricule: 20025851
* @author     jwsjack3@gmail.com
* @version    1
* @date       2017-02-20
* @description Contrôleur principale - point d'entrée
*/

public class BootstrapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //main activity on launch system

        if (!verifierAuthentification(this)) {
            // on passe par l'authentification
            Intent i = new Intent(this, AuthenticatorActivity.class);
            i.putExtra("Value1", "This value one for ActivityTwo ");
            startActivity(i);
        } else {
            // on lance la page d'accueil
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Value1", "This value one for MainActivity ");
            startActivity(i);
        }

    }

}
