package com.example.famdif_final;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.famdif_final.fragment.AboutUsFragment;
import com.example.famdif_final.fragment.AccederFragment;
import com.example.famdif_final.fragment.AchievementsFragment;
import com.example.famdif_final.fragment.BusquedaFragment;
import com.example.famdif_final.fragment.BusquedaSugerenciaFragment;
import com.example.famdif_final.fragment.BusquedaSugerenciaFragmentResult;
import com.example.famdif_final.fragment.BusquedaTiendaValorarResultFragment;
import com.example.famdif_final.fragment.BusquedaUsuarioFragment;
import com.example.famdif_final.fragment.BusquedaValorarTiendaFragment;
import com.example.famdif_final.fragment.CrearTiendaFragment;
import com.example.famdif_final.fragment.EditShopFragment;
import com.example.famdif_final.fragment.HomeFragment;
import com.example.famdif_final.fragment.IndexFragment;
import com.example.famdif_final.fragment.ListaUsuariosBorrarFragment;
import com.example.famdif_final.fragment.MapsFragment;
import com.example.famdif_final.fragment.MensajeSeleccionadoFragment;
import com.example.famdif_final.fragment.MessagesFragment;
import com.example.famdif_final.fragment.MySuggestionsFragment;
import com.example.famdif_final.fragment.NewsFragment;
import com.example.famdif_final.fragment.PollsFragment;
import com.example.famdif_final.fragment.RegistrarFragment;
import com.example.famdif_final.fragment.SuggestionFragment;
import com.example.famdif_final.fragment.TiendaSeleccionadaFragment;
import com.example.famdif_final.fragment.polls.RegisterPollFragment;
import com.example.famdif_final.fragment.polls.TestPollFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private DrawerLayout drawerLayout;
    public static FirebaseAuth mAuth;
    public static FirebaseFirestore db;
    public static DatabaseReference databaseReference;
    public static DatabaseReference image;
    public static StorageReference mStorageRef;
    public static Boolean isCheckedPrivacidad = false;

    public static LocationManager locationManager;
    public static LocationListener locationListener;
    public static Location loca; //Pa loca tú, calva

    public static ArrayList<String> logrosUsuario = new ArrayList<>();

    public static final int PICK_IMAGE_REQUEST=1;

    private static long presionado;

    public GPSManager gpsManager;

    public static Controlador controlador;

    SharedPreferences sharedPreferences;

    public static final String filename = "login";
    public static final String username = "username";

    public static String nombreComercioFiltros = "";
    public static int tipoComercioFiltros = -1;
    public static int subtipoComercioFiltros = -1;
    public static int distanciaComercioFiltros = -1;
    public static int accesibilidadComercioFiltros = -1;

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view.findViewById(R.id.checkboxPrivacidad)).isChecked();
        if (checked) {
            isCheckedPrivacidad = true;
        };
    }

    protected void onCreate(Bundle savedInstanceState) {

        controlador=Controlador.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://famdiffinal-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        image=FirebaseDatabase.getInstance("https://famdiffinal-default-rtdb.europe-west1.firebasedatabase.app/").getReference("/");
        mStorageRef = FirebaseStorage.getInstance().getReference("/");
        gpsManager = new GPSManager(this);
        Controlador.getInstance().getCurrentPosition();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView pageTitle = findViewById(R.id.toolbar_title);
        ImageView pageIcon = findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(View.GONE);
        pageTitle.setText("ACCEDE");

        int nightModeFlags =  getBaseContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            pageTitle.setTextColor(Color.WHITE);
        }else {
            pageTitle.setTextColor(Color.BLACK);
        }

        if (getSharedPreferences(filename, Context.MODE_PRIVATE).contains(username)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Hello " + username);
            // Create the AlertDialog object and return it
            builder.create();
        }

        ActionBar actionBar = getSupportActionBar();
   //     if (actionBar != null) {
   //         actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_hamburguesa);
   //         actionBar.setDisplayHomeAsUpEnabled(true);
   //     }

        //drawerLayout = findViewById(R.id.index_layout);

        NavigationView navigationView = findViewById(R.id.navigation_index_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);
        if (null == mAuth.getCurrentUser() || mAuth.getCurrentUser().getEmail().isEmpty()) {
            Log.d("user", "empty");
            setFragment(FragmentName.INDEX);
        } else {
            Log.d("user", "not empty");
            setFragment(FragmentName.HOME);
            ArrayList<String> arrayListAux = new ArrayList<String>();
            MainActivity.db.collection("userLogros").document(mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> ds) {
                    if (ds.isSuccessful()) {
                        DocumentSnapshot document = ds.getResult();
                        if (document.exists()) {
                            for (Object h : document.getData().values()) {
                                arrayListAux.add(h.toString());
                            }
                            MainActivity.logrosUsuario = arrayListAux;
                        } else {
                            Log.d("No such document", "");
                        }
                    } else {
                        Log.d("get failed with ", ds.getException().toString());
                    }

                }
            });
            Log.d("email del tolay",mAuth.getCurrentUser().getEmail());
        }

    }


    public NavigationView getNavigationView() {
        return (NavigationView) findViewById(R.id.navigation_index_view);
    }

    public void setOptionMenu(int res) {
        getNavigationView().getMenu().findItem(res).setChecked(true);
    }

    public void changeMenu(MenuType menuType) {
        getNavigationView().getMenu().clear();
        switch (menuType) {
            case DISCONNECTED:
                getNavigationView().inflateMenu(R.menu.index_menu);
                break;
            case USER_LOGGED:
                getNavigationView().inflateMenu(R.menu.home_menu);
                break;
            case ADMIN_LOGGED:
                getNavigationView().inflateMenu(R.menu.admin_menu);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void showPopup(View view, int id) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(id, popup.getMenu());
        this.setupPopupMenuRedirectContent(popup);
        popup.setForceShowIcon(true);
        popup.show();
    }

    private void setupPopupMenuRedirectContent(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_index:
                                setFragment(FragmentName.INDEX);
                                break;

                            case R.id.item_home:
                                setFragment(FragmentName.HOME);
                                break;

                            case R.id.item_log_in:
                                setFragment(FragmentName.LOG_IN);
                                break;

                            case R.id.item_add_shop:
                                setFragment(FragmentName.ADD_SHOP);
                                break;

                            case R.id.item_search:
                                setFragment(FragmentName.SEARCH);
                                break;

                            case R.id.item_suggestions:
                                setFragment(FragmentName.SUGGESTIONS);
                                break;

                            case R.id.item_my_suggestions:
                                setFragment(FragmentName.MY_SUGGESTIONS);
                                break;

                            case R.id.item_log_out:
                                logOut();
                                break;

                            case R.id.item_sign_in:
                                setFragment(FragmentName.SIGN_IN);
                                break;

                            case R.id.item_delete_user:
                                setFragment(FragmentName.DELETE_USER);
                                break;

                            case R.id.item_view_suggestions:
                                setFragment(FragmentName.VIEW_SUGGESTIONS);
                                break;

                            case R.id.item_rate_shops:
                                setFragment(FragmentName.RATE_SHOP);
                                break;

                            case R.id.item_about_us:
                                setFragment(FragmentName.ABOUT_US);
                                break;
                        }

                        return true;
                    }
                }
        );
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_index:
                                setFragment(FragmentName.INDEX);
                                break;

                            case R.id.item_home:
                                setFragment(FragmentName.HOME);
                                break;

                            case R.id.item_log_in:
                                setFragment(FragmentName.LOG_IN);
                                break;

                            case R.id.item_add_shop:
                                setFragment(FragmentName.ADD_SHOP);
                                break;

                            case R.id.item_search:
                                setFragment(FragmentName.SEARCH);
                                break;

                            case R.id.item_suggestions:
                                setFragment(FragmentName.SUGGESTIONS);
                                break;

                            case R.id.item_my_suggestions:
                                setFragment(FragmentName.MY_SUGGESTIONS);
                                break;

                            case R.id.item_log_out:
                                logOut();
                                break;

                            case R.id.item_sign_in:
                                setFragment(FragmentName.SIGN_IN);
                                break;

                            case R.id.item_delete_user:
                                setFragment(FragmentName.DELETE_USER);
                                break;

                            case R.id.item_view_suggestions:
                                setFragment(FragmentName.VIEW_SUGGESTIONS);
                                break;

                            case R.id.item_rate_shops:
                                setFragment(FragmentName.RATE_SHOP);
                                break;

                            case R.id.item_about_us:
                                setFragment(FragmentName.ABOUT_US);
                                break;
                        }

                        //drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );
    }


    public void setFragment(FragmentName options) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (options) {
            case INDEX:
                IndexFragment indexHomeFragment = new IndexFragment();
                fragmentTransaction.replace(R.id.index_fragment, indexHomeFragment);
                break;
            case SIGN_IN:
                RegistrarFragment signInFragment = new RegistrarFragment();
                fragmentTransaction.replace(R.id.index_fragment, signInFragment);
                break;
            case LOG_IN:
                AccederFragment logInFragment = new AccederFragment();
                fragmentTransaction.replace(R.id.index_fragment, logInFragment);
                break;
            case HOME:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.index_fragment, homeFragment);
                break;
            case SEARCH:
                BusquedaFragment busquedaFragment = new BusquedaFragment();
                fragmentTransaction.replace(R.id.index_fragment, busquedaFragment);
                break;
            case SUGGESTIONS:
                SuggestionFragment suggestionFragment = new SuggestionFragment();
                fragmentTransaction.replace(R.id.index_fragment, suggestionFragment);
                break;
            case MY_SUGGESTIONS:
                MySuggestionsFragment mySuggestionsFragment = new MySuggestionsFragment();
                fragmentTransaction.replace(R.id.index_fragment,mySuggestionsFragment);
                break;
            case ADD_SHOP:
                CrearTiendaFragment crearTiendaFragment = new CrearTiendaFragment();
                fragmentTransaction.replace(R.id.index_fragment,crearTiendaFragment);
                break;
            case MAP:
                MapsFragment mapsFragment = new MapsFragment();
                fragmentTransaction.replace(R.id.index_fragment, mapsFragment);
                break;
            case SEARCH_RESULT_DETAILS:
                TiendaSeleccionadaFragment tiendaSeleccionadaFragment = new TiendaSeleccionadaFragment();
                fragmentTransaction.replace(R.id.index_fragment, tiendaSeleccionadaFragment);
                break;

            case MESSAGE_DETAILS:
                MensajeSeleccionadoFragment mensajeSeleccionadoFragment = new MensajeSeleccionadoFragment();
                fragmentTransaction.replace(R.id.index_fragment, mensajeSeleccionadoFragment);
                break;

            case EDIT_SHOP:
                EditShopFragment editShopFragment = new EditShopFragment();
                fragmentTransaction.replace(R.id.index_fragment,editShopFragment);
                break;

            case DELETE_USER:
                BusquedaUsuarioFragment busquedaUsuarioFragment = new BusquedaUsuarioFragment();
                fragmentTransaction.replace(R.id.index_fragment,busquedaUsuarioFragment);
                break;

            case LIST_USUARIOS_BORRAR:
                ListaUsuariosBorrarFragment listaUsuariosBorrarFragment = new ListaUsuariosBorrarFragment();
                fragmentTransaction.replace(R.id.index_fragment,listaUsuariosBorrarFragment);
                break;

            case NEWS_FRAGMENT:
                NewsFragment newsFragment = new NewsFragment();
                fragmentTransaction.replace(R.id.index_fragment,newsFragment);
                break;

            case POLLS_FRAGMENT:
                PollsFragment pollsFragment = new PollsFragment();
                fragmentTransaction.replace(R.id.index_fragment,pollsFragment);
                break;

            case MESSAGES_FRAGMENT:
                MessagesFragment messagesFragment = new MessagesFragment();
                fragmentTransaction.replace(R.id.index_fragment,messagesFragment);
                break;

            case ACHIEVEMENTS_FRAGMENT:
                AchievementsFragment achievementsFragment = new AchievementsFragment();
                fragmentTransaction.replace(R.id.index_fragment,achievementsFragment);
                break;

            case VIEW_SUGGESTIONS:
                BusquedaSugerenciaFragment busquedaSugerenciaFragment = new BusquedaSugerenciaFragment();
                fragmentTransaction.replace(R.id.index_fragment,busquedaSugerenciaFragment);
                break;

            case VIEW_SUGGESTIONS_RESULT:
                BusquedaSugerenciaFragmentResult busquedaSugerenciaFragmentResult = new BusquedaSugerenciaFragmentResult();
                fragmentTransaction.replace(R.id.index_fragment,busquedaSugerenciaFragmentResult);
                break;

            case RATE_SHOP:
                BusquedaValorarTiendaFragment busquedaValorarTiendaFragment = new BusquedaValorarTiendaFragment();
                fragmentTransaction.replace(R.id.index_fragment,busquedaValorarTiendaFragment);
                break;

            case RATE_SHOP_RESULT:
                BusquedaTiendaValorarResultFragment busquedaTiendaValorarResultFragment = new BusquedaTiendaValorarResultFragment();
                fragmentTransaction.replace(R.id.index_fragment,busquedaTiendaValorarResultFragment);
                break;

            case ABOUT_US:
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                fragmentTransaction.replace(R.id.index_fragment,aboutUsFragment);
                break;

            //POLLS: Estas variables sirven para abrir las vistas de las distintas encuestas.
            case REGISTER_POLL:
                RegisterPollFragment registerPollFragment = new RegisterPollFragment();
                fragmentTransaction.replace(R.id.index_fragment, registerPollFragment);
                break;

            case TEST_POLL:

                TestPollFragment testPollFragment = new TestPollFragment();
                fragmentTransaction.replace(R.id.index_fragment, testPollFragment);
                break;
        }

        ((FragmentTransaction) fragmentTransaction).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }

    public void clearBackStack1() {
        FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
    }


    public void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                changeMenu(MenuType.DISCONNECTED);
                if(mAuth!=null){
                    Toast.makeText(getBaseContext(), "Hasta la proxima "+Controlador.getInstance().getUsuario(),Toast.LENGTH_LONG).show();
                    mAuth.signOut();
                    clearBackStack();
                    Controlador.getInstance().setNombreUsuarioActual(null);
                    Controlador.getInstance().setAdmin(0);
                    setFragment(FragmentName.INDEX);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public GPSManager getGpsManager() {
        return gpsManager;
    }


    @Override
    public void onBackPressed(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Si tiene fragments simplemente ejecuta la pulsación Back normal
        if (fragmentManager.getBackStackEntryCount()>1)
            super.onBackPressed();
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de la aplicación?")
                    .setTitle("CERRAR APLICACION");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


}