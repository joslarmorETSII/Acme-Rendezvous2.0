package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public Category() {
        super();
    }
}

    // Attributes -------------------------------------------------------------

    // Relationships ----------------------------------------------------------