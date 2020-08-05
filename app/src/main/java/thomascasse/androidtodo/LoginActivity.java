package thomascasse.androidtodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity
{
    private EditText email;
    private EditText password;
    private Button loginBtn;
    private Button registerBtn;
    private ProgressBar loginProgress;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.enterEmail);
        password = (EditText)findViewById(R.id.enterPassword);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        loginProgress = (ProgressBar)findViewById(R.id.loginProgress);

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isEmpty())
                    return;

                inProgress(true);

                firebaseAuth.signInWithEmailAndPassword(
                        email.getText().toString(),
                        password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        inProgress(false);
                        Toast.makeText(LoginActivity.this, "Login Failed - " + e.getMessage(),
                                Toast.LENGTH_LONG);
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isEmpty())
                    return;

                inProgress(true);

                firebaseAuth.createUserWithEmailAndPassword(
                        email.getText().toString(),
                        password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        Toast.makeText(LoginActivity.this, "Registration Successful", Toast.LENGTH_LONG);
                        inProgress(false);
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        inProgress(false);
                        Toast.makeText(LoginActivity.this, "Registration Failed - " + e.getMessage(),
                                Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    private void inProgress(boolean x)
    {
        if(x)
        {
            loginProgress.setVisibility(View.VISIBLE);
            loginBtn.setEnabled(false);
            registerBtn.setEnabled(false);
        }
        else
        {
            loginProgress.setVisibility(View.GONE);
            loginBtn.setEnabled(true);
            registerBtn.setEnabled(true);
        }
    }

    private boolean isEmpty()
    {
        if(TextUtils.isEmpty(email.getText().toString()))
        {
            email.setError("REQUIRED!");
            return true;
        }

        if(TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("REQUIRED!");
            return true;
        }

        return false;
    }
}