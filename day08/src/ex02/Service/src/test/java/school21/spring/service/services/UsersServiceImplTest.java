package school21.spring.service.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersServiceImplTest {
    private UsersService usersService;
    private UsersRepository usersRepository;
    @BeforeEach
    public void initialize() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersRepository = applicationContext.getBean("usersRepository", UsersRepositoryJdbcTemplateImpl.class);
        usersService = applicationContext.getBean("usersService", UsersServiceImpl.class);
    }
    @Test
    public void isSignedUpTest() {
        String email = "adevletukaev@gmail.com";
        String password = usersService.signUp(email);
        assertEquals(password, usersRepository.findByEmail(email).get().getPassword());
    }



}
