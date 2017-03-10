package com.tp1.e_cebanu.tp1.models;

import android.app.ActionBar;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/*
* Java# version 1.8
*
* @name       TP_1
* @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
* @author     EUGENIU CEBANU / matricule: 20025851
* @author     jwsjack3@gmail.com
* @version    1
* @date       2017-02-20
* @description Classe des fonctions globales et visibles dans toutes les parties
*/


public class MyApplication extends Application {
    private static Context context;
    private static Resources resources;
    private static Object systemService;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        MyApplication.resources = getApplicationContext().getResources();
        MyApplication.systemService = getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Service pour prendre le context
     * @return
     */
    public static Context getAppContext() {
        return MyApplication.context;
    }

    /**
     * Service pour prendre les resources
     * @return
     */
    public static Resources getAppResources() {
        return MyApplication.resources;
    }

    public static Object getSystemService() {
        return MyApplication.systemService;
    }


}
