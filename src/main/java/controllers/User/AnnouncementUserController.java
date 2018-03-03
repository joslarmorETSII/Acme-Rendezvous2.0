package controllers.User;

import controllers.AbstractController;
import domain.Announcement;
import domain.User;
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
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserService userService;

    // Constructor -----------------------------------------
    public AnnouncementUserController() {
        super();
    }


    // Create --------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        final Announcement announcement = this.announcementService.create();

        final ModelAndView res = this.createEditModelAndView(announcement);

        return res;
    }

    // Edit --------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required = true) final Integer announcementId) {

        final Announcement announcement = this.announcementService.findOneToEdit(announcementId);
        Assert.notNull(announcement);

        final ModelAndView res = this.createEditModelAndView(announcement);

        return res;
    }

    //Save --------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final Announcement announcement, final BindingResult binding) {

        ModelAndView res = null;
        User user = userService.findByPrincipal();
        Assert.isTrue(user.equals(announcement.getRendezvous().getCreator()));

        if (binding.hasErrors())
            res = this.createEditModelAndView(announcement);
        else
            try {
                this.announcementService.save(announcement);
                res = new ModelAndView("redirect:list.do");
            } catch (final Throwable t) {
                res = this.createEditModelAndView(announcement, "announcement.commit.error");
            }
        return res;
    }


    // Listing --------------------------------------------

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        final Collection<Announcement> announcements;
        User user;

        user = userService.findByPrincipal();
        announcements = this.announcementService.announcementFindByParticipated(user.getId());

        final ModelAndView res = new ModelAndView("announcement/list");
        res.addObject("announcements", announcements);
        res.addObject("user", user);
        res.addObject("requestURI", "announcement/user/list.do");

        return res;
    }

    @RequestMapping(value = "/listAllUser", method = RequestMethod.GET)
    public ModelAndView listAllUser() {

        final Collection<Announcement> announcements;
        User user = this.userService.findByPrincipal();
        announcements = this.announcementService.findAll();

        final ModelAndView res = new ModelAndView("announcement/list");
        res.addObject("announcements", announcements);
        res.addObject("user", user);
        res.addObject("requestURI", "announcement/user/listAllUser.do");

        return res;
    }
    // Ancillary methods

    private ModelAndView createEditModelAndView(final Announcement announcement) {

        // return this.createEditModelAndView(announcement, null);
        return this.createEditModelAndView(announcement, null);
    }

    private ModelAndView createEditModelAndView(final Announcement announcement, final String message) {

        final ModelAndView res = new ModelAndView("announcement/edit");
        User user;
        user = userService.findByPrincipal();
        res.addObject("announcement", announcement);
        res.addObject("message", message);
        res.addObject("myRendezvouses", user.getRendezvouses());
        res.addObject("actionUri", "announcement/user/edit.do");
        res.addObject("cancelUri","announcement/user/list.do");

        return res;

    }
}
