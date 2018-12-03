package com.company.sample.web.location;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.company.sample.entity.Location;


@UiController("sample_Location.edit")
@UiDescriptor("location-edit.xml")
@EditedEntityContainer("locationDc")
@LoadDataBeforeShow
@Route(path = "edit", parentPrefix = "locations")
public class LocationEdit extends StandardEditor<Location> {
}