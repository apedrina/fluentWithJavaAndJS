package com.alissonpedrina.core.validator;

import com.alissonpedrina.core.domain.Category;
import com.alissonpedrina.core.error.Error;
import com.alissonpedrina.core.error.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CategoryValidator implements Validator<Category> {

    private static final String ERROR_HEADER = "========CATEGORY ERROR=======\n";

    @Override
    public void validate(Category category) {
        List<String> errors = new ArrayList<>();
        Optional<String> typeValid = Stream.of(Category.TYPES).filter(type -> type.equalsIgnoreCase(category.getType())).findFirst();
        if (!typeValid.isPresent()) {
            errors.add(String.format(Error.CATEGORY_NOT_EXIST_FORMAT, category.getType()));
        }

        if (category.getName() == null) {
            errors.add(Error.CATEGORY_NAME_EMPTY);

        }
        if (Category.TYPES[0].equalsIgnoreCase(category.getType()) && category.getYears() == null) {
            errors.add(Error.FRIENDS_YEARS_NONEXISTENT);

        }
        if (Category.TYPES[0].equalsIgnoreCase(category.getType()) && category.getDescription() != null) {
            errors.add(Error.FRIENDS_HAS_DESCRIPTION);

        }
        if (Category.TYPES[1].equalsIgnoreCase(category.getType()) && category.getDescription() == null) {
            errors.add(Error.FAMILY_WITHOUT_DESCRIPTION);

        }
        if (Category.TYPES[2].equalsIgnoreCase(category.getType()) && category.getDescription() != null) {
            errors.add(Error.ACQUAINTANCE_HAS_DESCRIPTION);

        }
        if (Category.TYPES[2].equalsIgnoreCase(category.getType()) && category.getYears() != null) {
            errors.add(Error.ACQUAINTANCE_HAS_YEARS);

        }
        Error error = new Error();
        error.setDetails(category.toString());
        error.setErrors(errors);

        if (error.getErrors().size() > 0) {
            StringBuilder errorsMessage = new StringBuilder(ERROR_HEADER + error.getDetails() + "\n");
            for (String categoryError : error.getErrors()) {
                errorsMessage.append(categoryError + "\n");

            }
            throw new ValidatorException(errorsMessage.toString());

        }

    }

}
