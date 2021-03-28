package com.satyajeet.bibliophile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {


    private ArrayList<BookModel> mBook;
    Context mContext;

    public BookAdapter(ArrayList<BookModel> mBook, Context mContext) {
        this.mBook = mBook;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.book_name.setText(mBook.get(position).getBookName());
        holder.book_Author_Name.setText(mBook.get(position).getAuthorName());
        //Glide.with(holder.book_Image.getContext()).load(mBook.get(position).getBookImage()).into(holder.book_Image);
        Picasso.get()
                .load(mBook.get(position).getBookImage())
                .into(holder.book_Image);
    }

    @Override
    public int getItemCount() {
        return mBook.size();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView book_name;
        TextView book_Author_Name;
        ImageView book_Image;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            book_Image = itemView.findViewById(R.id.book_image);
            book_name = itemView.findViewById(R.id.book_name);
            book_Author_Name = itemView.findViewById(R.id.author_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BookDescrption.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bookDetails = new Bundle();
                    bookDetails.putString("title", mBook.get(getAdapterPosition()).getBookName());
                    bookDetails.putString("authors", mBook.get(getAdapterPosition()).getAuthorName());
                    bookDetails.putString("description", mBook.get(getAdapterPosition()).getDescription());
                    bookDetails.putString("imageLink", mBook.get(getAdapterPosition()).getBookImage());
                    intent.putExtras(bookDetails);
                    mContext.startActivity(intent);
                }
            });
        }
    }



}
