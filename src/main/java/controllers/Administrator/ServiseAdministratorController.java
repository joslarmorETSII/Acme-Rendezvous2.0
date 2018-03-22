package controllers.Administrator;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CategoryService;
import services.ServiseService;

import java.util.Collection;

@Controller
@RequestMapping("/servise/administrator")
public class ServiseAdministratorController extends AbstractController{


    // Services
    @Autowired
    ServiseService serviseService;

    @Autowired
    CategoryService categoryService;

    // Listing -------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;

        Collection<Servise> servises = this.serviseService.findServiceAppropriate();

        result = new ModelAndView("servise/list");
        result.addObject("servises", servises);
        result.addObject("requestURI","servise/administrator/list.do");

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int serviseId) {
        final ModelAndView result;
        this.serviseService.inappropriateDontRequest(serviseId);
        result = new ModelAndView("redirect: list.do");
        return result;
    }

    @RequestMapping(value = "/list-byCategoryId", method = RequestMethod.GET)
    public ModelAndView listByCategoryId(@RequestParam final Integer categoryId) {
        ModelAndView result;
        Collection<Servise> trips = null;
        Category category = null;

        category = this.categoryService.findOne(categoryId);
        Assert.notNull(category);
        trips = category.getServises();

        result = new ModelAndView("servise/list");
        result.addObject("servises", trips);
        result.addObject("requestURI", "servise/list.do");
        return result;
    }
}
