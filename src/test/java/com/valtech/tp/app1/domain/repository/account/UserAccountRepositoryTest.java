package com.valtech.tp.app1.domain.repository.account;

import static org.assertj.core.api.Assertions.assertThat;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.valtech.tp.app1.domain.model.account.UserAccount;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserAccountRepositoryTest {

    private Session getHibernateSession() {
        return entityManager.unwrap(Session.class);
    }

    @Autowired
    private EntityManager entityManager;

    private String email = "stephane.malbequi@gmail.com";

    private String firstName = "Stephane";

    private String lastName = "Malbequi";

    private String password = "password";

    private String role = "ADMIN";

    private UserAccount createDummyUserAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setPassword(password);
        userAccount.setRole(role);
        return userAccount;
    }

    @Test
    public void persist() {
        UserAccount userAccount = createDummyUserAccount();

        entityManager.persist(userAccount);

        entityManager.flush();

        assertThat(userAccount).isNotNull();

        entityManager.clear();

        userAccount = entityManager.find(UserAccount.class, userAccount.getId());

        assertThat(userAccount.getId()).isNotNull();
        assertThat(userAccount.getEmail()).isEqualTo(email);
        assertThat(userAccount.getFirstName()).isEqualTo(firstName);
        assertThat(userAccount.getLastName()).isEqualTo(lastName);
        assertThat(userAccount.getPassword()).isEqualTo(password);
        assertThat(userAccount.getRole()).isEqualTo(role);
    }

    @Test
    public void findByEmail_with_hql() {
        UserAccount userAccount = createDummyUserAccount();

        entityManager.persist(userAccount);

        entityManager.flush();
        entityManager.clear();

        userAccount = (UserAccount) entityManager
                .createQuery("from UserAccount where email=:email")
                .setParameter("email", email)
                .getSingleResult();

        assertThat(userAccount).isNotNull();
        assertThat(userAccount.getEmail()).isEqualTo(email);
    }

    @Test
    public void findByEmail_with_jpa_criteria() {
        UserAccount userAccount = createDummyUserAccount();

        entityManager.persist(userAccount);

        entityManager.flush();
        entityManager.clear();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserAccount> criteria = criteriaBuilder.createQuery(UserAccount.class);

        Root<UserAccount> userAccountRoot = criteria.from(UserAccount.class);

        criteria.select(userAccountRoot)
                .where(criteriaBuilder.equal(userAccountRoot.get("email"), email));

        userAccount = entityManager.createQuery(criteria).getSingleResult();

        assertThat(userAccount).isNotNull();
        assertThat(userAccount.getEmail()).isEqualTo(email);
    }

    @Test
    public void findByEmail_with_hibernate_criteria() {
        UserAccount userAccount = createDummyUserAccount();

        entityManager.persist(userAccount);

        entityManager.flush();
        entityManager.clear();

        Session session = getHibernateSession();

        userAccount = (UserAccount) session
                .createCriteria(UserAccount.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();

        assertThat(userAccount).isNotNull();
        assertThat(userAccount.getEmail()).isEqualTo(email);
    }

}
