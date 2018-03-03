package converters;

import domain.Answer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AnswerToStringConverter implements Converter<Answer,String> {

    @Override
    public String convert(Answer answer) {
        String result;

        if (answer== null)
            result = null;
        else
            result = String.valueOf(answer.getId());

        return result;
    }
}
