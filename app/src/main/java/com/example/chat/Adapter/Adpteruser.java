package com.example.chat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chat.R;
import com.example.chat.chatmassage;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adpteruser extends RecyclerView.Adapter<Adpteruser.MyHolder> {

    private Context context;
    private List<UserModul> userModuls;


    public Adpteruser(Context context, List<UserModul> userModuls) {
        this.context = context;
        this.userModuls = userModuls;
    }






    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view= LayoutInflater.from(context).inflate(R.layout.user_raw,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        final String Uid=userModuls.get(i).getUid();
        String userImage=userModuls.get(i).getImage();
        String username=userModuls.get(i).getName();
        final String about=userModuls.get(i).getAbout();
        myHolder.namelist.setText(username);
        myHolder.listabout.setText(about);
        try {
            Picasso.get().load(userImage).placeholder(R.drawable.images).into(myHolder.mlistimage);
        }catch (Exception e){

        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat_int=new Intent(context, chatmassage.class);
                 chat_int.putExtra("uid",Uid);
                 context.startActivity(chat_int);
            }
        });
    }

   @Override
    public int getItemCount() {
        return userModuls.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        CircleImageView mlistimage;
        TextView listabout,namelist;

         MyHolder(@NonNull View itemView) {
            super(itemView);

            mlistimage=itemView.findViewById(R.id.Rawiav);
            listabout=itemView.findViewById(R.id.Row_about);
            namelist=itemView.findViewById(R.id.Row_name);
        }
    }
}
