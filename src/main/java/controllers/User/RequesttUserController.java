package controllers.User;


import controllers.AbstractController;
import domain.*;
import forms.RequesttForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

        Assert.isTrue(servise.getInappropriate()==false,"This services is inappropiate");
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
                if(rendezvous.getServises().contains(servise))
                    return createEditModelAndView(requesttForm, "requestt.duplicate.error");
                else{
                requesttService.save(requestt);


                user = userService.findByPrincipal();
                creditCard = creditCardService.save(requesttForm.getCreditCard());
                user.setCreditCard(creditCard);

                result = new ModelAndView("servise/list");
                result.addObject("servises",serviseService.findAll());
                }
            }
            } catch( final Throwable oops){
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
        result.addObject("user",user);
        result.addObject("message", messageCode);

        return result;
    }
}
