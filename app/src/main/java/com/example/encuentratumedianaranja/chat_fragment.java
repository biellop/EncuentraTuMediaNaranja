package com.example.encuentratumedianaranja;

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
import java.util.ArrayList;
import java.util.List;

public class chat_fragment extends Fragment {

    private List<String> messages;
    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private Button buttonSend;
    private MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        messages = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recycler_view_messages);
        editTextMessage = rootView.findViewById(R.id.edit_text_message);
        buttonSend = rootView.findViewById(R.id.button_send);

        messageAdapter = new MessageAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(messageAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        return rootView;
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (!message.isEmpty()) {
            messages.add(message);
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messages.size() - 1);
            editTextMessage.setText("");
            // Aquí podrías implementar la lógica para enviar el mensaje a través de tu servicio o API preferida
        }
    }
}