package edu.school21.service.application;

import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        System.out.println("/* ---------------------------------- JDBCApi ---------------------------------- */");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbcImpl", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        User user = new User(null, "umkabear@gmail.com");
        usersRepository.save(user);
        System.out.println(usersRepository.findAll());
        user.setEmail("callux@hotmail.com");
        usersRepository.update(user);
        System.out.println(usersRepository.findAll());
        usersRepository.delete(user.getId());
        System.out.println(usersRepository.findAll());
        Optional<User> userOptional = usersRepository.findByEmail("adevletukaev@gmail.com");
        System.out.println(userOptional.isPresent() ? userOptional.get() : "Not found");

        System.out.println("/* -------------------------------- JDBCTemplate -------------------------------- */");

        usersRepository = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        user = new User(null, "umkabear@gmail.com");
        usersRepository.save(user);
        System.out.println(usersRepository.findAll());
        user.setEmail("callux@hotmail.com");
        usersRepository.update(user);
        System.out.println(usersRepository.findAll());
        usersRepository.delete(user.getId());
        System.out.println(usersRepository.findAll());
        userOptional = usersRepository.findByEmail("adevletukaev@gmail.com");
        System.out.println(userOptional.isPresent() ? userOptional.get() : "Not found");
    }
}
