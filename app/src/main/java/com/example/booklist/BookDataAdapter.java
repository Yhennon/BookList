package com.example.booklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

/* In this class, we create a unique Adapter, using android's ArrayAdapter, and our BookData class. */
public class BookDataAdapter extends ArrayAdapter<BookData> {

    private Context context;
    private int resource;
    private ArrayList<BookData> objects;

    public BookDataAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BookData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    /*Overwriting the getView() function,to work with the BookData class */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /* The adapter arranges the data into an ArrayList of BookDatas. Using their position in this list and the getters created for each variable,
         * we can assign values to them. */
        String bookName = getItem(position).getBookName();
        String deliveryAddress = getItem(position).getDeliveryAddress();
        String bookAuthor = getItem(position).getBookAuthor();
        String contactName = getItem(position).getContactName();
        Date deliveryDeadline = getItem(position).getDeliveryDeadline();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        /* Connecting the variables in the Adapter with the layout's elements by their given ids.*/
        TextView textViewBookName = convertView.findViewById(R.id.textViewBookName);
        TextView textViewDeliveryAddress = convertView.findViewById(R.id.textViewDeliveryAddress);
        TextView textViewBookAuthor = convertView.findViewById(R.id.textViewBookAuthor);
        TextView textViewContact = convertView.findViewById(R.id.textViewContact);
        TextView textViewDeliveryDeadline = convertView.findViewById(R.id.textViewDeliveryDeadline);

        /* Lastly, we set the values of the textViews. */
        textViewBookName.setText(bookName);
        textViewDeliveryAddress.setText(deliveryAddress);
        textViewBookAuthor.setText(bookAuthor);
        textViewContact.setText(contactName);
        textViewDeliveryDeadline.setText(String.valueOf(deliveryDeadline));

        return convertView;
    }
}
