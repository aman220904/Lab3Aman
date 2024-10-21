package algonquin.cst2335.lab3aman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText phoneEditText;
    private SharedPreferences prefs;
    private ImageView imageView;
    private Bitmap savedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView emailTextView = findViewById(R.id.textViewEmail);
        phoneEditText = findViewById(R.id.inputPhoneNumber);
        imageView = findViewById(R.id.imageView);

        // Get the email from the previous activity
        Intent intent = getIntent();
        String emailAddress = intent.getStringExtra("EmailAddress");
        emailTextView.setText("Welcome, " + emailAddress);

        prefs = getSharedPreferences("MyData", MODE_PRIVATE);
        String savedPhoneNumber = prefs.getString("PhoneNumber", "");
        phoneEditText.setText(savedPhoneNumber);

        Button btnSavePhone = findViewById(R.id.btnSavePhone);
        btnSavePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneEditText.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("PhoneNumber", phoneNumber);
                editor.apply();
                Toast.makeText(LoginActivity.this, "Phone number saved", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnChangePicture = findViewById(R.id.btnChangePicture);
        btnChangePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            savedBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(savedBitmap);
            // Save bitmap to disk (optional)
        }
    }
}