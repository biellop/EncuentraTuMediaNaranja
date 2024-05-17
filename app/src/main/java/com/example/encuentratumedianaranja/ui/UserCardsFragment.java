package com.example.encuentratumedianaranja.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.encuentratumedianaranja.R;
import com.example.encuentratumedianaranja.adapter.UserCardsAdapter;
import com.example.encuentratumedianaranja.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserCardsFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserCardsAdapter adapter;
    private List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_cards, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<>();

        // Aquí debes cargar los usuarios desde Firebase
        loadUsers();

        adapter = new UserCardsAdapter(userList, this::openUserProfile);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            userList.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Manejo de errores
                    }
                });
    }

    private void openUserProfile(User user) {
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedUser", user);
        userProfileFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, userProfileFragment)
                .addToBackStack(null)
                .commit();
    }
}
