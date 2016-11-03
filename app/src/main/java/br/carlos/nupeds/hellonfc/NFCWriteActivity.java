package br.carlos.nupeds.hellonfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import static br.carlos.nupeds.hellonfc.ApplicationSingleton.LOG;
import  static br.carlos.nupeds.hellonfc.ApplicationSingleton.WRISUCESS;

public class NFCWriteActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mtagWrite;
    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mWriteTagFilters;
    private PendingIntent mNfcPendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_nfc);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] { tagDetected };


        mTextView = (TextView)findViewById(R.id.tvW);
        mtagWrite = (EditText)findViewById(R.id.valorTagWrite);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter != null) {
            mTextView.setText("Aguardando Gravação de Tag");
        } else {
            mTextView.setText("Este dispositivo não tem suporte a NFC");
        }

        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                for (NdefMessage tmpMsg : msgs) {
                    for (NdefRecord tmpRecord : tmpMsg.getRecords()) {
                        Log.e(LOG,"\n" + new String(tmpRecord.getPayload()));
                    }
                }
            }

        }

        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


            if(!TextUtils.isEmpty(mtagWrite.getText().toString())){
                NdefRecord  record1 = createTextRecord(mtagWrite.getText().toString().toUpperCase());
                NdefMessage msg = new NdefMessage(new NdefRecord[] { record1 });

                if (writeTag(msg, detectedTag)) {


                   Intent main = new Intent(this, MainActivity.class);
                    main.putExtra(WRISUCESS, true);
                   this.startActivity(main);

                } else {
                    Snackbar.make(mTextView,"Falha na Gravação!",Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(mTextView,"Tag não Definida!",Snackbar.LENGTH_LONG).show();
            }


        }
    }

    public NdefRecord createTextRecord(String payload) {
        byte[] textBytes = payload.getBytes();
        byte[] data = new byte[1 + textBytes.length];
        data[0] = (byte) 0;
        System.arraycopy(textBytes, 0, data, 1, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }


    public static boolean writeTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    return false;
                }
                ndef.writeNdefMessage(message);
                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        return true;
                    } catch (IOException e) {
                        Log.e(LOG,e.getMessage());
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            Log.e(LOG,e.getMessage());
            return false;
        }
    }

}