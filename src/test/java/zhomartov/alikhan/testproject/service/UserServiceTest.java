package zhomartov.alikhan.testproject.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import zhomartov.alikhan.testproject.TestProjectApplication;
import zhomartov.alikhan.testproject.model.User;
import zhomartov.alikhan.testproject.repository.UserRepository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUserService() {
        User user = new User();
        user.setUsername("alikhan@gmail.com");
        user.setPassword("22332");
        user.setPhoneNumber("87081846854");
        user.setDateOfBirth(LocalDate.now());
        user.setAdditionalInformation("create for test service!");
        User user1 = userRepository.save(user);
        Assert.assertNotNull(user1);
    }

    @Test
    public void updateUserService() {
        Optional<User> user = userRepository.findByUsername("alikhan@gmail.com");
        if (user.isPresent()) {
            user.get().setUsername("zhomartov@gmail.com");
            userRepository.save(user.get());
            Assert.assertEquals(user.get().getUsername(), "zhomartov@gmail.com");
        }
    }

    @Test
    public void selectAllUsers() {
        List<User> userList = userRepository.findAll();
        Assert.assertNotNull(userList);
    }

    @Test
    public void selectUserById() {
        Optional<User> user1 = userRepository.findByUsername("zhomartov@gmail.com");
        Optional<User> user = userRepository.findById(user1.get().getId());
        Assert.assertEquals(user.get().getUsername(), "zhomartov@gmail.com");
    }

    @Test
    public void deleteUserService() {
        Optional<User> user = userRepository.findByUsername("zhomartov@gmail.com");
        userRepository.delete(user.get());
        Optional<User> user1 = userRepository.findByUsername("zhomartov@gmail.com");
        Assert.assertNull(user1);
    }
}