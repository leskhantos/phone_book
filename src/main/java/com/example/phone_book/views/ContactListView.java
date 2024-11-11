package com.example.phone_book.views;

import com.example.phone_book.layout.MainLayOut;
import com.example.phone_book.model.Contact;
import com.example.phone_book.repository.ContactRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "", layout = MainLayOut.class)
@SpringComponent
@UIScope
public class ContactListView extends VerticalLayout {
    private final ContactRepository contactRepository;
    private final Grid<Contact> grid;

    public ContactListView(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        this.grid = new Grid<>(Contact.class);

        H2 title = new H2("Список контактов");

        grid.setColumns("firstName", "lastName", "phoneNumber", "email", "address", "notes");
        grid.getColumnByKey("firstName").setHeader("Имя");
        grid.getColumnByKey("lastName").setHeader("Фамилия");
        grid.getColumnByKey("phoneNumber").setHeader("Телефон");
        grid.getColumnByKey("email").setHeader("Email");
        grid.getColumnByKey("address").setHeader("Адрес");
        grid.getColumnByKey("notes").setHeader("Примечание");

        grid.addComponentColumn(contact -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.addClickListener(e ->
                    editButton.getUI().ifPresent(ui ->
                            ui.navigate(ContactFormView.class, contact.getId()))
            );

            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addClickListener(e -> {
                contactRepository.delete(contact);
                updateList();
            });

            actions.add(editButton, deleteButton);
            return actions;
        }).setHeader("Действия").setWidth("150px");

        Button addButton = new Button("Добавить контакт", new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(e ->
                addButton.getUI().ifPresent(ui ->
                        ui.navigate(ContactFormView.class))
        );

        Button printButton = new Button("Печать", new Icon(VaadinIcon.PRINT));
        printButton.addClickListener(e -> generateReport());

        HorizontalLayout toolbar = new HorizontalLayout(addButton, printButton);
        toolbar.setSpacing(true);

        add(title, toolbar, grid);
        setSizeFull();

        updateList();
    }

    public void updateList() {
        grid.setItems(contactRepository.findAll());
    }

    private void generateReport() {
        getUI().ifPresent(ui ->
                ui.getPage().open("/api/contacts/pdf", "_blank")
        );
    }
}
