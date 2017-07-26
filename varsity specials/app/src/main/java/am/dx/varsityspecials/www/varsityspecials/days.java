package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class days extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private  String area = "";
    private String days[];
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);
        area = getIntent().getStringExtra("area");

        myRef = database.getReference(area);
        setTitle(area);
        days = new String[10];
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void logging(String t)
    {
        Log.i("Reading firebase", t);
    }

    private void showData(DataSnapshot dataSnapshot) {
        // toast("hello show data");
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt =0;
        //  Iterable<DataSnapshot> lstSnapshots = ;
        try {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                String key = ds.getKey();

                logging("key " + key);


                days[cnt] = key;

                cnt++;
                Card card = new Card(key.substring(1));
                cardArrayAdapter.add(card);

                listView.setAdapter(cardArrayAdapter);




                try {


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            logging("shit about to happen");
                            // TODO Auto-generated method stub
                            //toast("Shit hello");
                            Intent intent = new Intent(days.this, Category.class);
                            logging("shit about to happen part 2");
                            // toast("intenting");
                            intent.putExtra("day", days[position-1]);
                            intent.putExtra("area", area);
                            startActivity(intent);
                            logging("shit about to happen part 3");
                        }
                    });
                }
                catch(Exception ex)
                {
                    logging("shit happened  " + ex.getMessage());
                }



            }


        }catch (Exception ex)
        {
            logging("shit\n" + ex.getMessage());
        }

    }


}
