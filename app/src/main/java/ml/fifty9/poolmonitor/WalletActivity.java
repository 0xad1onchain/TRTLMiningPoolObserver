package ml.fifty9.poolmonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class WalletActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner spinner;
    private String poolType,wallet;
    private SharedPreferences urlPreferences,walletPref;
    private EditText walletAddress;
    private Button button;
    private int spinnerPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        spinner = findViewById(R.id.spinner);
        walletAddress = findViewById(R.id.wallet_text);
        button = findViewById(R.id.submit);
        urlPreferences = this.getSharedPreferences("URL_PREFS", 0);
        walletPref = this.getSharedPreferences("WALLET_PREFS",0);

        String walletString = walletPref.getString("wallet", "");
        if(!walletString.isEmpty()) {
            walletAddress.setText(walletString);
        }

        spinnerPos = urlPreferences.getInt("pos", 0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.
                createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerPos);

        spinner.setOnItemSelectedListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] urls = getResources().getStringArray(R.array.urls);
        poolType = urls[position];
        spinnerPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        wallet = walletAddress.getEditableText().toString();

        if(v.getId() == R.id.submit){
            if(wallet.isEmpty() || poolType.isEmpty()){
                Toast.makeText(this,"Enter all details", Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferences.Editor editor = urlPreferences.edit();
                editor.putString("url",poolType);
                editor.putInt("pos",spinnerPos);
                editor.apply();

                SharedPreferences.Editor walletEditor = walletPref.edit();
                walletEditor.putString("wallet",wallet);
                walletEditor.apply();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        }
    }
}
