package net.impacthub.members.usecase.filters;

import android.support.annotation.NonNull;

import net.impacthub.members.model.filters.Filter;
import net.impacthub.members.model.filters.Filters;
import net.impacthub.members.model.filters.SeparatedFilters;
import net.impacthub.members.usecase.ApiCall;
import net.impacthub.members.usecase.SoqlRequestFactory;
import net.impacthub.members.usecase.UseCaseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.functions.Function;

import static net.impacthub.members.usecase.UseCaseModule.apiCallProvider;
import static net.impacthub.members.usecase.UseCaseModule.soqlRequestFactoryProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/28/2017.
 */

public class FiltersUseCase implements UseCaseGenerator<Single<SeparatedFilters>> {

    private final ApiCall<Filters> mFiltersApiCall = apiCallProvider();
    private final SoqlRequestFactory mSoqlRequestFactory = soqlRequestFactoryProvider();

    @Override
    public Single<SeparatedFilters> getUseCase() {
        return Single.fromCallable(new FiltersCallable()).map(new FiltersSeparator());
    }

    private class FiltersCallable implements Callable<Filters> {
        @Override
        public Filters call() throws Exception {
            return mFiltersApiCall.getResponse(mSoqlRequestFactory.createFilterCriteriaRequest(), Filters.class);
        }
    }

    private static class FiltersSeparator implements Function<Filters, SeparatedFilters> {

        private final List<Filter> mCities = new ArrayList<>();
        private final List<Filter> mSectors = new ArrayList<>();

        private void separateFilters(@NonNull Filters filters) {
            for (Filter filter : filters.getFilters()) {
                sortFilter(filter);
            }
        }

        private void sortFilter(Filter filter) {
            if(filter.getGrouping().equals("City")) {
                mCities.add(filter);
            } else {
                mSectors.add(filter);
            }
        }

        @Override
        public SeparatedFilters apply(@io.reactivex.annotations.NonNull Filters filters) throws Exception {
            if (filters != null) {
                separateFilters(filters);
            }

            return new SeparatedFilters.Builder()
                    .cities(mCities)
                    .sectors(mSectors)
                    .build();
        }
    }
}
