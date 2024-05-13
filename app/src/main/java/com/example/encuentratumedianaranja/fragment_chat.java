package com.example.encuentratumedianaranja;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_chat extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_profiles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile(R.drawable.foto_perfil, "Nombre 1"));
        profiles.add(new Profile(R.drawable.foto_perfil, "Nombre 2"));
        // Agrega más perfiles según sea necesario

        recyclerView.setAdapter(new ProfileAdapter(profiles));

        return rootView;
    }

    public static class Profile {
        private final int photoRes;
        private final String name;

        public Profile(int photoRes, String name) {
            this.photoRes = photoRes;
            this.name = name;
        }

        public int getPhotoRes() {
            return photoRes;
        }

        public String getName() {
            return name;
        }
    }

    public static class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
        private final List<Profile> profiles;

        public ProfileAdapter(List<Profile> profiles) {
            this.profiles = profiles;
        }

        @NonNull
        @Override
        public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chats, parent, false);
            return new ProfileViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
            Profile profile = profiles.get(position);
            holder.bind(profile);
        }

        @Override
        public int getItemCount() {
            return profiles.size();
        }

        public static class ProfileViewHolder extends RecyclerView.ViewHolder {
            public ProfileViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(Profile profile) {
                // Importa la clase CircleImageView si no lo has hecho
                //import de.hdodenhof.circleimageview.CircleImageView;

                CircleImageView fotoPerfil = itemView.findViewById(R.id.foto_perfil);
                fotoPerfil.setImageResource(profile.getPhotoRes());

                TextView nombre = itemView.findViewById(R.id.nombre);
                nombre.setText(profile.getName());

                // Puedes configurar el botón de corazón aquí si es necesario
            }
        }
    }
}
