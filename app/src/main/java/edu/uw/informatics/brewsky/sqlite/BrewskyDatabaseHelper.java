package edu.uw.informatics.brewsky.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.uw.informatics.brewsky.sqlite.HomeBrewContract.*;

/**
 * Created by ginoclement on 3/16/15.
 */
public class BrewskyDatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Brewsky.db";

    //These are here to eliminate a bunch of string concatenation in the statements
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUM_TYPE = " NUM";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String BOOL_TYPE = " BOOLEAN";
    private static final String PK_INT_ID = " INTEGER PRIMARY KEY,";
    private static final String FK = " FOREIGN KEY(";
    private static final String REFERENCES = ") REFERENCES ";
    private static final String NOT_NULL = " NOT_NULL";
    private static final String COMMA = ",";

    private static final String SQL_CREATE_RECIPE = "CREATE TABLE "
            + Recipe.TABLE_NAME + " ("
            + Recipe.ID + PK_INT_ID + COMMA
            + Recipe.NAME + TEXT_TYPE + COMMA
            + Recipe.DESCRIPTION + TEXT_TYPE + COMMA
            + Recipe.AUTHOR + TEXT_TYPE + COMMA
            + Recipe.STYLE + TEXT_TYPE + COMMA
            + Recipe.RATING + NUM_TYPE + ");";

    private static final String SQL_CREATE_BREWER = "CREATE TABLE "
            + Brewer.TABLE_NAME + " ("
            + Brewer.ID + PK_INT_ID + COMMA
            + Brewer.NAME + TEXT_TYPE + COMMA
            + Brewer.HASH + TEXT_TYPE + COMMA
            + Brewer.IMAGE + TEXT_TYPE + ");";

    private static final String SQL_CREATE_COLORS = "CREATE TABLE "
            + Colors.TABLE_NAME + " ("
            + Colors.ID + PK_INT_ID + COMMA
            + Colors.COLOR + NUM_TYPE + COMMA
            + Colors.EBC + NUM_TYPE + COMMA
            + Colors.LOVIBOND + NUM_TYPE + COMMA
            + Colors.RGB + TEXT_TYPE + ");";

    private static final String SQL_CREATE_FERMENTING_DETAILS = "CREATE TABLE "
            + FermentingDetails.TABLE_NAME + " ("
            + FermentingDetails.ID + PK_INT_ID + COMMA
            + FermentingDetails.PRIMARY_DAYS + INT_TYPE + COMMA
            + FermentingDetails.PRIMARY_TEMP + REAL_TYPE + COMMA
            + FermentingDetails.SECONDARY_DAYS + INT_TYPE + COMMA
            + FermentingDetails.SECONDARY_TEMP + REAL_TYPE + COMMA
            + FermentingDetails.TERTIARY_DAYS + INT_TYPE + COMMA
            + FermentingDetails.TERTIARY_TEMP + REAL_TYPE + COMMA
            + FermentingDetails.AGING_DAYS + INT_TYPE + COMMA
            + FermentingDetails.AGING_TEMP + REAL_TYPE + COMMA
            + FermentingDetails.BOTTLING_PRESSURE + REAL_TYPE + COMMA
            + FermentingDetails.BOTTLING_TEMP + REAL_TYPE + COMMA
            + FK + FermentingDetails.FK_RECIPE + REFERENCES + Recipe.TABLE_NAME + "(" + Recipe.ID + "));";

    private static final String SQL_CREATE_RECIPE_DETAILS = "CREATE TABLE "
            + RecipeDetails.TABLE_NAME + " ("
            + RecipeDetails.ID + PK_INT_ID + COMMA
            + FK + RecipeDetails.FK_RECIPE + REFERENCES + Recipe.TABLE_NAME + "("  + Recipe.ID + "), "
            + FK + RecipeDetails.FK_FERMENTABLES + REFERENCES + Fermentable.TABLE_NAME + "("  + Fermentable.ID + "), "
            + FK + RecipeDetails.FK_SPICES + REFERENCES + Spices.TABLE_NAME + "("  + Spices.ID + "), "
            + FK + RecipeDetails.FK_YEAST + REFERENCES + Yeast.TABLE_NAME + "("  + Yeast.ID + "));";

    private static final String SQL_CREATE_RECIPE_INSTRUCTIONS = "CREATE TABLE "
            + RecipeInstructions.TABLE_NAME + " ("
            + RecipeInstructions.ID + PK_INT_ID + COMMA
            + RecipeInstructions.TIME + TEXT_TYPE + COMMA
            + RecipeInstructions.INSTRUCTION + TEXT_TYPE
            + FK + FermentingDetails.FK_RECIPE + REFERENCES + Recipe.TABLE_NAME + "("  + Recipe.ID + "));";;

    private static final String SQL_CREATE_RECIPE_MEASUREMENTS = "CREATE TABLE "
            + RecipeMeasurements.TABLE_NAME + " ("
            + RecipeMeasurements.ID + PK_INT_ID + COMMA
            + RecipeMeasurements.ABV + REAL_TYPE + COMMA
            + RecipeMeasurements.ABW + REAL_TYPE + COMMA
            + RecipeMeasurements.CALORIES + REAL_TYPE + COMMA
            + RecipeMeasurements.IBU + REAL_TYPE + COMMA
            + RecipeMeasurements.IBU_METHOD + TEXT_TYPE + COMMA
            + RecipeMeasurements.OG + REAL_TYPE + COMMA
            + RecipeMeasurements.OG_PLATO + REAL_TYPE + COMMA
            + RecipeMeasurements.FG + REAL_TYPE + COMMA
            + RecipeMeasurements.FG_PLATO + REAL_TYPE + COMMA
            + RecipeMeasurements.BATCH_SIZE + REAL_TYPE + COMMA
            + RecipeMeasurements.BOIL_SIZE + REAL_TYPE
            + FK + FermentingDetails.FK_RECIPE + REFERENCES + Recipe.TABLE_NAME + "("  + Recipe.ID + "));";

    private static final String SQL_CREATE_FERMENTABLES = "CREATE TABLE "
            + Fermentable.TABLE_NAME + " ("
            + Fermentable.ID + PK_INT_ID + COMMA
            + Fermentable.NAME + TEXT_TYPE + COMMA
            + Fermentable.WEIGHT + INT_TYPE + COMMA
            + Fermentable.YIELD + INT_TYPE + COMMA
            + Fermentable.COLOR + REAL_TYPE + COMMA
            + Fermentable.LATE + BOOL_TYPE + COMMA
            + Fermentable.TYPE + TEXT_TYPE + ");";

    private static final String SQL_CREATE_SPICES = "CREATE TABLE "
            + Spices.TABLE_NAME + " ("
            + Spices.ID + PK_INT_ID
            + Spices.NAME + TEXT_TYPE + COMMA
            + Spices.WEIGHT + REAL_TYPE + COMMA
            + HomeBrewContract.Spices.AA + REAL_TYPE + COMMA
            + Spices.USE + TEXT_TYPE + COMMA
            + Spices.TIME + INT_TYPE + COMMA
            + Spices.FORM + TEXT_TYPE + ");";

    private static final String SQL_CREATE_YEAST = "CREATE TABLE "
            + Yeast.TABLE_NAME + " ("
            + Yeast.ID + PK_INT_ID + COMMA
            + Yeast.NAME + TEXT_TYPE + COMMA
            + Yeast.TYPE + TEXT_TYPE + COMMA
            + Yeast.FORM + TEXT_TYPE + COMMA
            + Yeast.ATTENUATION + INT_TYPE + ");";

    private static final String[] SQL_CREATE_ENTRIES = {
            SQL_CREATE_RECIPE,
            SQL_CREATE_BREWER,
            SQL_CREATE_COLORS,
            SQL_CREATE_FERMENTING_DETAILS,
            SQL_CREATE_RECIPE_DETAILS,
            SQL_CREATE_RECIPE_INSTRUCTIONS,
            SQL_CREATE_RECIPE_MEASUREMENTS,
            SQL_CREATE_FERMENTABLES,
            SQL_CREATE_SPICES,
            SQL_CREATE_YEAST
    };

    private static final String SQL_DELETE_RECIPE = "DROP TABLE IF EXISTS " + Recipe.TABLE_NAME;
    private static final String SQL_DELETE_BREWER = "DROP TABLE IF EXISTS " + Brewer.TABLE_NAME;
    private static final String SQL_DELETE_COLORS = "DROP TABLE IF EXISTS " + Colors.TABLE_NAME;
    private static final String SQL_DELETE_FERMENTING_DETAILS = "DROP TABLE IF EXISTS " + FermentingDetails.TABLE_NAME;
    private static final String SQL_DELETE_RECIPE_DETAILS = "DROP TABLE IF EXISTS " + RecipeDetails.TABLE_NAME;
    private static final String SQL_DELETE_RECIPE_INSTRUCTIONS = "DROP TABLE IF EXISTS " + RecipeInstructions.TABLE_NAME;
    private static final String SQL_DELETE_RECIPE_MEASUREMENTS = "DROP TABLE IF EXISTS " + RecipeMeasurements.TABLE_NAME;
    private static final String SQL_DELETE_FERMENTABLES = "DROP TABLE IF EXISTS " + Fermentable.TABLE_NAME;
    private static final String SQL_DELETE_SPICES = "DROP TABLE IF EXISTS " + Spices.TABLE_NAME;
    private static final String SQL_DELETE_YEAST = "DROP TABLE IF EXISTS " + Yeast.TABLE_NAME;

    private static final String[] SQL_DELETE_ENTRIES = {
            SQL_DELETE_RECIPE,
            SQL_DELETE_BREWER,
            SQL_DELETE_COLORS,
            SQL_DELETE_FERMENTING_DETAILS,
            SQL_DELETE_RECIPE_DETAILS,
            SQL_DELETE_RECIPE_INSTRUCTIONS,
            SQL_DELETE_RECIPE_MEASUREMENTS,
            SQL_DELETE_FERMENTABLES,
            SQL_DELETE_SPICES,
            SQL_DELETE_YEAST
    };

    public BrewskyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        for (String statement : SQL_CREATE_ENTRIES){
            db.execSQL(statement);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        for (String statement : SQL_DELETE_ENTRIES){
            db.execSQL(statement);
        }
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
