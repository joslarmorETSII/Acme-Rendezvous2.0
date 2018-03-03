package controllers;

import controllers.AbstractController;
import domain.Administrator;
import domain.Rendezvous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;

    // Constructor -----------------------------------------
    public AdministratorController() {
        super();
    }

    // Edition  ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {

        ModelAndView result;

        final Administrator administrator = this.administratorService.findByPrincipal();
        result = this.createEditModelAndView(administrator);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors())
            result = this.createEditModelAndView(administrator);
        else
            try {
                this.administratorService.save(administrator);
                result = new ModelAndView("redirect:/welcome/index.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(administrator, "administrator.commit.error");
            }
        return result;
    }

    // Dashboard -------------------------------------------------------------
    @RequestMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView result;

        result = new ModelAndView("administrator/dashboard");

        // level c - queries

        Object[] avgDevRendezvousesPerUser;
        Double RatioCreatorsVsNoCreators;
        Object[] avgDevRendezvousPerUser;
        Object[] avgDevRendezvousParticipatePerUser;
        Collection<Rendezvous> top10RendezvousParticipated;

        // level b - queries

        Object[] avgDevAnnouncementsPerRendezvous;
        Collection<Rendezvous> rendezvousPlus75AvgAnnouncements;
        Collection<Rendezvous> rendezvousPlus10AvgAssociated;

        // level A - queries
        Object[] questionsPerRendezvous;
        Object[] avgDevAnswersQuestionsPerRendezvous;
        Object[] avgDevRepliesPerComment;


        // Initializating variables

        avgDevRendezvousesPerUser = this.rendezvousService.avgDevRendezvousesPerUser();
        RatioCreatorsVsNoCreators = this.userService.RatioCreatorsVsNoCreators();
        avgDevRendezvousPerUser = this.userService.avgDevRendezvousPerUser();
        avgDevRendezvousParticipatePerUser = this.rendezvousService.avgDevRendezvousParticipatePerUser();
        top10RendezvousParticipated = this.rendezvousService.top10RendezvousParticipated();
        rendezvousPlus75AvgAnnouncements = this.rendezvousService.rendezvousPlus75AvgAnnouncements();
        avgDevAnnouncementsPerRendezvous = this.announcementService.avgDevAnnouncementsPerRendezvous();
        avgDevRepliesPerComment = this.commentService.avgDevRepliesPerComment();
        rendezvousPlus10AvgAssociated = this.rendezvousService.rendezvousPlus10AvgAssociated();
        questionsPerRendezvous = this.questionService.questionsPerRendezvous();
        avgDevAnswersQuestionsPerRendezvous = this.answerService.avgDevAnswersQuestionsPerRendezvous();
        // Adding to result

        result.addObject("avgDevAnnouncementsPerRendezvous", avgDevAnnouncementsPerRendezvous);
        result.addObject("top10RendezvousParticipated", top10RendezvousParticipated);
        result.addObject("avgDevRendezvousParticipatePerUser", avgDevRendezvousParticipatePerUser);
        result.addObject("avgDevRendezvousPerUser", avgDevRendezvousPerUser);
        result.addObject("avgDevRepliesPerComment", avgDevRepliesPerComment);
        result.addObject("RatioCreatorsVsNoCreators", RatioCreatorsVsNoCreators);
        result.addObject("avgDevRendezvousesPerUser", avgDevRendezvousesPerUser);
        result.addObject("rendezvousPlus10AvgAssociated", rendezvousPlus10AvgAssociated);
        result.addObject("rendezvousPlus75AvgAnnouncements", rendezvousPlus75AvgAnnouncements);
        result.addObject("questionsPerRendezvous", questionsPerRendezvous);
        result.addObject("avgDevAnswersQuestionsPerRendezvous", avgDevAnswersQuestionsPerRendezvous);

        return result;
    }

    // Ancillary methods ------------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Administrator administrator) {

        ModelAndView result;

        result = this.createEditModelAndView(administrator, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {

        Assert.notNull(administrator);

        ModelAndView result;

        result = new ModelAndView("administrator/edit");
        result.addObject("administrator", administrator);
        result.addObject("message", message);

        return result;
    }

}
