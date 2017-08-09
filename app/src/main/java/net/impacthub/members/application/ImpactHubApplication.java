package net.impacthub.members.application;

import net.impacthub.members.application.salesforce.SalesforceApplication;
import net.impacthub.members.ui.splash.SplashActivity;


public class ImpactHubApplication extends SalesforceApplication<SplashActivity> {

    public ImpactHubApplication() {
        super(SplashActivity.class);
    }
}
