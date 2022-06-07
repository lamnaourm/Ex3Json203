package com.example.ex3json203;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<Employe> employes;
    Spinner sp;
    TextView tnom, tmat, tfonction, tnaissance, tsalaire;
    RadioGroup grp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employes = getEllEmps();
        sp = findViewById(R.id.sp);
        grp = findViewById(R.id.rgenre);
        tnom = findViewById(R.id.tnom);
        tmat = findViewById(R.id.tmat);
        tfonction = findViewById(R.id.tfonction);
        tnaissance = findViewById(R.id.tnais);
        tsalaire = findViewById(R.id.tsal);

       ArrayList<HashMap<String, Object>> list_emp = new ArrayList<>();

       for(Employe em : employes){
           HashMap<String, Object> item = new HashMap<>();
            item.put("nom",em.getNom());
            if(em.getGenre().equalsIgnoreCase("homme"))
                item.put("image",R.drawable.homme);
            else
                item.put("image",R.drawable.femme);
           list_emp.add(item);
       }

       String[] from = {"nom", "image"};
       int[] to = {R.id.itemnom, R.id.timage};

        SimpleAdapter ad = new SimpleAdapter(this,list_emp,R.layout.item_emp,from,to);
        sp.setAdapter(ad);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employe ee = employes.get(i);

                tnom.setText("Nom : " + ee.getNom());
                tmat.setText("Matricule : " + ee.getMatricule());
                tfonction.setText("Fonction : " + ee.getFonction());
                tnaissance.setText("Naissance : " + ee.getNaissance());
                tsalaire.setText("Saiare : " + String.valueOf(ee.getSalaire()));

                if (ee.getGenre().equalsIgnoreCase("homme"))
                    grp.check(R.id.rd1);
                else
                    grp.check(R.id.rd2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public String loadJSonFromRaw(int resId) {
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

    public ArrayList<Employe> getEllEmps() {
        ArrayList<Employe> emps = new ArrayList<>();
        String json = loadJSonFromRaw(R.raw.employes);
        try {
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
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