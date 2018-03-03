package controllers;

import domain.Announcement;
import domain.Rendezvous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AnnouncementService;
import services.RendezvousService;
import services.UserService;

import java.util.Collection;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;

    // Constructor -----------------------------------------
    public AnnouncementController() {
        super();
    }

    // List -----------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam final int rendezvousId) {

        final Collection<Announcement> announcements;
        Rendezvous rendezvous;

        rendezvous = rendezvousService.findOne(rendezvousId);
        announcements = rendezvous.getAnnouncements();

        final ModelAndView res = new ModelAndView("announcement/list");
        res.addObject("announcements", announcements);
        res.addObject("requestURI", "announcement/list.do");

        return res;
    }

    // List -----------------------------------------------
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView list() {

        final Collection<Announcement> announcements;

        announcements = this.announcementService.findAll();

        final ModelAndView res = new ModelAndView("announcement/list");
        res.addObject("announcements", announcements);
        res.addObject("requestURI", "announcement/listAll.do");

        return res;
    }
}
