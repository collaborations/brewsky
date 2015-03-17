package edu.uw.informatics.brewsky.sqlite;

import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by ginoclement on 3/12/15.
 *
 *
 *
 * I based most of the data on the Tapline API, although I left the following out:
 * https://github.com/homebrewing/tapline
 *
 * mash
 * mashEfficiency
 * servingSize
 * servingSizeOz
 * steepEfficiency
 * steepTime
 * brewDayDuration
 * buToGu
 * bv
 * price
 * realExtract
 * timeline
 *
 */
public class HomeBrewContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String NUM_TYPE = " NUM";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String BOOL_TYPE = " BOOLEAN";
    private static final String PK_INT_ID = " INTEGER PRIMARY KEY,";
    private static final String FK = " FOREIGN KEY(";
    private static final String REFERENCES = " FOREIGN KEY(";
    private static final String NOT_NULL = " NOT_NULL";
    private static final String COMMA = ",";

    public HomeBrewContract(){}

    private void checkStatements(){
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_RECIPE + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_BREWER + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_COLORS + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_FERMENTING_DETAILS + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_RECIPE_DETAILS + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_RECIPE_INSTRUCTIONS + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_RECIPE_MEASUREMENTS + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_FERMENTABLES + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_SPICES + "\n");
        Log.i("Debug", "Checking statement: \n" + SQL_CREATE_YEAST + "\n");
    }

    public static abstract class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "Recipes";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String AUTHOR = "author";
        public static final String STYLE = "recipe_style";
        public static final String RATING = "rating";
    }

    public static abstract class Brewer implements BaseColumns {
        public static final String TABLE_NAME = "brewers";
        public static final String ID = "id";
        public static final String NAME = "days";
        public static final String HASH = "hash";
        public static final String IMAGE = "image";
    }

    public static abstract class Colors implements BaseColumns {
        public static final String TABLE_NAME = "colors";
        public static final String ID = "id";
        public static final String COLOR = "color";
        public static final String EBC = "ebc";
        public static final String LOVIBOND = "lovibond";
        public static final String RGB = "rgb";
    }

    public static abstract class FermentingDetails implements BaseColumns {
        public static final String TABLE_NAME = "fermenting_details";
        public static final String ID = "id";
        public static final String PRIMARY_DAYS = "primary_days";
        public static final String PRIMARY_TEMP = "primary_temp";
        public static final String SECONDARY_DAYS = "secondary_days";
        public static final String SECONDARY_TEMP = "secondary_temp";
        public static final String TERTIARY_DAYS = "tertiary_days";
        public static final String TERTIARY_TEMP = "tertiary_temp";
        public static final String AGING_DAYS = "aging_days";
        public static final String AGING_TEMP = "aging_temp";
        public static final String BOTTLING_PRESSURE = "bottling_pressure";
        public static final String BOTTLING_TEMP = "bottling_temp";
        public static final String FK_RECIPE = "fk_recipe";
    }

    public static abstract class RecipeDetails implements BaseColumns {
        public static final String TABLE_NAME = "recipe_ingredients";
        public static final String ID = "id";
        public static final String FK_RECIPE = "fk_recipe";
        public static final String FK_YEAST = "fk_yeast";
        public static final String FK_SPICES = "fk_spices";
        public static final String FK_FERMENTABLES = "fk_fermentables";
    }

    public static abstract class RecipeInstructions implements BaseColumns {
        public static final String TABLE_NAME = "recipe_instructions";
        public static final String ID = "id";
        public static final String TIME = "time";
        public static final String INSTRUCTION = "instruction";
        public static final String FK_RECIPE = "fk_recipe";
    }

    public static abstract class RecipeMeasurements implements BaseColumns {
        public static final String TABLE_NAME = "recipe_measurements";
        public static final String ID = "id";
        public static final String ABV = "abv";
        public static final String ABW = "abw";
        public static final String CALORIES = "calories";
        public static final String IBU = "ibu";
        public static final String IBU_METHOD ="ibuMethod";
        public static final String OG = "og";
        public static final String OG_PLATO = "ogPlato";
        public static final String FG = "fg";
        public static final String FG_PLATO= "fgPlato";
        public static final String BATCH_SIZE = "batchSize";
        public static final String BOIL_SIZE = "boilSize";
        public static final String FK_RECIPE = "fk_recipe";
        public static final String FK_COLORS = "fk_colors";
    }

/*
    Tables for different ingredients
 */

    public static abstract class Fermentable implements BaseColumns {
        public static final String TABLE_NAME = "fermentables";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String WEIGHT = "weight";
        public static final String YIELD = "yield";
        public static final String COLOR = "color";
        public static final String LATE = "late";
        public static final String TYPE = "type";
    }

    public static abstract class Spices implements BaseColumns {
        public static final String TABLE_NAME = "spices";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String WEIGHT = "weight";
        public static final String AA = "aa";
        public static final String USE = "use";
        public static final String TIME = "time";
        public static final String FORM = "type";
    }

    public static abstract class Yeast implements BaseColumns {
        public static final String TABLE_NAME = "yeast";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String FORM = "form";
        public static final String ATTENUATION = "attenuation";
    }

/*
    Create Entries
 */

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
            + Spices.AA + REAL_TYPE + COMMA
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

}
