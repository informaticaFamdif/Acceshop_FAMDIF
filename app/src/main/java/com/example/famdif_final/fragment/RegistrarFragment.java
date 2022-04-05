package com.example.famdif_final.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.dialog.plus.ui.DialogPlus;
import com.dialog.plus.ui.DialogPlusBuilder;
import com.example.famdif_final.Controlador;
import com.example.famdif_final.FragmentName;
import com.example.famdif_final.MainActivity;
import com.example.famdif_final.MenuType;
import com.example.famdif_final.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.famdif_final.R.id.item_sign_in;


public class RegistrarFragment extends BaseFragment {
    private View v;
    private TextInputLayout nombre;
    private TextInputLayout email;
    private TextInputLayout pass;
    private TextInputLayout pass2;
    private Button btnRegistro;
    private Button seleccionarAnyo;
    private TextView errorPass;
    private TextView errorComplete;
    private TextInputLayout anyoNacimiento;

    private Spinner gradoSelector;
    private ArrayAdapter<String> adapt;

    private List<String> disc= Arrays.asList("Fisica u organica","Sensorial","Intelectual","Mental","Ninguna", "Seleccione grado de discapacidad");

    private static final String PASSWORD_PATTERN ="^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public RegistrarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setMainActivity((MainActivity) getActivity());
        //mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_registrar, container, false);
        nombre = view.findViewById(R.id.registroTextNombre);
        email = view.findViewById(R.id.registroTextEmail);
        pass = view.findViewById(R.id.registroTextPass);
        pass2 = view.findViewById(R.id.registroTextPass2);
        errorPass = view.findViewById(R.id.errorPass);
        errorComplete = view.findViewById(R.id.errorComplete);
        anyoNacimiento = view.findViewById(R.id.anyoNacimiento);
        gradoSelector = view.findViewById(R.id.spinnerPoseeDiscapacidad);

        seleccionarAnyo = view.findViewById(R.id.seleccionarAnyoBtn);
        btnRegistro = view.findViewById(R.id.registroBtnRegistrar);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        adapt = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapt.add("Fisica u organica");
        adapt.add("Sensorial");
        adapt.add("Intelectual");
        adapt.add("Mental");
        adapt.add("Ninguna");
        adapt.add("¿Posee alguna discapacidad?"); //This is the text that will be displayed as hint.


        gradoSelector.setAdapter(adapt);
        gradoSelector.setSelection(adapt.getCount()); //set the hint the default selection so it appears on launch.

        seleccionarAnyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogPlusBuilder().setSeparateActionButtons(true).blurBackground().setHeaderBgColor(R.color.white).setTitle("Año de nacimiento")
                        .setPrimaryTextColor(R.color.colorPrimary)
                        .buildYearPickerDialog(year, 1900, pickedYear ->
                            anyoNacimiento.getEditText().setText(String.valueOf(pickedYear))
                        ).show(getActivity().getSupportFragmentManager(), "Year Picker");

            }
        });



        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInClick(view);
            }
        });

        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.GONE);
        pageIcon.setImageResource(R.drawable.ic_noticias);
        pageTitle.setText("REGISTRO");;
        getMainActivity().changeMenu(MenuType.DISCONNECTED);
        getMainActivity().setOptionMenu(item_sign_in);

        return view;
    }

    private void signInClick(View view) {
        try {

            if(!isValid(pass.getEditText().getText().toString())){
                Toast.makeText(getContext(),"La contraseña no cumple con los criterios establecidos. Por favor, revisar",Toast.LENGTH_LONG).show();
                errorPass.setVisibility(View.VISIBLE);
            }else if(!pass.getEditText().getText().toString().matches(pass2.getEditText().getText().toString())){
                Toast.makeText(getContext(),"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            }else if(gradoSelector.getSelectedItem().toString() == "¿Posee alguna discapacidad?"){
                Toast.makeText(getContext(),"Por favor, marque el apartado de si posee alguna discapacidad",Toast.LENGTH_LONG).show();
                errorComplete.setVisibility(View.VISIBLE);
            }else if(nombre.getEditText().getText().toString() == ""){
                errorComplete.setVisibility(View.VISIBLE);
            }else if(email.getEditText().getText().toString() == ""){
                errorComplete.setVisibility(View.VISIBLE);
            }else if(anyoNacimiento.getEditText().getText().toString() == ""){
                errorComplete.setVisibility(View.VISIBLE);
            }
            else{
                MainActivity.mAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(),pass.getEditText().getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //REGISTRO FIRECLOUD
                            //Toast.makeText(getContext(), "Bienvenido "+email.getText().toString(), Toast.LENGTH_SHORT).show();
                            getMainActivity().clearBackStack();
                            getMainActivity().changeMenu(MenuType.USER_LOGGED);
                            getMainActivity().setFragment(FragmentName.HOME);
                            Controlador.getInstance().setUsuario(email.getEditText().getText().toString());

                            Map<String, String> userLogros = new HashMap<>();
                            userLogros.put("000000", "000000");

                            Map<String, Object> user = new HashMap<>();
                            user.put("nombre",nombre.getEditText().getText().toString());
                            user.put("email",email.getEditText().getText().toString());
                            user.put("pass",pass.getEditText().getText().toString());
                            user.put("nacidoEn", anyoNacimiento.getEditText().getText().toString());
                            user.put("discapacidad", gradoSelector.getSelectedItem().toString());
                            user.put("admin","0");
                            MainActivity.db.collection("users").document(email.getEditText().getText().toString())
                                    .set(user);
                            MainActivity.db.collection("userLogros").document(email.getEditText().getText().toString())
                                    .set(userLogros);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getMainActivity(), "El correo indicado ya se encuentra registrado", Toast.LENGTH_SHORT).show();
                        Log.i("ERROR LOGIN: ",e.getMessage());
                    }
            });
            }

        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_SHORT);
        }
    }

    public boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}