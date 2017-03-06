package com.tp1.e_cebanu.tp1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tp1.e_cebanu.tp1.R;
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.UserXmlImpl;
import com.tp1.e_cebanu.tp1.models.User;

import java.util.ArrayList;
import java.util.List;

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

        UserXmlImpl usersDao = new UserXmlImpl();
        List<User> users = usersDao.findAll();

        final ListView listview = (ListView) findViewById(R.id.list);
//        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
//                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
//                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<User> items = new ArrayList<>();
        for (User user:users) {
            items.add(user);
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
