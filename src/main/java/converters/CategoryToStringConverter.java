package converters;

import domain.Category;
import org.springframework.core.convert.converter.Converter;

public class CategoryToStringConverter implements Converter<Category, String> {
    @Override
    public String convert(final Category category) {
        String result;

        if (category == null)
            result = null;
        else
            result = String.valueOf(category.getId());

        return result;
    }
}
