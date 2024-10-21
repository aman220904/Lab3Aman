package algonquin.cst2335.lab3aman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);
        passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        prefs = getSharedPreferences("MyData", MODE_PRIVATE);
        String savedEmail = prefs.getString("Email", "");
        emailEditText.setText(savedEmail);

        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Email", email);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("EmailAddress", email);
                startActivity(intent);
            }
        });
    }
}