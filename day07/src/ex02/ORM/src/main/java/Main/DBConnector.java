package Main;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Scanner;

public class DBConnector {
    private static final String dbAddress;
    private static final String dbLogin;
    private static final String dbPassword;
    static {
        InputStream properties = DBConnector.class.getResourceAsStream("/database.properties");
        Scanner scanner = new Scanner(properties);
        dbAddress = scanner.nextLine().split(" = ")[1];
        dbLogin = scanner.nextLine().split(" = ")[1];
        dbPassword = scanner.nextLine().split(" = ")[1];
    }
    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbAddress);
        config.setUsername(dbLogin);
        config.setPassword(dbPassword);
        return new HikariDataSource(config);
    }
}
