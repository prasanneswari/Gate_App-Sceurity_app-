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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jtsuser.securityapp.HomeActivity;
import com.example.jtsuser.securityapp.R;
import com.example.jtsuser.securityapp.SHomeActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class LoginFragment extends Fragment /*implements MqttCallback*/ {

    Button buttonLogin,buttonegister;
    EditText edtUserName,edtPassWord;
  /*  Boolean intentFlagged = false;
    private static final Random random = new Random();
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";
    static String sm="18FE34DF0F33",dm="server",req="Login";

    //Login Creds
    SharedPreferences sharedpreferences;
    public static final String myPreference = "GenAcPref";
    public static final String ipKey = "ipKey";
    public static final String usernameKey = "usernameKey";
    public static final String passwordKey = "passwordKey";

    //MQTT
    public static final String TAG = "GEN_AC";
    public static MqttAndroidClient client;
    MqttConnectOptions options = new MqttConnectOptions();*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        edtUserName=(EditText)view.findViewById(R.id.edt_email);
        edtPassWord=(EditText)view.findViewById(R.id.edt_password);
        buttonLogin=(Button)view.findViewById(R.id.but_login);

       /* Log.d("Hello","Hello");
        MQTT_Connect();
        getCredentials();

        buttonLogin.setEnabled(false);
        buttonLogin.setClickable(false);
*/
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SHomeActivity.class);
                startActivity(intent);
              /*  String Iusername = edtUserName.getText().toString().trim();
                if(checkString(Iusername)){
                    Toast.makeText(getContext(),"user name contains only lower case letters",Toast.LENGTH_LONG).show();
                    return;
                }
                String Ipassword = edtPassWord.getText().toString().trim();
                if(Iusername != null && !Iusername.isEmpty() && Ipassword != null && !Ipassword.isEmpty()) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(usernameKey, Iusername);
                    editor.putString(passwordKey, Ipassword);
                    editor.commit();

                    String json = "{\"username\":\""+Iusername+"\",\"password\":\""+Ipassword+"\",\"sm\":\""+sm+"\",\"dm\":\""+dm+"\",\"req\":\""+req+"\"}";
                    String topic = "jts/accessControl/v_0_0_1/2s";
                    MQTT_Publish(json,topic);
                }
                else {
                    showAlert("Error","Cannot be empty");
                }
*/
            }
        });

        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        MQTT_Connect();
    }

    private void MQTT_Connect() {
        String clientId = MqttClient.generateClientId();
        String server_ip = "tcp://cld003.jts-prod.in:1883";
        client = new MqttAndroidClient(getActivity().getApplicationContext(), server_ip, clientId);
        client.setCallback(LoginFragment.this);

        try
        {
            options.setUserName("esp");
            options.setPassword("ptlesp01".toCharArray());
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener()
            {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("loginActivity","Connected");
                    MQTT_Subscribe( "jts/accessControl/v_0_0_1/4s");
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
            Log.d("Anji", "\nMqtt Exception :- \n"+e.toString());
        }
    }
    private void getCredentials() {
        sharedpreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(usernameKey)) {
            String text = sharedpreferences.getString(usernameKey, "");
            edtUserName.setText(text);
        }
        if (sharedpreferences.contains(passwordKey)) {
            String text = sharedpreferences.getString(passwordKey, "");
            edtPassWord.setText(text);
        }
    }
    private void MQTT_Subscribe(String s) {

        int qos = 0;
        try
        {
            IMqttToken subToken = client.subscribe(s, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //Activate login button only on MQTT SUCCESS
                    Log.d("loginActivity","Subscribe");
                    buttonLogin.setEnabled(true);
                    buttonLogin.setClickable(true);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Log.d("loginActivity","Subscribe Error\n"+exception.toString());
                }
            });
        }
        catch (MqttException e) {
            e.printStackTrace();

        }
        catch (NullPointerException e) {
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
            Log.d("Login","Error:----\n"+e.toString());
            e.printStackTrace();
        }
    }
    @Override
    public void connectionLost(Throwable cause) {
        //Nothing
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        parseRespose(topic,String.valueOf(message));
        Log.d("Response",""+message+topic);
    }

    private void parseRespose(String topic, String s) throws JSONException {

        JSONObject json = new JSONObject(s);
        try{
           if(json.get("error_code").equals("0")){
                    sharedpreferences =getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.commit();
                    intentFlagged = true;
                    client.unsubscribe("jts/accessControl/v_0_0_1/4s");
                    client.disconnect();

                    Intent setting = new Intent(getContext(), SHomeActivity.class);
                    startActivity(setting);
                    //loadPic(imagePath);
                }
                if(json.get("error_code").equals("2")){
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    builder.setTitle("Invalid login credentials");
                    builder.setMessage("Please enter correct Username and Password!");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    android.support.v7.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //nothing
    }
    public void showAlert(String title,String message)
    {
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
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }*/
}
