package com.example.ex3json203;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Employe> employes;
    Spinner sp;
    TextView tnom, tmat, tfonction, tnaissance, tsalaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employes = getEllEmps();
        tnom = findViewById(R.id.tnom);
        tmat = findViewById(R.id.tmat);
        tfonction = findViewById(R.id.tfonction);
        tnaissance = findViewById(R.id.tnais);
        tsalaire = findViewById(R.id.tsal);

        ArrayList<String> nomEmps = new ArrayList<>();

        for(Employe e : employes)
            nomEmps.add(e.getNom());

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1,nomEmps);
        sp.setAdapter(ad);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employe ee = employes.get(i);

                tnom.setText(ee.getNom());
                tmat.setText(ee.getMatricule());
                tfonction.setText(ee.getFonction());
                tnaissance.setText(ee.getNaissance());
                tsalaire.setText(String.valueOf(ee.getSalaire()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public String loadJSonFromRaw(int resId){
        try {
            InputStream in = getResources().openRawResource(resId);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            return new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<Employe> getEllEmps(){
        ArrayList<Employe> emps = new ArrayList<>();
        String json = loadJSonFromRaw(R.raw.employes);
        try {
            JSONArray array = new JSONArray(json);

            for(int i=0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                Employe e = new Employe();
                e.setNom(obj.getString("nom"));
                e.setMatricule(obj.getString("matricule"));
                e.setGenre(obj.getString("genre"));
                e.setFonction(obj.getString("fonction"));
                e.setNaissance(obj.getString("naissance"));
                e.setSalaire(obj.getDouble("salaire"));
                emps.add(e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return emps;
    }
}