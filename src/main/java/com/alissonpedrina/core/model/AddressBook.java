package com.alissonpedrina.core.model;

import com.alissonpedrina.core.domain.Category;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {

    private List<Category> categories = new ArrayList<>();

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
