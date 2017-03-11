package example.assignment_2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhoto extends AppCompatActivity {
    EditText edt;
    Button capture_image;
    Button save_notes;
    ImageView mimage;
    Bitmap mImageBitMap;
    //String path = "";
    String image_path ="";
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        edt= (EditText)findViewById(R.id.edit);
        //mimage = (ImageView) findViewById(R.id.image_capture);
        capture_image = (Button)findViewById(R.id.capture_image);
        save_notes = (Button) findViewById(R.id.save_data);
        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = null;
                try {
                    file = getFile();
                    if (file != null) {
                       Uri photoURI =  FileProvider.getUriForFile(AddPhoto.this,
                               "com.example1.android.fileprovider",
                               file);

                       camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        if (camera_intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(camera_intent, 1);
                        }
                    }
                }
                catch(Exception ex)
                {
                    String s = ex.getMessage();
                }


            }
        });

        save_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caption= edt.getText().toString();
                addData(caption,image_path);
                writeData();
                finish();
            }
        });
    }
    public  void addData(String caption, String path){
        Dbhelper db = new Dbhelper(this);
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntry.COLUMN_CAPTION,caption);
        cv.put(DbContract.DbEntry.COLUMN_PATH,path);
        long result = mDb.insert(DbContract.DbEntry.TABLE_NAME,null,cv);
        if(result!=-1)
            Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

    }

    private File getFile() throws IOException {
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String file_name= "IMG_"+timeStamp +"_";
        File directory = getExternalFilesDir(Environment.getExternalStorageDirectory()+"/DCIM");


        try {
            File file = File.createTempFile(file_name,".jpg",directory);
            image_path = file.getAbsolutePath();
            return file;
        }
        catch (Exception ex)
        {
            String s = ex.getMessage();
            return null;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            File file = new File(image_path);
            Uri uri = Uri.fromFile(file);
            Bitmap bm;
            try
            {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                mImageBitMap =bm;

            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
             }


    }

    private void writeData()
    {

        Bitmap bitmap = mImageBitMap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(getFile());
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can't write to storage", Toast.LENGTH_SHORT).show();
        }

    }


}
