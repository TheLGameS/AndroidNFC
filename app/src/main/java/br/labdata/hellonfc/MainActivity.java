package br.labdata.hellonfc;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.carlos.nupeds.hellonfc.R;

import static br.labdata.hellonfc.ApplicationSingleton.CONFIG_KEY;
import static br.labdata.hellonfc.ApplicationSingleton.LOG;
import static br.labdata.hellonfc.ApplicationSingleton.WRISUCESS;
import static br.labdata.hellonfc.ApplicationSingleton.bancoNoSql;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




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

        FloatingActionButton fabRec = (FloatingActionButton) findViewById(R.id.fabRec);
        fabRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent recommender = new Intent(MainActivity.this, NFCRecommenderActivity.class);
                MainActivity.this.startActivity(recommender);


            }
        });

        mTextView = (TextView)findViewById(R.id.verificarSensor);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter != null) {
            mTextView.setText("Selecione uma Ação");
            fab.setEnabled(true);
            fabW.setEnabled(true);
            fabRec.setEnabled(true);
        } else {
            mTextView.setText("Este dispositivo não tem suporte a NFC");
            fab.setEnabled(false);
            fabW.setEnabled(false);
           fabRec.setEnabled(false);
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


            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_cfg);
            dialog.setTitle("Microservice SWARM");
            dialog.show();

            final TextView inputCfg = (TextView) dialog.findViewById(R.id.edt_conf);

            String cfg = bancoNoSql.getString(CONFIG_KEY, null);
            inputCfg.setText( cfg!=null ? cfg : "");


            inputCfg.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        SharedPreferences.Editor editor = bancoNoSql.edit();
                        editor.putString(CONFIG_KEY, inputCfg.getText().toString().toUpperCase());
                        editor.commit();
                        dialog.dismiss();
                        return true;
                    }
                    return false;
                }
            });

            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        try{
            if(getIntent().getExtras().getBoolean(WRISUCESS, false)){
                Snackbar.make(mTextView,"Gravação efetuada com Sucesso!",Snackbar.LENGTH_LONG).show();
                getIntent().removeExtra(WRISUCESS);
            }
        }catch (NullPointerException e){
            Log.e(LOG,e.getMessage());
        }


        super.onResume();
    }
}