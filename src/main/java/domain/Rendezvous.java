package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Rendezvous extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Rendezvous() {
        super();
    }


    // Attributes -------------------------------------------------------------
    private String name;
    private String description;
    private Date moment;
    private String picture;
    private GPSCoordinates	location;
    private  Boolean finalMode;
    private Boolean deleted;
    private  Boolean forAdults;

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @URL
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Valid
    public GPSCoordinates getLocation() {
        return location;
    }

    public void setLocation(GPSCoordinates location) {
        this.location = location;
    }

    public Boolean getFinalMode() {
        return finalMode;
    }

    public void setFinalMode(Boolean finalMode) {
        this.finalMode = finalMode;
    }


    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public Boolean getForAdults() {
        return forAdults;
    }

    public void setForAdults(Boolean forAdults) {
        this.forAdults = forAdults;
    }

    // Relationships ----------------------------------------------------------
    private User creator;
    private Collection<Rendezvous> associated;
    private Collection<Announcement> announcements;
    private Collection<Participate> participated;
    private Collection<Comment> comments;
    private Collection<Question> questions;
    private Collection<Rendezvous> parentRendezvous;
    private Collection<Servise> servises;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Valid
    @NotNull
    @ManyToMany(mappedBy = "parentRendezvous")
    public Collection<Rendezvous> getAssociated() {
        return associated;
    }

    public void setAssociated(Collection<Rendezvous> associated) {
        this.associated = associated;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "rendezvous")
    public Collection<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Collection<Announcement> announcements) {
        this.announcements = announcements;
    }


    @Valid
    @NotNull
    @OneToMany(mappedBy = "rendezvous")
    public Collection<Participate> getParticipated() {
        return participated;
    }

    public void setParticipated(Collection<Participate> Participated) {
        this.participated = Participated;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "rendezvous")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "rendezvous")
    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    @Valid
    @NotNull
    @ManyToMany
    public Collection<Rendezvous> getParentRendezvous() {
        return parentRendezvous;
    }

    public void setParentRendezvous(Collection<Rendezvous> parentRendezvous) {
        this.parentRendezvous = parentRendezvous;
    }

    @Valid
    @NotNull
    @ManyToMany(mappedBy = "rendezvouses")
    public Collection<Servise> getServises() {
        return servises;
    }

    public void setServises(Collection<Servise> servises) {
        this.servises = servises;
    }
}
