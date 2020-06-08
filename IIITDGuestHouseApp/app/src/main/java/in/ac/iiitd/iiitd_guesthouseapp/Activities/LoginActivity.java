package in.ac.iiitd.iiitd_guesthouseapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import in.ac.iiitd.iiitd_guesthouseapp.Model.UserDetail;
import in.ac.iiitd.iiitd_guesthouseapp.R;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private static final String NAME = "name";

    private ImageView iiitd_logo_large, iiitd_logo_name;
    private TextView tv_guest_house;
    private static SignInButton loginBtn;
    private UserDetail userDetail;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iiitd_logo_large = findViewById(R.id.imageView);
        iiitd_logo_name = findViewById(R.id.imageView2);
        tv_guest_house = findViewById(R.id.textView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }


        // do something
        // TODO to future animate the  logo
//        Pair[] pair = new Pair[3];
//        pair[0] = new Pair<View, String>(iiitd_logo_large, getString(R.string.login_transaction_logo_big));
//        pair[1] = new Pair<View, String>(iiitd_logo_name, getString(R.string.login_transaction_logo_small));
//        pair[2] = new Pair<View, String>(tv_guest_house, getString(R.string.logintransaction_text));
//
//        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,
//                pair);

        loginBtn = findViewById(R.id.sign_in_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                GoogleSignInAccount acct = result.getSignInAccount();
                String personName = acct.getDisplayName();
                if (personName == null) personName ="";
                firebaseAuthWithGoogle(acct, personName);
            } else {
                Toast.makeText(LoginActivity.this, "There was a trouble signing in-Please try again", Toast.LENGTH_SHORT).show();
                ;
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct, final String name) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed. Try After Some Time",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra(NAME, name);
                            startActivity(intent);
                        }
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


}
