package com.example.alexandru.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Alexandru on 5/10/2017.
 */

public final class PetContact {

    public static final String CONTENT_AUTHORITY = "com.example.alexandru.data.PetProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS = "pets";

    public static abstract class PetEntry implements BaseColumns {

        public static final String TABLE_NAME = "pets";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_WEIGHT = "weight";
        public static final String COLUMN_PET_GENDER = "in_stock";

        /**
         * Possible values for the style of the headphone.
         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);


        private static final String CREATE_TABLE = "";

    }
}
