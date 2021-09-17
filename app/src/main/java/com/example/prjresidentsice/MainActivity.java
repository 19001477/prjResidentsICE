package com.example.prjresidentsice;

import static android.provider.Contacts.SettingsColumns.KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Main UI Elements:
    private Button btnAddScreen;
    private Button btnSearchScreen;
    private Button btnUpdateScreen;

    //Add UI Elements:
    private EditText txtAddUnitID;
    private EditText txtAddName;
    private EditText txtAddSurname;
    private EditText txtAddEmail;
    private EditText txtAddCell;
    private EditText txtAddYear;
    private Button btnAdd;
    private Button btnAddBack;

    //Search UI Elements:
    private EditText txtSearchUnitID;
    private ListView lvSearchOutput;
    private Button btnSearch;
    private Button btnSearchBack;

    //Update UI Elements:
    private EditText txtUpdateUnitID;
    private EditText txtUpdateName;
    private EditText txtUpdateSurname;
    private EditText txtUpdateEmail;
    private EditText txtUpdateCell;
    private EditText txtUpdateYear;
    private Button btnUpdate;
    private Button btnUpdateBack;

    private String name, surname, email, cellNum, moveInYear;

    //Objects:
    private DatabaseConnection dbConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        main_screen();
        ui_nav();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startConn();
    }

    private void ui_nav() {
        //Main:
        btnAddScreen = findViewById(R.id.btnAddScreen);
        btnSearchScreen = findViewById(R.id.btnSearchScreen);
        btnUpdateScreen = findViewById(R.id.btnUpdateScreen);

        main_button_listeners();
    }

    private void main_button_listeners() {
        //Main:
        btnAddScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_screen();
            }
        });

        btnSearchScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_screen();
            }
        });

        btnUpdateScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_screen();
            }
        });
    }

    private void add_button_listeners() {
        //Add:
        txtAddUnitID = findViewById(R.id.txtAddUnitID);
        txtAddName = findViewById(R.id.txtAddName);
        txtAddSurname = findViewById(R.id.txtAddSurname);
        txtAddEmail = findViewById(R.id.txtAddEmail);
        txtAddCell = findViewById(R.id.txtAddCell);
        txtAddYear = findViewById(R.id.txtAddYear);
        btnAdd = findViewById(R.id.btnAdd);
        btnAddBack = findViewById(R.id.btnAddBack);

        //Add:
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_to_db();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_screen();
            }
        });
    }

    private void search_button_listeners() {
        //Search:
        txtSearchUnitID = findViewById(R.id.txtSearchUnitID);
        lvSearchOutput = findViewById(R.id.lvSearchOutput);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearchBack = findViewById(R.id.btnSearchBack);

        //Search:
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_db();
            }
        });

        btnSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_screen();
            }
        });
    }

    private void update_button_listeners() {
        //Update:
        txtUpdateUnitID = findViewById(R.id.spinUpdateUserID);
        txtUpdateName = findViewById(R.id.txtUpdateName);
        txtUpdateSurname = findViewById(R.id.txtUpdateSurname);
        txtUpdateEmail = findViewById(R.id.txtUpdateEmail);
        txtUpdateCell = findViewById(R.id.txtUpdateCell);
        txtUpdateYear = findViewById(R.id.txtUpdateYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdateBack = findViewById(R.id.btnUpdateBack);

        //Update:
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_db();
            }
        });

        btnUpdateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_screen();
            }
        });
    }

    //Main:
    //------------------------------------------------------------------------
    private void main_screen() {
        setContentView(R.layout.main);
    }

    private void startConn() {
        dbConn = new DatabaseConnection();
    }
    //------------------------------------------------------------------------

    //Add:
    //------------------------------------------------------------------------
    private void add_screen() {
        setContentView(R.layout.add);

        add_button_listeners();

        txtAddUnitID.setText("");
        txtAddName.setText("");
        txtAddSurname.setText("");
        txtAddEmail.setText("");
        txtAddCell.setText("");
        txtAddYear.setText("");
    }

    private void add_to_db() throws IOException {
        int id = Integer.parseInt(txtAddUnitID.getText().toString());
        String name = txtAddName.getText().toString();
        String surname = txtAddSurname.getText().toString();
        String email = txtAddEmail.getText().toString();
        String cell = txtAddCell.getText().toString();
        int year = Integer.parseInt(txtAddYear.getText().toString());

        Thread thread = new Thread(){
            public void run(){
                String urlString = "https://prjopsc-ice.azurewebsites.net/api/createResident?" +
                        "unitID=" + id + "&" +
                        "name=" + name + "&" +
                        "surname=" + surname + "&" +
                        "email=" + email + "&" +
                        "cellNum=" + cell + "&" +
                        "moveInYear=" + year;

                System.out.println(urlString);

                try {
                    URL url = new URL(urlString);
                    URLConnection conn = url.openConnection();
                    InputStream is = conn.getInputStream();
                }
                catch(Exception e) {

                }
            }
        };

        thread.start();
    }
    //------------------------------------------------------------------------

    //Search:
    //------------------------------------------------------------------------
    private void search_screen() {
        setContentView(R.layout.search);

        search_button_listeners();

        txtSearchUnitID.setText("");
    }

    private void search_db() {
        int id = Integer.parseInt(txtSearchUnitID.getText().toString());

        runJsonRequest(id);
    }

    private void runJsonRequest(int id) {
        //Create RequestQueue:
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        //URL to access resident information:
        String url = "https://prjopsc-ice.azurewebsites.net/api/readResident?" + "unitID=" + id;
        Log.d("URL", url);

        //Store array from URL:
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject residentDetails = (JSONObject) response.get(0); //Store objects from the array

                    //Store values from the array:
                    name = response.getString("name");
                    surname = response.getString("surname");
                    email = response.getString("email");
                    cellNum = response.getString("cellNum");
                    moveInYear = response.getString("moveInYear");

                    System.out.println(
                            "\nDETAILS:\nName: " + name + "\nSurname: " + surname + "\nEmail: " + email + "\nCell: " + cellNum + "\nYear: " + moveInYear
                    );

                    List<String> residentList = new ArrayList<String>();
                    residentList.add("Name: " + name);
                    residentList.add("Surname: " + surname);
                    residentList.add("Email: " + email);
                    residentList.add("Cell: " + cellNum);
                    residentList.add("Moved In: " + moveInYear);

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, residentList);

                    lvSearchOutput.setAdapter(arrayAdapter);
                }
                catch (Exception e) {
                    //Display error:
                    Toast.makeText(getApplicationContext(), "JSON Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("JSON Error", "" + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Display error:
                Toast.makeText(getApplicationContext(), "onErrorResponse: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("onErrorResponse", "" + error.toString());
            }
        });

        queue.add(request); //Add request to queue
    }
    //------------------------------------------------------------------------

    //Update:
    //------------------------------------------------------------------------
    private void update_screen() {
        setContentView(R.layout.update);

        update_button_listeners();

        txtUpdateUnitID.setText("");
        txtUpdateName.setText("");
        txtUpdateSurname.setText("");
        txtUpdateEmail.setText("");
        txtUpdateCell.setText("");
        txtUpdateYear.setText("");
    }

    private void update_db() {
        int id = Integer.parseInt(txtUpdateUnitID.getText().toString());
        String name = txtUpdateName.getText().toString();
        String surname = txtUpdateSurname.getText().toString();
        String email = txtUpdateEmail.getText().toString();
        String cell = txtUpdateCell.getText().toString();
        int year = Integer.parseInt(txtUpdateYear.getText().toString());

        Thread thread = new Thread(){
            public void run(){
                String urlString = "https://prjopsc-ice.azurewebsites.net/api/updateResident?" +
                        "unitID=" + id + "&" +
                        "name=" + name + "&" +
                        "surname=" + surname + "&" +
                        "email=" + email + "&" +
                        "cellNum=" + cell + "&" +
                        "moveInYear=" + year;

                System.out.println(urlString);

                try {
                    URL url = new URL(urlString);
                    URLConnection conn = url.openConnection();
                    InputStream is = conn.getInputStream();
                }
                catch(Exception e) {

                }
            }
        };

        thread.start();
    }
    //------------------------------------------------------------------------
}