package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {

    private String	holder;
    private String	brand;
    private String	number;
    private Integer		expirationMonth;
    private Integer		expirationYear;
    private Integer		cvv;


    public CreditCard() {
        super();
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getHolder() {
        return this.holder;
    }

    public void setHolder(final String holder) {
        this.holder = holder;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getBrand() {
        return this.brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    @NotBlank
    @CreditCardNumber
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getNumber() {
        return this.number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    @Range(min = 1, max = 12)
    @NotNull
    public Integer getExpirationMonth() {
        return this.expirationMonth;
    }

    public void setExpirationMonth(final Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Min(2018)
    @NotNull
    public Integer getExpirationYear() {
        return this.expirationYear;
    }

    public void setExpirationYear(final Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    @NotNull
    @Range(min = 100, max = 999)
    public Integer getCvv() {
        return this.cvv;
    }

    public void setCvv(final Integer cvv) {
        this.cvv = cvv;
    }


    //// Relationships ----------------------------------------------------------

   /* private Actor	actor;


    @Valid
    @NotNull
    @OneToOne(optional = false)
    public Actor getActor() {
        return this.actor;
    }

    public void setActor(final Actor actor) {
        this.actor = actor;
    }*/

}

