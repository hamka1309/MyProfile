package com.example.app1;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_LOAD_IMG = 1;
    private final String SHARED_PREFERENCES = "my_profile";
    private final String NAME = "name";
    private final String BIRTHDAY = "birthday";
    private final String EMAIL = "email";
    private final String WEIGHT = "weight";
    private final String HEIGHT = "height";
    private final String GENDER = "gender";
    private final String URI = "uri";
    private EditText etName, etEmail, etWeight, etHeight;
    private TextView tvIBM, tvLevel, tvBirthDay, tvInfor;
    private Spinner spinner;
    private ImageView ivAvatar, ivCamera;
    private String gender[] = {"Male", "Female"};
    private String uri;
    private String weight;
    private String height;
    private final String TAG = "tag";
    private Toolbar toolbar;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toobal1);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.profile_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.profile_ic_arrow_left);
        initPermission();
        init();
        if (checkFileShare()) {
            showShare();
        } else return;

    }

    private void init() {
        etName = findViewById(R.id.et_name);
        tvBirthDay = findViewById(R.id.tv_birthday);
        etEmail = findViewById(R.id.et_email);
        etWeight = findViewById(R.id.et_weight);
        etHeight = findViewById(R.id.et_height);
        tvIBM = findViewById(R.id.tv_ibm);
        spinner = findViewById(R.id.id_spanner);
        tvInfor = findViewById(R.id.tv_infor);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivCamera = findViewById(R.id.iv_camera);
        tvLevel = findViewById(R.id.et_level);
        tvIBM = findViewById(R.id.tv_ibm);
        ivAvatar.setOnClickListener(this);
        ivCamera.setOnClickListener(this);

        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                count = countChar(s.toString());
                Log.e(TAG, "onTextChanged: " + count);
                if (count == 0 || etHeight.getText().toString().equals("") || etHeight.getText().toString().equals("0")) {
                    tvInfor.setVisibility(View.VISIBLE);
                    tvIBM.setVisibility(View.INVISIBLE);
                    tvLevel.setVisibility(View.INVISIBLE);
                    return;
                }
                countIBM(Float.parseFloat(s.toString()), Float.parseFloat(etHeight.getText().toString()));
                tvInfor.setVisibility(View.GONE);
                tvIBM.setVisibility(View.VISIBLE);
                tvLevel.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                count = countChar(s.toString());
                if (count == 0 || etWeight.getText().toString().equals("") || s.toString().equals("0")) {
                    tvInfor.setVisibility(View.VISIBLE);
                    tvIBM.setVisibility(View.INVISIBLE);
                    tvLevel.setVisibility(View.INVISIBLE);
                    return;
                }
                countIBM(Float.parseFloat(etWeight.getText().toString()), Float.parseFloat(s.toString()));
                tvInfor.setVisibility(View.GONE);
                tvIBM.setVisibility(View.VISIBLE);
                tvLevel.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tvBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDay();
            }
        });

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
            case R.id.iv_camera:
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
            uri = imageUri.toString();
            Toast.makeText(this, uri, Toast.LENGTH_LONG).show();
            try {
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
              ivAvatar.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivAvatar);
        } else {
        }
        Toast.makeText(this, R.string.main_error_picture, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else {
            weight = etWeight.getText().toString();
            height = etHeight.getText().toString();
            saveShare();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this,  R.string.main_error_permission, Toast.LENGTH_SHORT).show();
                } else {
                }
                Toast.makeText(this,  R.string.main_error_permission1, Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public void chooseDay() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DATE);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int dayOfMonth) {
                calendar.set(myear, mmonth, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                if (year > myear) {
                    tvBirthDay.setText(simpleDateFormat.format(calendar.getTime()));
                } else if (year == myear) {
                    if (mmonth < month) {
                        tvBirthDay.setText(simpleDateFormat.format(calendar.getTime()));
                    } else if (mmonth == month) {
                        if (dayOfMonth <= day) {
                            tvBirthDay.setText(simpleDateFormat.format(calendar.getTime()));
                        } else {
                            Toast.makeText(ProfileActivity.this, "\n" +
                                    R.string.main_error_date, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "\n" +
                                R.string.main_error_date, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "\n" +
                            R.string.main_error_date, Toast.LENGTH_SHORT).show();
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }


    public void countIBM(float weight, float height) {

        float index1 = ((weight * 100 * 100) / (height * height));
        float index = Math.round(index1 * 100) / 100;

        if (index < 18.5) {
            tvLevel.setText(R.string.main_lever_underweight);
            tvIBM.setText("" + index);
        } else if (18.5 <= index && index < 22.99) {
            tvLevel.setText(R.string.main_lever_normal);
            tvIBM.setText("" + index);
        } else if (23.0 <= index && index < 24.99) {
            tvLevel.setText(R.string.main_lever_overweight);
            tvIBM.setText("" + index);
        } else if (index >= 25) {
            tvLevel.setText(R.string.main_lever_fat);
            tvIBM.setText("" + index);
        }
    }


    public void loadImg(Uri imageUri) {
        try {
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ivAvatar.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Glide.with(this)
                .load(imageUri)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivAvatar);
    }

    public void saveShare() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (checkEdit()) {
            editor.putString(EMAIL, etEmail.getText().toString());
        } else {
            Toast.makeText(this, R.string.main_error_email, Toast.LENGTH_SHORT).show();
        }
        editor.putString(NAME, etName.getText().toString());
        editor.putString(BIRTHDAY, tvBirthDay.getText().toString());
        editor.putString(HEIGHT, etHeight.getText().toString());
        editor.putString(WEIGHT, etWeight.getText().toString());
        editor.putString(GENDER, spinner.getSelectedItem().toString());
        Toast.makeText(this, R.string.main_save_success, Toast.LENGTH_LONG).show();
        editor.putString(URI, uri);
        editor.apply();

    }

    public void showShare() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        weight = sharedPreferences.getString(WEIGHT, "");
        height = sharedPreferences.getString(HEIGHT, "");
        etName.setText(sharedPreferences.getString(NAME, ""));
        tvBirthDay.setText(sharedPreferences.getString(BIRTHDAY, ""));
        etWeight.setText(weight);
        etHeight.setText(height);
        etEmail.setText(sharedPreferences.getString(EMAIL, ""));
        uri = sharedPreferences.getString(URI, "");
        loadImg(Uri.parse(uri));
        String gd = sharedPreferences.getString(GENDER, "");
        checkGender(gd);

        if (checkIBM() && Float.parseFloat(height) != 0) {
            countIBM(Float.parseFloat(weight), Float.parseFloat(height));
            tvIBM.setVisibility(View.VISIBLE);
            tvLevel.setVisibility(View.VISIBLE);
            tvInfor.setVisibility(View.GONE);
        } else {
            tvLevel.setVisibility(View.GONE);
            tvIBM.setVisibility(View.INVISIBLE);
            tvInfor.setVisibility(View.VISIBLE);
        }

    }

    public boolean checkEdit() {
        final String email = etEmail.getText().toString().trim();
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern) || email.matches("")) {
            return true;
        }
        return false;
    }

    public void checkGender(String gt) {
        for (int i = 0; i < gender.length; i++) {
            if (TextUtils.equals(gt, gender[i])) {
                spinner.setSelection(i);
            } else {
                Log.e(TAG, "checkGender: ");
            }
        }
    }

    public boolean checkFileShare() {
        File f = new File("/data/data/com.example.app1/shared_prefs/my_profile.xml");
        if (f.exists()) {
            return true;
        }
        return false;
    }

    public int countChar(String s) {
        int count = s.length();
        return count;
    }

    public boolean checkIBM() {
        if (weight.equals("") || height.equals("") || height.equals("0")) {
            return false;
        }
        return true;
    }

}
