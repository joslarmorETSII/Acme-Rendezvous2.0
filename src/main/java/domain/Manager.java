package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

    // Constructors -----------------------------------------------------------

    public Manager() {
        super();
    }


    // Attributes -------------------------------------------------------------

    private  String vat;


    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^(\\d{3})\\-([A-Z]{3})$")
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getVat() {
        return vat;
    }


    public void setVat(String vat) {
        this.vat = vat;
    }
    // Relationships ----------------------------------------------------------

    private Collection<Service> services;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "manager")
    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }




}
