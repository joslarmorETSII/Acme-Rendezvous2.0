package services;

import domain.*;
import forms.QuestionsForm;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import repositories.ParticipateRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ParticipateService {

    // Managed Repository -----------------------------------------------------
    @Autowired
    private ParticipateRepository participateRepository;

    // Supporting services ----------------------------------------------------
    @Autowired
    private UserService userService;

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private Validator validator;

    // Constructors -----------------------------------------------------------
    public ParticipateService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------
    public Participate create(){
        Participate participate;
        User principal;

        principal = userService.findByPrincipal();
        participate = new Participate();
        participate.setAttendant(principal);

        return participate;
    }

    public Participate findOne(Integer participateId){
        return participateRepository.findOne(participateId);
    }

    public Collection<Participate> findAll(){
        return participateRepository.findAll();
    }

    public Participate save(Participate participate){
        Date currentDate = new Date();
        Rendezvous rendezvous;
        Participate res;

        Assert.notNull(participate);
        checkByPrincipal(participate);
        rendezvous = participate.getRendezvous();
        //Assert.isTrue(!rendezvous.equals(participate.getRendezvous()));
        if(participate.getRendezvous().getForAdults())
            checkMayorEdad(participate.getAttendant());

        //Assert.isTrue(currentDate.before(participate.getMoment()));
        participate.setMoment(currentDate);

        res =  participateRepository.save(participate);

        rendezvous.getParticipated().add(participate);

        return res;
    }

    public void delete(Participate participate){
        Assert.notNull(participate);
        checkByPrincipal(participate);
        Assert.isTrue(participate.getRendezvous().getMoment().after(participate.getMoment()));
        if(participate.getAttendant().equals(participate.getRendezvous().getCreator())){
            rendezvousService.delete(participate.getRendezvous());
        }

        participateRepository.delete(participate);
    }

    public void deleteParticipated(Rendezvous rendezvous){
        this.participateRepository.delete(rendezvous.getParticipated());
    }

    // Other business methods -------------------------------------------------
    private void checkByPrincipal(Participate participate) {
        User principal = userService.findByPrincipal();
        Assert.isTrue(principal.equals(participate.getAttendant()));
    }

    public List<Answer> reconstruct(QuestionsForm questionsForm, String[] answers,BindingResult binding) {
        Rendezvous result;

        List<Question> questions = new ArrayList<>(questionsForm.getQuestions());
        List<Answer> allAnswer = new ArrayList<>();
        questions = new ArrayList<>(questionsForm.getQuestions());

        for(int i = 0;i<questions.size();i++){
            Answer userAnswer = answerService.create();
            userAnswer.setAnswer(answers[i+1]);
            userAnswer.setQuestion(questions.get(i));
            validator.validate(userAnswer,binding);

            allAnswer.add(userAnswer);
        }


        return allAnswer;
    }
    public Participate participate(int attendantId,int rendezvousId){
        Participate participate =participateRepository.participate(attendantId,rendezvousId);
        return participate;
    }

    public void checkMayorEdad(User attendant){

        Calendar calendar = Calendar.getInstance();
        Long fechaActual = calendar.getTimeInMillis();

        Calendar birthday = Calendar.getInstance();
        birthday.setTime(attendant.getBirthday());
        Long fechaNacimiento = birthday.getTimeInMillis();

        Long aux = fechaActual -  fechaNacimiento;
        Long anosDieciocho = (long) 568036800000L;

        Assert.isTrue(aux > anosDieciocho,"Debe ser mayor de edad");
    }


}
