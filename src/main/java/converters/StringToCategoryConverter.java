package converters;

import domain.Answer;
import domain.Category;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import repositories.AnswerRepository;
import repositories.CategoryRepository;
import security.Authority;

import java.net.URLDecoder;

public class StringToCategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category convert(final String text) {
        Category result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = categoryRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
