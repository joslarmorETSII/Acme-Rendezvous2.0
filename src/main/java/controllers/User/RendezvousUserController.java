package controllers.User;

import controllers.AbstractController;
import domain.Rendezvous;
import domain.User;
import forms.AssociatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.RendezvousService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;


    // Constructor --------------------------------------------

    public RendezvousUserController() {
        super();
    }

    // Listing -------------------------------------------------------

   @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(required= false, defaultValue = "0") Integer userId ) {
        ModelAndView result;
        User user;
        Collection<Rendezvous> rendezvouses;

       SimpleDateFormat formatterEs;
       SimpleDateFormat formatterEn;
       String momentEs;
       String momentEn;

       formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
       momentEs = formatterEs.format(new Date());
       formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
       momentEn = formatterEn.format(new Date());

        if(userId!=0){
             user=userService.findOne(userId);
            rendezvouses= rendezvousService.userParticipate(user.getId());
        }else {
            user = userService.findByPrincipal();
            rendezvouses = rendezvousService.userParticipate(user.getId());
        }
        result = new ModelAndView("rendezvous/list");
        result.addObject("rendezvous", rendezvouses);
        result.addObject("user",user);
        result.addObject("requestUri","rendezvous/user/list.do");
        result.addObject("cancelUri","cancel");
       result.addObject("now",new Date());
       result.addObject("momentEs", momentEs);
       result.addObject("momentEn", momentEn);

       return result;

    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Rendezvous rendezvous;

        rendezvous = this.rendezvousService.create();
        result = this.createEditModelAndView(rendezvous);

        return result;
    }




    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int rendezvousId) {
        ModelAndView result;
        Rendezvous rendezvous;

        rendezvous = this.rendezvousService.findOne(rendezvousId);
        result = new ModelAndView("rendezvous/display");
        result.addObject("rendezvous", rendezvous);
        result.addObject("cancelURI", "rendezvous/user/list.do");
        result.addObject("associated",rendezvous.getAssociated());

        return result;
    }


   //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int rendezvousId) {
        final ModelAndView result;
        Rendezvous rendezvous;
        rendezvous = this.rendezvousService.findOneToEdit(rendezvousId);
        Assert.notNull(rendezvous);
        result = this.createEditModelAndView(rendezvous);
        return result;
    }

    @RequestMapping(value = "/associate", method = RequestMethod.GET)
    public ModelAndView associate(@RequestParam  int rendezvousId,@RequestParam(required= false) String message) {
        ModelAndView result;
        User user = userService.findByPrincipal();
        Rendezvous rendezvous =rendezvousService.findOne(rendezvousId);
        Assert.isTrue(rendezvous.getCreator().equals(user));
        AssociatForm associatForm = new AssociatForm();
        associatForm.setFormid(rendezvousId);
        Collection<Rendezvous> notAssociated = user.getRendezvouses();
        notAssociated.removeAll(rendezvous.getAssociated());
        result = new ModelAndView("rendezvous/associate");
        result.addObject("associatForm", associatForm);
        result.addObject("allRendezvous", notAssociated);
        result.addObject("emptyCollection",notAssociated.size()==0);
        result.addObject("message",message);
        return result;
    }
    @RequestMapping(value = "/associate", method = RequestMethod.POST, params = "save")
    public ModelAndView associate( AssociatForm associatForm, HttpServletRequest request) {
        ModelAndView result;
        Collection<Rendezvous> rendezvousToAssociate = new HashSet<>();
        Rendezvous rendezvous = rendezvousService.findOne(associatForm.getFormid());

        String[] rendezvousesToAssociateId = request.getParameterValues("rendezvous");
        if(request.getParameterValues("rendezvous")==null){
            return associate(rendezvous.getId(),"rendezvous.select.error");
        }
        for (int i = 0; i <= rendezvousesToAssociateId.length - 1; i++) {
            Rendezvous aux = rendezvousService.findOne(new Integer(rendezvousesToAssociateId[i]));
            rendezvousToAssociate.add(aux);
        }

        rendezvous.getAssociated().addAll(rendezvousToAssociate);
        try {
            rendezvousService.associate(rendezvous,rendezvousToAssociate);
        }catch (Throwable oops){
            result = associate(rendezvous.getId(),oops.getCause().getMessage());
        }
        result = new ModelAndView("redirect:display.do?rendezvousId="+rendezvous.getId());

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid  Rendezvous rendezvous, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(rendezvous);
        else
            try {
                this.rendezvousService.save(rendezvous);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
            }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "delete")
    public ModelAndView edit(Rendezvous rendezvous){
        ModelAndView result;

        try{
            rendezvousService.delete(rendezvous);
            result = new ModelAndView("redirect:list.do");
        }catch (Throwable oops){
            result = createEditModelAndView(rendezvous,"rendezvous.delete.error");
        }

        return result;
    }


    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Rendezvous rendezvous) {
        ModelAndView result;

        result = this.createEditModelAndView(rendezvous, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Rendezvous rendezvous, final String messageCode) {
        ModelAndView result;
        result = new ModelAndView("rendezvous/edit");
        result.addObject("rendezvous", rendezvous);
        result.addObject("message", messageCode);

        return result;
    }
}


