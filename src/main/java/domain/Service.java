package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Service extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public Service() {
        super();
    }

    // Attributes -------------------------------------------------------------

    // Relationships ----------------------------------------------------------

    private Manager manager;

    @ManyToOne(optional = false)
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
