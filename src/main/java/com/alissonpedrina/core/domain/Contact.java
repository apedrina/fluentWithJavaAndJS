package com.alissonpedrina.core.domain;

import com.alissonpedrina.core.error.Error;
import com.alissonpedrina.core.error.ScriptException;
import com.alissonpedrina.core.model.AddressBook;
import com.alissonpedrina.core.validator.ContactValidator;
import com.alissonpedrina.core.validator.Validator;
import com.alissonpedrina.io.DB;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Contact {
    private transient AddressBook editAddressBook;
    private transient Validator<Contact> validator;
    private String name;
    private String surname;
    private String telephone;
    private String email;
    private int age;
    private String hairColor;
    private String category;

    public Contact() {
        validator = new ContactValidator();

    }

    private Contact(ContactBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.telephone = builder.telephone;
        this.age = builder.age;
        this.email = builder.email;
        this.hairColor = builder.hairColor;
        this.category = builder.category;

        validator = new ContactValidator();

    }

    public void save() throws IOException {
        DB db = new DB();
        AddressBook addressBook = db.read();
        validate();

        Optional<Category> optionalCategory = addressBook.getCategories()
                .stream()
                .filter(category -> category.getType().equalsIgnoreCase(this.category))
                .findFirst();
        if (!optionalCategory.isPresent()) {
            throw new IllegalArgumentException(String.format(Error.CATEGORY_NOT_EXIST_FORMAT, this.category));

        }

        Optional<Contact> optionalContact = addressBook.getCategories()
                .stream()
                .flatMap(category -> category.getContacts().stream())
                .filter(contact -> contact.getName().equalsIgnoreCase(name) && contact.getSurname().equalsIgnoreCase(surname))
                .findFirst();
        if (optionalContact.isPresent()) {
            throw new IllegalArgumentException(Error.CONTACT_ALREADY_EXIST);

        } else {
            addressBook.getCategories().forEach(category -> {
                if (category.getType().equalsIgnoreCase(this.category)) {
                    category.getContacts().add(this);
                }
            });
            db.save(addressBook);

        }

    }

    public void edit() throws IOException {
        DB db = new DB();
        AddressBook addressBook = db.read();
        validate();

        Optional<Category> optionalCategory = addressBook.getCategories()
                .stream()
                .filter(category -> category.getType().equalsIgnoreCase(this.category))
                .findFirst();
        if (!optionalCategory.isPresent()) {
            throw new IllegalArgumentException(String.format(Error.CATEGORY_NOT_EXIST_FORMAT, this.category));

        }
        Optional<Contact> optionalContact = addressBook.getCategories()
                .stream()
                .flatMap(category -> category.getContacts().stream())
                .filter(contact -> contact.equals(this))
                .findFirst();
        if (optionalContact.isPresent()) {
            throw new IllegalArgumentException(Error.CONTACT_ALREADY_EXIST);

        } else {
            editAddressBook.getCategories().forEach(category -> {
                if (category.getType().equalsIgnoreCase(this.category)) {
                    category.getContacts().add(this);
                }
            });
            db.save(editAddressBook);

        }

    }

    public void remove() throws IOException {
        DB db = new DB();
        AddressBook addressBook = db.read();
        Optional<Contact> optionalContact = addressBook.getCategories()
                .stream()
                .flatMap(category -> category.getContacts().stream())
                .filter(contact -> contact.getName().equalsIgnoreCase(name) && contact.getSurname().equalsIgnoreCase(surname))
                .findFirst();
        if (optionalContact.isPresent()) {
            Contact contactFromOpt = optionalContact.get();
            addressBook.getCategories().forEach(category -> {
                category.getContacts().remove(contactFromOpt);
            });
            db.save(addressBook);

        } else {
            throw new ScriptException(String.format(Error.CONTACT_REMOVE_FAIL_FORMAT, name, surname));

        }

    }

    public void validate() {
        this.validator.validate(this);
    }

    public String getCategory() {
        return category;
    }

    public Contact setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;

    }

    public String getSurname() {
        return surname;
    }

    public Contact setSurname(String surname) {
        this.surname = surname;
        return this;

    }

    public String getTelephone() {
        return telephone;
    }

    public Contact setTelephone(String telephone) {
        this.telephone = telephone;
        return this;

    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;

    }

    public int getAge() {
        return age;
    }

    public Contact setAge(int age) {
        this.age = age;
        return this;

    }

    public String getHairColor() {
        return hairColor;
    }

    public Contact setHairColor(String hairColor) {
        this.hairColor = hairColor;
        return this;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return age == contact.age &&
                name.equals(contact.name) &&
                surname.equals(contact.surname) &&
                telephone.equals(contact.telephone) &&
                email.equals(contact.email) &&
                Objects.equals(hairColor, contact.hairColor) &&
                Objects.equals(category, contact.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, telephone, email, age, hairColor, category);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", surName='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", hairColor='" + hairColor + '\'' +
                '}';

    }

    public void setEditAddressBook(AddressBook addressBook) {
        this.editAddressBook = addressBook;

    }

    public static class ContactBuilder {
        private String name;
        private String surname;
        private String telephone;
        private String email;
        private Integer age;
        private String hairColor;
        private String category;

        public ContactBuilder() {

        }

        public ContactBuilder name(String value) {
            name = value;
            return this;

        }

        public ContactBuilder surname(String value) {
            surname = value;
            return this;

        }

        public ContactBuilder age(Integer value) {
            age = value;
            return this;

        }

        public ContactBuilder hairColor(String value) {
            hairColor = value;
            return this;

        }

        public ContactBuilder telephone(String value) {
            telephone = value;
            return this;

        }

        public ContactBuilder email(String value) {
            email = value;
            return this;

        }

        public ContactBuilder category(String value) {
            category = value;
            return this;

        }

        public void save() throws IOException {
            Contact contact = new Contact(this);
            contact.validate();
            contact.save();

        }

        public Contact getByNames(String name, String surname) throws IOException {
            DB db = new DB();
            AddressBook addressBook = db.read();
            Optional<Contact> optionalContact = addressBook.getCategories()
                    .stream()
                    .flatMap(category -> category.getContacts().stream())
                    .filter(contact -> contact.getName().equalsIgnoreCase(name) && contact.getSurname().equalsIgnoreCase(surname))
                    .findFirst();
            if (optionalContact.isPresent()) {
                Contact contactToEdit = optionalContact.get();
                addressBook.getCategories().forEach(category -> {
                    category.getContacts().removeIf(contact -> contact.equals(contactToEdit));

                });
                contactToEdit.setEditAddressBook(addressBook);
                return contactToEdit;

            } else {
                throw new ScriptException(String.format(Error.CONTACT_NOT_FOUND_FORMAT, name, surname));

            }

        }

        public String showAll() throws IOException {
            DB db = new DB();
            AddressBook addressBook = db.read();
            StringBuilder index = new StringBuilder();

            List<Contact> contacts = addressBook.getCategories()
                    .stream()
                    .flatMap(category -> category.getContacts().stream())
                    .collect(Collectors.toList());
            contacts.sort((Contact c1, Contact c2) -> c1.getSurname().compareTo(c2.getSurname()));

            contacts.forEach(contact -> {
                index.append(contact.getSurname() + ";");
                display(contact);

            });

            return index.toString();
        }

    }

    public void show() throws IOException {
        display(this);

    }

    private static void display(Contact contact) {
        StringBuilder display = new StringBuilder("\n\n");
        display.append("Surname: " + contact.getSurname() + "\n");
        display.append("Name: " + contact.getName() + "\n");
        display.append("Category: " + contact.getCategory() + "\n");
        display.append("Email: " + contact.getEmail() + "\n");
        display.append("Telephone: " + contact.getTelephone() + "\n");
        if (contact.getHairColor() != null) {
            display.append("Hair color: " + contact.getHairColor() + "\n");

        }
        if (contact.getAge() > 0) {
            display.append("Age: " + contact.getAge() + "\n");

        }
        System.out.println(display.toString() + "\n");
        System.out.println("\n---------------------------------");

    }

}
