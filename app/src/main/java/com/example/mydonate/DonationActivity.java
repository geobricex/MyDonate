package com.example.mydonate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class DonationActivity extends AppCompatActivity {
    Button btnPay, btnReturn;
    Double tax = 6.0, Amount = 0.0, newAmount = 0.0;
    EditText txtCode, txtAmountNoTax, txtID, txtNumberCell, txtAmount, txtAmountTax, txtReference;

    String organizatioName = "";
    DecimalFormat format, formatDecimal;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        Bundle bundle = this.getIntent().getExtras();
        organizatioName = (bundle.getString("Organization"));
        if (!organizatioName.equals("")) {
            Log.i("Logs", organizatioName);
            Log.i("Logs", "" + System.currentTimeMillis());
            initialize();
            OnClickListener();
            OnChangedListener();
        } else {
            Toast.makeText(DonationActivity.this, "No Organization", Toast.LENGTH_LONG).show();
        }
    }

    private void initialize() {
        txtCode = findViewById(R.id.txtCode);
        txtAmountNoTax = findViewById(R.id.txtAmountNoTax);
        txtID = findViewById(R.id.txtID);
        txtNumberCell = findViewById(R.id.txtNumberCell);
        txtAmount = findViewById(R.id.txtAmount);
        txtAmountTax = findViewById(R.id.txtAmountTax);
        txtReference = findViewById(R.id.txtReference);
        btnPay = findViewById(R.id.btnPay);
        btnReturn = findViewById(R.id.btnReturn);

        format = new DecimalFormat("#");
        formatDecimal = new DecimalFormat("#.00");

        requestQueue = Volley.newRequestQueue(this);
        txtReference.setText(organizatioName);
    }

    private void cleanData() {
        txtAmountNoTax.setText("");
        txtAmount.setText("");
    }

    private void OnClickListener() {
        btnPay.setOnClickListener(v -> {
            loadDatatoSend();
        });
        btnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(DonationActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void OnChangedListener() {
        txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Amount = Double.parseDouble(txtAmount.getText().toString());
//                newAmount = Amount + (Amount * (tax / 100));
                    txtAmountTax.setText(formatDecimal.format(Amount - (tax / 100)));
                    txtAmountNoTax.setText(formatDecimal.format(0));
                } catch (Exception exception) {
                    Log.i("Logs", exception.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0 && s.charAt(s.length() - 1) == ' ') {
                }
            }
        });
    }

    private void loadDatatoSend() {
//            if (txtCode.getText() != null && txtID.getText() != null && txtNumberCell.getText() != null &&
//                    txtAmount.getText() != null && txtReference.getText() != null) {
//            if (!txtCode.getText().toString().equals("") && txtCode.getText() != null) {
        if (!txtCode.getText().toString().equals("") && !txtID.getText().toString().equals("") && !txtNumberCell.getText().toString().equals("") &&
                !txtAmount.getText().toString().equals("") && Double.parseDouble(txtAmount.getText().toString()) >= 1.00 && !txtReference.getText().toString().equals("")) {
            Double amount = Double.valueOf(txtAmount.getText().toString());
            amount = amount * 100;
            int amountTotal = Integer.parseInt(format.format(amount));

            String jsonData = "{" +
                    "\"phoneNumber\":\"" + txtNumberCell.getText().toString() + "\"," +
                    "\"countryCode\":\"" + txtCode.getText().toString() + "\"," +
                    "\"clientUserId\":\"" + txtID.getText().toString() + "\"," +
                    "\"reference\":\"" + organizatioName + "\"," +
                    "\"amount\":" + amountTotal + "," +
                    "\"amountWithTax\":" + (amountTotal - tax) + "," +
//                        "\"amountWithTax\":" + newAmount + "," +
//                        "\"amountWithoutTax\":" + Amount + "," +
                    "\"amountWithoutTax\":" + 0 + "," +
                    "\"tax\":" + (tax) + "," +
                    "\"clientTransactionId\":\"" + System.currentTimeMillis() + "\"" +
                    "}";
            Log.i("Logs", jsonData.toString());
            SendToPay(jsonData);
        } else {
            Toast.makeText(DonationActivity.this, "Fill in the details", Toast.LENGTH_LONG).show();
        }
    }

    private void SendToPay(String jsonDataSend) {
        Log.i("Logs", "SendToPay");
        try {
            JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                    "https://pay.payphonetodoesposible.com/api/Sale", new JSONObject(jsonDataSend), new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    cleanData();
                    Log.i("Logs", response.toString());
                    Toast.makeText(DonationActivity.this, "Your transaction has been successful", Toast.LENGTH_LONG).show();
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DonationActivity.this, "Your transaction could not be completed", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Accept", "application/json");
                    header.put("Authorization",
                            "Bearer 3lqOICWabqdLq9wCRP3S8i7aIXWgtUB6Zqi0QYWfG_Y_Uq-GEO2UhfQAAxwDJ-GEONwx95_V6uz9Lrgsi_zrQNHX-GEOI6655KVW4fuPoToosNBOxeLy8JgaZGKJ7dys78T0F1Ak2NLic0cF1cG7vlkewlzfnkButoinyBF7U1K-GEOPHN115v0CJN1_u4KQMIrG_5EJq7K0wop4hpzgUlwkI8tbnQfT8FA2bTyxB08Pyg1IDsz_M6rHObKIE5AhsMwhLzIgpCfAWyJmnpSatsYtbzbi-GEOG6ZR0szz1QRPNfTLXc_nBB0EHJJjisOKqjufnRfzfO5wByrP85mfVB3ITCu6XorogRLhEBgSPk");
                    return header;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            requestQueue.add(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}