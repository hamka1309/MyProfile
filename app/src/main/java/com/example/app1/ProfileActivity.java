package com.example.app1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;

import java.io.InputStream;



public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_LOAD_IMG =1 ;
    private EditText edtName,edtNgaySinh,edtEmail,edtWeight,edtHeight;
    private TextView tvIBM,edtMucDo;
    private Spinner spinner;

    private ImageButton ibtImage1, ibtImage2;
    private final String SHARED_PREFERENCES="myprofile";
    private final String NAME="name";
    private final String NGAYSINH="ngay_sinh";
    private final String EMAIL="email";
    private final String WEIGHT="weight";
    private final String HEIGHT="height";
    private final String GENDER="gender";
    private final String MUCDO="mucdo";
    private final String CHISO="chiso";
    private final String URI="uri";
    String gender[] = { "Nam", "Nữ" };
    private String uri;
    private String uri1;
    String weight ;
    String height ;
    private final String TAG="tag";


    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initPermission();
        init();
        showShare();
        tinhIBM();

    }



    private void init() {

        edtName = findViewById(R.id.edt_name);
        edtNgaySinh = findViewById(R.id.edt_ngay_sinh);
        edtEmail = findViewById(R.id.edt_email);
        edtWeight = findViewById(R.id.edt_weight);
        edtHeight = findViewById(R.id.edt_height);
        tvIBM = findViewById(R.id.tv_ibm);
        spinner = findViewById(R.id.id_spanner);
        ibtImage1 = findViewById(R.id.ibt_image1);
        ibtImage2 = findViewById(R.id.ibt_image2);
        edtMucDo = findViewById(R.id.edt_muc_do);
        tvIBM= findViewById(R.id.tv_ibm);
        ibtImage1.setOnClickListener(this);
        ibtImage2.setOnClickListener(this);
        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        //set adapter vào sp (spinner)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               // ketqua = DSThanhPho[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
              //  ketqua = "";
            }
        });


    }
    public boolean checkIBM()
    {
        if ( !weight.isEmpty() && !height.isEmpty())
        {
           return true;
        }
        return false;
    }
    public void tinhIBM()
    {

        weight = edtWeight.getText().toString();
        height = edtHeight.getText().toString();
        float chiso1 = (Float.parseFloat(weight)/(Float.parseFloat(height)*Float.parseFloat(height)));
        float chiso =  Math.round(chiso1 * 100) / 100;
        if(chiso<18.5)
        {
           edtMucDo.setText("Underweight");
           tvIBM.setText(""+chiso);
        }
        else if (18.5 <= chiso && chiso < 22.99)
        {
            edtMucDo.setText("Normal");
            tvIBM.setText(""+chiso);
        }
        else if(23.0 <= chiso && chiso < 24.99)
        {
            edtMucDo.setText("overweight");
            tvIBM.setText(""+chiso);
        }
        else if(chiso>=25)
        {
            edtMucDo.setText("fat");
            tvIBM.setText(""+chiso);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibt_image1:
            case  R.id.ibt_image2:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            uri=imageUri.toString();
            Log.e(TAG, "onActivityResult: " );
            Toast.makeText(this,uri,Toast.LENGTH_LONG).show();
            try {

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ibtImage1.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }

            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ibtImage1);
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

public void loadImg(Uri imageUri)
{
    try {

        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        ibtImage1.setImageBitmap(selectedImage);
    } catch (FileNotFoundException e) {
        e.printStackTrace();

    }

    Glide.with(this)
            .load(imageUri)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(ibtImage1);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkl,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        saveShare();
        tinhIBM();
        return super.onOptionsItemSelected(item);
    }
    public void saveShare()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME,edtName.getText().toString());
        editor.putString(NGAYSINH,edtNgaySinh.getText().toString());
        editor.putString(EMAIL,edtEmail.getText().toString());
        editor.putString(WEIGHT,edtWeight.getText().toString());
        editor.putString(HEIGHT,edtHeight.getText().toString());
        editor.putString(GENDER,spinner.getSelectedItem().toString());
//        editor.putString(MUCDO,edtMucDo.getText().toString());
        Toast.makeText(this,uri,Toast.LENGTH_LONG).show();
        editor.putString(URI,uri);
        editor.apply();
        Log.e(TAG, "saveShare: "+uri );

    }
    public void showShare()
    {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        edtName.setText(sharedPreferences.getString(NAME,""));
        edtNgaySinh.setText(sharedPreferences.getString(NGAYSINH,""));
        edtWeight.setText(sharedPreferences.getString(WEIGHT,""));
        edtHeight.setText(sharedPreferences.getString(HEIGHT,""));
        edtEmail.setText(sharedPreferences.getString(EMAIL,""));
        uri= sharedPreferences.getString(URI,"");
        loadImg(Uri.parse(uri));
//        tvIBM.setText(sharedPreferences.getString(CHISO,""));
//        edtMucDo.setText(sharedPreferences.getString(MUCDO,""));
        Log.e(TAG, "showShare: " +uri);

    }

    @Override
    protected void onStart() {
//        showShare();
        Log.e(TAG, "onStart: "+uri );
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
