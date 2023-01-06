package slev.if5a.komanku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignActivity extends AppCompatActivity {
    private EditText etEmail, etPassword, etKonfirmasiPassword, etNamaLengkap;
    private Button btnRegister, btnMasuk, btnLupaPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRoot, mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRoot = mDatabase.getReference();

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etKonfirmasiPassword = findViewById(R.id.et_konfirmasipassword);
        etNamaLengkap = findViewById(R.id.et_namalengkap);
        btnMasuk = findViewById(R.id.btn_masuk);
        btnRegister = findViewById(R.id.btn_register);
        btnLupaPassword = findViewById(R.id.btn_resetpassword);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etKonfirmasiPassword.getText().toString();
                String fullName = etNamaLengkap.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    etEmail.setError("Masukkan alamat email !");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    etPassword.setError("Masukkan password ! ");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword))
                {
                    etKonfirmasiPassword.setError("Konfirmasi password !");
                    return;
                }
                if (TextUtils.isEmpty(fullName))
                {
                    etNamaLengkap.setError("Masukkan nama lengkap !");
                    return;
                }
                if (password.length() < 6)
                {
                    etPassword.setError("Password terlalu pendek, masukkan minimal 6 karakter !");
                    return;
                }
                if (!confirmPassword.equals(password))
                {
                    etKonfirmasiPassword.setError("Password tidak cocok !");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(SignActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                                    User user = new User(email, fullName);
                                    String userId = task.getResult().getUser().getUid();
                                    mRef = mRoot.child("users").child(userId);
                                    mRef.setValue(user);
                                }
                                else
                                {
                                    Toast.makeText(SignActivity.this, "Sign Up failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}