package controllers.User;

import controllers.AbstractController;
import domain.Question;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.QuestionService;
import services.RendezvousService;
import services.UserService;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/question/user")
public class QuestionUserController extends AbstractController {

    // Services --------------------------------------------
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RendezvousService rendezvousService;

    // Constructor -----------------------------------------
    public QuestionUserController() {
        super();
    }

    // Creation --------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Question question;

        question = questionService.create();
        result = this.createEditModelAndView(question);

        return result;
    }


    // edition ---------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int questionId){
        ModelAndView result;
        Question question;

        question = questionService.findOneToEdit(questionId);
        result = createEditModelAndView(question);
        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "save")
    public ModelAndView edit(@Valid Question question, BindingResult bindingResult){
        ModelAndView result;
        if(bindingResult.hasErrors())
            result = createEditModelAndView(question);
        else{
            try{
                questionService.save(question);
                result = new ModelAndView("redirect: list.do?rendezvousId="+question.getRendezvous().getId());

            }catch (Throwable oops){
                result = createEditModelAndView(question,"question.save.error");
            }
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "delete")
    public ModelAndView edit(Question question){
        ModelAndView result;

        try{
            questionService.delete(question);
            result = new ModelAndView("redirect: list.do?rendezvousId="+question.getRendezvous().getId());
        }catch (Throwable oops){
            result = createEditModelAndView(question,"question.delete.error");
        }

        return result;
    }
    // listing ---------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int rendezvousId){
        ModelAndView result;
        Rendezvous rendezvous;
        Collection<Question> questions;

        rendezvous = rendezvousService.findOne(rendezvousId);
        questions  = rendezvous.getQuestions();
        result = new ModelAndView("question/list");
        result.addObject("questions",questions);

        return result;
    }



    protected ModelAndView createEditModelAndView(Question question) {
        ModelAndView result;

        result = this.createEditModelAndView(question, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(Question question,  String messageCode) {
        ModelAndView result;
        Collection<Rendezvous> rendezvousCollection;
        User user ;

        user = userService.findByPrincipal();
        //rendezvousCollection = user.getRendezvouses();
        rendezvousCollection =rendezvousService.rendezvousWithNoquestionAnswered(user.getId());

        result = new ModelAndView("question/edit");
        result.addObject("question", question);
        result.addObject("rendezvousCollection", rendezvousCollection);
        result.addObject("cancelUri", "welcome/index.do");
        result.addObject("message", messageCode);
        return result;
    }
}
