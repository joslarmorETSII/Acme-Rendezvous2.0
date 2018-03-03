package repositories;

import domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("select avg(r.questions.size),stddev(r.questions.size) from Rendezvous r")
    Object[] questionsPerRendezvous();
}
