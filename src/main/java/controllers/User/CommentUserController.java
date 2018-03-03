package controllers.User;

import controllers.AbstractController;
import domain.Comment;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CommentService;
import services.RendezvousService;
import services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RendezvousService rendezvousService;

    // Constructor --------------------------------------------

    public CommentUserController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int rendezvousId) {
        ModelAndView result;
        Comment comment;
        Rendezvous rendezvous = this.rendezvousService.findParticipate(rendezvousId);

        comment = this.commentService.create();
        comment.setRendezvous(rendezvous);
        result = this.createEditModelAndView(comment);

        return result;
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public ModelAndView response(@RequestParam int commentId) {
        ModelAndView result;
        Comment parentComment = this.commentService.findOne(commentId);

        Comment comment = this.commentService.create();
        comment.setRendezvous(parentComment.getRendezvous());
        comment.setParentComment(parentComment);
        result = this.createEditModelAndView(comment);

        return result;
    }

    // Display ----------------------------------------------------------------

   /* @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int noteId) {
        ModelAndView result;
        Note note;

        note = this.noteService.findOne(noteId);
        result = new ModelAndView("note/display");
        result.addObject("note", note);
        result.addObject("cancelURI", "note/auditor/list.do");

        return result;
    }

*/

    // Listing -------------------------------------------------------

   @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Comment> comments;

        User user = userService.findByPrincipal();
        comments = user.getComments();
        result = new ModelAndView("comment/list");
        result.addObject("comments", comments);
       result.addObject("requestURI", "comment/user/list.do");
       result.addObject("user",user);
        return result;

    }


    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int commentId) {
        final ModelAndView result;
        Comment comment;
        comment = this.commentService.findOneToEdit(commentId);
        Assert.notNull(comment);
        result = this.createEditModelAndView(comment);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid  Comment comment, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(comment);
        else
            try {
                this.commentService.save(comment);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(comment, "comment.commit.error");
            }
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
        result.addObject("actionURI", "comment/user/edit.do");
        result.addObject("message", messageCode);
        return result;
    }
}


