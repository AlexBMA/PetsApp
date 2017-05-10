package com.example.alexandru.data;

import android.provider.BaseColumns;

/**
 * Created by Alexandru on 5/10/2017.
 */

public final class PetContact {


    public static abstract class PetEntry implements BaseColumns {

        public static final String TABLE_NAME = "pets";

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BREED = "breed";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_GENDER = "in_stock";

        /**
         * Possible values for the style of the headphone.
         */
        public static final int BREED_UNKNOWN = 0;
        public static final int BREED_MALE = 1;
        public static final int BREED_FEMALE = 2;

    }
}
