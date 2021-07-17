package com.example.chattingex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class MessageAdapter extends RecyclerView.Adapter{

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();


    public  MessageAdapter (LayoutInflater inflater) {
        this.inflater = inflater;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt = itemView.findViewById(R.id.sentTxt);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, messageTxt;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            messageTxt = itemView.findViewById(R.id.receivedTxt);
        }
    }

    @Override
    public int getItemViewType(int position) {

        JSONObject message = messages.get(position);

        try {
            if (message.getBoolean("isSent")) {

                if (message.has("message"))
                    return TYPE_MESSAGE_SENT;
//                else
//                    return TYPE_IMAGE_SENT;

            } else {

                if (message.has("message"))
                    return TYPE_MESSAGE_RECEIVED;
//                else
//                    return TYPE_IMAGE_RECEIVED;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_sent_message, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:

                view = inflater.inflate(R.layout.item_received_message, parent, false);
                return new ReceivedMessageHolder(view);

//            case TYPE_IMAGE_SENT:
//
//                view = inflater.inflate(R.layout.item_sent_image, parent, false);
//                return new SentImageHolder(view);
//
//            case TYPE_IMAGE_RECEIVED:
//
//                view = inflater.inflate(R.layout.item_received_photo, parent, false);
//                return new ReceivedImageHolder(view);

        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        JSONObject message = messages.get(position);

        try {
            if (message.getBoolean("isSent")) {

                if (message.has("message")) {

                    SentMessageHolder messageHolder = (SentMessageHolder) holder;
                    messageHolder.messageTxt.setText(message.getString("message"));

                } else {
//
//                    SentImageHolder imageHolder = (SentImageHolder) holder;
//                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
//
//                    imageHolder.imageView.setImageBitmap(bitmap);

                }

            } else {

                if (message.has("message")) {

                    ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
                    messageHolder.nameTxt.setText(message.getString("name"));
                    messageHolder.messageTxt.setText(message.getString("message"));

                } else {

//                    ReceivedImageHolder imageHolder = (ReceivedImageHolder) holder;
//                    imageHolder.nameTxt.setText(message.getString("name"));
//
//                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
//                    imageHolder.imageView.setImageBitmap(bitmap);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem (JSONObject jsonObject) {
        messages.add(jsonObject);
        notifyDataSetChanged();
    }
}
