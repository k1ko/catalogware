package com.wp.finki.ukim.mk.catalogware.repository;

import com.wp.finki.ukim.mk.TestUtils;
import com.wp.finki.ukim.mk.catalogware.CatalogwareApplication;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.ProductLikeId;
import com.wp.finki.ukim.mk.catalogware.model.User;
import com.wp.finki.ukim.mk.catalogware.utils.ProductImageUtils;
import com.wp.finki.ukim.mk.catalogware.utils.SetUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Borce on 01.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CatalogwareApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ProductLikeRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLikeRepository repository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ProductImageUtils productImageUtils;

    private User admin = new User(1, "Admin", "admin@admin.com", "pass", new Date(), User.Role.ADMIN);
    private User user1 = new User(2, "User 1", "user@user.com", "pass", new Date(), User.Role.CUSTOMER);
    private User user2 = new User(3, "User 2", "user-2@user.com", "pass", new Date(), User.Role.CUSTOMER);
    private Product product1 = new Product(1, "Product 1", "Temp Product 1", 123, null, new Date(), new Date(), admin);
    private Product product2 = new Product(2, "Product 2", "Temp Product 2", 123, null, new Date(), new Date(), admin);
    private final int NUMBER_OF_LIKES = 3;
    private ProductLike like1 = new ProductLike(new ProductLikeId(user1, product1), (short) 5);
    private ProductLike like2 = new ProductLike(new ProductLikeId(user1, product2), (short) 3);
    private ProductLike like3 = new ProductLike(new ProductLikeId(user2, product1), (short) 1);
    private ProductLike unExistingLike = new ProductLike(new ProductLikeId(user2, product2), (short) 2);
    private Set<ProductLike> product1Likes = new HashSet<>(Arrays.asList(like1, like3));
    private Set<ProductLike> product2Likes = new HashSet<>(Collections.singletonList(like2));

    @Before
    public void setup() {
        byte[] image = productImageUtils.getBytes();
        product1.setImage(image);
        product2.setImage(image);
        userRepository.save(admin);
        userRepository.save(user1);
        userRepository.save(user2);
        productRepository.save(product1);
        productRepository.save(product2);
        repository.save(like1);
        repository.save(like2);
        repository.save(like3);
    }

    @After
    public void shutdown() {
        repository.deleteAll();
        productRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, Product.class);
        userRepository.deleteAll();
        TestUtils.resetTableAutoincrement(context, User.class);
    }

    /**
     * Temp that findAll will return all likes in the dastabase.
     */
    @Test
    public void testFindAll() {
        List<ProductLike> likes = repository.findAll();
        assertEquals(NUMBER_OF_LIKES, likes.size());
        int counter = 0;
        for (ProductLike like : likes) {
            ProductLikeId id = like.getPk();
            long userId = id.getUser().getId();
            long productId = id.getProduct().getId();
            if (like1.getPk().getUser().getId().equals(userId) &&
                    like1.getPk().getProduct().getId().equals(productId)) {
                assertEquals(like1, like);
                counter++;
            } else if (like2.getPk().getUser().getId().equals(userId) &&
                    like2.getPk().getProduct().getId().equals(productId)) {
                assertEquals(like2, like);
                counter++;
            } else if (like3.getPk().getUser().getId().equals(userId) &&
                    like3.getPk().getProduct().getId().equals(productId)) {
                assertEquals(like3, like);
                counter++;
            }
        }
        assertEquals(NUMBER_OF_LIKES, counter);
    }

    /**
     * Temp that findOne will return the like when the id exists in the database.
     */
    @Test
    public void testFindOne() {
        ProductLike like = repository.findOne(like2.getPk());
        assertEquals(like2, like);
    }

    /**
     * Temp that behavior when findOne is called with ProductLikeId which contains user and product that
     * have empty data and only the primary key is set.
     */
    @Test
    public void testFindOneWhenTheUserAndProductContainsOnlyThePrimaryKey() {
        User user = new User(like1.getPk().getUser().getId(), null, null, null, null, null);
        Product product = new Product(like1.getPk().getProduct().getId(), null, null, 0, null, null, null, null);
        ProductLikeId id = new ProductLikeId(user, product);
        ProductLike like = repository.findOne(id);
        assertNotEquals(like1, like);
        assertEquals(like1.getRating(), like.getRating());
        assertEquals(like1.getPk().getUser().getId(), like.getPk().getUser().getId());
        assertEquals(like1.getPk().getProduct().getId(), like.getPk().getProduct().getId());
    }

    /**
     * Temp that findOne will return null when the id don't exists in the database.
     */
    @Test
    public void testFindOneOnUnExistingId() {
        assertNull(repository.findOne(unExistingLike.getPk()));
    }

    /**
     * Temp that count will return the number of users in the database.
     */
    @Test
    public void testCount() {
        assertEquals(NUMBER_OF_LIKES, repository.count());
    }

    /**
     * Temp that save method will save a new like in the database when the id don't exists in the database.
     */
    @Test
    public void testStore() {
        ProductLike like = repository.save(unExistingLike);
        assertEquals(NUMBER_OF_LIKES + 1, repository.count());
        assertEquals(unExistingLike, like);
        ProductLike savedLike = repository.findOne(unExistingLike.getPk());
        assertEquals(unExistingLike, savedLike);
    }

    /**
     * Temp that save will update the like data when the id exists in the database.
     */
    @Test
    public void testUpdate() {
        like3.setRating((short) 4);
        ProductLike like = repository.save(like3);
        assertEquals(NUMBER_OF_LIKES, repository.count());
        assertEquals(like3, like);
        ProductLike updatedLike = repository.findOne(like3.getPk());
        assertEquals(like3, updatedLike);
    }

    /**
     * Temp the behavior when save is called with existing primary key on which the user and the
     * product are empty and only have their ids.
     */
    @Test
    public void testUpdateWhenUserAndProductContainsOnlyThePrimaryKey() {
        final short newRating = 4;
        User user = new User(user2.getId());
        Product product = new Product(product1.getId());
        like3.setPk(new ProductLikeId(user, product));
        like3.setRating(newRating);
        ProductLike like = repository.save(like3);
        assertEquals(NUMBER_OF_LIKES, repository.count());
        assertEquals(user.getId(), like.getPk().getUser().getId());
        assertEquals(product.getId(), like.getPk().getProduct().getId());
        assertEquals(newRating, like.getRating());
        ProductLike updatedLike = repository.findOne(like3.getPk());
        assertEquals(user.getId(), updatedLike.getPk().getUser().getId());
        assertEquals(product.getId(), updatedLike.getPk().getProduct().getId());
        assertEquals(newRating, updatedLike.getRating());
    }

    /**
     * Temp that delete will remove the like from the database.
     */
    @Test
    public void testDelete() {
        repository.delete(like1.getPk());
        assertEquals(NUMBER_OF_LIKES - 1, repository.count());
        assertNull(repository.findOne(like1.getPk()));
    }

    /**
     * Temp the behavior when delete is called with existing primary key on which the user and the
     * product are empty and only contains their id.
     */
    @Test
    public void testDeleteWhenUserAndProductOnlyContainsThePrimaryKey() {
        User user = new User(like2.getPk().getUser().getId());
        Product product = new Product(like2.getPk().getProduct().getId());
        repository.delete(new ProductLikeId(user, product));
        assertEquals(NUMBER_OF_LIKES - 1, repository.count());
        assertNull(repository.findOne(like2.getPk()));
    }

    @Test
    public void testGetProductLikes() {
        Product product = productRepository.findOne(product1.getId());
        assertTrue(SetUtils.equals(product1Likes, product.getLikes()));
    }
}
