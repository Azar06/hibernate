package com.valtech.tp.app1.domain.repository.product;

import com.valtech.tp.app1.domain.model.product.Product;
import com.valtech.tp.app1.domain.model.product.ProductCriteria;
import com.valtech.tp.app1.domain.model.product.ProductLite;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    private EntityManager em;

    public ProductRepository() {

    }

    public void insert(Product product) {
        em.persist(product);
    }

    public Product getProduct(Long id) {
        return em.find(Product.class, id);
    }

    public Product findProductByReference(String ref) {
        return (Product) getHibernateSession()
                .createQuery("from Product where reference = :ref")
                .setParameter("ref", ref)
                .uniqueResult();
    }

    public Product findProductByReferenceWithCriteria(String ref) {
        return (Product) getHibernateSession()
                .createCriteria(Product.class)
                .add(Restrictions.eq("reference", ref))
                .uniqueResult();
    }

    public Product findProductByReferenceWithJpaCriteria(String ref) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = criteriaBuilder.createQuery(Product.class);

        Root<Product> productRoot = criteria.from(Product.class);
        criteria.select(productRoot)
                .where(criteriaBuilder.equal(productRoot.get("reference"), ref));

        return em.createQuery(criteria).getSingleResult();
    }

    public List<ProductLite> findProductsByCriteria(ProductCriteria productCriteria) {
        Criteria criteria = getHibernateSession().createCriteria(Product.class);
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

    private Session getHibernateSession() {
        return em.unwrap(Session.class);
    }
}
