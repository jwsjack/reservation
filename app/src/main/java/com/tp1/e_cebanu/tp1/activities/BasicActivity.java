package com.tp1.e_cebanu.tp1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.XMLParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ----------------  DELETE THIS AFTER APP IS DONE ----------------------
 */

public class BasicActivity extends AppCompatActivity {

    private String xmlRows;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // get data from xml
        setContentView(R.layout.listview);
        ArrayList menuItems = new ArrayList();
        XMLParser parser = new XMLParser(getBaseContext());

        //ArrayList<User> users = (ArrayList)ObjectRepositoryDAO.findAll();

        try {
            xmlRows = parser.getXmlFromResources("users");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] xmlData = xmlRows.split("\n");
        final ListView listview = (ListView) findViewById(R.id.list);
//        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
//                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
//                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < xmlData.length; ++i) {
            items.add(xmlData[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, items);
        listview.setAdapter(adapter);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });*/

    }

}
