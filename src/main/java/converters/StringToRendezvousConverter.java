package converters;

import domain.Rendezvous;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import services.RendezvousService;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToRendezvousConverter implements Converter<String, Rendezvous> {

    @Autowired
    RendezvousService rendezvousService;

    @Override
    public Rendezvous convert(String text) {
        Rendezvous result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = rendezvousService.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
