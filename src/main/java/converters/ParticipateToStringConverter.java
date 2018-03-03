package converters;


import domain.Announcement;
import domain.Participate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ParticipateToStringConverter implements Converter<Participate, String> {
    @Override
    public String convert(final Participate participate) {
        String result;

        if (participate == null)
            result = null;
        else
            result = String.valueOf(participate.getId());

        return result;
    }
}
