package com.company.sample.web.location;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.company.sample.entity.Location;


@UiController("sample_Location.browse")
@UiDescriptor("location-browse.xml")
@LookupComponent("locationsTable")
@LoadDataBeforeShow
@Route("locations")
public class LocationBrowse extends StandardLookup<Location> {
}