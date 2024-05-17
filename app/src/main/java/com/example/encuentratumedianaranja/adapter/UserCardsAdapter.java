package com.example.encuentratumedianaranja.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.encuentratumedianaranja.R;
import com.example.encuentratumedianaranja.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserCardsAdapter extends RecyclerView.Adapter<UserCardsAdapter.ViewHolder> {
    private List<User> userList;
    private OnUserClickListener onUserClickListener;

    public UserCardsAdapter(List<User> userList, OnUserClickListener onUserClickListener) {
        this.userList = userList;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView profileName;
        private Button matchButton;
        private Button dislikeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            profileName = itemView.findViewById(R.id.profileName);
            matchButton = itemView.findViewById(R.id.matchButton);
            dislikeButton = itemView.findViewById(R.id.dislikeButton);

            itemView.setOnClickListener(v -> {
                User user = userList.get(getAdapterPosition());
                onUserClickListener.onUserClick(user);
            });

            matchButton.setOnClickListener(v -> {
                User user = userList.get(getAdapterPosition());
                handleMatch(user);
            });

            dislikeButton.setOnClickListener(v -> {
                User user = userList.get(getAdapterPosition());
                handleDislike(user);
            });
        }

        public void bind(User user) {
            profileName.setText(user.getName());
            Glide.with(itemView.getContext()).load(user.getProfileImageUrl()).into(profileImage);
        }

        private void handleMatch(User user) {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("matches").document(currentUserId)
                    .collection("matches").document(user.getUid())
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        // Lógica después de un match exitoso
                    })
                    .addOnFailureListener(e -> {
                        // Manejo de errores
                    });
        }

        private void handleDislike(User user) {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("dislikes").document(currentUserId)
                    .collection("dislikes").document(user.getUid())
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        // Lógica después de un dislike exitoso
                    })
                    .addOnFailureListener(e -> {
                        // Manejo de errores
                    });
        }
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }
}
