package br.carlos.nupeds.hellonfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent read = new Intent(MainActivity.this, NFCReadActivity.class);
                MainActivity.this.startActivity(read);
            }
        });


        FloatingActionButton fabW = (FloatingActionButton) findViewById(R.id.fabW);
        fabW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent read = new Intent(MainActivity.this, NFCWriteActivity.class);
                MainActivity.this.startActivity(read);
            }
        });


        mTextView = (TextView)findViewById(R.id.verificarSensor);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter != null) {
            mTextView.setText("Read an NFC tag");
        } else {
            mTextView.setText("This phone is not NFC enabled.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}