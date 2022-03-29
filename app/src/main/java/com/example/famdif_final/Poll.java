package com.example.famdif_final;

public class Poll {
    private String titulo;
    private String preguntas;
    private Long vecesRespondido;
    private String fragmentName;
    private String logroId;
    private boolean completado;

    public Poll(){}

    public Poll(String preguntas, String titulo, String fragmentName){
        this.preguntas = preguntas;
        this.titulo = titulo;
        this.fragmentName = fragmentName;
        this.completado = false;
    }

    public String getPreguntas() {
        return preguntas;
    }

    public Long getVecesRespondido() {
        return vecesRespondido;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFragmentName() {return fragmentName;}

    public String getLogroId() {return logroId;}

    public boolean getCompletado(){
        return completado;
        /*
        MainActivity.db.collection("suggestions")
                .whereEqualTo("email",MainActivity.mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Sugerencia s = doc.toObject(Sugerencia.class);
                                model.add(s);
                                adaptador.notifyDataSetChanged();
                            }
                        }
                    }
                });
        */
    }


}