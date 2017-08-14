package am.dx.varsityspecials.www.varsityspecials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class blogHome extends base_activity {

    private ImageButton imageButton;
    private static final int Gallery_Request = 1;
    private EditText title;
    private EditText des;
    private Uri imageUri=null;
    private StorageReference storageReference;
    private ProgressDialog pb;
    private DatabaseReference mdref;
    private RatingBar rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_home);
        super.onCreateDrawer();
        storageReference = FirebaseStorage.getInstance().getReference();
        mdref = FirebaseDatabase.getInstance().getReference().child("Blog");
        imageButton = (ImageButton) findViewById(R.id.ibImageSelect);
title = (EditText) findViewById(R.id.NOP);
        des = (EditText) findViewById(R.id.DES) ;
        pb = new ProgressDialog (this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Request);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Request && resultCode==RESULT_OK)
        {
            imageUri  = data.getData();
            imageButton.setImageURI(imageUri);
        }
    }

    public void onSubmit(View view)
    {

        try {
            startPosting();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void startPosting() throws IOException

    {
        pb.setMessage("Image is uploading...");
        pb.show();
        final String titl= title.getText().toString();
        final String desc = des.getText().toString();
        StorageReference upload = storageReference.child("blog");

        try {

            if (!titl.isEmpty() && !desc.isEmpty() && imageUri != null) {
                //String file = imageUri.toString();
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                byte[] data = baos.toByteArray();
                Toast.makeText(this, imageUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                UploadTask filePath = upload.child(imageUri.getLastPathSegment()).putBytes(data);

                pb.show();
                final FirebaseUser cu = FirebaseAuth.getInstance().getCurrentUser();

                filePath.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //  Uri downloadurl = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = mdref.push();
                        newPost.child("title").setValue(titl);
                        newPost.child("desc").setValue(desc);
                        newPost.child("image").setValue(imageUri.getLastPathSegment());
                        newPost.child("uid").setValue(cu.getUid());



                    }
                });

            }
        }catch (Exception ex)
        {
            Toast.makeText(this, "An error happened, image probably still got uplaoded\n" + ex.getMessage() , Toast.LENGTH_SHORT).show();
        }
        finally {
            pb.dismiss();
        }

    }
}
