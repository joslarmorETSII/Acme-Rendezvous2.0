package controllers.User;


import controllers.AbstractController;
import domain.Requestt;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.RendezvousService;
import services.RequesttService;
import services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/requestt/user")
public class RequesttUserController  extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;

    @Autowired
    private RequesttService requesttService;


    // Constructor --------------------------------------------

    public RequesttUserController(){
        super();
    }

    // Creating -----------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Requestt requestt;
        User user;

        user = userService.findByPrincipal();
        requestt = requesttService.create();
        result = createEditModelAndView(requestt);


        return result;
    }


    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Requestt requestt, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(requestt);
        else
            try {
                requesttService.save(requestt);
                result = new ModelAndView("servise/list");//Todo: add servise Collection
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(requestt, "rendezvous.commit.error");
            }
        return result;
    }


    protected ModelAndView createEditModelAndView(Requestt requestt) {
        return createEditModelAndView(requestt,null);
    }

    protected ModelAndView createEditModelAndView(Requestt requestt, final String messageCode) {
        ModelAndView result;

        User user;
        user = userService.findByPrincipal();
        result = new ModelAndView("request/edit");
        result.addObject("requestt", requestt);
        result.addObject("user",user);
        result.addObject("userRendezvous",user.getRendezvouses());
        result.addObject("message", messageCode);

        return result;
    }
}
