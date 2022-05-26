package com.example.famdif_final.fragment;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.famdif_final.MainActivity;

public class BaseFragment extends Fragment {
    private AlertDialog spotsDialog;
    private MainActivity mainActivity;

    public BaseFragment() {

    }

    protected View inflateFragment(int resId, LayoutInflater inflater, ViewGroup container) {
        final View view = inflater.inflate(resId, container, false);
        return view;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void endLoadingDialog() {
        if (spotsDialog != null && spotsDialog.isShowing()) {
            spotsDialog.dismiss();
        }
    }

    public void youMustBeLoggedIn(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setMessage("Debe estar conectado para acceder a esta funcionalidad");
        dialog.setTitle("Error");
        dialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getContext(),"Conectese para disfrutar de más servicios",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

}
