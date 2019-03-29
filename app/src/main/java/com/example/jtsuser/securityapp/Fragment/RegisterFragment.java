package com.example.jtsuser.securityapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jtsuser.securityapp.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterFragment extends Fragment implements MqttCallback{

    EditText editTextFName, editTextLname, editTextEmail, editTextPassword, editTextCPassword, editTextMobilenumber,edtbuilding,edtfloor,edtsection,edtPremises;
    Button buttonRegister;
    Boolean intentFlagged = true;
    String city_topic="",type_id="",type_topic="",city_id="";
    TextView type, city;
    ImageView imageType,imageCity;
    List<String> typeid = new ArrayList<String>();
    List<String> typeDesc = new ArrayList<String>();
    List<String> cityid = new ArrayList<String>();
    List<String> CityDesc = new ArrayList<String>();
    private static final Random random = new Random();
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";
    static String sm = "18FE34DF0F33", dm = "server", req = "add_user", lsi = "1", ut = "", ci = "Hyderabad", pn = "Hyderabad", fl = "302", sec = "B", app = "MarthuiSuzuki", bld = "2", pi = "1";

    List<String> usertypeName = new ArrayList<String>();
    ArrayAdapter<String> userAdapter;
    Spinner spinnerUserType;

    //Login Creds
    SharedPreferences sharedpreferences;
    public static final String myPreference = "GenAcPref";
    public static final String ipKey = "ipKey";
    public static final String usernameKey = "usernameKey";
    public static final String userFnameKey = "userFnameKey";
    public static final String emailKey = "emailKey";
    public static final String numberKey = "numberKey";
    public static final String passwordKey = "passwordKey";
    public static final String cpasswordKey = "CpasswordKey";
    public static final String buildingkey = "buildingKey";
    public static final String floorkey = "floorkey";
    public static final String sectionkey = "sectionkey";
    public static final String premiseskey = "premiseskey";

    //MQTT
    public static final String TAG = "GEN_AC";
    public static MqttAndroidClient client;
    MqttConnectOptions options = new MqttConnectOptions();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        editTextFName = (EditText) view.findViewById(R.id.edt_first_name);
        editTextLname = (EditText) view.findViewById(R.id.edt_last_name);
        editTextEmail = (EditText) view.findViewById(R.id.edt_email);
        editTextPassword = (EditText) view.findViewById(R.id.edt_password);
        editTextCPassword = (EditText) view.findViewById(R.id.edt_confirm_password);
        editTextMobilenumber = (EditText) view.findViewById(R.id.edt_number);
        edtbuilding=(EditText)view.findViewById(R.id.edt_building);
        edtfloor=(EditText)view.findViewById(R.id.edt_floors);
        edtsection=(EditText)view.findViewById(R.id.edt_sections);
        edtPremises=(EditText)view.findViewById(R.id.edt_premises);
        city=(TextView)view.findViewById(R.id.city);
        type=(TextView)view.findViewById(R.id.type);
        imageCity=(ImageView)view.findViewById(R.id.image_city);
        imageType=(ImageView)view.findViewById(R.id.image_user_type);
        buttonRegister = (Button) view.findViewById(R.id.but_register);
       // spinnerUserType=(Spinner)view.findViewById(R.id.sp_register_type);
        getCredentials();
        MQTT_Connect();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<typeid.size(); i++){
                    if (type.getText().toString().contentEquals(typeDesc.get(i))){
                        type_id = typeid.get(i);
                    }
                }

                for(int i=0; i<cityid.size(); i++){
                    if (city.getText().toString().contentEquals(CityDesc.get(i))){
                        city_id= cityid.get(i);
                    }
                }
                String Iusername = editTextFName.getText().toString().trim();
              /*  if (checkString(Iusername)) {
                    Toast.makeText(getContext(), "user name contains only lower case letters", Toast.LENGTH_LONG).show();
                    return;
                }*/
                String Ilastname = editTextLname.getText().toString().trim();
                String Iemail = editTextEmail.getText().toString().trim();
                String Imobilenumber = editTextMobilenumber.getText().toString().trim();
                String Ipassword = editTextPassword.getText().toString().trim();
                String Ibuilding=edtbuilding.getText().toString().trim();
                String Ifloors=edtfloor.getText().toString().trim();
                String Isection=edtsection.getText().toString().trim();
                String Ipremises=edtPremises.getText().toString().trim();

                if (Iusername != null && !Iusername.isEmpty() && Ipassword != null && !Ipassword.isEmpty()) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(usernameKey, Iusername);
                    editor.putString(userFnameKey, Ilastname);
                    editor.putString(emailKey, Iemail);
                    editor.putString(numberKey, Imobilenumber);
                    editor.putString(passwordKey, Ipassword);
                    editor.putString(buildingkey,Ibuilding);
                    editor.putString(floorkey,Ifloors);
                    editor.putString(sectionkey,Isection);
                    editor.putString(premiseskey,Ipremises);

                    editor.commit();

                    String json = "{\"fn\":\"" + Iusername + "\",\"Email\":\"" + Iemail + "\",\"phon\":\"" + Imobilenumber + "\",\"pw\":\"" + Ipassword + "\",\"ln\":\"" + Ilastname + "\",\"sm\":\"" + sm + "\",\"dm\":\"" + dm + "\",\"req\":\"" + req + "\",\"bn\":\"" + Ibuilding + "\",\"floor\":\"" + Ifloors + "\",\"sec\":\"" + Isection + "\",\"pn\":\"" + Ipremises + "\",\"city\":\"" + CityDesc + "\",\"ut\":\"" + typeDesc + "\"}";
                    Log.d("Publish", "Values" + json);
                    String topic = "jts/accessControl/v_0_0_1/2s";
                    MQTT_Publish(json, topic);
                } else {
                    showAlert("Error", "Cannot be empty");
                }
            }
        });

        imageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(getContext(), type);
                //Inflating the Popup using xml file
                //popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener

                for (int i=0; i<typeid.size();i++){
                    popup.getMenu().add(typeDesc.get(i));
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        String Name = item.getTitle().toString();
                        type.setText(Name);

                        return true;
                    }
                });

                popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        //Log.d("Please select ","Type");
                    }
                });

                popup.show();//showing popup menu
            }
        });

        imageCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(getContext(), city);
                //Inflating the Popup using xml file
               // popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener

                for (int i=0; i<cityid.size();i++){
                    popup.getMenu().add(CityDesc.get(i));
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        String Name = item.getTitle().toString();
                        city.setText(Name);

                        return true;
                    }
                });

                popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        //Log.d("Please select ","Client");
                    }
                });

                popup.show();//showing popup menu
            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        MQTT_Connect();
    }

    public void FunctionUserType() {
        String json = "{\"sm\":\"" + sm + "\",\"dm\":\"" + dm + "\",\"req\":\"" + "get_user_type" + "\"}";
        Log.d("Publish", "Values" + json);
        String topic = "jts/accessControl/v_0_0_1/2s";
        MQTT_Publish(json, topic);
    }

    public void FunctionAddPremises() {
        String json = "{\"sm\":\"" + sm + "\",\"dm\":\"" + dm + "\",\"req\":\"" + "add_premises" + "\",\"city\":\"" + ci + "\",\"pn\":\"" + pn + "\"}";
        Log.d("Publish", "Values" + json);
        String topic = "jts/accessControl/v_0_0_1/2s";
        MQTT_Publish(json, topic);
    }

    public void FunctionGetCity() {
        String json = "{\"sm\":\"" + sm + "\",\"dm\":\"" + dm + "\",\"req\":\"" + "get_city" + "\"}";
        Log.d("Publish", "Values" + json);
        String topic = "jts/accessControl/v_0_0_1/2s";
        MQTT_Publish(json, topic);
    }

    public void FunctionAddLivingSpace() {
        String json = "{\"sm\":\"" + sm + "\",\"dm\":\"" + dm + "\",\"req\":\"" + "add_living_space" + "\",\"pi\":\"" + pi + "\",\"bld\":\"" + bld + "\",\"fl\":\"" + fl + "\",\"sec\":\"" + sec + "\",\"app\":\"" + app + "\"}";
        Log.d("Publish", "Values" + json);
        String topic = "jts/accessControl/v_0_0_1/2s";
        MQTT_Publish(json, topic);
    }

    private void MQTT_Connect() {
        String clientId = MqttClient.generateClientId();
        String server_ip = "tcp://cld003.jts-prod.in:1883";
        client = new MqttAndroidClient(getActivity().getApplicationContext(), server_ip, clientId);
        client.setCallback(RegisterFragment.this);

        try {
            options.setUserName("esp");
            options.setPassword("ptlesp01".toCharArray());
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("registerActivity", "Connected");
                    MQTT_Subscribe("jts/accessControl/v_0_0_1/4s");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    builder.setTitle("Error");
                    builder.setMessage("Server Reachability");

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MQTT_Connect();
                        }
                    });

                    android.support.v7.app.AlertDialog dialog = builder.create();

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
            Log.d("Anji", "\nMqtt Exception :- \n" + e.toString());
        }
    }

    private void getCredentials() {
        sharedpreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(usernameKey)) {
            String text = sharedpreferences.getString(usernameKey, "");
            //editTextFName.setText(text);
        }
        if (sharedpreferences.contains(userFnameKey)) {
            String text = sharedpreferences.getString(userFnameKey, "");
            //editTextLname.setText(text);
        }
        if (sharedpreferences.contains(numberKey)) {
            String text = sharedpreferences.getString(numberKey, "");
            //editTextMobilenumber.setText(text);
        }
        if (sharedpreferences.contains(emailKey)) {
            String text = sharedpreferences.getString(emailKey, "");
            //editTextEmail.setText(text);
        }
        if (sharedpreferences.contains(passwordKey)) {
            String text = sharedpreferences.getString(passwordKey, "");
            //editTextPassword.setText(text);
        }
        if (sharedpreferences.contains(cpasswordKey)) {
            String text = sharedpreferences.getString(cpasswordKey, "");
            //editTextCPassword.setText(text);
        }
    }

    private void MQTT_Subscribe(String s) {

        int qos = 0;
        try {
            IMqttToken subToken = client.subscribe(s, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //Activate login button only on MQTT SUCCESS
                    Log.d("registerActivity", "Subscribe");
                    buttonRegister.setEnabled(true);
                    buttonRegister.setClickable(true);
                    FunctionUserType();
                    FunctionAddPremises();
                    FunctionGetCity();
                    FunctionAddLivingSpace();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    //  Log.d("registerActivity","Subscribe Error\n"+exception.toString());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();

        } catch (NullPointerException e) {
            e.printStackTrace();

        }

    }

    public void MQTT_Publish(String payload, String tag) {
        //String topic = "jts/oyo/"+tag;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(true);
            client.publish(tag, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            Log.d("Register", "Error:----\n" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        JSONObject json = null;  //your response
        try {
            json = new JSONObject(String.valueOf(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        parseRespose(String.valueOf(message));
      /*  parseRespose(topic, String.valueOf(message));
        Log.d("Response", "" + message + topic);
      *//*  JSONObject json = new JSONObject(String.valueOf(message));
        if (json.get("ret_code").equals("0")) {
            if (json.has(String.valueOf(req == "get_user_type_res"))) {
                getUserType(String.valueOf(json));
                Log.d(TAG, "----getUserType ----" + String.valueOf(json));
            }

            if (json.has(String.valueOf(req == "get_city_res"))) {
                getCityType(String.valueOf(json));
            }

        }
        if (json.has("function")) {
            if ((json.get("function").equals("add_unit"))) {
                parseRespose(topic, String.valueOf(message));
                Log.d("Response", "" + message + topic);
            }
        }*/
    }


    private void parseRespose(String s) throws JSONException {
        try {
            JSONObject json = new JSONObject(s);

            if(json.get("ret_code").equals("0"))
            {
                if(json.has("get_user_type_res"))
                {
                    typeDesc.clear();
                    typeid.clear();
                    JSONArray roles = json.getJSONArray("get_user_type_res");
                    for (int i = 0; i < roles.length(); i++)
                    {
                        JSONObject c = roles.getJSONObject(i);

                        String id = c.getString("SLU");
                        String desc = c.getString("user_type");

                        typeid.add(id);
                        typeDesc.add(desc);
                    }
                }

                if(json.has("get_city"))
                {
                    CityDesc.clear();
                    cityid.clear();
                    JSONArray clients = json.getJSONArray("get_city");
                    for (int i = 0; i < clients.length(); i++)
                    {
                        JSONObject c = clients.getJSONObject(i);

                        //String id = c.getString("client_id");
                        String desc = c.getString("city_name");

                        //cityid.add(id);
                        CityDesc.add(desc);
                    }
                }

                if(json.has("function"))
                {
                    if(json.getString("function").toString().contentEquals("add_user_res")){
                        String resp = json.getString("function").toString();
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        builder.setTitle("Info");
                        builder.setMessage(resp);

                        builder.setNegativeButton("Add more?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface1, int i) {

                                Intent setting = new Intent(getContext(), RegisterFragment.class);
                                startActivity(setting);

                            }
                        });

                        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface1, int i) {

                                dialogInterface1.dismiss();
                            }
                        });

                        android.support.v7.app.AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }else {
                //Log.d("json","\n"+json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void showAlert(String title, String message) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }
}

