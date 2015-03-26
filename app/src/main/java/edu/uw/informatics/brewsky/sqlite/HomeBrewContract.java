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

    public HomeBrewContract(){}

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

}
