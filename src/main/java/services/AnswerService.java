package services;

import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AnswerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class AnswerService {

    // Managed repository -----------------------------------------------------
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnnouncementService announcementService;

    // Supporting services ----------------------------------------------------

    // Constructors -----------------------------------------------------------
    public AnswerService(){super();}

    // Simple CRUD methods ----------------------------------------------------

    public Answer create(){
        Answer answer;
        User user = userService.findByPrincipal();
        answer = new Answer();
        answer.setUser(user);

        return answer;
    }

    public Answer findOne(Integer answerId){
        return answerRepository.findOne(answerId);
    }

    public Collection<Answer> findAll(){
        return answerRepository.findAll();
    }

    public Answer save(Answer answer){
        User user = answer.getUser();
        Answer aux = answerRepository.save(answer);
        user.getAnswers().add(aux);
        userService.save(user);
        return aux;
    }

    public void deleteAnswers(Collection<Answer> answers, Rendezvous rendezvous){
        List<Question> questions = new ArrayList<>(rendezvous.getQuestions());
        List<Answer> answersAux = new ArrayList<>(answers);

        for(int i=0;i<questions.size();i++){
            Question q =questions.get(i);
            q.getAnswers().remove(answersAux.get(i));
        }

        answerRepository.delete(answers);
    }
    // Other business methods -------------------------------------------------
    public void saveAnswers(Collection<Answer> answers){
        answerRepository.save(answers);
    }

    public Object[] avgDevAnswersQuestionsPerRendezvous() {
        Object[] result;
        result = this.answerRepository.avgDevAnswersQuestionsPerRendezvous();
        this.announcementService.formatDecimal(result);
        return result;
    }

    public Collection<Answer> answersOfUserInRendezvous(Integer rendezvousId,Integer attendantID){
        return answerRepository.answersOfUserInRendezvous(rendezvousId,attendantID);
    }
}
