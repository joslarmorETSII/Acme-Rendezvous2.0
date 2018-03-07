package converters;

import domain.Answer;
import domain.Servise;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ServiseToStringConverter implements Converter<Servise,String> {

    @Override
    public String convert(Servise servise) {
        String result;

        if (servise== null)
            result = null;
        else
            result = String.valueOf(servise.getId());

        return result;
    }
}
