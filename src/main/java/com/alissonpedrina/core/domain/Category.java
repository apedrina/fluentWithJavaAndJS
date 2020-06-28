package com.alissonpedrina.core.domain;

import com.alissonpedrina.core.error.Error;
import com.alissonpedrina.core.error.ScriptException;
import com.alissonpedrina.core.model.AddressBook;
import com.alissonpedrina.core.validator.CategoryValidator;
import com.alissonpedrina.core.validator.Validator;
import com.alissonpedrina.io.DB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Category {

    public static transient String[] TYPES = {"Friends", "Family", "Acquaintance"};

    private transient Validator validator;
    private transient AddressBook addressBook;
    private String type;
    private String name;
    private String description;
    private Integer years;
    private List<Contact> contacts = new ArrayList<>();

    public Category() {
        validator = new CategoryValidator();

    }

    private Category(CategoryBuilder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.years = builder.years;
        this.description = builder.description;
        validator = new CategoryValidator();

    }

    public void remove() throws IOException {
        DB db = new DB();
        AddressBook addressBook = db.read();
        Optional<Category> optionalCategory = addressBook.getCategories()
                .stream()
                .filter(category -> category.getName().equalsIgnoreCase(name))
                .findFirst();
        if (optionalCategory.isPresent()) {
            addressBook.getCategories().remove(optionalCategory.get());
            db.save(addressBook);

        } else {
            throw new ScriptException(String.format(Error.CATEGORY_NOT_EXIST_FORMAT, this.name));

        }

    }

    public void save() throws IOException {
        DB db = new DB();
        AddressBook addressBook = db.read();
        Optional<Category> optionalCategory = addressBook.getCategories().stream().filter(category -> category.getName().equalsIgnoreCase(name)).findFirst();
        if (optionalCategory.isPresent()) {
            throw new IllegalArgumentException(Error.CATEGORY_ALREADY_EXIST);

        } else {
            addressBook.getCategories().add(this);
            db.save(addressBook);

        }

    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;

    }

    public void validate() {
        this.validator.validate(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return type.equals(category.type) &&
                name.equals(category.name) &&
                Objects.equals(description, category.description) &&
                Objects.equals(years, category.years);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, description, years);
    }

    @Override
    public String toString() {
        return "Category{" +
                "type='" + type + '\'' +
                ", validator=" + validator +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", years=" + years +
                ", contacts=" + contacts +
                '}';
    }

    public static class CategoryBuilder {
        private String type;
        private String name;
        private String description;
        private Integer years;
        private List<Contact> contacts;
        private Category categoryToEdit;

        public CategoryBuilder() {

        }

        public CategoryBuilder name(String value) {
            name = value;
            return this;

        }

        public CategoryBuilder type(String value) {
            type = value;
            return this;

        }

        public CategoryBuilder description(String value) {
            description = value;
            return this;
        }

        public CategoryBuilder years(int value) {
            years = value;
            return this;

        }

        public Category build() {
            Category category = new Category(this);
            return category;
        }

        public void save() throws IOException {
            Category category = new Category(this);
            category.validate();
            category.save();

        }

        public Category getByName(String nameParam) throws IOException {
            DB db = new DB();
            AddressBook addressBook = db.read();
            Optional<Category> optionalCategory = addressBook.getCategories()
                    .stream()
                    .filter(category -> category.getName().equalsIgnoreCase(nameParam))
                    .findFirst();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                addressBook.getCategories().remove(category);
                category.setAddressBook(addressBook);
                return optionalCategory.get();

            } else {
                throw new ScriptException(String.format(Error.CATEGORY_NOT_FOUND_FORMAT, nameParam));

            }

        }

    }

}
