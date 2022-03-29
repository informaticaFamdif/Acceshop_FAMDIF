package com.example.famdif_final.fragment.polls;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.ScrollView;
        import android.widget.Space;

        import androidx.annotation.NonNull;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.constraintlayout.widget.ConstraintSet;

        import com.example.famdif_final.FragmentName;
        import com.example.famdif_final.MainActivity;
        import com.example.famdif_final.Poll;
        import com.example.famdif_final.R;
        import com.example.famdif_final.fragment.BaseFragment;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;

        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;

public class RegisterPollFragment extends BaseFragment {

    public RegisterPollFragment() {
        // Required empty public constructor
    }

    private void enviarRegisterPoll(String r1, String r2, String r3, String r4) {

        Map<String, Object> respuestaEncuesta = new HashMap<>();

        respuestaEncuesta.put("transportePropio", r1);
        respuestaEncuesta.put("transportePublico", r2);
        respuestaEncuesta.put("acompañante", r3);
        respuestaEncuesta.put("comoConocernos", r4);

        MainActivity.db.collection("encuestaRegistro")
                .document(MainActivity.mAuth.getCurrentUser().getEmail())
                .set(respuestaEncuesta);

        MainActivity.db.collection("userLogros")
                .document(MainActivity.mAuth.getCurrentUser().getEmail())
                .update("000001", "000001");
        MainActivity.logrosUsuario.add("000001");

    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            setMainActivity((MainActivity) getActivity());
            View view = inflateFragment(R.layout.fragment_register_poll, inflater, container);

            Button enviarPoll = (Button) view.findViewById(R.id.buttonEnviarRegisterPoll);

            enviarPoll.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d("babbaboi", "babbaboi");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    RadioGroup pregunta1 = view.findViewById(R.id.preguntaUno);
                    RadioGroup pregunta2 = view.findViewById(R.id.preguntaDos);
                    RadioGroup pregunta3 = view.findViewById(R.id.preguntaTres);
                    RadioGroup pregunta4 = view.findViewById(R.id.preguntaCuatro);

                    View respuesta1 = pregunta1.findViewById(pregunta1.getCheckedRadioButtonId());
                    int idx1 = pregunta1.indexOfChild(respuesta1);
                    RadioButton r1 = (RadioButton) pregunta1.getChildAt(idx1);
                    String selectedtext1 = r1.getText().toString();

                    View respuesta2 = pregunta2.findViewById(pregunta2.getCheckedRadioButtonId());
                    int idx2 = pregunta2.indexOfChild(respuesta2);
                    RadioButton r2 = (RadioButton) pregunta2.getChildAt(idx2);
                    String selectedtext2 = r2.getText().toString();

                    View respuesta3 = pregunta3.findViewById(pregunta3.getCheckedRadioButtonId());
                    int idx3 = pregunta3.indexOfChild(respuesta3);
                    RadioButton r3 = (RadioButton) pregunta3.getChildAt(idx3);
                    String selectedtext3 = r3.getText().toString();

                    View respuesta4 = pregunta4.findViewById(pregunta4.getCheckedRadioButtonId());
                    int idx4 = pregunta4.indexOfChild(respuesta4);
                    RadioButton r4 = (RadioButton) pregunta4.getChildAt(idx4);
                    String selectedtext4 = r4.getText().toString();

                    try {
                        enviarRegisterPoll(selectedtext1, selectedtext2, selectedtext3, selectedtext4);

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity mactiv = (MainActivity) getActivity();
                                mactiv.setFragment(FragmentName.POLLS_FRAGMENT);
                            }
                        });

                        builder.setMessage("Muchas gracias por su participación.")
                                .setTitle("La encuesta ha sido enviada");
                        builder.create();
                        builder.show();

                    } catch (Exception e) {
                        e.printStackTrace();

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity mactiv = (MainActivity) getActivity();
                                mactiv.setFragment(FragmentName.POLLS_FRAGMENT);
                            }
                        });

                        builder.setMessage("La encuesta no ha sido enviada. Disculpe las molestias.")
                                .setTitle("Error al enviar los datos");
                        builder.create();
                        builder.show();
                    }

                }
            });

            FrameLayout includeExteriorFrame = view.findViewById(R.id.scrollViewRegisterPollFrame);
            Space space = view.findViewById(R.id.bottomSpacePoll);

            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            float ratio = ((float)metrics.heightPixels / (float)metrics.widthPixels);

            if(ratio > 2){

                ConstraintSet constraintSet = new ConstraintSet();

                Log.d("constraints",String.valueOf(constraintSet.getHeight(includeExteriorFrame.getId())));

                includeExteriorFrame.setLayoutParams(new ConstraintLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen._560sdp)));
                View includeExteriorFrameParent = (View) includeExteriorFrame.getParent();

                ConstraintLayout constraintLayout = (ConstraintLayout) includeExteriorFrame.getParent();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(includeExteriorFrame.getId(),ConstraintSet.BOTTOM,space.getId(),ConstraintSet.TOP);

                constraintSet.applyTo(constraintLayout);

            }

            return view;

        }

    }
