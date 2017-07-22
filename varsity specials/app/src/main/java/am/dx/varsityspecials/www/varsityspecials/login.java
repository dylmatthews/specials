package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;

import static android.R.attr.password;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText em;
    EditText pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            em = (EditText) findViewById(R.id.etEmail) ;
            pa= (EditText) findViewById(R.id.etPassword);
            mAuth = FirebaseAuth.getInstance();
            FirebaseCrash.log("Activity created");
        }
        catch(Exception ex) {
            FirebaseCrash.log("Activity created");
            FirebaseCrash.logcat(Log.ERROR, ex.getMessage(), "NPE caught");
            FirebaseCrash.report(ex);
            Log.e("shit" , ex.getMessage());
        }



    }
    public void onSignUp(View view)
    {

        Intent in = new Intent(login.this,signUp.class);
        startActivity(in);
    }

    @Override
    public void onStart() {
        super.onStart();
       //;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void toast(String t)
    {
        Toast output= Toast.makeText(this, t, Toast.LENGTH_SHORT);
        output.setGravity(Gravity.CENTER,0,0);
        output.show();
    }


    public void onLogin(View view)
    {

        String email= em.getText().toString();
        String password = pa.getText().toString();

         mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("test", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("test", "signInWithEmail", task.getException());

                           toast("Login Error");

                        }
                        else
                        {
                            Log.w("test", "signInWithEmail", task.getException());

                            toast("Login Successful");

                        }

                        // ...
                    }
                });


    }
}
