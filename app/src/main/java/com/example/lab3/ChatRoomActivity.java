package com.example.lab3;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<Message> listOfMessages = new ArrayList<>();
    Button sBtn;
    Button rBtn;
    private ChatAdapter adpt;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        loadDataFromDatabase();

        listView = (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.ChatEditText);
        Button sBtn = (Button) findViewById(R.id.SendBtn);
        rBtn = (Button) findViewById(R.id.ReceiveBtn);
        adpt = new ChatAdapter(listOfMessages, getApplicationContext());
        listView.setAdapter(adpt);

        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editText.getText().toString();
                System.out.println("Send Message is ---> "+message);
                Message model = new Message(message, true );
                listOfMessages.add(model);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyOpener.COL_MESSAGE, message);
                contentValues.put(MyOpener.COL_ISSENT,true);
                long newRowId = db.insert(MyOpener.TABLE_NAME, null, contentValues);
                editText.setText("");
                ChatAdapter adt = new ChatAdapter(listOfMessages, getApplicationContext());
                listView.setAdapter(adt);
                Toast.makeText(ChatRoomActivity.this, "Message added with id -> "+newRowId, Toast.LENGTH_SHORT).show();
                adt.notifyDataSetChanged();

            }
        });
        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                System.out.println("Receive Message is ---> "+message);
                Message model = new Message(message, false);
                listOfMessages.add(model);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyOpener.COL_MESSAGE, message);
                contentValues.put(MyOpener.COL_ISSENT,false);
                long newRowId = db.insert(MyOpener.TABLE_NAME, null, contentValues);
                editText.setText("");
                ChatAdapter adt = new ChatAdapter(listOfMessages, getApplicationContext());
                listView.setAdapter(adt);
                Toast.makeText(ChatRoomActivity.this, "Message added with id -> "+newRowId, Toast.LENGTH_SHORT).show();
                adt.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Message message = listOfMessages.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);

                builder.setTitle("Do you want to delete this?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        deleteMessage(listOfMessages.get(position));
                        listOfMessages.remove(position);
                        adpt.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }

        });

    }

    private void loadDataFromDatabase() {

        MyOpener myOpener = new MyOpener(this);
        db = myOpener.getWritableDatabase();

        String[] columnNames = {myOpener.COL_ID, myOpener.COL_MESSAGE, myOpener.COL_ISSENT};

        Cursor results = db.query(false, MyOpener.TABLE_NAME, columnNames, null, null, null, null, null, null);
        printCursor(results, db.getVersion());
        int idColIdx = results.getColumnIndex(myOpener.COL_ID);
        int messageColIdx = results.getColumnIndex(myOpener.COL_MESSAGE);
        int isSentIdx = results.getColumnIndex(myOpener.COL_ISSENT);

        while (results.moveToNext()){
            listOfMessages.add(new Message(results.getLong(idColIdx), results.getString(messageColIdx),results.getInt(isSentIdx) == 0 ? false : true));

        }
    }

    protected void  deleteMessage(Message msgObj) {
        System.out.println(msgObj.toString());
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(msgObj.getId())});
    }

    public void printCursor(Cursor c, int version) {
        Log.i("Datebase version No -> ",version+"");
        Log.i("No of columns -> ",c.getColumnCount()+"");
        Log.i("Name of columns -> ",c.getColumnNames().toString());
        Log.i("No of results -> ",c.getCount()+"");
        DatabaseUtils.dumpCursorToString(c);
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            String message =c.getString( c.getColumnIndex( MyOpener.COL_MESSAGE ));
            Log.i("Message", message + "isSent: "+ (c.getInt(c.getColumnIndex(MyOpener.COL_ISSENT)) == 0));
            c.moveToNext();
        }
    }
}
