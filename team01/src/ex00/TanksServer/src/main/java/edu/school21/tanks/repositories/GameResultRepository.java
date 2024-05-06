package edu.school21.tanks.repositories;

import edu.school21.tanks.models.GameResult;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public interface GameResultRepository {
    void save(GameResult entity);

}
