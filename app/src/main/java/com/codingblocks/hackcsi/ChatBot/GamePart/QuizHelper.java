package com.codingblocks.ChatBot.GamePart;

/**
 * Created by Sachin on 11/13/2016.
 */

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mathsone";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    // tasks Table Columns names
    private static final String KEY_ID = "qid";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; // correct option
    private static final String KEY_OPTA = "opta"; // option a
    private static final String KEY_OPTB = "optb"; // option b
    private static final String KEY_OPTC = "optc"; // option c

    private SQLiteDatabase dbase;

    public QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT)";
        db.execSQL(sql);
        addQuestion();
        // db.close();
    }

    private void addQuestion() {
        Question q1 = new Question("Entomology is the science that studies?", "Human Brain", "Insects", "Rock Formation", "Insects");
        this.addQuestion(q1);
        Question q2 = new Question("India's First technicolor film was?", "Jhansi Rani", "Mirza Ghalib", "Sholay", "Jhansi Rani");
        this.addQuestion(q2);
        Question q3 = new Question("India has largest deposits of ____ in the world.", "Gold", "Copper", "Mica", "Mica");
        this.addQuestion(q3);
        Question q4 = new Question("Joule is the unit of", "Temperature", "Heat", "Work", "Work");
        this.addQuestion(q4);
        Question q5 = new Question("Malfunctioning of which organ cause jaundice?", "Liver", "Kidney", "Pancreas", "Liver");
        this.addQuestion(q5);
        Question q6 = new Question("Kiran Bedi is first woman", "IPS officer", "IAS officer", "Police officer", "IPS officer");
        this.addQuestion(q6);
        Question q7 = new Question("National Defence Academy is in", "Delhi", "Rajasthan", "Khadakvasla", "Khadakvasla");
        this.addQuestion(q7);
        Question q8 = new Question("John F. Kennedy, President of USA, died on", "1963", "1964", "1965", "1963");
        this.addQuestion(q8);
        Question q9 = new Question("Current President of India is", "Arvind Kejriwal", "Narendar Modi", "Pranab Mukherjee", "Pranab Mukherjee");
        this.addQuestion(q9);
        Question q10 = new Question("Instrument used to measure relative humidity is", "Hygrometer", "Hydrometer", "Barometer", "Hygrometer");
        this.addQuestion(q10);
        Question q11 = new Question(
                "Most commonly used bleaching agent is", "chlorine", "alcohol", "NaCl", "chlorine");
        this.addQuestion(q11);
        Question q12 = new Question("Marco Polo", "discovered Asia", "discovered Canada", "travelled whole Asia", "travelled whole Asia");
        this.addQuestion(q12);
        Question q13 = new Question("6-3*4-7 = ?", "5", "15", "-13", "-13");
        this.addQuestion(q13);
        Question q14 = new Question("Which is not a prime no.= ?", "2", "3", "1", "1");
        this.addQuestion(q14);
        Question q15 = new Question("1+1 = ?", "1", "2", "11", "2");
        this.addQuestion(q15);
        Question q16 = new Question("Light year is related to = ?", "Energy", "Time", "Distance", "Distance");
        this.addQuestion(q16);
        Question q17 = new Question("Lata Mangeshkar has record of ?", "Highest songs Composed", "Highest songs sang", "Highest songs recorded", "Highest songs recorded");
        this.addQuestion(q17);
        Question q18 = new Question("If a+b=3 and a is positive ,b is perfect sq.", "a=2", "a=3", "a=1", "a=2");
        this.addQuestion(q18);
        Question q19 = new Question("Who has record of highest centuries in cricket", "Don Brad Man", "Sachin Tendulkar", "Brain Lara", "Sachin Tendulkar");
        this.addQuestion(q19);
        Question q20 = new Question("Sania Mirza plays for", "India", "Pakistan", "Both", "India");
        this.addQuestion(q20);
        Question q21 = new Question("Bangladesh got its freedom from", "Britishers", "French", "Pakistan", "Pakistan");
        this.addQuestion(q21);
        // END
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(Question quest) {
        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());

        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));

                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


}
