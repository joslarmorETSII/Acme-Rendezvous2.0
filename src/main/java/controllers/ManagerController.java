package controllers;


import domain.Manager;
import domain.User;
import forms.ManagerForm;
import forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ManagerService;
import services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/manage")
public class ManagerController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private ManagerService managerService;

    // Constructor -----------------------------------------
    public ManagerController() {
        super();
    }

    // Edition Profile ----------------------------------------------------------------

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView editProfile() {

        ModelAndView result;

        final Manager manager = this.managerService.findByPrincipal();
        result = this.createEditModelAndView2(manager);

        return result;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors())
            result = this.createEditModelAndView2(manager);
        else
            try {
                this.managerService.save(manager);
                result = new ModelAndView("redirect:/welcome/index.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView2(manager, "manager.commit.error");
            }
        return result;
    }

    //Edition --------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView edit() {

        ModelAndView result;
        result = new ModelAndView("manager/edit");

        result.addObject("managerForm", new ManagerForm());

        return result;
    }

    // Save ------------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final ManagerForm managerForm, final BindingResult binding) {
        ModelAndView result;
        Manager manager;

        try {
            manager = this.managerService.reconstruct(managerForm, binding);

            if (binding.hasErrors())
                result = this.createEditModelAndView(managerForm, "manager.save.error");
            else {
                result = new ModelAndView("redirect:/welcome/index.do");
                manager = this.managerService.save(manager);

            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(managerForm, "manager.save.error");
        }

        return result;
    }



//    // Creating  --------------------------------------------------
//
//    @RequestMapping(value = "/create", method = RequestMethod.GET)
//    public ModelAndView create() {
//
//        ModelAndView result;
//
//        final User user = this.userService.create();
//        result = this.createEditModelAndView(user);
//        return result;
//    }


    // Listing -------------------------------------------------------



    // Ancillary methods ------------------------------------------------------------

    private ModelAndView createEditModelAndView(final ManagerForm managerForm, final String message) {

        final ModelAndView result = new ModelAndView("manager/edit");

        result.addObject("managerForm", managerForm);
        result.addObject("message", message);
        return result;
    }

    private ModelAndView createEditModelAndView2(final Manager manager) {

        return this.createEditModelAndView2(manager, null);
    }

    private ModelAndView createEditModelAndView2(final Manager manager, final String message) {

        final ModelAndView result = new ModelAndView("manager/editProfile");

        result.addObject("manager", manager);
        result.addObject("message", message);

        return result;
    }

}
