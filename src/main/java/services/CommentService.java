package services;

import domain.Administrator;
import domain.Comment;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.CommentRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class CommentService {

    // Managed Repository -----------------------------------------------------

    @Autowired
    private CommentRepository commentRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private AnnouncementService announcementService;

    // Constructors -----------------------------------------------------------

    public CommentService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Comment create(){

        Comment result;
        User user;
        Collection<Comment> childrenComments;

        user = this.userService.findByPrincipal();
        childrenComments = new ArrayList<Comment>();

        result = new Comment();

        result.setUser(user);
        result.setMoment(new Date(System.currentTimeMillis() - 1000));
        result.setChildrenComments(childrenComments);
        user.getComments().add(result);

        return result;

    }

    public Comment save(Comment comment){

        Assert.notNull(comment);
        Assert.isTrue(checkByPrincipal(comment) || checkByPrincipalAdmin(comment));
        Comment res = comment;
        Comment parentComment = comment.getParentComment();

        res.setMoment(new Date(System.currentTimeMillis()-1000));

        if(comment.getId() == 0){
            this.commentRepository.save(comment);
        }else{
            if(parentComment != null) {
                parentComment.getChildrenComments().add(comment);
                this.commentRepository.save(parentComment);
                this.commentRepository.save(res);
            }else{
                this.commentRepository.save(comment);
            }
        }

        return res;
    }

    public void delete(Comment comment){
        Assert.notNull(comment);
        Assert.isTrue(checkByPrincipalAdmin(comment));

        if(comment.getChildrenComments().size() == 0) {
            this.commentRepository.delete(comment);

        }else if(comment.getChildrenComments().size() >= 1) {
            for (Comment c : comment.getChildrenComments()) {
                c.setParentComment(null);
                commentRepository.save(c);
            }
            comment.setChildrenComments(new ArrayList<Comment>());
            commentRepository.delete(comment);
        }
    }


    public Comment findOne(int commentId){
        Assert.notNull(commentId);
        return this.commentRepository.findOne(commentId);
    }

    public Comment findOneToEdit(int commentId){
        Assert.notNull(commentId);
        Comment comment = this.commentRepository.findOne(commentId);
        Assert.isTrue(checkByPrincipal(comment));
        return this.commentRepository.findOne(commentId);
    }

    public Collection<Comment> findAll(){
        return this.commentRepository.findAll();
    }

    public  void deleteComments(Rendezvous rendezvous){
        this.commentRepository.delete(rendezvous.getComments());
    }
    // Other business methods -------------------------------------------------

    public boolean checkByPrincipal(final Comment comment) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();
        Assert.notNull(principal);

        if (comment.getUser().equals(principal))
            res = true;

        return res;
    }

    public boolean checkByPrincipalAdmin(Comment comment){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        Collection<Authority> authorities= administrator.getUserAccount().getAuthorities();
        String authority= authorities.toArray()[0].toString();
        res= authority.equals("ADMINISTRATOR");
        return res;

    }

    public Object[] avgDevRepliesPerComment() {
        Object[] result;

        result = this.commentRepository.avgDevRepliesPerComment();
        this.announcementService.formatDecimal(result);
        return result;
    }
}
