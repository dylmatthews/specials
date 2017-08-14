package am.dx.varsityspecials.www.varsityspecials;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private DatabaseReference mData;
    private blod_array cardArrayAdapter;
    private ListView listView;
    private ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);
        im = (ImageView) findViewById(R.id.post_image) ;
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        mData = FirebaseDatabase.getInstance().getReference().child("Blog");



        mData.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void showData(DataSnapshot dataSnapshot)  {

        cardArrayAdapter = new blod_array(getApplicationContext(), R.layout.blow_row);
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
           final  String des = ds.child("desc").getValue().toString();
           final String location = ds.child("title").getValue().toString();
           final String imgURl = ds.child("image").getValue().toString();
            final int num = 1;



                    Card card = new Card(location, des, imgURl, num);
                    cardArrayAdapter.add(card);
                    listView.setAdapter(cardArrayAdapter);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }



    }



}
