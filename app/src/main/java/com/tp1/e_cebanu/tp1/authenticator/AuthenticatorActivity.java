package com.tp1.e_cebanu.tp1.authenticator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.activities.MainActivity;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.User;
import com.tp1.e_cebanu.tp1.util.TextWatcherAdapter;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.tp1.e_cebanu.tp1.util.UIUtils.isTablet;

/*
* Java# version 1.8
*
* @name       TP_1
* @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
* @author     EUGENIU CEBANU / matricule: 20025851
* @author     jwsjack3@gmail.com
* @version    1
* @date       2017-02-20
* @description Contrôleur d'autentification - authentification par login et mot de passe
*/

public class AuthenticatorActivity extends AppCompatActivity {

    protected AutoCompleteTextView loginText;
    protected EditText passwordText;
    protected CheckBox ckRequestNewAccount;
    protected Button signInButton;
    private final TextWatcher watcher = validationTextWatcher();

    private String login;
    private String password;
    public static final String MON_CLE_LOGIN = "loginAutentification";
    public static final String CLE_ACCES = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // pour different types de emulateur on charge les views different
        if (isTablet(this)) {
            setContentView(R.layout.authenticator_activity_tablet);
        } else {
            setContentView(R.layout.authenticator_activity);
        }
        //l'authentification
        loginText = (AutoCompleteTextView) findViewById(R.id.et_login);
        passwordText = (EditText) findViewById(R.id.et_password);
        ckRequestNewAccount = (CheckBox) findViewById(R.id.requestNewAccount);
        signInButton = (Button) findViewById(R.id.b_signin);

        // écouteur d'événement sur bouton
        passwordText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
                if (event != null && ACTION_DOWN == event.getAction()
                        && keyCode == KEYCODE_ENTER && signInButton.isEnabled()) {
                    handleLogin(signInButton);
                    return true;
                }
                return false;
            }
        });

        // écouteur d'événement sur modification de mot de passe
        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(final TextView v, final int actionId,
                                          final KeyEvent event) {
                if (actionId == IME_ACTION_DONE && signInButton.isEnabled()) {
                    handleLogin(signInButton);
                    return true;
                }
                return false;
            }
        });

        // écouteur d'événement sur bouton
        ckRequestNewAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { buttonView.setTextColor(Color.RED); }
                if (!isChecked) { buttonView.setTextColor(getResources().getColor(R.color.text)); }
            }
        });

        loginText.addTextChangedListener(watcher);
        passwordText.addTextChangedListener(watcher);
        ckRequestNewAccount.setVisibility(View.INVISIBLE); // temporairement jusqu'à sera mis en œuvre
        updateUIWithValidation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIWithValidation();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Vérifier si le texte a été changé (activer le boutton Submit)
     * @return boolean
     */
    private TextWatcher validationTextWatcher() {
        return new TextWatcherAdapter() {
            public void afterTextChanged(final Editable gitDirEditText) {
                updateUIWithValidation();
            }
        };
    }

    /**
     * Traiter onClick événement sur le bouton Soumettre. Envoie le nom d'utilisateur / mot de passe à
     * la système pour l'authentification.
     *
     * @param view
     */
    public void handleLogin(final View view) {

        login = loginText.getText().toString();
        password = passwordText.getText().toString();

        if (login != null && password != null ) {
            User liu = AppService.authenticate(login,password);
            if (liu.getId() != 0) {

                // sauvegarde les valeurs dans  SharedPreferences
                SharedPreferences.Editor editor = this.getSharedPreferences(MON_CLE_LOGIN, MODE_PRIVATE).edit();
                editor.putString("login", login.trim());
                editor.putString("pass", password.trim());
                editor.putString("liu",liu.toJSON()); // l'objet utilisateur authentifié
                editor.commit();

                //passe à la page d'accueil
                Toast.makeText(this, getResources().getString(R.string.messageWelcome),
                        Toast.LENGTH_LONG).show();

                // ici on transmettre les valeurs supplémentaires pour l'activite Main
                // placer ici les valeurs si on veut passér les extras dans l'activité demande
                Intent i = new Intent(this, MainActivity.class);
//                i.putExtra("Value1", "This value 1 for MainActivity");
//                i.putExtra("Value2", "This value 1 for MainActivity");
                startActivity(i);

            } else {
                String mess = getResources().getString(R.string.messageWrongAuthentification);
                Toast.makeText(this, mess,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            String mess = getResources().getString(R.string.messageValidationAuthentification);
            Toast.makeText(this, mess,
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Vérifier si les champs email et password sont remplis
     */
    private void updateUIWithValidation() {
        final boolean populated = populated(loginText) && populated(passwordText);
        signInButton.setEnabled(populated);
    }

    /**
     * Vérifier la longueur du text dans une champ
     * @param editText
     * @return
     */
    private boolean populated(final EditText editText) {
        return editText.length() > 0;
    }

}
