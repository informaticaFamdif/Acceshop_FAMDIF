package com.famdif.famdif_final.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.dialog.plus.ui.DialogPlus;
import com.dialog.plus.ui.DialogPlusBuilder;
import com.famdif.famdif_final.Controlador;
import com.famdif.famdif_final.FragmentName;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.MenuType;
import com.famdif.famdif_final.R;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
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
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.famdif.famdif_final.R.id.errorPrivacy;
import static com.famdif.famdif_final.R.id.item_sign_in;


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
    private TextView myTextView;
    private CheckBox checkPrivacidad;
    private TextView errorPrivacidad;

    private Spinner gradoSelector;
    private ArrayAdapter<String> adapt;

    private List<String> disc = Arrays.asList("Fisica u organica", "Sensorial", "Intelectual", "Mental", "Ninguna", "Seleccione grado de discapacidad");

    private static final String PASSWORD_PATTERN = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public RegistrarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setMainActivity((MainActivity) getActivity());
        final View view = inflater.inflate(R.layout.fragment_registrar, container, false);

        nombre = view.findViewById(R.id.registroTextNombre);
        email = view.findViewById(R.id.registroTextEmail);
        pass = view.findViewById(R.id.registroTextPass);
        pass2 = view.findViewById(R.id.registroTextPass2);
        errorPass = view.findViewById(R.id.errorPass);
        errorComplete = view.findViewById(R.id.errorComplete);
        errorPrivacidad = view.findViewById(errorPrivacy);
        anyoNacimiento = view.findViewById(R.id.anyoNacimiento);
        gradoSelector = view.findViewById(R.id.spinnerPoseeDiscapacidad);
        checkPrivacidad = view.findViewById(R.id.checkboxPrivacidad);
        TextView privacidadLink = view.findViewById(R.id.privacidadLink);
        privacidadLink.setMovementMethod(LinkMovementMethod.getInstance());

        seleccionarAnyo = view.findViewById(R.id.seleccionarAnyoBtn);
        btnRegistro = view.findViewById(R.id.registroBtnRegistrar);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        adapt = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };

        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapt.add("Física u orgánica");
        adapt.add("Sensorial");
        adapt.add("Intelectual");
        adapt.add("Mental");
        adapt.add("Ninguna");
        adapt.add("¿Tiene alguna discapacidad?"); //This is the text that will be displayed as hint.


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

        MainActivity mactiv = (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.GONE);
        pageIcon.setImageResource(R.drawable.ic_noticias);
        pageTitle.setText("REGISTRO");
        ;
        getMainActivity().changeMenu(MenuType.DISCONNECTED);
        getMainActivity().setOptionMenu(item_sign_in);



        return view;
    }

    private void signInClick(View view) {
        try {
            if (!MainActivity.isCheckedPrivacidad) {
                Toast.makeText(getContext(), "Por favor, acepte la política de privacidad.", Toast.LENGTH_LONG).show();
                errorPrivacidad.setVisibility(View.VISIBLE);
            }else if (!isValid(pass.getEditText().getText().toString())) {
                Toast.makeText(getContext(), "La contraseña no cumple con los criterios establecidos. Por favor, revisar", Toast.LENGTH_LONG).show();
                errorPass.setVisibility(View.VISIBLE);
            } else if (!pass.getEditText().getText().toString().matches(pass2.getEditText().getText().toString())) {
                Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            } else if (gradoSelector.getSelectedItem().toString() == "¿Tiene alguna discapacidad?") {
                Toast.makeText(getContext(), "Por favor, marque el apartado de si tiene alguna discapacidad", Toast.LENGTH_LONG).show();
                errorComplete.setVisibility(View.VISIBLE);
            } else if (nombre.getEditText().getText().toString() == "") {
                errorComplete.setVisibility(View.VISIBLE);
            } else if (email.getEditText().getText().toString() == "") {
                errorComplete.setVisibility(View.VISIBLE);
            } else if (anyoNacimiento.getEditText().getText().toString() == "") {
                errorComplete.setVisibility(View.VISIBLE);
            } else {
                errorPrivacidad.setVisibility(View.INVISIBLE);
                errorComplete.setVisibility(View.INVISIBLE);
                errorPass.setVisibility(View.INVISIBLE);
                MainActivity.mAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), pass.getEditText().getText().toString())
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
                                user.put("nombre", nombre.getEditText().getText().toString());
                                user.put("email", email.getEditText().getText().toString());
                                user.put("pass", pass.getEditText().getText().toString());
                                user.put("nacidoEn", anyoNacimiento.getEditText().getText().toString());
                                user.put("discapacidad", gradoSelector.getSelectedItem().toString());
                                user.put("admin", "0");
                                MainActivity.db.collection("users").document(email.getEditText().getText().toString())
                                        .set(user);
                                MainActivity.db.collection("userLogros").document(email.getEditText().getText().toString())
                                        .set(userLogros);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getMainActivity(), "El correo indicado ya se encuentra registrado", Toast.LENGTH_SHORT).show();
                        Log.i("ERROR LOGIN: ", e.getMessage());
                    }
                });
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT);
        }

    }

    public boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}