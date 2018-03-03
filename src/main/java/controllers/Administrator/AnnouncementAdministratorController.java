package controllers.Administrator;

import controllers.AbstractController;
import domain.Announcement;
import domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AnnouncementService;

import services.UserService;

import javax.validation.Valid;
import java.util.Collection;


@Controller
@RequestMapping("/announcement/administrator")
public class AnnouncementAdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserService userService;


    // Constructor --------------------------------------------

    public AnnouncementAdministratorController() {
        super();
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Announcement> announcements;

        announcements = this.announcementService.findAll();

        result = new ModelAndView("announcement/list");
        result.addObject("announcements", announcements);
        result.addObject("requestURI", "announcement/administrator/list.do");
        return result;

    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int announcementId) {
        final ModelAndView result;
        Announcement announcement;
        announcement = this.announcementService.findOne(announcementId);
        this.announcementService.delete(announcement);
        Assert.notNull(announcement);
        result = new ModelAndView("redirect: list.do");
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView save(@Valid  Announcement announcement, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(announcement);
        else
            try {
                this.announcementService.delete(announcement);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(announcement, "announcement.commit.error");
            }
        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Announcement announcement) {
        ModelAndView result;

        result = this.createEditModelAndView(announcement, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Announcement announcement, final String messageCode) {
        ModelAndView result;


        result = new ModelAndView("announcement/edit");
        result.addObject("announcement", announcement);
        result.addObject("actionURI", "announcement/administrator/edit.do");
        result.addObject("message", messageCode);
        return result;
    }
}


