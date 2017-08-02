package net.impacthub.members.usecase;

/**
 * Created by Lightful on 02/08/2017.
 */

public class UseCaseModule {

    public static <T> ApiCall<T> apiCallProvider() {
        return new ApiCall<>();
    }

    public static SoqlRequestFactory soqlRequestFactoryProvider() {
        return new SoqlRequestFactory();
    }
}
