package services;

import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.QuestionRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class QuestionService {

    // Managed repository -----------------------------------------------------
    @Autowired
    private QuestionRepository questionRepository;

    // Supporting services ----------------------------------------------------
    @Autowired
    private UserService userService;

    @Autowired
    private AnnouncementService announcementService;

    // Constructors -----------------------------------------------------------
    public QuestionService(){super();}

    // Simple CRUD methods ----------------------------------------------------
    public Question create(){
        Question question;

        question = new Question();
        question.setAnswers(new ArrayList<Answer>());

        return question;
    }

   public Question findOne(Integer questionId){
        return questionRepository.findOne(questionId);
   }

   public Collection<Question> findAll(){
        return questionRepository.findAll();
   }

   public Question save(Question question){
        checkByPrincipal(question);
        Assert.isTrue(question.getAnswers().isEmpty(),"has answers");
        return questionRepository.saveAndFlush(question);
   }

   public void delete(Question question){
        checkByPrincipal(question);
        questionRepository.delete(question);
   }

   public Question findOneToEdit(Integer questionId){
        Question question = questionRepository.findOne(questionId);
        Assert.notNull(question);
        Assert.isTrue(question.getAnswers().isEmpty());
        checkByPrincipal(question);
        return question;
   }

   public void saveAll(Collection<Question> questions){
        questionRepository.save(questions);
   }

   public void deleteQuestions(Rendezvous rendezvous){
       this.questionRepository.delete(rendezvous.getQuestions());
   }

    // Other business methods -------------------------------------------------

    private void checkByPrincipal(Question question) {
        User principal = userService.findByPrincipal();
        User creater = question.getRendezvous().getCreator();
        Assert.isTrue(principal.equals(creater));
    }

    public Object[] questionsPerRendezvous() {

        Object[] result;

        result = this.questionRepository.questionsPerRendezvous();
        result = this.announcementService.formatDecimal(result);
        return result;
    }
}
