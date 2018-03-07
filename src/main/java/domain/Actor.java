package domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import security.UserAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {


    // Constructors -----------------------------------------------------------

    public Actor() {
        super();
    }


    // Attributes -------------------------------------------------------------

    private String	name;
    private String	surname;
    private String	phone;
    private String	email;
    private String	postalAddresses ;

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
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Pattern(regexp = "^\\+([3][4])( )(\\d{9})|()$")
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Email
    @NotNull
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPostalAddresses() {
        return postalAddresses;
    }

    public void setPostalAddresses(String postalAddresses) {
        this.postalAddresses = postalAddresses;
    }

    // Relationships ----------------------------------------------------------

    private UserAccount userAccount;
    private CreditCard creditCard;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @OneToOne(optional = true)
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
