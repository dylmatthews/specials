package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
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

import org.w3c.dom.Text;

public class day extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    TextView tv ;
    TextView tv2;
    private cardarray2 cardArrayAdapter;
    private ListView listView;
   private String text[][];
    String day ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listview);
            listView = (ListView) findViewById(R.id.card_listView);
            tv=(TextView) findViewById(R.id.line1);
            text= new String[10][10];
           day = getIntent().getStringExtra("day");
            myRef = database.getReference("Durban North and Umhlanga/"+day);
            setTitle(day);
            listView.addHeaderView(new View(this));
            listView.addFooterView(new View(this));




           // day = "Durban North and Umhlanga/Monday/Connors public house";



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
        catch (Exception ex)
        {
            logging("shit happened  "+ ex.getMessage());
        }
    }

    public void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }

    public void logging(String t)
    {
        Log.i("Reading firebase", t);
    }

    private void showData(DataSnapshot dataSnapshot) {
       // toast("hello show data");
        cardArrayAdapter = new cardarray2(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt =0;
        //  Iterable<DataSnapshot> lstSnapshots = ;
        try {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                //listView.
                //specialsInfo spi = new specialsInfo();

                //spi.setDescription(ds.child("description").getValue(specialsInfo.class).getDescription().toString());
               // toast(spi.getDescription());
               // spi.setLocation(ds.child(key).getValue(specialsInfo.class).getLocation());
                /*spi.setName(ds.child(key).getValue(specialsInfo.class).getName());
                spi.setPrice(ds.child(key).getValue(specialsInfo.class).getPrice());
                spi.setNumber(ds.child(key).getValue(specialsInfo.class).getNumber());

                Log.d("test", "show desertion " + spi.getDescription());
                toast(spi.getDescription());
                logging(spi.getDescription());
                Log.d("test", "show location " + spi.getLocation());
                Log.d("test", "show number " + spi.getNumber());
                Log.d("test", "show price " + spi.getPrice());
                Log.d("test", "show name " + spi.getName());

               // String info = spi.getDescription() + spi.getLocation() +
                  //      spi.getNumber() + spi.getPrice() + spi.getName();
               // Toast.makeText(this, info, Toast.LENGTH_SHORT).show();*/

                String key = ds.getKey();
                logging("key " + key);
               // key = key+"/description";
                //logging("key " + key);
                 //toast(key);
                //logging("Before desciption");
                //logging(ds.child(key).toString());
                String des = ds.child("description").getValue().toString();
                String number = ds.child("number").getValue().toString();
                String location = ds.child("location").getValue().toString();
                String price = ds.child("Price").getValue().toString();
                logging("description\t" + des);
                logging("number\t" + number);
                logging("location\t" + location);
                logging("price\t" + price);

               text[cnt][0] = key;
                text[cnt][1]=des;
                text[cnt][2]= number;
                text[cnt][3]=location;
                text[cnt][4]=price;
              //toast(text[cnt]);
                // tv.setText(text[cnt]);
                cnt++;
                Card card = new Card(key, des);
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
                            Intent intent = new Intent(day.this, ScrollingActivity.class);
                            logging("shit about to happen part 2");
                            // toast("intenting");
                            intent.putExtra("name", text[position-1][0]);
                            intent.putExtra("description",text[position-1][1]);
                            intent.putExtra("number",  text[position-1][2]);
                            intent.putExtra("location",text[position-1][3]);
                            intent.putExtra("price", text[position-1][4]);
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
