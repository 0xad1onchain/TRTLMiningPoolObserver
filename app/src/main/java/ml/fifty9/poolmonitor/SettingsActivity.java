package ml.fifty9.poolmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox notifs;
    private SharedPreferences sharedPreferences;
    private boolean isOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notifs = findViewById(R.id.checkbox);
        sharedPreferences = this.getSharedPreferences("NOTIFS",0);

        notifs.setOnClickListener(v-> {
            isOn = notifs.isChecked();
            Log.d("SettingsActivity",String.valueOf(isOn));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("ison", isOn);
            editor.apply();
            Intent intent = getIntent();
            setResult(Activity.RESULT_OK, intent);
        });

        SharedPreferences pref = this.getSharedPreferences("NOTIFS",0);
        if(pref.getBoolean("ison",false)){
            notifs.setChecked(true);
        }else{
            notifs.setChecked(false);
        }
    }
}
