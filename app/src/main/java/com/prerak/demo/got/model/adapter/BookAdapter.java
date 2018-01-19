package com.prerak.demo.got.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prerak.demo.R;
import com.prerak.demo.got.model.BookActivity;
import com.prerak.demo.got.model.MainModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emxcel on 3/1/18.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    // variable declaration
    private Context context;
    List<MainModel> mBookList = new ArrayList<>();
    String strBook, strPublisher, strCountry;
    int pages;

    public BookAdapter(BookActivity bookActivity, List<MainModel> mBookList) {
        this.context = bookActivity;
        this.mBookList = mBookList;
        this.strBook = strBook;
        this.strPublisher = strPublisher;
        this.strCountry = strCountry;
        this.pages = pages;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_book_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MainModel mainModel =mBookList.get(position);
        holder.tv_country.setText(mainModel.getCountry());
        holder.tv_publisher.setText(mainModel.getPublisher());
        holder.tv_no_page.setText(mainModel.getNumberOfPages());
        holder.tv_book_name.setText(mainModel.getName());
        for (int i= 0; i<= mainModel.getAuthors().size(); i++){
         holder.tv_author_name.setText(i);
        }

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_author_name, tv_no_page, tv_publisher, tv_country, tv_book_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_book_name = (TextView) itemView.findViewById(R.id.tv_book_name);
            tv_author_name = (TextView) itemView.findViewById(R.id.tv_author_name);
            tv_no_page = (TextView) itemView.findViewById(R.id.tv_no_page);
            tv_publisher = (TextView) itemView.findViewById(R.id.tv_publisher);
            tv_country = (TextView) itemView.findViewById(R.id.tv_country);
        }
    }
}
