package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
public class TestApplicationConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().addScript("/schema.sql").build();
    }
    @Bean
    public UsersRepository usersRepository(DataSource dataSource) {
        return new UsersRepositoryJdbcTemplateImpl(dataSource);
    }
    @Bean
    public UsersService usersService(UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }
}
