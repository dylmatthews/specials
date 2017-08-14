package am.dx.varsityspecials.www.varsityspecials;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CardListActivity extends Activity {

    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    String area[];
   /// String [] days = {"Everyday (Monday to Friday)","Monday","Tuesday", "Wednesday", "Thursday", "Friday","Saturday", "Sunday"};
    TextView line;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);
        area = new String [10];
        myRef = database.getReference();
        line = (TextView) findViewById(R.id.line1);
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        if (FirebaseDatabase.getInstance() != null) {


            FirebaseDatabase.getInstance().goOnline();



        }
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

    public void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onResume(){
        super.onResume();
        if (FirebaseDatabase.getInstance() != null) {
            //toast("Gone online onResume Area");

                FirebaseDatabase.getInstance().goOnline();



        }
    }



    @Override
    public void onStop(){
        super.onStop();
        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();


        }
    }



    private void showData(DataSnapshot dataSnapshot) {
        // toast("hello show data");
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt =0;
        //  Iterable<DataSnapshot> lstSnapshots = ;
        try {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                if (!(ds.getKey().equals("Blog"))) {
                    area[cnt] = ds.getKey();
                    logging("area " + area[cnt]);

                    Card card = new Card(area[cnt]);
                    cardArrayAdapter.add(card);

                    listView.setAdapter(cardArrayAdapter);

                    cnt++;


                    try {


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                logging("shit about to happen");
                                // TODO Auto-generated method stub
                                //toast("Shit hello");
                                Intent intent = new Intent(CardListActivity.this, days.class);
                                logging("shit about to happen part 2");
                                // toast("intenting");
                                intent.putExtra("area", area[position - 1]);

                                startActivity(intent);

                                logging("shit about to happen part 3");
                            }
                        });
                    } catch (Exception ex) {
                        logging("shit happened  " + ex.getMessage());
                    }


                }
            }

        }catch (Exception ex)
        {
            logging("shit\n" + ex.getMessage());
        }

    }

    public void logging(String t)
    {
        Log.i("Reading firebase", t);
    }



}