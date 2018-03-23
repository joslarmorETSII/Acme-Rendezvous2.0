package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.AnnouncementRepository;
import security.Authority;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class AnnouncementService {

    // Managed Repository -----------------------------------------------------

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AdministratorService adminService;


    // Simple CRUD methods ----------------------------------------------------

    public Announcement create() {

        Announcement announcement;

        announcement = new Announcement();
        announcement.setMoment(new Date(System.currentTimeMillis() - 1000));

        return announcement;
    }


    public Announcement save(final Announcement announcement) {
        Assert.notNull(announcement);

        checkByPrincipal(announcement);


        final Announcement saved = this.announcementRepository.save(announcement);
        return saved;
    }

    public Announcement findOne(final int announcementId) {
        Announcement announcement = null;

        announcement = this.announcementRepository.findOne(announcementId);

        return announcement;
    }

    public Collection<Announcement> findAll() {
        return this.announcementRepository.findAll();
    }

    public void delete(final Announcement announcement) {
        checkByPrincipalAdministrator();
        this.announcementRepository.delete(announcement);
    }
    public void deleteAnnouncements(Rendezvous rendezvous){
        this.announcementRepository.delete(rendezvous.getAnnouncements());
    }

    // Other business methods -------------------------------------------------

    public Announcement findOneToEdit(final int announcementId) {
        Announcement announcement = null;
        announcement = this.announcementRepository.findOne(announcementId);

        this.checkByPrincipal(announcement);

        return announcement;
    }

    private void checkByPrincipal(final Announcement announcement) {

        User user = null;
        user = this.userService.findByPrincipal();
        Assert.isTrue(announcement.getRendezvous().getCreator().equals(user));

    }

    private void checkByPrincipalAdministrator() {

        Administrator administrator = null;
        administrator = this.adminService.findByPrincipal();
        Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
        String authority = authorities.toArray()[0].toString();
        Assert.isTrue(authority.equals("ADMINISTRATOR"));

    }

    public Collection<Announcement> announcementFindByParticipated(int userId){
        Collection<Announcement> result;
        Assert.isTrue(actorService.isUser());
        result = this.announcementRepository.announcementFindByParticipated(userId);
        return result;
    }

    public Object[] avgDevAnnouncementsPerRendezvous() {
        Object[] result;

        result = this.announcementRepository.avgDevAnnouncementsPerRendezvous();

        this.formatDecimal(result);

        return result;
    }

    public Object[] formatDecimal(Object[] res) {
        Object[] result;
        DecimalFormat df = new DecimalFormat("#0.00");
        if (res[0] == null || res[1]==null) {
            return null;
        }else {
            res[0] = df.format(res[0]);
            res[1] = df.format(res[1]);
        }

        result = res;
        return result;
    }

    public void flush() {
        announcementRepository.flush();
    }
}

