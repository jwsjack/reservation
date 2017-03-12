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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.models.Role;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.List;

import static com.tp1.e_cebanu.tp1.models.AppService.getRolesService;
import static com.tp1.e_cebanu.tp1.util.UIUtils.refreshFragment;

public class RolesFragment extends Fragment {
    //list view
    private View v = null;
    //lists
    private List<Role> roles;
    private int countLines = 0;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private CheckBox ckSuperadmin;
    private Context context;

    MyCustomAdapter dataAdapter = null;


    public RolesFragment() {
        // Constructeur vide obligatoire
    }

    public static RolesFragment newInstance() {
        RolesFragment fragment = new RolesFragment();
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
        roles = getRolesService().findAll();

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
                // pour les utilisateurs qui ne correspond pas à une index dans le tableau d'images, sélectionnez dernière
                //trouver l'icon
                final ImageView imageView = (ImageView) dialogView.findViewById(R.id.img);
                // pour les superadmin on va afficher l'icon
                if (role.getSuperadmin() == 1) {
                    imageView.setImageResource(R.drawable.superadmin);
                } else {
                    imageView.setImageResource(R.drawable.user1);
                }

                final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
                txtId.setText(String.valueOf(role.getId()));
                final EditText txtName = (EditText) dialogView.findViewById(R.id.etName);
                txtName.setText(String.valueOf(role.getName()));
                ckSuperadmin = (CheckBox) dialogView.findViewById(R.id.ckSuperadmin);
                if (role.getSuperadmin() == 1) {
                    ckSuperadmin.setChecked(true);
                    if (role.getId() == 1) {
                        ckSuperadmin.setEnabled(false);
                    }
                }
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
                        int superadmin = ckSuperadmin.isChecked() ? 1 : 0;
                        //validation
                        Boolean valide = false;
                        valide = UIUtils.checkFieldValueString(name, "Name");
                        if (valide) {
                            update(Integer.parseInt(id), name, superadmin);
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
        final ImageView imageView = (ImageView) dialogView.findViewById(R.id.img);
        imageView.setVisibility(View.GONE);
        final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
        txtId.setText("");
        final EditText txtName = (EditText) dialogView.findViewById(R.id.etName);
        txtName.setText("");
        final CheckBox ckSuperadmin = (CheckBox) dialogView.findViewById(R.id.ckSuperadmin);

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
                int superadmin = ckSuperadmin.isChecked() ? 1 : 0;
                //validation
                Boolean valide = false;
                valide = UIUtils.checkFieldValueString(name, "Name");
                if (valide) {
                    update(0, name, superadmin);
                    ad.dismiss();
                }
            }
        });
    }

    public void update(int id, String name, int superadmin) {
        //validation
        Boolean valide = false;
        valide = UIUtils.checkFieldValueString(name, "Name");
        if (valide) {
            if (id == 0) {
                //création nouveau objet
                Toast.makeText(context, String.valueOf(name), Toast.LENGTH_SHORT).show();
                Role role = AppService.getRoleObject();
                role.setName(name);
                role.setSuperadmin(superadmin);
                getRolesService().create(role);
                Toast.makeText(context, getResources().getString(R.string.success_created), Toast.LENGTH_SHORT).show();
                refreshFragment(new RolesFragment(), getActivity(), "roles");
            } else {
                Role role = AppService.getRoleObject();
                role.setId(id);
                role.setName(name);
                role.setSuperadmin(superadmin);
                getRolesService().update(role);
                Toast.makeText(context, getResources().getString(R.string.success_updated), Toast.LENGTH_SHORT).show();
                refreshFragment(new RolesFragment(), getActivity(), "roles");
            }
        }
    }

    public void delete(int id) {
        if (id != 0){
            if (id == 1) {
                Toast.makeText(context, getResources().getString(R.string.roleAdminDeleteProhibited), Toast.LENGTH_LONG).show();
                refreshFragment(new RolesFragment(), getActivity(), "roles");
            } else {
                getRolesService().delete(getRolesService().findById(id));
                Toast.makeText(context, getResources().getString(R.string.success_deleted), Toast.LENGTH_LONG).show();
                refreshFragment(new RolesFragment(), getActivity(), "roles");
            }
        }

    }

    /*ADAPTER list*/
    private class MyCustomAdapter extends ArrayAdapter<Role> {

        private List<Role> rolesList;

        public MyCustomAdapter(Context context, List<Role> rolesList) {
            super(context, R.layout.fragment_roles_list, rolesList);
            this.rolesList = rolesList;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
            View rowView = inflater.inflate(R.layout.fragment_role_list_item, null);
            //trouver l'icon
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            // pour les superadmin on va afficher l'icon
            if (rolesList.get(position).getSuperadmin() == 1) {
                imageView.setImageResource(R.drawable.ic_superadmin);
            }
            TextView txtId = (TextView) rowView.findViewById(R.id.txtId);
            txtId.setText(String.valueOf(rolesList.get(position).getId()));
            TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
            txtName.setText(String.valueOf(rolesList.get(position).getName()));
            return rowView;
        }
    }
}
