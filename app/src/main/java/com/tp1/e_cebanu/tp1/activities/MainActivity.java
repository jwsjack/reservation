package com.tp1.e_cebanu.tp1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tp1.e_cebanu.tp1.fragments.SettingsFragment;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.fragments.HomeFragment;
import com.tp1.e_cebanu.tp1.fragments.UsersFragment;
import com.tp1.e_cebanu.tp1.fragments.LocalsFragment;
import com.tp1.e_cebanu.tp1.fragments.ReasonsFragment;
import com.tp1.e_cebanu.tp1.fragments.ReservationsFragment;
import com.tp1.e_cebanu.tp1.fragments.RolesFragment;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.other.CircleTransform;
import com.tp1.e_cebanu.tp1.util.UIUtils;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // Urls pour charger l'en-tête de navigation
    // et image de profil
    private static final String urlNavHeaderBg = "http://www.infocurse.com/wp-content/uploads/2015/06/Block-Calls-On-Your-Android-Lollipop-1.jpg";
    private static final String urlProfileImg = "https://i.mycdn.me/image?id=550056883481&t=52&plc=WEB&tkn=*3rdychwoo66exsEYQo7KB8wTzYA";

    // l'index pour identifier l'élément de menu nav current
    public static int navItemIndex = 0;

    // les tags utilisés pour définir les fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_USERS = "users";
    private static final String TAG_RESERVATIONS = "reservations";
    private static final String TAG_REASONS = "reasons";
    private static final String TAG_LOCALS = "locals";
    private static final String TAG_ROLES = "roles";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_GUIDE = "guide";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_LOGOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;


    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppService.getLiu().getRole() == 1) {
            // Liste menu complet
            setContentView(R.layout.activity_main);
        } else {
            // Configurer la liste des menus pour l'utilisateur simple
            setContentView(R.layout.activity_main_user);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


//        //VICTOR: get all users
//        User userTest = new User();
//        userTest.setId(833);
//        userTest.setNom("IVAN");
//        userTest.setLogin("ivan");
//        userTest.setPassword("ivan");
//        userTest.setRole(1);
//
//
//        AppService.getUsersService().create(userTest);
//        List<User> users = AppService.getUsersService().findAll();
//        for (User user:users) {
//            System.out.println("USER: " + user.toString());
//            Log.i("USER: ", user.toString());
//        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // nome, website
        txtName.setText("Eugeniu Cebanu");
        txtWebsite.setText("www.jackprof.com");

        // Chargement de l'en-tête image de fond
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Chargement de l'image du profil
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // Affichant le point à côté de l'étiquette des notifications
        if (AppService.getLiu().getRole() == 1) {
            navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
        }

        //Personnaliser l'élément de menu sélectionné
//        for(int i = 0; i < navigationView.getMenu().size(); i++){
//            MenuItem menuItem = navigationView.getMenu().getItem(i);
//            if(menuItem.isChecked()) {
//                SpannableString s = new SpannableString(menuItem.getTitle());
//                s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getBaseContext(), R.color.colorWhite)), 0, s.length(), 0);
//                menuItem.setTitle(s);
//            } else {
//                SpannableString s = new SpannableString(menuItem.getTitle());
//                s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getBaseContext(), R.color.text_shadow)), 0, s.length(), 0);
//                menuItem.setTitle(s);
//            }
//        }
    }
    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // users
                UsersFragment usersFragment = new UsersFragment();
                return usersFragment;
            case 2:
                // reservations
                ReservationsFragment reservationsFragment = new ReservationsFragment();
                return reservationsFragment;
            case 3:
                // reasons
                ReasonsFragment reasonsFragment = new ReasonsFragment();
                return reasonsFragment;
            case 4:
                // locals
                LocalsFragment localsFragment = new LocalsFragment();
                return localsFragment;
            case 5:
                // roles
                RolesFragment rolesFragment = new RolesFragment();
                return rolesFragment;

            case 6:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(UIUtils.fromHtml("<big><b><font color='#FFFFFF'>"+activityTitles[navItemIndex]+"</font></b></big>"));
        getSupportActionBar().setSubtitle(UIUtils.fromHtml("<small><font color='"+UIUtils.getColor(R.color.colorSuccess)+"'>" + getResources().getString(R.string.active).toLowerCase() + "</font>: "  + AppService.getLiu().getLogin() + "</small>"));
    }

    private void selectNavMenu() {
        MenuItem menuItem = navigationView.getMenu().getItem(navItemIndex);
        menuItem.setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_users:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_USERS;
                        break;
                    case R.id.nav_reservations:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_RESERVATIONS;
                        break;
                    case R.id.nav_reasons:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_REASONS;
                        break;
                    case R.id.nav_locals:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_LOCALS;
                        break;
                    case R.id.nav_roles:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ROLES;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    //des activités

                    case R.id.nav_profile:
                        // Lancer une Inetent au lieu de charger un fragment
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_guide:
                        // Lancer une Inetent au lieu de charger un fragment
                        startActivity(new Intent(MainActivity.this, GuideActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_logout:
                        // Lancer une Inetent au lieu de charger un fragment

                        finishAffinity();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            //getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        /*
        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }
        */

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }
}
