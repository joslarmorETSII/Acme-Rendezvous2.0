package controllers;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AnnouncementService;
import services.AnswerService;
import services.RendezvousService;
import services.UserService;

import java.util.Collection;

@Controller
@RequestMapping("/answer")
public class AnswerController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AnswerService answerService;

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;

    // Constructor -----------------------------------------
    public AnswerController() {
        super();
    }

    // List -----------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam final int rendezvousId) {
        Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);

        Collection<User> attendants= userService.rendezvousAttendants(rendezvousId);
        final ModelAndView res = new ModelAndView("rendezvous/participated");
        res.addObject("rendezvous", rendezvous);
        res.addObject("questions", rendezvous.getQuestions());
        res.addObject("requestURI", "answer/list.do");

        return res;
    }


}
