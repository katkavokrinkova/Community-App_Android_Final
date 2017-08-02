package net.impacthub.members.ui.widgets;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class Iso8601DateFormat extends SimpleDateFormat {

    public Iso8601DateFormat() {
        super("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
    }
}
