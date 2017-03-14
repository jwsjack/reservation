package com.tp1.e_cebanu.tp1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.models.Reason;
import com.tp1.e_cebanu.tp1.models.Reservation;
import com.tp1.e_cebanu.tp1.models.User;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import java.util.Date;
import java.util.List;

import static com.tp1.e_cebanu.tp1.models.AppService.getLocalsService;
import static com.tp1.e_cebanu.tp1.models.AppService.getReasonsService;
import static com.tp1.e_cebanu.tp1.models.AppService.getUsersService;

public class ReservationsFragment extends Fragment {

    private Context context;
    private List<Local> locals;
    private List<User> users;
    private List<Reason> reasons;
    private String[] localsNames;
    private String[] usersNames;
    private String[] reasonsNames;
    private Integer[] hours = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private Integer[] minutes = new Integer[]{00, 30};
    // boutons
    private Button btAdd, btUpdate, btDelete;

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;


    public ReservationsFragment() {
        // Required empty public constructor
    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        int firstDay = cal.getActualMinimum(Calendar.DATE);
        int lastDay = cal.getActualMaximum(Calendar.DATE);


        ColorDrawable gray = new ColorDrawable(Color.GRAY);
        ColorDrawable green = new ColorDrawable(Color.GREEN);
        ArrayList<Date> disabled = new ArrayList<>();

        for (int i = firstDay; i <= lastDay; i++) {
            if (caldroidFragment != null) {
                cal.set(Calendar.DAY_OF_MONTH, i);
                List<Reservation> reservations = AppService.getReservationService().findByDate(cal);
                Date date = cal.getTime();
                ColorDrawable color = reservations.isEmpty() ? green : gray;
                caldroidFragment.setBackgroundDrawableForDate(color, date);
                caldroidFragment.setTextColorForDate(R.color.colorWhite, date);
                if (!reservations.isEmpty()) {
                    disabled.add(date);
                }
                caldroidFragment.setDisableDates(disabled);
            }
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationsFragment newInstance(String param1, String param2) {
        ReservationsFragment fragment = new ReservationsFragment();
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
        View dialogView = inflater.inflate(R.layout.fragment_reservation_list, container, false);
        context = dialogView.getContext();

        // --------------------- CALDROID - ADD RESERVATION -------------------------
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.SUNDAY);


            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_caldroid, caldroidFragment).commit();
        }

        setCustomResourceForDates();

        // Ajouter Reservation
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                // Une fois cliqué, affichez une boîte de dialogue d'alerte
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // Obtenez l'inflateur de mise en page
                LayoutInflater inflater = (LayoutInflater) MyApplication.getSystemService();
                // Remplissez et définissez la mise en page de la boîte de dialogue
                dialogBuilder.setTitle(getResources().getString(R.string.reservationData));
                dialogBuilder.setCancelable(true);
                dialogBuilder.setIcon(R.drawable.ic_action_reservation_add);
                //dialog form
                final View dialogView = inflater.inflate(R.layout.fragment_reservation, null);
                //spinner local
                final Spinner dynamicSpinnerLocal = (Spinner) dialogView.findViewById(R.id.etLocal);
                locals = getLocalsService().findAll();
                localsNames = new String[locals.size()];
                for (int i = 0; i < locals.size(); i++) {
                    localsNames[i] = String.valueOf(locals.get(i).getNombre()) + " - " + String.valueOf(locals.get(i).getTypeNom());
                }
                ArrayAdapter<String> adapterLocals = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, localsNames);
                dynamicSpinnerLocal.setAdapter(adapterLocals);
                //afficher la date
                // Show selected date
                final TextView txtDate = (TextView) dialogView.findViewById(R.id.txtDate);
                txtDate.setText(formatter.format(date));
                final TextView txtDateTymeStamp = (TextView) dialogView.findViewById(R.id.txtDateTymeStamp);
                txtDateTymeStamp.setText(String.valueOf(date.getTime()));
                //spinner pour l'heures et minutes from
                final Spinner dynamicSpinnerHoursFrom = (Spinner) dialogView.findViewById(R.id.etHourFrom);
                ArrayAdapter<Integer> adapterHoursFrom = new ArrayAdapter<Integer>(getContext(),
                        android.R.layout.simple_spinner_item, hours);
                dynamicSpinnerHoursFrom.setAdapter(adapterHoursFrom);
                dynamicSpinnerHoursFrom.setSelection(8);
                final Spinner dynamicSpinnerMinutesFrom = (Spinner) dialogView.findViewById(R.id.etMinutesFrom);
                ArrayAdapter<Integer> adapterMinutesFrom = new ArrayAdapter<Integer>(getContext(),
                        android.R.layout.simple_spinner_item, minutes);
                dynamicSpinnerMinutesFrom.setAdapter(adapterMinutesFrom);
                dynamicSpinnerMinutesFrom.setSelection(0);
                //spinner pour l'heures et minutes to
                final Spinner dynamicSpinnerHoursTo = (Spinner) dialogView.findViewById(R.id.etHourTo);
                ArrayAdapter<Integer> adapterHoursTo = new ArrayAdapter<Integer>(getContext(),
                        android.R.layout.simple_spinner_item, hours);
                dynamicSpinnerHoursTo.setAdapter(adapterHoursTo);
                dynamicSpinnerHoursTo.setSelection(9);
                final Spinner dynamicSpinnerMinutesTo = (Spinner) dialogView.findViewById(R.id.etMinutesTo);
                ArrayAdapter<Integer> adapterMinutesTo = new ArrayAdapter<Integer>(getContext(),
                        android.R.layout.simple_spinner_item, minutes);
                dynamicSpinnerMinutesTo.setAdapter(adapterMinutesTo);
                dynamicSpinnerMinutesTo.setSelection(1);

                //spinner users
                final Spinner dynamicSpinnerUser = (Spinner) dialogView.findViewById(R.id.etUser);
                users = getUsersService().findAll();
                usersNames = new String[users.size()];
                int spinnerPositionUser = 0;
                for (int i = 0; i < users.size(); i++) {
                    usersNames[i] = String.valueOf(users.get(i).getNom());
                }
                ArrayAdapter<String> adapterUsers = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, usersNames);
                dynamicSpinnerUser.setAdapter(adapterUsers);
                dynamicSpinnerUser.setSelection(spinnerPositionUser);

                //spinner reasons
                final Spinner dynamicSpinnerReason = (Spinner) dialogView.findViewById(R.id.etReason);
                reasons = getReasonsService().findAll();
                reasonsNames = new String[reasons.size()];
                int spinnerPositionReason = 0;
                for (int i = 0; i < reasons.size(); i++) {
                    reasonsNames[i] = String.valueOf(reasons.get(i).getName());
                }
                ArrayAdapter<String> adapterReasons = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, reasonsNames);
                dynamicSpinnerReason.setAdapter(adapterReasons);
                dynamicSpinnerReason.setSelection(spinnerPositionReason);

                // autre champs
                final EditText txtOtherReason = (EditText) dialogView.findViewById(R.id.etOtherReason);
                txtOtherReason.setText("");
                final EditText txtCours = (EditText) dialogView.findViewById(R.id.etCours);
                txtCours.setText("");

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
                        //Victor: TODO here add logic
                        Toast.makeText(getContext(), "RESERVATION ADD",
                                Toast.LENGTH_SHORT).show();
//                        String nom = txtName.getText().toString();
//                        String login = txtLogin.getText().toString();
//                        String password = txtPassword.getText().toString();
//                        int role = dynamicSpinner.getSelectedItemPosition();
//                        //validation
//                        Boolean valide = false;
//                        valide = UIUtils.checkFieldValueString(nom, "Name");
//                        if (valide) {
//                            valide = UIUtils.checkFieldValueString(login, "Login");
//                        }
//                        if (valide) {
//                            valide = UIUtils.checkFieldValueString(password, "Password");
//                        }
//                        if (role > roles.size()-1) {
//                            valide = false;
//                        }
//                        if (valide) {
//                            update(0, nom, login, password, role);
//                            ad.dismiss();
//                        }
                    }
                });
            }

            @Override
            public void onChangeMonth(int month, int year) {
//                String text = "month: " + month + " year: " + year;
//                Toast.makeText(getContext(), text,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
//                Toast.makeText(getContext(),
//                        "Long click " + formatter.format(date),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
//                if (caldroidFragment.getLeftArrowButton() != null) {
//                    Toast.makeText(getContext(),
//                            "Caldroid view is created", Toast.LENGTH_SHORT)
//                            .show();
//                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) dialogView.findViewById(R.id.textview);

        /**final Button customizeButton = (Button) dialogView.findViewById(R.id.customize_button);

        // Customize the calendar
        customizeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (undo) {
                    customizeButton.setText(getString(R.string.customize));
                    textView.setText("");

                    // Reset calendar
                    caldroidFragment.clearDisableDates();
                    caldroidFragment.clearSelectedDates();
                    caldroidFragment.setMinDate(null);
                    caldroidFragment.setMaxDate(null);
                    //caldroidFragment.setShowNavigationArrows(true);
                    //caldroidFragment.setEnableSwipe(true);
                    caldroidFragment.refreshView();
                    undo = false;
                    return;
                }

                // Else
                undo = true;
                customizeButton.setText(getString(R.string.undo));
                Calendar cal = Calendar.getInstance();

                // Min date is last 7 days
                cal.add(Calendar.DATE, -7);
                Date minDate = cal.getTime();

                // Max date is next 7 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 14);
                Date maxDate = cal.getTime();

                // Set selected dates
                // From Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                Date fromDate = cal.getTime();

                // To Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 3);
                Date toDate = cal.getTime();

                // Set disabled dates
                ArrayList<Date> disabledDates = new ArrayList<Date>();
                for (int i = 5; i < 8; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    disabledDates.add(cal.getTime());
                }

                // Customize
                caldroidFragment.setMinDate(minDate);
                caldroidFragment.setMaxDate(maxDate);
                caldroidFragment.setDisableDates(disabledDates);
                caldroidFragment.setSelectedDates(fromDate, toDate);
                //caldroidFragment.setShowNavigationArrows(false);
                //caldroidFragment.setEnableSwipe(false);

                caldroidFragment.refreshView();

                // Move to date
                // cal = Calendar.getInstance();
                // cal.add(Calendar.MONTH, 12);
                // caldroidFragment.moveToDate(cal.getTime());

                String text = "Today: " + formatter.format(new Date()) + "\n";
                text += "Min Date: " + formatter.format(minDate) + "\n";
                text += "Max Date: " + formatter.format(maxDate) + "\n";
                text += "Select From Date: " + formatter.format(fromDate)
                        + "\n";
                text += "Select To Date: " + formatter.format(toDate) + "\n";
                for (Date date : disabledDates) {
                    text += "Disabled Date: " + formatter.format(date) + "\n";
                }

                textView.setText(text);
            }
        });*/

        Button showDialogButton = (Button) dialogView.findViewById(R.id.add_reservation);

        final Bundle state = savedInstanceState;
        showDialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);
                Calendar cal = Calendar.getInstance();
                // Min date is last is current day
                cal.add(Calendar.DATE, 0);
                Date blueDate = cal.getTime();

                // Max date is next 30 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 30);
                Date greenDate = cal.getTime();
                dialogCaldroidFragment.setMinDate(blueDate);
                dialogCaldroidFragment.setMaxDate(greenDate);

                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getActivity().getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                dialogCaldroidFragment.show(getActivity().getSupportFragmentManager(),
                        dialogTag);
            }
        });


        // ---------------------- CALDROID - ADD RESERVATION ------------------------


        // ---------------------- RESERVATIONS LIST ----------------


        return dialogView;
    }
}
