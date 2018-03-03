package repositories;

import domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

    @Query("select avg(c.childrenComments.size),sqrt(sum(c.childrenComments.size * c.childrenComments.size)/ count(c) - (avg(c.childrenComments.size) *avg(c.childrenComments.size))) from Comment c")
    Object[] avgDevRepliesPerComment();
}
