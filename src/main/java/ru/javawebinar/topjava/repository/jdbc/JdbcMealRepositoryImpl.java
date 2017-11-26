package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcMealRepositoryImpl.class);

    private final static BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final SimpleJdbcInsert simpleJdbcInsert;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcMealRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                  SimpleJdbcInsert simpleJdbcInsert) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        LOG.info("userId {}", userId);
        LOG.info("meal {}", meal);
        MapSqlParameterSource parameterMap = new MapSqlParameterSource();

        parameterMap.addValue("user_id", userId);
        parameterMap.addValue("description", meal.getDescription());
        parameterMap.addValue("calories", meal.getCalories());
        parameterMap.addValue("datetime", meal.getDateTime());

        if (meal.isNew()) {
            Number mealId = simpleJdbcInsert.executeAndReturnKey(parameterMap);
            meal.setId(mealId.intValue());
        } else {
            simpleJdbcInsert.execute(parameterMap);
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return namedParameterJdbcTemplate
                .getJdbcOperations()
                .update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = namedParameterJdbcTemplate
                .getJdbcOperations()
                .query("SELECT id, description, datetime, calories FROM meals WHERE id=? AND user_id=?",
                        ROW_MAPPER, id, userId);

        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("getAll()");
        List<Meal> meals = namedParameterJdbcTemplate
                .getJdbcOperations()
                .query("SELECT id, description, calories, datetime FROM meals WHERE user_id=? ORDER BY datetime",
                        (ResultSet resultSet, int i) -> {
                            LOG.info("datetime = {}", resultSet.getString("datetime"));
                            LocalDateTime dateTime =
                                    LocalDateTime.parse(resultSet.getString("datetime"),
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            Meal meal = new Meal(dateTime,
                                resultSet.getObject("description", String.class),
                                    resultSet.getInt("calories"));
                                meal.setId(resultSet.getInt("id"));
                                return meal;}
                                , userId);
        LOG.info("meals.size() = {}", meals.size());
//        LOG.info("meals.get(0) = {}", meals.get(0));
        return meals;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        LOG.info("getBetween()");
        List<Meal> meals = namedParameterJdbcTemplate
                .getJdbcOperations()
                .query("SELECT id, description, datetime, calories " +
                        "FROM meals " +
                        "WHERE (datetime BETWEEN ? AND ?) AND user_id=?", ROW_MAPPER, startDate, endDate, userId);
        return meals;
    }
}
