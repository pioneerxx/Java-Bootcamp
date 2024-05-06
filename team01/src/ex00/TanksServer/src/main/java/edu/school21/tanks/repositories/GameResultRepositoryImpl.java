package edu.school21.tanks.repositories;

import edu.school21.tanks.models.GameResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;

@Component("gameResultRepository")
@DependsOn("dataSource")
public class GameResultRepositoryImpl implements GameResultRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GameResultRepositoryImpl(@Qualifier("dataSource")DataSource dataSource) throws SQLException {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("CREATE SCHEMA IF NOT EXISTS tanks;\n" +
                                "CREATE TABLE IF NOT EXISTS tanks.results(\n" +
                                "    id SERIAL PRIMARY KEY,\n" +
                                "    shots INTEGER NOT NULL,\n" +
                                "    shots_missed INTEGER NOT NULL,\n" +
                                "    shots_hit INTEGER NOT NULL,\n" +
                                "    date timestamp default CURRENT_TIMESTAMP);");
    }
    public void save(GameResult entity) {
        jdbcTemplate.update("INSERT INTO tanks.results(shots, shots_missed, shots_hit) " +
                "VALUES(:shotsAll, :shotsMissed, :shotsHit)", new MapSqlParameterSource()
                .addValue("shotsAll", entity.getShotsShot())
                .addValue("shotsMissed", entity.getShotsShot() - entity.getShotsHit())
                .addValue("shotsHit", entity.getShotsHit()));
    }
}
