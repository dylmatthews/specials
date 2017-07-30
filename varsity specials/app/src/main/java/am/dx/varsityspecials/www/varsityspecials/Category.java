package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Category extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private String day = "";
    private String area = "";
    private  String category[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);
        category = new String[10];

        day = getIntent().getStringExtra("day");
        area = getIntent().getStringExtra("area");
        myRef = database.getReference(area + "/" + day);
        setTitle(day.substring(1));
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        if (FirebaseDatabase.getInstance() != null) {
          //  toast("Gone online onResume Area");

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

    public void logging(String t) {
        Log.i("Reading firebase", t);
    }

    private void showData(DataSnapshot dataSnapshot) {
        // toast("hello show data");
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt = 0;
        //  Iterable<DataSnapshot> lstSnapshots = ;
        try {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                String key = ds.getKey();
                logging("key " + key);


                category[cnt] = key;

                cnt++;
                Card card = new Card(key);
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
                            Intent intent = new Intent(Category.this, day.class);
                            logging("shit about to happen part 6969");
                            // toast("intenting");
                            //Toast.makeText(Category.this, category + area + day, Toast.LENGTH_SHORT).show();

                            intent.putExtra("category", category[position - 1]);
                            intent.putExtra("area", area);
                            intent.putExtra("day", day);
                            logging("shit about to happen part 69693");
                            startActivity(intent);



                        }
                    });
                } catch (Exception ex) {
                    logging("poes 1  " + ex.getMessage());
                }


            }


        } catch (Exception ex) {
            logging("poes 2\n" + ex.getMessage());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (FirebaseDatabase.getInstance() != null) {
           // toast("Gone online onResume Area");

            FirebaseDatabase.getInstance().goOnline();



        }
    }


    @Override
    public void onStop(){
        super.onStop();
        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();
           // toast("Gone offline onStop Area");

        }
    }
}
