package ml.fifty9.poolmonitor;

import android.content.Context;
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
    private SharedPreferences sharedPreferences,walletPref;
    private EditText walletAddress;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        spinner = findViewById(R.id.spinner);
        walletAddress = findViewById(R.id.wallet_text);
        button = findViewById(R.id.submit);
        sharedPreferences = this.getSharedPreferences("URL_PREFS", 0);
        walletPref = this.getSharedPreferences("WALLET_PREFS",0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.
                createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] urls = getResources().getStringArray(R.array.urls);
        poolType = urls[position];

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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("url",poolType);
                editor.apply();

                SharedPreferences.Editor walletEditor = walletPref.edit();
                walletEditor.putString("wallet",wallet);
                walletEditor.apply();

                //Start intent for activity
            }
        }
    }
}
