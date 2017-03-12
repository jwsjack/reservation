package com.tp1.e_cebanu.tp1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.models.Reason;
import com.tp1.e_cebanu.tp1.models.Role;
import com.tp1.e_cebanu.tp1.models.User;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.List;

import static android.R.attr.type;
import static com.tp1.e_cebanu.tp1.R.id.txtId;
import static com.tp1.e_cebanu.tp1.R.id.txtLogin;
import static com.tp1.e_cebanu.tp1.R.id.txtType;
import static com.tp1.e_cebanu.tp1.R.string.role;
import static com.tp1.e_cebanu.tp1.models.AppService.getReasonsService;
import static com.tp1.e_cebanu.tp1.models.AppService.getRolesService;
import static com.tp1.e_cebanu.tp1.models.AppService.getUserObject;
import static com.tp1.e_cebanu.tp1.models.AppService.getUsersService;
import static com.tp1.e_cebanu.tp1.util.UIUtils.refreshFragment;

public class UsersFragment extends Fragment {
    //list view
    private View v = null;
    //lists
    private List<User> users;
    private List<Role> roles;
    private String[] rolesNames;
    private int countLines = 0;
    // boutons
    private Button btAdd, btUpdate, btDelete;
    private Context context;

    MyCustomAdapter dataAdapter = null;


    public UsersFragment() {
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
        View v = inflater.inflate(R.layout.fragment_users_list, container, false);
        context = v.getContext();

        // les boutons
        btAdd = (Button) v.findViewById(R.id.add);

        // retrieve list
        ListView listView = (ListView) v.findViewById(R.id.list);
        //donnée
        users = getUsersService().findAll();

        countLines = users.size();
        TextView text_count_lines = (TextView) v.findViewById(R.id.text_count_lines);
        text_count_lines.setText(getResources().getString(R.string.lbCountLines) + Integer.toString(countLines));

        roles = getRolesService().findAll();

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(context, users);
        // Affecter l'adaptateur à ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Une fois cliqué, affichez une boîte de dialogue d'alerte
                final User user = (User) parent.getItemAtPosition(position);

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // Obtenez l'inflateur de mise en page
                LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
                // Remplissez et définissez la mise en page de la boîte de dialogue
                // Pass null as the parent view because its going in the
                // dialog layout
                dialogBuilder.setTitle(getResources().getString(R.string.reasonData));
                dialogBuilder.setCancelable(true);
                dialogBuilder.setIcon(R.drawable.ic_action_users);
                //dialog form
                final View dialogView = inflater.inflate(R.layout.fragment_user, null);
                final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
                txtId.setText(String.valueOf(user.getId()));
                final EditText txtName = (EditText) dialogView.findViewById(R.id.etNom);
                txtName.setText(String.valueOf(user.getNom()));
                final EditText txtLogin = (EditText) dialogView.findViewById(R.id.etLogin);
                txtLogin.setText(String.valueOf(user.getLogin()));
                final EditText txtPassword = (EditText) dialogView.findViewById(R.id.etPassword);
                txtPassword.setText(String.valueOf(user.getPassword()));
                //spinner role
                final Spinner dynamicSpinner = (Spinner) dialogView.findViewById(R.id.etRole);

                rolesNames = new String[roles.size()];
                int spinnerPosition = 0;
                for (int i = 0; i < roles.size(); i++) {
                    rolesNames[i] = String.valueOf(roles.get(i).getName());
                    if (roles.get(i).getId() == user.getRole()) {
                        spinnerPosition = i;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, rolesNames);
                dynamicSpinner.setAdapter(adapter);
                dynamicSpinner.setSelection(spinnerPosition);

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
                        String nom = txtName.getText().toString();
                        String login = txtLogin.getText().toString();
                        String password = txtPassword.getText().toString();
                        int role = dynamicSpinner.getSelectedItemPosition();
                        //validation
                        Boolean valide = false;
                        valide = UIUtils.checkFieldValueString(nom, "Name");
                        if (valide) {
                            valide = UIUtils.checkFieldValueString(login, "Login");
                        }
                        if (valide) {
                            valide = UIUtils.checkFieldValueString(password, "Password");
                        }
                        if (role > roles.size()-1) {
                            valide = false;
                        }
                        if (valide) {
                            update(user.getId(), nom, login, password, role);
                            ad.dismiss();
                        }
                    }
                });
                btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(user.getId());
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
        dialogBuilder.setIcon(R.drawable.ic_action_users);
        //dialog form
        final View dialogView = inflater.inflate(R.layout.fragment_user, null);
        final EditText txtId = (EditText) dialogView.findViewById(R.id.etId);
        txtId.setText("");
        final EditText txtName = (EditText) dialogView.findViewById(R.id.etNom);
        txtName.setText("");
        final EditText txtLogin = (EditText) dialogView.findViewById(R.id.etLogin);
        txtLogin.setText("");
        final EditText txtPassword = (EditText) dialogView.findViewById(R.id.etPassword);
        txtPassword.setText("");
        //spinner role
        final Spinner dynamicSpinner = (Spinner) dialogView.findViewById(R.id.etRole);

        roles = getRolesService().findAll();
        rolesNames = new String[roles.size()];
        for (int i = 0; i < roles.size(); i++) {
            rolesNames[i] = String.valueOf(roles.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, rolesNames);
        dynamicSpinner.setAdapter(adapter);

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
                String nom = txtName.getText().toString();
                String login = txtLogin.getText().toString();
                String password = txtPassword.getText().toString();
                int role = dynamicSpinner.getSelectedItemPosition();
                //validation
                Boolean valide = false;
                valide = UIUtils.checkFieldValueString(nom, "Name");
                if (valide) {
                    valide = UIUtils.checkFieldValueString(login, "Login");
                }
                if (valide) {
                    valide = UIUtils.checkFieldValueString(password, "Password");
                }
                if (role > roles.size()-1) {
                    valide = false;
                }
                if (valide) {
                    update(0, nom, login, password, role);
                    ad.dismiss();
                }
            }
        });
    }

    public void update(int id, String nom, String login, String password ,int role) {
        //validation
        Boolean valide = false;
        valide = UIUtils.checkFieldValueString(nom, "Name");
        if (valide) {
            valide = UIUtils.checkFieldValueString(login, "Login");
        }
        if (valide) {
            valide = UIUtils.checkFieldValueString(password, "Password");
        }
        int roleId = roles.get(role).getId();

        if (valide) {
            if (id == 0) {
                //création nouveau objet
                User user = getUserObject();
                user.setNom(nom);
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(roleId);
                getUsersService().create(user);
                Toast.makeText(context, getResources().getString(R.string.success_created), Toast.LENGTH_SHORT).show();
                refreshFragment(new UsersFragment(), getActivity(), "users");
            } else {
                User user = getUserObject();
                user.setId(id);
                user.setNom(nom);
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(roleId);
                getUsersService().update(user);
                Toast.makeText(context, getResources().getString(R.string.success_updated), Toast.LENGTH_SHORT).show();
                refreshFragment(new UsersFragment(), getActivity(), "users");
            }
        }
    }

    public void delete(int id) {
        if (id != 0){
            if (id >= 1 && id <= 5) {
                Toast.makeText(context, getResources().getString(R.string.defaultDeleteProhibited), Toast.LENGTH_LONG).show();
                refreshFragment(new UsersFragment(), getActivity(), "users");
            } else {
                getUsersService().delete(getUsersService().findById(id));
                Toast.makeText(context, getResources().getString(R.string.success_deleted), Toast.LENGTH_LONG).show();
                refreshFragment(new UsersFragment(), getActivity(), "users");
            }
        }
    }

    /*ADAPTER list*/
    private class MyCustomAdapter extends ArrayAdapter<User> {

        private List<User> usersList;

        public MyCustomAdapter(Context context, List<User> usersList) {
            super(context, R.layout.fragment_users_list, usersList);
            this.usersList = usersList;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
            View rowView = inflater.inflate(R.layout.fragment_user_list_item, null);

            //trouver l'icon
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            // pour les utilisateurs qui ne correspond pas à une index dans le tableau d'images, sélectionnez dernière
            if (usersList.get(position).getRole() == 1) {
                imageView.setImageResource(R.drawable.superadmin);
            } else {
                imageView.setVisibility(View.GONE);
            }
            TextView txtId = (TextView) rowView.findViewById(R.id.txtId);
            txtId.setText(String.valueOf(usersList.get(position).getId()));
            TextView txtNom = (TextView) rowView.findViewById(R.id.txtNom);
            txtNom.setText(String.valueOf(usersList.get(position).getNom()));
            TextView txtLogin = (TextView) rowView.findViewById(R.id.txtLogin);
            txtLogin.setText(MyApplication.getAppResources().getString(R.string.login).toLowerCase() + " ( " + String.valueOf(usersList.get(position).getLogin())+ " ) ");
            TextView txtRole = (TextView) rowView.findViewById(R.id.txtRole);
            txtRole.setText(MyApplication.getAppResources().getString(role).toLowerCase() + " ( " + String.valueOf(getRolesService().findById(usersList.get(position).getRole()).getName()) + " ) ");
            return rowView;
        }
    }
}
