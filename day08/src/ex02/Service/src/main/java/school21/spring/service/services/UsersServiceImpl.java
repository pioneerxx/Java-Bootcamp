package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

@Component("usersServiceImpl")
public class UsersServiceImpl implements UsersService {
    UsersRepository usersRepository;
    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplateImpl") UsersRepository repository) {
        this.usersRepository = repository;
    }
    @Override
    public String signUp(String email) {
        UUID password = UUID.randomUUID();
        User user = new User(null, email, password.toString());
        usersRepository.save(user);
        return password.toString();
    }
}
