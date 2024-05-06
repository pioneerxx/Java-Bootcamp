package edu.school21.tanks.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Bean;
import com.zaxxer.hikari.HikariConfig;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"edu.school21.tanks.server", "edu.school21.tanks.repositories"})
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.driver.name}")
    private String driverName;
    @Bean("dataSource")
    public DataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverName);
        return new HikariDataSource(hikariConfig);
    }
}
