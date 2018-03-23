package controllers.Manager;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CategoryService;
import services.ManagerService;
import services.ServiseService;

import javax.validation.Valid;

@Controller
@RequestMapping("/servise/manager")
public class ServiseManagerController extends AbstractController {

    //Services -------------------------------------------------------

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ServiseService serviseService;

    @Autowired
    private CategoryService categoryService;

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

        Manager manager = managerService.findByPrincipal();
        Assert.notNull(manager);

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
        Assert.notNull(serviseId);
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
                result = createEditModelAndView(serviseService.create(),"servise.save.error");
            }
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(Servise servise) {

        ModelAndView res;

        try {
            this.serviseService.delete(servise);
            res = new ModelAndView("redirect:/welcome/index.do");
        } catch (final Throwable oops) {
            res = this.createEditModelAndView(servise, "servise.commit.error");
        }
        return res;
    }


    // Ancillary methods

    private ModelAndView createEditModelAndView(final Servise servise) {
        ModelAndView result;

        result =this.createEditModelAndView(servise, null);

        return result;
    }

    private ModelAndView createEditModelAndView(Servise servise, String messageCode) {
        ModelAndView res;

        res = new ModelAndView("servise/edit");
        res.addObject("servise", servise);
        res.addObject("actionUri", "servise/manager/edit.do");
        res.addObject("cancelUri", "servise/manager/list.do");
        res.addObject("categories",categoryService.findAll());
        res.addObject("message", messageCode);

        return res;

    }
}
