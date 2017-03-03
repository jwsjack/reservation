package com.tp1.e_cebanu.tp1.authenticator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.XMLParser;
import com.tp1.e_cebanu.tp1.util.TextWatcherAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.id.list;
import static com.tp1.e_cebanu.tp1.util.UIUtils.showProgress;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.tp1.e_cebanu.tp1.util.UIUtils.isTablet;


/**
 * Created by jack on 26.02.2017.
 */

public class AuthenticatorActivity extends AppCompatActivity {

    protected AutoCompleteTextView emailText;
    protected EditText passwordText;
    protected CheckBox ckRequestNewAccount;
    protected Button signInButton;
    private final TextWatcher watcher = validationTextWatcher();

    private String email;
    private String password;
    private String xmlRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //l'authentification
        if (isTablet(this)) {
            setContentView(R.layout.authenticator_activity_tablet);
        } else {
            setContentView(R.layout.authenticator_activity);
        }



        /*Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String value1 = extras.getString("Value1");


        Toast.makeText(this, value1,
                Toast.LENGTH_LONG).show();*/

        emailText = (AutoCompleteTextView) findViewById(R.id.et_email);
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

        emailText.addTextChangedListener(watcher);
        passwordText.addTextChangedListener(watcher);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //updateUIWithValidation();
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

        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        //showProgress();

        /*Toast.makeText(this, "SALUT!!!!",
                Toast.LENGTH_LONG).show();*/
    }

    /**
     * Vérifier si les champs email et password sont remplis
     */
    private void updateUIWithValidation() {
        final boolean populated = populated(emailText) && populated(passwordText);
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
