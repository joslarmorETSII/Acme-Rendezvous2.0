package repositories;

import domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query("select avg(q.answers.size), stddev(q.answers.size) from Rendezvous r join r.questions q")
    Object[] avgDevAnswersQuestionsPerRendezvous();

    @Query("select a from Answer a where a.question.rendezvous.id=?1 and a.user.id = ?2")
    Collection<Answer> answersOfUserInRendezvous(Integer rendezvousId,Integer attendantId);

    //select a from Answer a.user = 31 and q.question.rendezvous=35;

}
