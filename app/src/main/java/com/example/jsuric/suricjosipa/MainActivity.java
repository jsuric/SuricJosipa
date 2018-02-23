package com.example.jsuric.suricjosipa;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAdapter db = new DBAdapter(this);


        //---add lik---

        db.open();
        long id = db.insertLik(1,"Mickey", "Walt Disney");
        id = db.insertLik(2,"Nick", "Manfred Schmidt");
        id = db.insertLik(3,"Tom", "William Hanna");
        id = db.insertLik(4,"Jerry", "Joseph Barbera");
        id = db.insertLik(5,"Misty", "Takeshi Shudo");
        db.close();


        //---add film---

        db.open();
        long id2 = db.insertFilm("Mickey Mouse", 1);
        id2 = db.insertFilm("Nick Praskaton", 2);
        id2 = db.insertFilm("Tom and Jerry", 3);
        id2 = db.insertFilm("Tom and  Jerry", 4);
        id2 = db.insertFilm("Pokemon", 5);
        db.close();

        //--get all likovi---

        db.open();
        Cursor c = db.getAllLikovi();
        TextView  name = (TextView) findViewById(R.id.likName);
        name.setText("Popis likova: \n ");

        if (c.moveToFirst())
        {
            do {
                DisplayLik(c, name);
            } while (c.moveToNext());
        }
        db.close();


        //--get all films---

        db.open();
        Cursor d = db.getAllFilmovi();
        TextView  name2 = (TextView) findViewById(R.id.filmName);
        name2.setText("Popis filmova: \n ");
        if (d.moveToFirst())
        {
            do {
                DisplayFilm(d, name2);
            } while (d.moveToNext());
        }
        db.close();

        //---get lik---

       db.open();
        Cursor cu = db.getLik(1);
        if (cu.moveToFirst())
            DisplayLikAutor(cu);
        else
            Toast.makeText(this, "No lik found", Toast.LENGTH_LONG).show();
        db.close();


/*
        //---update contact---
        db.open();
        if (db.updateContact(1, "Wei-Meng Lee", "weimenglee@gmail.com"))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
        db.close();



        //---delete a contact---
        db.open();
        if (db.deleteContact(1))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close(); */

    }

    public void onClickDelete(View view)
    {
        DBAdapter db = new DBAdapter(this);

        EditText brisanje = (EditText) findViewById(R.id.deleteID);
        String id=brisanje.getText().toString();
        long lg;
        lg=Long.parseLong(id);

        //---delete film---
        db.open();
        if (db.deleteFilmBYID(lg))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close();

        //---delete a character---
        db.open();
        if (db.deleteLik(lg))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close();

    }

    //funkcija za ispis
    public void DisplayLik(Cursor c, TextView name)
    {
        name.append(" id: " + c.getString(0) + "\n" +
                " Ime: " + c.getString(1) + "\n" +
                " Autor:  " + c.getString(2)+ "\n");

        /*Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Ime: " + c.getString(1) + "\n" +
                        "Autor:  " + c.getString(2),
                Toast.LENGTH_LONG).show();  */
    }

    public void DisplayLikAutor(Cursor c)
    {
        EditText text = (EditText) findViewById(R.id.plain_text_input);
         String autor = "Autor Mickeya:  " + c.getString(2);
        text.setText(autor);

    }

    public void DisplayFilm(Cursor c, TextView name )
    {
        name.append(" Film: " + c.getString(0) + "\n" +
                " Id lika: " + c.getString(1) + "\n");

        /*Toast.makeText(this,
                "Film: " + c.getString(0) + "\n" +
                        "Id: " + c.getString(1),
                Toast.LENGTH_LONG).show(); */
    }
}
