package ba.sum.fpmoz.dnevnik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.dnevnik.model.Curses;
import ba.sum.fpmoz.dnevnik.model.Student;
import ba.sum.fpmoz.dnevnik.ui.a.adapters.CursesAdapter;
import ba.sum.fpmoz.dnevnik.ui.a.adapters.StudentAdapter;
import ba.sum.fpmoz.dnevnik.ui.a.adapters.StudentNavigationAdapter;

public class StudentMainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference ref;
    StudentNavigationAdapter adapter;
    RecyclerView studentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        this.studentRecyclerView = findViewById(R.id.studentMainRecyclerView);
        this.db = FirebaseDatabase.getInstance();
        this.ref = this.db.getReference("ednevnik/predmeti");
        this.studentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<Curses> options = new FirebaseRecyclerOptions
                .Builder<Curses>()
                .setQuery(this.ref, Curses.class).build();
        this.adapter = new StudentNavigationAdapter(options);
        this.studentRecyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}