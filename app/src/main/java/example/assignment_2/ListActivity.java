package example.assignment_2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends ActionBarActivity {

    FloatingActionButton fab;
    private  SQLiteDatabase mDb;
    ListView lv;
    ListAdapter listAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        fab = (FloatingActionButton)findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, AddPhoto.class));
            }
        });
        showlist();
        lv = (ListView)findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =new Intent(ListActivity.this,ViewPhoto.class);
                String pass = Integer.toString(position);
                i.putExtra("ID",pass);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.uninstall :
                Uri packageUri = Uri.parse("package:example.assignment_2");
                Intent uninstallIntent =
                        new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                startActivity(uninstallIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public Cursor getAllContent(){
        Dbhelper db = new Dbhelper(this);
        mDb = db.getWritableDatabase();
        return mDb.query(DbContract.DbEntry.TABLE_NAME,null, null,null,null,null, null);
    }
    protected void onResume() {
        super.onResume();
        showlist();
    }
    public void showlist(){
        lv = (ListView)findViewById(R.id.listView);
        ArrayList<String> theList = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
        Cursor data = getAllContent();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
            }
            lv.setAdapter(listAdapter);
        }
    }
}
