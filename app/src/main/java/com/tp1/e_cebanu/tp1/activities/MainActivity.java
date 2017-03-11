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


/*
* Java# version 1.8
*
* @name       TP_1
* @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
* @author     EUGENIU CEBANU / matricule: 20025851
* @author     jwsjack3@gmail.com
* @version    1
* @date       2017-02-20
* @description Contrôleur principale - Gérer toutes les opérations dans le menu
*/

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


    // Titres de la barre d'outils respectés à l'élément de menu nav sélectionné
    private String[] activityTitles;

    // Flag pour charger le fragment de HOME lorsque l'utilisateur clique la touche de retour (back key)
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

        // Charger les titres de la barre d'outils à partir des ressources de chaînes
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Charger les données d'en-tête du menu de navigation
        loadNavHeader();

        // Initialisation du menu de navigation
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

     /**
     * Charger les informations d'en-tête du menu de navigation
     * Comme image de fond, image de profil
     * Nom, site web, notifications action view (point)
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
    }
    /***
     * Retourne le fragment respecté de cet utilisateur
     * Sélectionné dans le menu de navigation
     */
    private void loadHomeFragment() {
        // Sélectionner l'élément de menu nav approprié
        selectNavMenu();

        // Définir le titre de la barre d'outils
        setToolbarTitle();

        // Si l'utilisateur choisit de nouveau le menu de navigation actuel, ne rien faire
        // fermez simplement le drawer de navigation
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // Afficher ou cacher le bouton fab
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

        // Afficher ou cacher le bouton fab
        toggleFab();

        //Fermeture de drawer sur le clique d'item
        drawer.closeDrawers();

        // Actualiser le menu de la barre d'outils
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
                //Vérifier si l'item est en état sélectionné ou non, si ce n'est pas le faire en état de vérification
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
                // Code ici sera déclenché une fois que le drawer fermé car nous ne voulons pas que quelque chose se produise alors nous laissons ce blanc
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                // Code ici sera déclenché une fois que le drawer ouvert car nous ne voulons pas que quelque chose se produise alors nous laissons ce blanc
                super.onDrawerOpened(drawerView);
            }
        };

        //Mise le actionbarToggle sur drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        // appel d'état de synchronisation est nécessaire ou bien votre icône hamburger ne s'affiche pas
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
        //Ce code charge le fragment HOME lorsque la touche de retour est pressé
        // quand l'utilisateur se trouve dans l'autre fragment que HOME
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
        // Inflater le menu; cela ajoute des éléments à la barre d'action s'il est présent.

        // Afficher le menu uniquement lorsque le fragment HOME est sélectionné
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // Pour les éléments spécifiques (ex: locals) du menu sur chargeur le menu personalisee
        if (navItemIndex == 3) {
            //getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Poignée barre d'action article clique ici(Handle action bar item clicks here.)
        // La barre d'action va gérer automatiquement les clics sur le Home/Up button,
        // aussi longtemps que vous spécifiez une activité parent dans AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Afficher ou cacher le bouton fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }
}
