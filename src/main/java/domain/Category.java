package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public Category() {
        super();
    }


    // Attributes -------------------------------------------------------------

    private String	name;
    private String  description;


    @NotBlank
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Relationships ----------------------------------------------------------

    private Category				parentCategory;
    private Collection<Category>	childrenCategories;
    private Collection<Servise>     servises;

    @Valid
    @ManyToOne(optional = true)
    public Category getParentCategory() {
        return this.parentCategory;
    }

    public void setParentCategory(final Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "parentCategory")
    public Collection<Category> getChildrenCategories() {
        return this.childrenCategories;
    }

    public void setChildrenCategories(final Collection<Category> childrenCategories) {
        this.childrenCategories = childrenCategories;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "category")
    public Collection<Servise> getServises() {
        return servises;
    }

    public void setServises(Collection<Servise> servises) {
        this.servises = servises;
    }
}
