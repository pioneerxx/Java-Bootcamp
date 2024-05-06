package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class DBConnection {
    private static String dbAddress;
    private static String dbLogin;
    private static String dbPassword;
    static {
        InputStream properties = DBConnection.class.getResourceAsStream("/database.properties");
        Scanner scanner = new Scanner(properties);
        dbAddress = scanner.nextLine().split(" = ")[1];
        dbLogin = scanner.nextLine().split(" = ")[1];
        dbPassword = scanner.nextLine().split(" = ")[1];
    }
    public static DataSource getConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbAddress);
        config.setUsername(dbLogin);
        config.setPassword(dbPassword);
        return new HikariDataSource(config);
    }
}
