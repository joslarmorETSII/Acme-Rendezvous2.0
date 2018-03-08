package controllers.User;


import controllers.AbstractController;
import domain.*;
import forms.RequesttForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.Valid;
import java.util.Collection;

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

    @Autowired
    private ServiseService serviseService;

    @Autowired
    private CreditCardService creditCardService;


    // Constructor --------------------------------------------

    public RequesttUserController(){
        super();
    }

    // Creating -----------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam Integer serviseId) {
        ModelAndView result;
        Servise servise;
        RequesttForm requesttForm;

        servise = serviseService.findOne(serviseId);
        requesttForm = new RequesttForm();
        requesttForm.setServise(servise);
        result = createEditModelAndView(requesttForm);

        return result;
    }

    //  Edition ----------------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid RequesttForm requesttForm, final BindingResult binding) {
        ModelAndView result;
        Requestt requestt;
        Rendezvous rendezvous;
        Servise servise;
        User user;
        CreditCard creditCard;
        try {
            requestt = requesttService.reconstruct(requesttForm, binding);

            if (binding.hasErrors())
                result = createEditModelAndView(requesttForm);
            else {

                rendezvous = requesttForm.getRendezvous();
                servise = requesttForm.getServise();
                servise.setAssigned(true);
                rendezvous.getServises().add(servise);
                servise.getRendezvouses().add(rendezvous);
                user = userService.findByPrincipal();
                creditCard = creditCardService.save(requesttForm.getCreditCard());
                user.setCreditCard(creditCard);
                userService.save(user);
                requesttService.save(requestt);
                rendezvousService.save(rendezvous);
                serviseService.save(servise);
                result = new ModelAndView("servise/list");
                result.addObject("servises",serviseService.findAll());
            }
            } catch( final Throwable oops){
                if(oops.getCause().getCause().getMessage().contains("Duplicate entry"))
                    result = createEditModelAndView(requesttForm, "requestt.duplicate.error");
                else
                    result = createEditModelAndView(requesttForm, "general.commit.error");
        }
        return result;
    }

    protected ModelAndView createEditModelAndView(RequesttForm requesttForm) {
        return createEditModelAndView(requesttForm,null);
    }

    protected ModelAndView createEditModelAndView(RequesttForm requesttForm, final String messageCode) {
        ModelAndView result;
        User user;
        Collection<Rendezvous> userRendezvous;


        user = userService.findByPrincipal();
        userRendezvous = user.getRendezvouses();
        result = new ModelAndView("request/edit");
        result.addObject("requesttForm", requesttForm);
        result.addObject("userRendezvous",userRendezvous);
        result.addObject("nothingToDisplay",userRendezvous.size()==0);
        result.addObject("message", messageCode);

        return result;
    }
}
