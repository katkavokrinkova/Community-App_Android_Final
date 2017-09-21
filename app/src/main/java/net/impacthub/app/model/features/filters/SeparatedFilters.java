package net.impacthub.app.model.features.filters;

import java.util.List;

public class SeparatedFilters {

    private final List<Filter> cities;
    private final List<Filter> sectors;

    private SeparatedFilters(Builder builder) {
        cities = builder.cities;
        sectors = builder.sectors;
    }

    public List<Filter> getCities() {
        return cities;
    }

    public List<Filter> getSectors() {
        return sectors;
    }

    public static final class Builder {
        private List<Filter> cities;
        private List<Filter> sectors;

        public Builder() {
        }

        public Builder cities(List<Filter> cities) {
            this.cities = cities;
            return this;
        }

        public Builder sectors(List<Filter> sectors) {
            this.sectors = sectors;
            return this;
        }

        public SeparatedFilters build() {
            return new SeparatedFilters(this);
        }
    }
}
