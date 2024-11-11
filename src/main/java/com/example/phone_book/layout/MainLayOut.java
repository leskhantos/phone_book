package com.example.phone_book.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MainLayOut extends AppLayout {

    public MainLayOut() {
        createHeader();
    }

    private void createHeader() {
        H1 logo = new H1("Телефонный справочник");
        logo.addClassNames("text-l", "m-m");
        HorizontalLayout header = new HorizontalLayout(logo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
}