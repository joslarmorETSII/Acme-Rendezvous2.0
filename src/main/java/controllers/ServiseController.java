package controllers;

import domain.Actor;
import domain.Manager;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.RendezvousService;
import services.ServiseService;
import services.UserService;

import java.util.Date;

@Controller
@RequestMapping("/servise")
public class ServiseController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServiseService serviseService;

    @Autowired
    private ActorService actorService;


    // Constructor --------------------------------------------

    public ServiseController(){
        super();
    }

    // Listing -------------------------------------------------------
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView listAll() {
        ModelAndView result;

        Actor actor = this.actorService.findByPrincipal();

        result = new ModelAndView("servise/list");
        result.addObject("servises", serviseService.findAll());
        result.addObject("requestURI","servise/listAll.do");

        if(actorService.isManager()){
            result.addObject("manager",actor);
        }
        return result;
    }
}
