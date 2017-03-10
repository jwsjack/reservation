package com.tp1.e_cebanu.tp1.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.country;
import static android.R.id.edit;
import static com.tp1.e_cebanu.tp1.R.id.list;
import static com.tp1.e_cebanu.tp1.R.id.txtCapacite;
import static com.tp1.e_cebanu.tp1.R.id.txtId;
import static com.tp1.e_cebanu.tp1.R.string.localData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocalsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //list view
    private View v = null;
    //lists
    private String[] menuItems;
    private List<Local> locals;
    private int countLines = 0;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private Context context;
    private Spinner spinnerType;


    MyCustomAdapter dataAdapter = null;


    private OnFragmentInteractionListener mListener;

    public LocalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalsFragment newInstance(String param1, String param2) {
        LocalsFragment fragment = new LocalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        locals = AppService.getLocalsService().findAll();

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

//                final EditText txtType = (EditText) dialogView.findViewById(R.id.etType);
//                txtType.setText(String.valueOf(local.getTypeNom()));

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
                        String nombre = txtNombre.getText().toString();
                        int type = staticSpinner.getSelectedItemPosition() + 1;
                        String capacite = txtCapacite.getText().toString();
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
                            update(id, nombre, type, capacite);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        btUpdate = (Button) dialogView.findViewById(R.id.update);
        btDelete = (Button) dialogView.findViewById(R.id.delete);
        btDelete.setVisibility(View.GONE);

        dialogBuilder.setView(dialogView);
        dialogBuilder.create();
        // pour fermer le dialog
        final AlertDialog ad = dialogBuilder.show();

        //buttons
        dialogBuilder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtId.getText().toString();
                String nombre = txtNombre.getText().toString();
                int type = staticSpinner.getSelectedItemPosition() + 1;
                String capacite = txtCapacite.getText().toString();
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
                    update(id, nombre, type, capacite);
                    ad.dismiss();
                }
            }
        });
    }

    public void update(String id, String nombre, int type, String capacite) {
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
            if (id.equals(null) || id.equals("")) {
                //création nouveau objet
                Toast.makeText(context, String.valueOf(type), Toast.LENGTH_SHORT).show();
                // Victor: TODO implement here add local by "id"
                Local local = AppService.getLocalObject();
                local.setNombre(Integer.parseInt(nombre));
                local.setType(type);
                local.setCapacite(Integer.parseInt(capacite));

            } else {
                // Victor: TODO implement here update local by "id"
                Local local = AppService.getLocalObject();
                local.setId(Integer.parseInt(id));
                local.setNombre(Integer.parseInt(nombre));
                local.setType(type);
                local.setCapacite(Integer.parseInt(capacite));


                Toast.makeText(context, String.valueOf(type), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete(int id) {
        // Victor: TODO implement here delete local by "id"
        Toast.makeText(context, "Delete local - " + String.valueOf(id), Toast.LENGTH_LONG).show();

    }


    /*ADAPTER list*/
    private class MyCustomAdapter extends ArrayAdapter<Local> {

        private List<Local> localsList;

        public MyCustomAdapter(Context context, List<Local> localsList) {
            super(context, R.layout.fragment_local_list, localsList);
            this.localsList = localsList;
        }
//
//        private class ViewHolder {
//            TextView nombre, type, capacite;
//        }

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
