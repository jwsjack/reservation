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
import com.tp1.e_cebanu.tp1.models.Role;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.List;

public class ReasonsFragment extends Fragment {
    //list view
    private View v = null;
    //lists
    private List<Reason> reasons;
    private int countLines = 0;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private Context context;

    MyCustomAdapter dataAdapter = null;


    public ReasonsFragment() {
        // Constructeur vide obligatoire
    }

    public static ReasonsFragment newInstance() {
        ReasonsFragment fragment = new ReasonsFragment();
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
        View v = inflater.inflate(R.layout.fragment_reason_list, container, false);
        context = v.getContext();

        // les boutons
        btAdd = (Button) v.findViewById(R.id.add);

        // retrieve list
        ListView listView = (ListView) v.findViewById(R.id.list);
        //donnée
        reasons = AppService.getReasonsService().findAll();

        countLines = reasons.size();
        TextView text_count_lines = (TextView) v.findViewById(R.id.text_count_lines);
        text_count_lines.setText(getResources().getString(R.string.lbCountLines) + Integer.toString(countLines));

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(context, reasons);
        // Affecter l'adaptateur à ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Une fois cliqué, affichez une boîte de dialogue d'alerte
                final Reason reason = (Reason) parent.getItemAtPosition(position);

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // Obtenez l'inflateur de mise en page
                LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
                // Remplissez et définissez la mise en page de la boîte de dialogue
                // Pass null as the parent view because its going in the
                // dialog layout
                dialogBuilder.setTitle(getResources().getString(R.string.reasonData));
                dialogBuilder.setCancelable(true);
                dialogBuilder.setIcon(R.drawable.ic_action_list);
                //dialog form
                final View dialogView = inflater.inflate(R.layout.fragment_reason, null);
                final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
                txtId.setText(String.valueOf(reason.getId()));
                final EditText txtName = (EditText) dialogView.findViewById(R.id.etName);
                txtName.setText(String.valueOf(reason.getName()));

                btUpdate = (Button) dialogView.findViewById(R.id.update);
                btDelete = (Button) dialogView.findViewById(R.id.delete);

                dialogBuilder.setView(dialogView);
                dialogBuilder.create();

                //buttons
                dialogBuilder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // pour fermer le dialog
                final AlertDialog ad = dialogBuilder.show();

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = txtId.getText().toString();
                        String name = txtName.getText().toString();
                        //validation
                        Boolean valide = false;
                        valide = UIUtils.checkFieldValueString(name, "Name");
                        if (valide) {
                            update(Integer.parseInt(id), name);
                            ad.dismiss();
                        }
                    }
                });
                btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(reason.getId());
                        ad.dismiss();
                    }
                });

            }
        });
        // ecouteurs d'evenements sur le bouton Ajouter
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        return v;
    }

    /*BOUTONS*/
    public void add() {
        // Une fois cliqué, affichez une boîte de dialogue d'alerte
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        // Obtenez l'inflateur de mise en page
        LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
        // Remplissez et définissez la mise en page de la boîte de dialogue
        dialogBuilder.setTitle(getResources().getString(R.string.localData));
        dialogBuilder.setCancelable(true);
        dialogBuilder.setIcon(R.drawable.ic_action_list);
        //dialog form
        final View dialogView = inflater.inflate(R.layout.fragment_reason, null);
        final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
        txtId.setText("");
        final EditText txtName = (EditText) dialogView.findViewById(R.id.etName);
        txtName.setText("");

        btUpdate = (Button) dialogView.findViewById(R.id.update);
        btUpdate.setText(R.string.add);
        btDelete = (Button) dialogView.findViewById(R.id.delete);
        btDelete.setVisibility(View.GONE);

        //buttons
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.create();

        // pour fermer le dialog
        final AlertDialog ad = dialogBuilder.show();

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtId.getText().toString();
                String name = txtName.getText().toString();
                //validation
                Boolean valide = false;
                valide = UIUtils.checkFieldValueString(name, "Name");
                if (valide) {
                    update(0, name);
                    ad.dismiss();
                }
            }
        });
    }

    public void update(int id, String name) {
        //validation
        Boolean valide = false;
        valide = UIUtils.checkFieldValueString(name, "Name");
        if (valide) {
            if (id == 0) {
                //création nouveau objet
                Toast.makeText(context, String.valueOf(name), Toast.LENGTH_SHORT).show();
                // Victor: TODO implement here add local by "id"
                Reason reason = AppService.getReasonObject();
                reason.setName(name);

            } else {
                // Victor: TODO implement here update local by "id"
                Reason reason = AppService.getReasonObject();
                reason.setId(id);
                reason.setName(name);

                Toast.makeText(context, String.valueOf(name), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete(int id) {
        // Victor: TODO implement here delete Reason by "id"
        Toast.makeText(context, "Delete reason - " + String.valueOf(id), Toast.LENGTH_LONG).show();

    }

    /*ADAPTER list*/
    private class MyCustomAdapter extends ArrayAdapter<Reason> {

        private List<Reason> reasonsList;

        public MyCustomAdapter(Context context, List<Reason> reasonsList) {
            super(context, R.layout.fragment_reason_list, reasonsList);
            this.reasonsList = reasonsList;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
            View rowView = inflater.inflate(R.layout.fragment_reason_list_item, null);

            TextView txtId = (TextView) rowView.findViewById(R.id.txtId);
            txtId.setText(String.valueOf(reasonsList.get(position).getId()));
            TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
            txtName.setText(String.valueOf(reasonsList.get(position).getName()));
            return rowView;
        }
    }
}
