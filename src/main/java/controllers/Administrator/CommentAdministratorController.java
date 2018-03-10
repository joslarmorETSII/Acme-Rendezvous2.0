package controllers.Administrator;

import controllers.AbstractController;
import domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CommentService;
import services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    // Constructor --------------------------------------------

    public CommentAdministratorController() {
        super();
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Comment> comments;

        comments = this.commentService.findAll();

        result = new ModelAndView("comment/list");
        result.addObject("comments", comments);
        result.addObject("requestURI", "comment/administrator/list.do");
        return result;

    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int commentId) {
        final ModelAndView result;
        Comment comment;
        comment = this.commentService.findOne(commentId);
        this.commentService.delete(comment);
        result = new ModelAndView("redirect: list.do");
        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Comment comment) {
        ModelAndView result;

        result = this.createEditModelAndView(comment, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Comment comment, final String messageCode) {
        ModelAndView result;


        result = new ModelAndView("comment/edit");
        result.addObject("comment", comment);
        result.addObject("actionURI", "comment/administrator/edit.do");
        result.addObject("message", messageCode);
        return result;
    }
}


