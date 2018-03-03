package controllers.Administrator;

import controllers.AbstractController;
import domain.Comment;
import domain.Rendezvous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CommentService;
import services.RendezvousService;
import services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/rendezvous/administrator")
public class RendezvousAdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;


    // Constructor --------------------------------------------

    public RendezvousAdministratorController() {
        super();
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int rendezvousId) {
        final ModelAndView result;
        Rendezvous rendezvous;
        rendezvous = this.rendezvousService.findOne(rendezvousId);
        this.rendezvousService.deleteAdmin(rendezvous);
        result = new ModelAndView("redirect: listAll.do");
        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Rendezvous> rendezvous;

        rendezvous = this.rendezvousService.findAll();

        result = new ModelAndView("rendezvous/listAll");
        result.addObject("rendezvous", rendezvous);
        result.addObject("requestURI", "rendezvous/administrator/listAll.do");
        return result;

    }

}


