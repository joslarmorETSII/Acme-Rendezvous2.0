package controllers.User;

import controllers.AbstractController;
import domain.Answer;
import domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AnswerService;
import services.QuestionService;
import services.RendezvousService;

import java.util.Collection;

@Controller
@RequestMapping("/answer/user")
public class AnswerUserController  extends AbstractController {

    // Services --------------------------------------------
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RendezvousService rendezvousService;

    // Constructor -----------------------------------------
    public AnswerUserController() {
        super();
    }

    // Creation --------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Answer answer;

        answer = answerService.create();
        result = createEditModelAndView(answer);
        return result;
    }

    // Listing ----------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int questionId){
        ModelAndView result;
        Question question;
        Collection<Answer> answers;

        question = questionService.findOne(questionId);
        answers  = question.getAnswers();
        result = new ModelAndView("answer/list");
        result.addObject("answers",answers);

        return result;
    }

    private ModelAndView createEditModelAndView(Answer answer) {
        return null;
    }
}
