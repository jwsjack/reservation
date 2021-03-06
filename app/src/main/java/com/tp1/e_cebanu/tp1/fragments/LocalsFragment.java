package com.tp1.e_cebanu.tp1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.List;

import static com.tp1.e_cebanu.tp1.models.AppService.getLocalsService;
import static com.tp1.e_cebanu.tp1.util.UIUtils.refreshFragment;


public class LocalsFragment extends Fragment {
    //list view
    private View v = null;
    //lists
    private List<Local> locals;
    private int countLines = 0;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private Context context;
    private LocalsFragment currentFragment;


    MyCustomAdapter dataAdapter = null;


    public LocalsFragment() {
        // Required empty public constructor
    }

    public static LocalsFragment newInstance(String param1, String param2) {
        LocalsFragment fragment = new LocalsFragment();
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
        View v = inflater.inflate(R.layout.fragment_local_list, container, false);
        context = v.getContext();

        // les boutons
        btAdd = (Button) v.findViewById(R.id.add);

        // retrieve list
        ListView listView = (ListView) v.findViewById(R.id.list);
        //donnée
        locals = getLocalsService().findAll();

        countLines = locals.size();
        TextView text_count_lines = (TextView) v.findViewById(R.id.text_count_lines);
        text_count_lines.setText(getResources().getString(R.string.lbCountLines) + Integer.toString(countLines));

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(context, locals);
        // Affecter l'adaptateur à ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Une fois cliqué, affichez une boîte de dialogue d'alerte
                final Local local = (Local) parent.getItemAtPosition(position);

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // Obtenez l'inflateur de mise en page
                LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
                // Remplissez et définissez la mise en page de la boîte de dialogue
                // Pass null as the parent view because its going in the
                // dialog layout
                dialogBuilder.setTitle(getResources().getString(R.string.localData));
                dialogBuilder.setCancelable(true);
                dialogBuilder.setIcon(R.drawable.ic_action_list);
                //dialog form
                final View dialogView = inflater.inflate(R.layout.fragment_local, null);
                final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
                txtId.setText(String.valueOf(local.getId()));
                final EditText txtNombre = (EditText) dialogView.findViewById(R.id.etNombre);
                txtNombre.setText(String.valueOf(local.getNombre()));
                final EditText txtDescription = (EditText) dialogView.findViewById(R.id.etDescription);
                txtDescription.setText(String.valueOf(local.getDescription()));


                // Spinner pour la sélection du type de local
                final Spinner staticSpinner = (Spinner) dialogView.findViewById(R.id.etType);
                // Create an ArrayAdapter using the string array and a default spinner
                ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                        .createFromResource(getContext(), R.array.locations_types,
                                android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                staticAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set selected value
                // Apply the adapter to the spinner
                staticSpinner.setAdapter(staticAdapter);
                int currentType = local.getType();
                if (currentType != 0) {
                    int spinnerPosition = getLocalTypeByCode(currentType);
                    staticSpinner.setSelection(spinnerPosition);
                }
                // fin Spinner

                final EditText txtCapacite = (EditText) dialogView.findViewById(R.id.etCapacity);
                txtCapacite.setText(String.valueOf(local.getCapacite()));
                btUpdate = (Button) dialogView.findViewById(R.id.update);
                btDelete = (Button) dialogView.findViewById(R.id.delete);

                dialogBuilder.setView(dialogView);
                dialogBuilder.create();
                //buttons
                dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
                        String nombre = txtNombre.getText().toString();
                        int type = staticSpinner.getSelectedItemPosition() + 1;
                        String capacite = txtCapacite.getText().toString();
                        String description = txtDescription.getText().toString();
                        //validation
                        Boolean valide = false;
                        valide = UIUtils.checkFieldValueString(nombre, "Number");
                        if (valide) {
                            valide = UIUtils.checkFieldValueInteger(type, "Type");
                        }
                        if (valide) {
                            valide = UIUtils.checkFieldValueString(capacite, "capacity");
                        }
                        if (valide) {
                            valide = UIUtils.checkFieldValueString(description, "description");
                        }
                        if (valide) {
                            update(local.getId(), nombre, type, capacite,description);
                            ad.dismiss();
                        }
                    }
                });
                btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(local.getId());
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

    /**
     * Fonction personalisee pour retrieve la valeaur de type selectionee
     *
     * @param code
     * @return
     */
    private int getLocalTypeByCode(int code) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.codes_locations_types)) {
            i++;
            if (Integer.parseInt(cc) == code)
                break;
        }
        return i;
    }

    /*BOUTONS*/

    public void add() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        // Obtenez l'inflateur de mise en page
        LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
        // Remplissez et définissez la mise en page de la boîte de dialogue
        dialogBuilder.setTitle(getResources().getString(R.string.localData));
        dialogBuilder.setCancelable(true);
        dialogBuilder.setIcon(R.drawable.ic_action_list);
        //dialog form
        final View dialogView = inflater.inflate(R.layout.fragment_local, null);
        final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
        txtId.setText("");
        final EditText txtNombre = (EditText) dialogView.findViewById(R.id.etNombre);
        txtNombre.setText("");
        // Spinner pour la sélection du type de local
        final Spinner staticSpinner = (Spinner) dialogView.findViewById(R.id.etType);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.locations_types,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set selected value
        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);
        // fin Spinner

        final EditText txtCapacite = (EditText) dialogView.findViewById(R.id.etCapacity);
        txtCapacite.setText("");
        final EditText txtDescription = (EditText) dialogView.findViewById(R.id.etDescription);
        txtDescription.setText("");
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
                String nombre = txtNombre.getText().toString();
                int type = staticSpinner.getSelectedItemPosition() + 1;
                String capacite = txtCapacite.getText().toString();
                String description = txtDescription.getText().toString();
                //validation
                Boolean valide = false;
                valide = UIUtils.checkFieldValueString(nombre, "Number");
                if (valide) {
                    valide = UIUtils.checkFieldValueInteger(type, "Type");
                }
                if (valide) {
                    valide = UIUtils.checkFieldValueString(capacite, "capacity");
                }
                if (valide) {
                    update(0, nombre, type, capacite, description);
                    ad.dismiss();
                }
            }
        });
    }

    public void update(int id, String nombre, int type, String capacite, String description) {
        //validation
        Boolean valide = false;
        valide = UIUtils.checkFieldValueString(nombre, "Number");
        if (valide) {
            valide = UIUtils.checkFieldValueInteger(type, "Type");
        }
        if (valide) {
            valide = UIUtils.checkFieldValueString(capacite, "capacity");
        }
        if (valide) {
            valide = UIUtils.checkFieldValueString(description, "description");
        }
        if (valide) {
            if (id == 0) {
                //création nouveau objet
                Local local = AppService.getLocalObject();
                local.setNombre(Integer.parseInt(nombre));
                local.setType(type);
                local.setCapacite(Integer.parseInt(capacite));
                local.setDescription(description);
                getLocalsService().create(local);
                Toast.makeText(context, getResources().getString(R.string.success_created), Toast.LENGTH_SHORT).show();
                refreshFragment(new LocalsFragment(), getActivity(), "locals");
            } else {
                Local local = AppService.getLocalObject();
                local.setId(id);
                local.setNombre(Integer.parseInt(nombre));
                local.setType(type);
                local.setCapacite(Integer.parseInt(capacite));
                local.setDescription(description);
                getLocalsService().update(local);
                Toast.makeText(context, getResources().getString(R.string.success_updated), Toast.LENGTH_SHORT).show();
                refreshFragment(new LocalsFragment(), getActivity(), "locals");
            }
        }
    }

    public void delete(int id) {
        if (id != 0) {
            if (id >= 1 && id <= 10) {
                // on bloque ici, jusqu'à la vérification ON DELETE CASCADE sera fait
                Toast.makeText(context, getResources().getString(R.string.defaultDeleteProhibited), Toast.LENGTH_LONG).show();
                refreshFragment(new LocalsFragment(), getActivity(), "locals");
            } else {
                getLocalsService().delete(getLocalsService().findById(id));
                Toast.makeText(context, getResources().getString(R.string.success_deleted), Toast.LENGTH_LONG).show();
                refreshFragment(new LocalsFragment(), getActivity(), "locals");
            }
        }

    }


    /*ADAPTER list*/
    private class MyCustomAdapter extends ArrayAdapter<Local> {

        private List<Local> localsList;

        public MyCustomAdapter(Context context, List<Local> localsList) {
            super(context, R.layout.fragment_local_list, localsList);
            this.localsList = localsList;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
            View rowView = inflater.inflate(R.layout.fragment_local_list_item, null);
            TextView txtId = (TextView) rowView.findViewById(R.id.txtId);
            txtId.setText(String.valueOf(localsList.get(position).getId()));
            TextView txtNombre = (TextView) rowView.findViewById(R.id.txtNombre);
            txtNombre.setText(String.valueOf(localsList.get(position).getNombre()));
            TextView txtType = (TextView) rowView.findViewById(R.id.txtType);
            txtType.setText(String.valueOf(localsList.get(position).getTypeNom()));
            TextView txtCapacite = (TextView) rowView.findViewById(R.id.txtCapacite);
            txtCapacite.setText(MyApplication.getAppResources().getString(R.string.capacity) + "( " + String.valueOf(localsList.get(position).getCapacite()) + " ) places");
            return rowView;
        }
    }
}
