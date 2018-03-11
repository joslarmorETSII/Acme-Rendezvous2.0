package controllers.Manager;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ServiseService;

import javax.validation.Valid;

@Controller
@RequestMapping("/servise/manager")
public class ServiseManagerController {

    //Services -------------------------------------------------------

    @Autowired
    ActorService actorService;

    @Autowired
    ServiseService serviseService;

    // Create -------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        final Servise servise = this.serviseService.create();

        final ModelAndView res = this.createEditModelAndView(servise);

        return res;
    }


    // Listing -------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;

        Manager manager = (Manager) this.actorService.findByPrincipal();

        result = new ModelAndView("servise/list");
        result.addObject("servises", manager.getServises());
        result.addObject("requestURI","servise/manager/list.do");
        result.addObject("manager",manager);

        return result;
    }

    // edition ---------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int serviseId){
        ModelAndView result;
        Servise servise;

        servise = serviseService.findOneToEdit(serviseId);
        result = createEditModelAndView(servise);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "save")
    public ModelAndView edit(@Valid Servise servise, BindingResult bindingResult){
        ModelAndView result;
        if(bindingResult.hasErrors())
            result = createEditModelAndView(servise);
        else{
            try{
                serviseService.save(servise);
                result = new ModelAndView("redirect: list.do");

            }catch (Throwable oops){
                result = createEditModelAndView(servise,"servise.save.error");
            }
        }
        return result;
    }
    @RequestMapping(value = "/editDelete", method = RequestMethod.GET)
    public ModelAndView editDelete(@RequestParam  int serviseId) {
        final ModelAndView result;
        Servise servise;
        servise = this.serviseService.findOneToEdit(serviseId);
        this.serviseService.delete(servise);
        result = new ModelAndView("redirect: list.do");
        result.addObject("manager",(Manager)actorService.findByPrincipal());
        return result;
    }

    // Ancillary methods

    private ModelAndView createEditModelAndView(final Servise servise) {

        return this.createEditModelAndView(servise, null);
    }

    private ModelAndView createEditModelAndView(final Servise servise, final String message) {

        final ModelAndView res = new ModelAndView("servise/edit");
        Manager manager;
        manager = (Manager)actorService.findByPrincipal();
        res.addObject("servise", servise);
        res.addObject("message", message);
        res.addObject("actionUri", "servise/manager/edit.do");
        res.addObject("cancelUri", "servise/manager/list.do");

        return res;

    }
}
