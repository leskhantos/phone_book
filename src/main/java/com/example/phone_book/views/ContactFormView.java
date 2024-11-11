package com.example.phone_book.views;

import com.example.phone_book.layout.MainLayOut;
import com.example.phone_book.model.Contact;
import com.example.phone_book.repository.ContactRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.*;

@Route(value = "contact", layout = MainLayOut.class)
@SpringComponent
@UIScope
public class ContactFormView extends VerticalLayout implements HasUrlParameter<String> {
    private final ContactRepository contactRepository;

    private final TextField firstName = new TextField("Имя");
    private final TextField lastName = new TextField("Фамилия");
    private final TextField phoneNumber = new TextField("Телефон");
    private final EmailField email = new EmailField("Email");
    private final TextField address = new TextField("Адрес");
    private final TextField notes = new TextField("Заметки");

    private final Button back = new Button("Назад");

    private final Button save = new Button("Сохранить");
    private final Button cancel = new Button("Отмена");

    private final Binder<Contact> binder = new Binder<>(Contact.class);
    private Contact contact;

    public ContactFormView(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;

        H2 title = new H2("Контакт");
        back.addClickListener(event -> navigateToList());
        HorizontalLayout backAndTitle = new HorizontalLayout(title, back);

        firstName.setRequired(true);
        lastName.setRequired(true);
        phoneNumber.setRequired(true);

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                firstName, lastName, phoneNumber,
                email, address, notes
        );

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());

        cancel.addClickListener(e -> navigateToList());

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        buttons.setSpacing(true);

        binder.bindInstanceFields(this);

        add(backAndTitle, formLayout, buttons);

        setMaxWidth("600px");
        setMargin(true);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String contactId) {
        if (contactId != null) {
            contact = contactRepository.findById(contactId)
                    .orElseGet(Contact::new);
        } else {
            contact = new Contact();
        }
        binder.setBean(contact);
    }

    private void save() {
        if (binder.validate().isOk()) {
            contactRepository.save(contact);
            showSuccessNotification();
            navigateToList();
        }
    }

    private void navigateToList() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }

    private void showSuccessNotification() {
        Notification notification = new Notification(
                "Контакт сохранен",
                3000,
                Notification.Position.TOP_CENTER
        );
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();
    }
}