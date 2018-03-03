package converters;

import domain.Comment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.CommentRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class CommentToStringConverter implements Converter<Comment,String> {

    @Override
    public String convert(final Comment comment) {
        String result;

        if (comment== null)
            result = null;
        else
            result = String.valueOf(comment.getId());

        return result;
    }

}
