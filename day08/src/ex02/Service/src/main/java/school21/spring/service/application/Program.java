package school21.spring.service.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;


public class Program {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersService usersService = applicationContext.getBean("usersServiceImpl", UsersServiceImpl.class);
        UsersRepository usersRepository = applicationContext.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
        usersService.signUp("adevletukaev@gmail.com");
        usersService.signUp("umkabear@gmail.com");
        System.out.println(usersRepository.findAll());
    }
}
