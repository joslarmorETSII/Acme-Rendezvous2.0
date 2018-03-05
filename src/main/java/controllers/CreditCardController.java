package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CreditCardService;
import domain.Actor;
import domain.CreditCard;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController {

    @Autowired
    private CreditCardService	creditCardService;

    @Autowired
    private ActorService		actorService;


    public CreditCardController() {
        super();
    }

    // Create

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        ModelAndView res;
        CreditCard creditCard;

        final Actor actor = this.actorService.findByPrincipal();
        creditCard = this.creditCardService.create();
        creditCard.setActor(actor);

        res = this.createEditModelAndView(creditCard);

        return res;
    }

    //Edit

    @RequestMapping("/edit")
    public ModelAndView edit() {

        CreditCard creditCard;
        ModelAndView res;

        final Actor actor = this.actorService.findByPrincipal();

        creditCard = this.creditCardService.findOneByActorId(actor.getId());

        if (creditCard == null)

            res = new ModelAndView("redirect:/creditCard/create.do");

        else {

            res = new ModelAndView("creditCard/edit");
            res.addObject("creditCard", creditCard);

        }

        return res;
    }

    //Save

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {

        ModelAndView res;

        if (binding.hasErrors())
            res = this.createEditModelAndView(creditCard);
        else
            try {

                this.creditCardService.save(creditCard);
                res = new ModelAndView("redirect:/welcome/index.do");

            } catch (final Throwable oops) {
                res = this.createEditModelAndView(creditCard, "creditCard.commit.error");
            }
        return res;
    }

    // Delete

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(@Valid final CreditCard creditCard, final BindingResult binding) {

        ModelAndView res;

        try {
            this.creditCardService.delete(creditCard);
            res = new ModelAndView("redirect:create.do");
        } catch (final Throwable oops) {
            res = this.createEditModelAndView(creditCard, "creditCard.commit.error");
        }
        return res;
    }

    // Ancillary methods

    protected ModelAndView createEditModelAndView(final CreditCard creditCard) {

        ModelAndView res;

        res = this.createEditModelAndView(creditCard, null);
        return res;
    }

    protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String message) {

        ModelAndView res;

        res = new ModelAndView("creditCard/edit");

        res.addObject("creditCard", creditCard);
        res.addObject("message", message);

        return res;
    }
}
