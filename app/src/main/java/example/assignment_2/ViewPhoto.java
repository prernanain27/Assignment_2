package example.assignment_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewPhoto extends AppCompatActivity {
    private SQLiteDatabase mDb;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);


        Intent i = getIntent();
        String id = i.getStringExtra("ID");
        Integer value = Integer.parseInt(id)+1;
        Dbhelper db = new Dbhelper(this);
        mDb = db.getReadableDatabase();
        tv = (TextView)findViewById(R.id.tv);
        String[] projection = {DbContract.DbEntry.COLUMN_CAPTION, DbContract.DbEntry.COLUMN_PATH};
        Cursor data = mDb.query(DbContract.DbEntry.TABLE_NAME,projection, DbContract.DbEntry._ID + " = "+ value,
                null,null,null,null,null);
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                tv.setText(data.getString(0).toString());
                File imgFile = new  File(data.getString(1).toString());

                if(imgFile.exists()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    ImageView myImage = (ImageView) findViewById(R.id.image);

                    myImage.setImageBitmap(myBitmap);

                }
            }
        }
       // tv.setText(data.getString(0));
    }


}
