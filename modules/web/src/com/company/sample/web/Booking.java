package com.company.sample.web;

import com.company.sample.entity.Location;
import com.company.sample.entity.LocationType;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.UrlRouting;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.navigation.UrlParamsChangedEvent;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.theme.HaloTheme;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@UiController("sample_Booking")
@UiDescriptor("booking.xml")
@Route("booking")
public class Booking extends Screen {

    @Inject
    private CollectionContainer<Location> locations;

    @Inject
    private TextField<String> nameFilter;
    @Inject
    private LookupField<LocationType> typeFilter;
    @Inject
    private TextField<Integer> capacityFilter;
    @Inject
    private LookupField<Integer> starsFilter;
    @Inject
    private ScrollBoxLayout resultsBox;

    @Inject
    private UiComponents uiComponents;
    @Inject
    private Messages messages;
    @Inject
    private UrlRouting urlRouting;

    private boolean initialized = false;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        if (!initialized) {
            getScreenData().loadAll();

            initFilters(null, LocationType.COUNTRY_HOUSE, 6, 3);
            filterResults();

            updateUrl();
        }
    }

    @Subscribe
    protected void onUrlParamsChanged(UrlParamsChangedEvent event) {
        if (!initialized) {
            initialized = true;

            getScreenData().loadAll();
        }

        Map<String, String> params = event.getParams();

        String name = params.get("name");
        LocationType locationType = LocationType.fromId(params.get("type"));
        Integer capacity = NumberUtils.toInt(params.get("capacity"));
        Integer stars = NumberUtils.toInt(params.get("stars"));

        initFilters(name, locationType, capacity, stars);
        filterResults(name, locationType, capacity, stars);
    }

    @Subscribe("applyFiltersBtn")
    protected void onApplyFiltersBtnClick(Button.ClickEvent event) {
        updateUrl();
        filterResults();
    }

    @Subscribe("resetFiltersBtn")
    protected void onResetFiltersBtnClick(Button.ClickEvent event) {
        nameFilter.setValue("");
        typeFilter.setValue(null);
        capacityFilter.setValue(null);
        starsFilter.setValue(null);

        filterResults("", null, null, null);
        updateUrl();
    }

    private void updateUrl() {
        String name = nameFilter.getValue();
        LocationType type = typeFilter.getValue();
        Integer capacity = capacityFilter.getValue();
        Integer stars = starsFilter.getValue();

        Map<String, String> params = new LinkedHashMap<>();

        if (StringUtils.isNotEmpty(name)) {
            params.put("name", name);
        }
        if (type != null) {
            params.put("type", type.getId());
        }
        if (capacity != null) {
            params.put("capacity", capacity.toString());
        }
        if (stars != null) {
            params.put("stars", stars.toString());
        }

        urlRouting.replaceState(this, params);
    }

    private void filterResults() {
        filterResults(nameFilter.getValue(), typeFilter.getValue(), capacityFilter.getValue(), starsFilter.getValue());
    }

    private void filterResults(String name, LocationType locationType, Integer capacity, Integer stars) {
        List<Location> filtered = locations.getItems().stream()
                .filter(location -> isEmpty(location.getName()) || isEmpty(name) || location.getName().contains(name))
                .filter(location -> locationType == null || location.getType() == locationType)
                .filter(location -> capacity == null || location.getCapacity() >= capacity)
                .filter(location -> stars == null || location.getStars() == null || location.getStars() >= stars)
                .collect(Collectors.toList());

        showResults(filtered);
    }

    private void initFilters(String name, LocationType locationType, Integer capacity, Integer stars) {
        nameFilter.setValue(name);
        typeFilter.setValue(locationType);
        capacityFilter.setValue(capacity);
        starsFilter.setOptionsList(Arrays.asList(1, 2, 3, 4, 5));
        starsFilter.setValue(stars);
    }

    private void showResults(Collection<Location> locations) {
        resultsBox.removeAll();

        for (Location location : locations) {
            VBoxLayout container = uiComponents.create(VBoxLayout.class);
            container.setStyleName(HaloTheme.LAYOUT_WELL);

            VBoxLayout header = uiComponents.create(VBoxLayout.class);
            header.setStyleName(HaloTheme.LAYOUT_HEADER);

            Label<String> headerLabel = uiComponents.create(Label.TYPE_STRING);
            headerLabel.setStyleName(HaloTheme.LABEL_H2);
            headerLabel.setValue(location.getName());

            header.add(headerLabel);
            container.add(header);

            VBoxLayout details = uiComponents.create(VBoxLayout.class);

            VBoxLayout detailsWrapper = uiComponents.create(VBoxLayout.class);
            detailsWrapper.setMargin(true);
            details.add(detailsWrapper);

            Label<String> type = uiComponents.create(Label.TYPE_STRING);
            type.setValue(messages.getMessage(location.getType()));
            detailsWrapper.add(type);

            Label<String> capacity = uiComponents.create(Label.TYPE_STRING);
            capacity.setValue("Capacity: " + location.getCapacity());
            detailsWrapper.add(capacity);

            if (location.getStars() != null) {
                Label<String> rating = uiComponents.create(Label.TYPE_STRING);
                rating.setValue("Rating: " + location.getStars());
                detailsWrapper.add(rating);
            }

            container.add(details);

            resultsBox.add(container);
        }
    }
}