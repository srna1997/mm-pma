package ba.sum.fpmoz.dnevnik.ui.a.fragments.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.dnevnik.R;
import ba.sum.fpmoz.dnevnik.model.Student;
import ba.sum.fpmoz.dnevnik.ui.a.adapters.StudentAdapter;

public class ListStudentFragment extends Fragment {
    FirebaseDatabase db;
    DatabaseReference ref;
    StudentAdapter adapter;
    RecyclerView studentRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userListAdminView = inflater.inflate(R.layout.activity_user_list,container,false);

        this.studentRecyclerView = userListAdminView.findViewById(R.id.studentRecyclerView);
        this.db = FirebaseDatabase.getInstance();
        this.ref = this.db.getReference("ednevnik/ucenici");
        this.studentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Student> options = new FirebaseRecyclerOptions
                .Builder<Student>()
                .setQuery(this.ref, Student.class).build();
        this.adapter = new StudentAdapter(options);
        this.studentRecyclerView.setAdapter(this.adapter);

        return userListAdminView;
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
