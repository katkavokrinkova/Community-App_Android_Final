package net.impacthub.members.application;

import net.impacthub.members.application.salesforce.SalesforceApplication;
import net.impacthub.members.ui.MainTabsActivity;


public class ImpactHubApplication extends SalesforceApplication<MainTabsActivity> {

    public ImpactHubApplication() {
        super(MainTabsActivity.class);
    }
}
