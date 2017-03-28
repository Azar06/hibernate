package com.valtech.tp.app1.domain.repository.product;

import com.valtech.tp.app1.domain.model.product.*;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private EntityManager em;

    private static String ref = "MyRef";
    private static String name = "MyName";
    private static Double price = 10.9d;
    private static String description = "description";

    public static Product createDummyProduct() {
        Product product = new SuperProduct(ref);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        //product.setSuperInformation("SuperInfo");
        return product;
    }

    @Test
    public void insert() {
        Product p = createDummyProduct();
        repo.insert(p);
        em.flush();

        assertThat(p.getId()).isNotNull();

        Product p2 = em.find(Product.class, p.getId());
        assertThat(p.getName()).isEqualTo(name);
    }

    @Test
    public void findByType() throws Exception {


        Session session = em.unwrap(Session.class);
        session.createCriteria(MegaProduct.class)
                //.add(Restrictions.eq("class", "super"))
                .list();
    }

    @Test
    public void getProduct() throws Exception {
        Product product = createDummyProduct();
        em.persist(product);

        Product productFound = repo.getProduct(product.getId());

        assertThat(productFound).isEqualTo(product);
    }

    @Test
    public void findProductByReferenceWithCriteria() throws Exception {
        Product product = createDummyProduct();
        em.persist(product);

        Product foundProduct = repo.findProductByReferenceWithCriteria(ref);
        assertThat(foundProduct).isEqualTo(product);

        Product foundProduct2 = repo.findProductByReferenceWithCriteria("FakeRef");
        assertThat(foundProduct2).isNull();
    }

    @Test
    public void findProductByReferenceWithJpaCriteria() throws Exception {
        Product product = createDummyProduct();
        em.persist(product);
        em.clear();

        Product foundProduct = repo.findProductByReferenceWithJpaCriteria(ref);
        assertThat(foundProduct).isEqualTo(product);

        Product foundProduct2 = repo.findProductByReferenceWithCriteria("FakeRef");
        assertThat(foundProduct2).isNull();
    }

    @Test
    public void findProductByReference() throws Exception {
        Product p = createDummyProduct();
        repo.insert(p);
        //em.flush();
        //em.clear();

        Product p2 = repo.findProductByReference(ref);
        assertThat(p2).isEqualTo(p);
    }

    @Test
    public void findProductByCriteria_withOnlyName() throws Exception {
        Product p = createDummyProduct();
        repo.insert(p);
        em.clear();

        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setName("my*");

        List<ProductLite> productLites = repo.findProductsByCriteria(productCriteria);
        assertThat(productLites.size()).isEqualTo(1);
        assertThat(productLites.get(0).getId()).isEqualTo(p.getId());
        assertThat(productLites.get(0).getName()).isEqualTo(p.getName());
        assertThat(productLites.get(0).getReference()).isEqualTo(p.getReference());
    }

    @Test
    public void findProductByCriteria_withOnlyDescription() throws Exception {
        Product p = createDummyProduct();
        repo.insert(p);
        em.flush();
        em.clear();

        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setDescription("desc*");

        List<ProductLite> productLites = repo.findProductsByCriteria(productCriteria);
        assertThat(productLites.size()).isEqualTo(1);
        assertThat(productLites.get(0).getId()).isEqualTo(p.getId());
        assertThat(productLites.get(0).getName()).isEqualTo(p.getName());
        assertThat(productLites.get(0).getReference()).isEqualTo(p.getReference());
    }
}
