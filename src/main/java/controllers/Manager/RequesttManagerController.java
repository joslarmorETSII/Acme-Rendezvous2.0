package controllers.Manager;

import controllers.AbstractController;
import domain.Manager;
import domain.Requestt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ManagerService;
import services.RequesttService;

import java.util.Collection;

@Controller
@RequestMapping("/requestt/manager")
public class RequesttManagerController extends AbstractController{

    //Services -------------------------------------------------------

    @Autowired
    private RequesttService requesttService;

    @Autowired
    private ManagerService managerService;


    // Listing -------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Requestt> requestts;

        Manager manager = managerService.findByPrincipal();
        Assert.notNull(manager);
        requestts = requesttService.requestsByManagerId(manager.getId());

        result = new ModelAndView("request/list");
        result.addObject("requestts",requestts );
        result.addObject("requestURI","requestt/manager/list.do");

        return result;
    }
}
