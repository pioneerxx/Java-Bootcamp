package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("usersService")
@DependsOn("encoder")
public class UsersServiceImpl implements UsersService {
    UsersRepository usersRepository;
    PasswordEncoder encoder;
    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepository") UsersRepository repository,
                            @Qualifier("encoder") PasswordEncoder encoder) {
        this.usersRepository = repository;
        this.encoder = encoder;
    }
    @Override
    public void signUp(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
}
