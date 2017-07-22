package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CardListActivity extends Activity {

    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    String [] days = {"Everyday","Monday","Tuesday", "Wednesday", "Thursday", "Friday","Saturday", "Sunday"};
    TextView line;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);
        line = (TextView) findViewById(R.id.line1);
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));


        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

        for (int i = 0; i < days.length; i++) {
            Card card = new Card(days[i]);
            cardArrayAdapter.add(card);
        }

        listView.setAdapter(cardArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String day =days[position-1];
                Intent intent = new Intent(CardListActivity.this,day.class);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });

    }


}