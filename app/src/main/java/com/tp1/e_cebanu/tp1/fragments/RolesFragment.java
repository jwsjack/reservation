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
import com.tp1.e_cebanu.tp1.models.Role;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RolesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RolesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RolesFragment extends Fragment {
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
    private List<Role> roles;
    private int countLines = 0;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private Context context;

    MyCustomAdapter dataAdapter = null;

    private OnFragmentInteractionListener mListener;

    public RolesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RolesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RolesFragment newInstance(String param1, String param2) {
        RolesFragment fragment = new RolesFragment();
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
        roles = AppService.getRolesService().findAll();

        countLines = roles.size();
        TextView text_count_lines = (TextView) v.findViewById(R.id.text_count_lines);
        text_count_lines.setText(getResources().getString(R.string.lbCountLines) + Integer.toString(countLines));

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(context, roles);
        // Affecter l'adaptateur à ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Une fois cliqué, affichez une boîte de dialogue d'alerte
                final Role role = (Role) parent.getItemAtPosition(position);

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // Obtenez l'inflateur de mise en page
                LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
                // Remplissez et définissez la mise en page de la boîte de dialogue
                // Pass null as the parent view because its going in the
                // dialog layout
                dialogBuilder.setTitle(getResources().getString(R.string.roleData));
                dialogBuilder.setCancelable(true);
                dialogBuilder.setIcon(R.drawable.ic_action_list);
                //dialog form
                final View dialogView = inflater.inflate(R.layout.fragment_role, null);
                final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
                txtId.setText(String.valueOf(role.getId()));
                final EditText txtName = (EditText) dialogView.findViewById(R.id.etName);
                txtName.setText(String.valueOf(role.getName()));

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
                        delete(role.getId());
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
        // Une fois cliqué, affichez une boîte de dialogue d'alerte
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        // Obtenez l'inflateur de mise en page
        LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
        // Remplissez et définissez la mise en page de la boîte de dialogue
        dialogBuilder.setTitle(getResources().getString(R.string.localData));
        dialogBuilder.setCancelable(true);
        dialogBuilder.setIcon(R.drawable.ic_action_list);
        //dialog form
        final View dialogView = inflater.inflate(R.layout.fragment_role, null);
        final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
        txtId.setText("");
        final EditText txtName = (EditText) dialogView.findViewById(R.id.etName);
        txtName.setText("");

        btUpdate = (Button) dialogView.findViewById(R.id.update);
        btDelete = (Button) dialogView.findViewById(R.id.delete);
        btDelete.setVisibility(View.GONE);

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
                Role role = AppService.getRoleObject();
                role.setName(name);

            } else {
                // Victor: TODO implement here update local by "id"
                Role role = AppService.getRoleObject();
                role.setId(id);
                role.setName(name);

                Toast.makeText(context, String.valueOf(name), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete(int id) {
        // Victor: TODO implement here delete Role by "id"
        Toast.makeText(context, "Delete role - " + String.valueOf(id), Toast.LENGTH_LONG).show();

    }

    /*ADAPTER list*/
    private class MyCustomAdapter extends ArrayAdapter<Role> {

        private List<Role> rolesList;

        public MyCustomAdapter(Context context, List<Role> rolesList) {
            super(context, R.layout.fragment_roles_list, rolesList);
            this.rolesList = rolesList;
        }
//
//        private class ViewHolder {
//            TextView nombre, type, capacite;
//        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
            View rowView = inflater.inflate(R.layout.fragment_role_list_item, null);

            TextView txtId = (TextView) rowView.findViewById(R.id.txtId);
            txtId.setText(String.valueOf(rolesList.get(position).getId()));
            TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
            txtName.setText(String.valueOf(rolesList.get(position).getName()));
            return rowView;
        }
    }
}
