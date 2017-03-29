package com.valtech.tp.app1.domain.repository.product;

import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import com.valtech.tp.app1.domain.model.commun.EntityDoNotExist;
import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.product.ProductCriteria;
import com.valtech.tp.app1.domain.model.product.ProductLite;
import com.valtech.tp.app1.domain.repository.commun.DomainRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductRepository extends DomainRepository {

    public ProductRepository() {

    }

    public void insert(Product product) throws EntityAlreadyExist {
        if(findProductByReference(product.getReference()) == null) {
            getEntityManager().persist(product);
        }
        else {
            throw new EntityAlreadyExist("The reference is already used.");
        }
    }

    public Product getProduct(Long id) {
        return getEntityManager().find(Product.class, id);
    }

    public Product findProductByReference(String ref) {
        return (Product) getCurrentHbnSession()
                .createQuery("from Product where reference = :ref")
                .setParameter("ref", ref)
                .uniqueResult();
    }

    public Product findProductByReferenceWithCriteria(String ref) {
        return (Product) getCurrentHbnSession()
                .createCriteria(Product.class)
                .add(Restrictions.eq("reference", ref))
                .uniqueResult();
    }

    public Product findProductByReferenceWithJpaCriteria(String ref) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> criteria = criteriaBuilder.createQuery(Product.class);

        Root<Product> productRoot = criteria.from(Product.class);
        criteria.select(productRoot)
                .where(criteriaBuilder.equal(productRoot.get("reference"), ref));

        return getEntityManager().createQuery(criteria).getSingleResult();
    }

    public List<ProductLite> findProductsByCriteria(ProductCriteria productCriteria) {
        Criteria criteria = getCurrentHbnSession().createCriteria(Product.class);
        if (!StringUtils.isEmpty(productCriteria.getName())) {
            criteria.add(Restrictions.ilike("name", productCriteria.getName().replace('*', '%')));
        }
        if (!StringUtils.isEmpty(productCriteria.getDescription())) {
            criteria.add(Restrictions.ilike("description", productCriteria.getDescription().replace('*', '%')));
        }
        criteria.addOrder(Order.asc("name"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(10);

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("reference").as("reference"))
                .add(Projections.property("id").as("id"))
                .add(Projections.property("name").as("name")));
        criteria.setResultTransformer(Transformers.aliasToBean(ProductLite.class));
        return criteria.list();
    }

    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        if(product != null) {
            getEntityManager().remove(product);
        }
        else {
            throw new EntityDoNotExist("This id is not used by a product.");
        }
    }
}
