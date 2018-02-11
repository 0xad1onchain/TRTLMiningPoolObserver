package ml.fifty9.poolmonitor;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox notifs;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notifs = findViewById(R.id.checkbox);
        sharedPreferences = this.getSharedPreferences("NOTIFS",0);

        boolean isOn = notifs.isChecked();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ison", isOn);
        editor.apply();
    }
}
