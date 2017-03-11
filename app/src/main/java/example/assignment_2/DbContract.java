package example.assignment_2;

import android.provider.BaseColumns;

/**
 * Created by prernaa on 3/5/2017.
 */

public class DbContract {

    public static final class DbEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
