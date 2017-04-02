package com.tp1.e_cebanu.tp1.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.MyApplication;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

// Example HTML dans layout
//        String htmlText = " <html><body style="text-align:justify"> %s </body></Html>";
//        String myData = MyApplication.getAppResources().getString(R.string.guide_instruction).toString();
//        WebView webView = (WebView) findViewById(R.id.webView1);
//        webView.loadData(String.format(htmlText, myData), "text/html", "utf-8");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Poignée barre d'action article clique ici(Handle action bar item clicks here.)
        // La barre d'action va gérer automatiquement les clics sur le Home/Up button,
        // aussi longtemps que vous spécifiez une activité parent dans AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // terminer l'activité
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
