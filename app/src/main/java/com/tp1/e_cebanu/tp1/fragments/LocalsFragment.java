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
import android.widget.TextView;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.MyApplication;

import java.util.ArrayList;

import static android.R.attr.country;
import static android.R.id.edit;
import static com.tp1.e_cebanu.tp1.R.id.txtCapacite;
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
    private ArrayList<Local> locals;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private Context context;


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


//        btUpdate = (Button) v.findViewById(R.id.update);
//        btDelete = (Button) v.findViewById(R.id.delete);

        // retrieve list
        ListView listView = (ListView) v.findViewById(R.id.list);

        // Victor: TODO implement here findAll() for locals
        // salles de réunion salles de cours
        // 2165 2166 2211 2195 3213 2333 3181 3189 3248 3311
        locals = new ArrayList<Local>();
        locals.add(new Local(2165, 1, 30));
        locals.add(new Local(2166, 1, 30));
        locals.add(new Local(2211, 1, 30));
        locals.add(new Local(2195, 1, 30));
        locals.add(new Local(3213, 1, 30));
        locals.add(new Local(2333, 2, 30));
        locals.add(new Local(3181, 2, 30));
        locals.add(new Local(3189, 2, 30));
        locals.add(new Local(3248, 2, 30));
        locals.add(new Local(3311, 2, 30));


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
                final EditText txtNombre = (EditText) dialogView.findViewById(R.id.etNombre);
                txtNombre.setText(String.valueOf(local.getNombre()));
                final EditText txtType = (EditText) dialogView.findViewById(R.id.etType);
                txtType.setText(String.valueOf(local.getTypeNom()));
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
                        String nombre=txtNombre.getText().toString();
                        String type=txtType.getText().toString();
                        String capacite=txtCapacite.getText().toString();
                        update(nombre,type,capacite);
                        ad.dismiss();
                    }
                });
                btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(local.getNombre());
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
        Toast.makeText(context, "Add button!", Toast.LENGTH_LONG).show();

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
        final EditText txtNombre = (EditText) dialogView.findViewById(R.id.etNombre);
        txtNombre.setText("");
        final EditText txtType = (EditText) dialogView.findViewById(R.id.etType);
        txtType.setText("");
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
                String nombre=txtNombre.getText().toString();
                String type=txtType.getText().toString();
                String capacite=txtCapacite.getText().toString();
                update(nombre,type,capacite);
                ad.dismiss();
            }
        });
    }

    public void update(String nombre,String type,String capacite) {
        //int nombre = Integer.parseInt(String.valueOf(txtNombre));
        // Victor: TODO implement here update local by "nombre"
        Toast.makeText(context, nombre, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, type, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, capacite, Toast.LENGTH_LONG).show();
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        alertDialog.setView(v);
//        alertDialog.setTitle("Update Role");
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//        alertDialog.setPositiveButton("Save",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        dialog.cancel();
//                    }
//                });
//
//        alertDialog.show();
    }

    public void delete(int nombre) {
        // Victor: TODO implement here delete local by "nombre"
        Toast.makeText(context, "Delete local - " + String.valueOf(nombre), Toast.LENGTH_LONG).show();
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        alertDialog.setView(v);
//        alertDialog.setTitle("Delete Role");
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//        alertDialog.setPositiveButton("Delete",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        dialog.cancel();
//                    }
//                });
//
//        alertDialog.show();
    }


    /*ADAPTER*/

    //http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html
    //http://www.androidcode.ninja/android-sqlite-tutorial/

    private class MyCustomAdapter extends ArrayAdapter<Local> {

        private ArrayList<Local> localsList;

        public MyCustomAdapter(Context context, ArrayList<Local> localsList) {
            super(context, R.layout.fragment_local_list, localsList);
            this.localsList = new ArrayList<Local>();
            this.localsList.addAll(localsList);
        }

        private class ViewHolder {
            TextView nombre, type, capacite;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
            View rowView = inflater.inflate(R.layout.fragment_local_list_item, null);

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
