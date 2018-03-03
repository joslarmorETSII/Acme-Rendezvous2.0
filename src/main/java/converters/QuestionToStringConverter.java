package converters;

import domain.Question;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class QuestionToStringConverter implements Converter<Question,String> {

    @Override
    public String convert(final Question question) {
        String result;

        if (question== null)
            result = null;
        else
            result = String.valueOf(question.getId());

        return result;
    }
}
