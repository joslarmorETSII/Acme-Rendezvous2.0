package converters;

import domain.Requestt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RequestToStringConverter  implements Converter<Requestt,String> {

    @Override
    public String convert(final Requestt request) {
        String result;

        if (request== null)
            result = null;
        else
            result = String.valueOf(request.getId());

        return result;
    }


}
