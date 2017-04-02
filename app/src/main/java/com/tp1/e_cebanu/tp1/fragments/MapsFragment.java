package com.tp1.e_cebanu.tp1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.models.Reason;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.List;

import static com.tp1.e_cebanu.tp1.R.id.text_count_lines;
import static com.tp1.e_cebanu.tp1.models.AppService.getReasonsService;
import static com.tp1.e_cebanu.tp1.util.UIUtils.refreshFragment;

public class MapsFragment extends Fragment {
    //list view
    private View v = null;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private Context context;



    public MapsFragment() {
        // Constructeur vide obligatoire
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps_list, container, false);
        context = v.getContext();

        // les boutons
//        btAdd = (Button) v.findViewById(R.id.add);



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // Une fois cliqué, affichez une boîte de dialogue d'alerte
//                final Reason reason = (Reason) parent.getItemAtPosition(position);
//
//                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//                // Obtenez l'inflateur de mise en page
//                LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
//                // Remplissez et définissez la mise en page de la boîte de dialogue
//                // Pass null as the parent view because its going in the
//                // dialog layout
//                dialogBuilder.setTitle(getResources().getString(R.string.reasonData));
//                dialogBuilder.setCancelable(true);
//                dialogBuilder.setIcon(R.drawable.ic_action_list);
//                //dialog form
//                final View dialogView = inflater.inflate(R.layout.fragment_reason, null);
//                final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
//                txtId.setText(String.valueOf(reason.getId()));
//                final EditText txtName = (EditText) dialogView.findViewById(R.id.etName);
//                txtName.setText(String.valueOf(reason.getName()));
//
//                btUpdate = (Button) dialogView.findViewById(R.id.update);
//                btDelete = (Button) dialogView.findViewById(R.id.delete);
//
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.create();
//
//                //buttons
//                dialogBuilder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//                // pour fermer le dialog
//                final AlertDialog ad = dialogBuilder.show();
//
//                btUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String id = txtId.getText().toString();
//                        String name = txtName.getText().toString();
//                        //validation
//                        Boolean valide = false;
//                        valide = UIUtils.checkFieldValueString(name, "Name");
//                        if (valide) {
//                            update(reason.getId(), name);
//                            ad.dismiss();
//                        }
//                    }
//                });
//                btDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        delete(reason.getId());
//                        ad.dismiss();
//                    }
//                });
//
//            }
//        });
//        // ecouteurs d'evenements sur le bouton Ajouter
//        btAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                add();
//            }
//        });
        return v;
    }


}
