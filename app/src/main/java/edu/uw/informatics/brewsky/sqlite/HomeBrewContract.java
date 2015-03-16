package edu.uw.informatics.brewsky.sqlite;

import android.provider.BaseColumns;

/**
 * Created by ginoclement on 3/12/15.
 */
public class HomeBrewContract {

    public HomeBrewContract(){}

    public static abstract class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_STYLE = "recipe_style";
        public static final String COLUMN_NAME_ABV = "abv";
        public static final String COLUMN_NAME_RATING = "rating";
    }

    public static abstract class YeastEntry implements BaseColumns {

    }

    public static abstract class FermentableEntry implements BaseColumns {

    }

    public static abstract class SpicesEntry implements BaseColumns {

    }

    public static abstract class CommentsEntry implements BaseColumns {

    }

    public static abstract class RecipeInstructionsEntry implements BaseColumns {

    }

    public static abstract class RecipePropertiesEntry {
        public static final String TABLE_NAME = "recipe_properties";
        public static final String COLUMN_OG = "original_gravity";
        public static final String COLUMN_FG = "final_gravity";
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            RecipeEntry.TABLE_NAME + RecipeEntry._ID "INTEGER PRIMARY KEY,"
}
