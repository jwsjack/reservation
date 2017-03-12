package com.tp1.e_cebanu.tp1.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.fragments.ReasonsFragment;
import com.tp1.e_cebanu.tp1.models.MyApplication;

import static android.R.attr.tag;
import static android.content.Context.MODE_PRIVATE;

/*
* Java# version 1.8
*
* @name       TP_1
* @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
* @author     EUGENIU CEBANU / matricule: 20025851
* @author     jwsjack3@gmail.com
* @version    1
* @date       2017-02-20
* @description Classe des fonctions supplémentaires et communes
*/

public class UIUtils {


    private SharedPreferences prefs;
    public static final String MON_CLE_LOGIN = "loginAutentification";
    public static final String CLE_ACCES = "12345";
    public static final String FILE_STORAGE_FOLDER = "raw";
    public static final String TAG = "IFT1155:RESERVATION:";

    /**
     * Aide à déterminer si l'application s'exécute dans un contexte Tablet.
     *
     * @param context
     * @return boolean
     */
    public static boolean isTablet(Context context) {
        boolean var = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        return var;
    }

    /**
     * @param context
     * @return boolean
     * @desc Verifier si l'utilisateur est authentifié deja ou non
     */
    public static boolean verifierAuthentification(Context context) {
        //récupérer les données de SharedPreferences
        SharedPreferences prefsAut = context.getSharedPreferences(MON_CLE_LOGIN, MODE_PRIVATE);
        String restoredNom = prefsAut.getString("login", null);
        String restoredPass = prefsAut.getString("pass", null);
        if (restoredNom != null && restoredPass != null) {
            return true; // l'utilisateur est autorisée
        }
        return false;
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    public static void showProgress() {
        createdDialog(0).show();
    }

    public static Dialog createdDialog(int id) {
        throw new RuntimeException("Stub!");
    }


    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public static  int getColor(int colorId) {
        Integer result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = ContextCompat.getColor(MyApplication.getAppContext(), colorId);
        } else {
            result = MyApplication.getAppResources().getColor(colorId);
        }
        return result;
    }

    /**
     * Validation du valeur du champ String
     * utiliser pour les forms avec d'inputs
     * @param val
     * @param nameField
     * @return
     */
    public static boolean checkFieldValueString(String val, String nameField) {
        if (val.equals(null) || val.equals("")) {
            Toast.makeText(MyApplication.getAppContext(), MyApplication.getAppResources().getString(R.string.filling_message) + nameField, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Validation du valeur du champ Integer
     * utiliser pour les forms avec d'inputs
     * @param val
     * @param nameField
     * @return
     */
    public static boolean checkFieldValueInteger(int val, String nameField) {
        if (val == 0) {
            Toast.makeText(MyApplication.getAppContext(), MyApplication.getAppResources().getString(R.string.filling_message) + nameField, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Recharge la page avec fragment
     * @param fragment
     * @param activity
     * @param tag
     */
    public static void refreshFragment(Fragment fragment, FragmentActivity activity, String tag) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, "reasons");
        fragmentTransaction.commitAllowingStateLoss();
    }

}
