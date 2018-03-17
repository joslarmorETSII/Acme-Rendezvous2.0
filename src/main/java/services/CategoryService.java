
package services;

import java.util.ArrayList;
import java.util.Collection;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Category create() {

		Assert.isTrue(actorService.isAdministrator());

		Collection<Servise> services;
		Collection<Category> children;
		Category res;

		children = new ArrayList<Category>();
		services = new ArrayList<Servise>();

		res = new Category();

		res.setServises(services);
		res.setChildrenCategories(children);

		return res;
	}

	public Category findOne(final int categoryId) {

		Assert.notNull(categoryId);
		Category res = this.categoryRepository.findOne(categoryId);
		return res;

	}

	public Collection<Category> findAll() {

		Collection<Category> result;

		result = this.categoryRepository.findAll();
		return result;

	}
	public Category save(final Category category) {

		Category saved;
		Integer parentId;

		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(category);
		Assert.isTrue(!category.getName().equals(category.getParentCategory().getName()));
		Assert.isTrue(category.getServises().isEmpty());

		parentId = category.getParentCategory().getId();

		if (category.getId() == 0) {
			Assert.isTrue(!this.categoryRepository.existsThisCategoryName(category.getName(), parentId));
			saved = this.categoryRepository.save(category);
			saved.getParentCategory().getChildrenCategories().add(saved);
		} else {
			String oldName = findOne(category.getId()).getName();

			if (category.getName().equals(oldName)) {
				saved = this.categoryRepository.save(category);
				saved.getParentCategory().getChildrenCategories().add(saved);
			} else {
				Assert.isTrue(!this.categoryRepository.existsThisCategoryName(category.getName(), parentId));
				saved = this.categoryRepository.save(category);
				saved.getParentCategory().getChildrenCategories().add(saved);
			}
		}

		return saved;

	}
	public void delete(final Category category) {

		Assert.notNull(category);
		Assert.isTrue(actorService.isAdministrator());
		Assert.isTrue(category.getServises().isEmpty());

		category.getParentCategory().getChildrenCategories().remove(category);

		for (final Servise s : category.getServises())
			s.setCategory(null);

		for (final Category c : category.getChildrenCategories()) {
			c.setParentCategory(category.getParentCategory());
			category.getParentCategory().getChildrenCategories().add(c);
		}

		this.categoryRepository.delete(category);
	}

	// Other business methods -------------------------------------------------

	public Collection<Rendezvous> findAllRendezvousByCategoryId(final int categoryId) {

		Collection<Rendezvous> result;

		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(categoryId);

		result = this.categoryRepository.findAllRendezvousByCategoryId(categoryId);

		return result;

	}

	public Collection<Category> findCategoryChildrenId(final int categoryId) {

		Collection<Category> result = null;

		Assert.notNull(categoryId);

		result = this.categoryRepository.findCategoryChildrenId(categoryId);
		return result;
	}

	public Collection<Rendezvous> findAllRendezvousByCategoryId2( int categoryId) {
		return categoryRepository.findAllRendezvousByCategoryId2(categoryId);
	}

    public Category findOneToEdit(int entityId) {
		Assert.isTrue(actorService.isAdministrator());
		return findOne(entityId);
    }

	public void flush() {
		categoryRepository.flush();
	}


//	public Category saveDelete(final Category category) {
//		Assert.notNull(category);
//
//		return this.categoryRepository.save(category);
//	}

}
