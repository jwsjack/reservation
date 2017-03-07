package com.tp1.e_cebanu.tp1.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;

import com.tp1.e_cebanu.tp1.models.MyApplication;

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

    /**
     * Aide à déterminer si l'application s'exécute dans un contexte Tablet.
     *
     * @param context
     * @return boolean
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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
        if (restoredNom != null && restoredPass != null && restoredPass.toString().trim().equals(CLE_ACCES)) {
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

}
