package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Servise extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public Servise() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String name;
    private String description;
    private String picture;
    private boolean assigned;
    private boolean inappropriate;


    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @URL
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    @NotNull
    public boolean getInappropriate() {
        return inappropriate;
    }

    public void setInappropriate(boolean inappropriate) {
        this.inappropriate = inappropriate;
    }

    // Relationships ----------------------------------------------------------

    private Manager manager;
    private Category category;
    private Collection<Rendezvous> rendezvouses;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }


    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Valid
    @NotNull
    @ManyToMany
    public Collection<Rendezvous> getRendezvouses() {
        return rendezvouses;
    }

    public void setRendezvouses(Collection<Rendezvous> rendezvouses) {
        this.rendezvouses = rendezvouses;
    }
}
