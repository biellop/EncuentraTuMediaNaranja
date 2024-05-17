package com.example.encuentratumedianaranja.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.encuentratumedianaranja.R;
import com.example.encuentratumedianaranja.adapter.ChatAdapter;
import com.example.encuentratumedianaranja.model.Message;
import com.example.encuentratumedianaranja.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private Button sendButton;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private User matchUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        messageEditText = view.findViewById(R.id.messageEditText);
        sendButton = view.findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRecyclerView.setAdapter(chatAdapter);

        if (getArguments() != null) {
            matchUser = (User) getArguments().getSerializable("matchUser");
            loadChat();
        }

        sendButton.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void loadChat() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("chats").document(currentUserId)
                .collection(matchUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Message message = document.toObject(Message.class);
                            messageList.add(message);
                            Log.d(TAG, "Message loaded: " + message.getText());
                        }
                        chatAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "Error getting messages: ", task.getException());
                    }
                });
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString();
        if (!messageText.isEmpty()) {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Message message = new Message(currentUserId, matchUser.getUid(), messageText);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("chats").document(currentUserId)
                    .collection(matchUser.getUid()).add(message)
                    .addOnSuccessListener(documentReference -> {
                        messageEditText.setText("");
                        loadChat(); // Refresca el chat después de enviar un mensaje
                        Log.d(TAG, "Message sent: " + message.getText());
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error sending message: ", e);
                    });

            db.collection("chats").document(matchUser.getUid())
                    .collection(currentUserId).add(message)
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error saving message for receiver: ", e);
                    });
        }
    }
}
