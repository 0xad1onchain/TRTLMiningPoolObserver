package ml.fifty9.poolmonitor;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView developText, adityaWallet, hemantWallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        developText = findViewById(R.id.develop_text);
        adityaWallet = findViewById(R.id.aditya_address_trtl);
        hemantWallet = findViewById(R.id.hemant_address_trtl);

        int unicodeHeart = 0x2764;

        String heart = getEmojiByUnicode(unicodeHeart);
        String developString = "Developed with " + heart + " by";
        developText.setText(developString);

        adityaWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Wallet Address", adityaWallet.getText());
                clipboard.setPrimaryClip(clip);
                Snackbar snackbar = Snackbar
                        .make( view ,"Aditya's wallet address copied to clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        hemantWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Wallet Address", hemantWallet.getText());
                clipboard.setPrimaryClip(clip);
                Snackbar snackbar = Snackbar
                        .make( view ,"Hemant's wallet address copied to clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });



    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
