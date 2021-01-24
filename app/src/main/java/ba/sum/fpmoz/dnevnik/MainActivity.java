package ba.sum.fpmoz.dnevnik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView messageTxt;
    private EditText emailInp;
    private EditText passwordInp;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

        this.messageTxt = findViewById(R.id.messageTxt);
        this.emailInp = findViewById(R.id.emailInp);
        this.passwordInp = findViewById(R.id.passwordInp);
        this.loginBtn = findViewById(R.id.loginBtn);

        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInp.getText().toString();
                String password = passwordInp.getText().toString();


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            messageTxt.setText("Uspješno ste se prijavili na sustav.");
                            String uId = mAuth.getCurrentUser().getUid();
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference ref = db.getReference("ednevnik/korisnici/"+uId);
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.child("role").getValue().toString().equals("Admin")){
                                        startActivity(new Intent(getApplicationContext(), HomeNavigationActivity.class));
                                    } else if(snapshot.child("role").getValue().toString().equals("Nastavnik")){
                                        startActivity(new Intent(getApplicationContext(), TeacherMainActivity.class));
                                    } else{
                                        startActivity(new Intent(getApplicationContext(), StudentMainActivity.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }


        });
    }
}