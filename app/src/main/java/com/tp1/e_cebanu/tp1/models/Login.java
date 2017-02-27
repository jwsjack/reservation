package com.tp1.e_cebanu.tp1.models;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.tp3.e_cebanu.devoir3.R;

import com.tp1.e_cebanu.tp1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements View.OnClickListener {

    // variables privées
    private View form = null;

    public Login() {
        // Constructeur public vide obligatoire
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        form = inflater.inflate(R.layout.authenticator_activity, container, false);
        return form;

    }

    @Override
    public void onClick(View v) {

    }




}
