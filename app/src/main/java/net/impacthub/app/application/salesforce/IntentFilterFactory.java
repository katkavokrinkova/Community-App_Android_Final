package net.impacthub.app.application.salesforce;

import android.content.IntentFilter;

class IntentFilterFactory {

    IntentFilter createFrom(String action) {
        return new IntentFilter(action);
    }
}
