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
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcMealRepositoryImpl.class);
    private final static BeanPropertyRowMapper<Meal> ROW_MAPPER =
                                BeanPropertyRowMapper.newInstance(Meal.class);
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
    public Boolean save(Meal meal) {
        LOGGER.info("Save meal: {}", meal);
        MapSqlParameterSource parameterMap = new MapSqlParameterSource();

        parameterMap.addValue("user_id", AuthorizedUser.getId());
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
    public Boolean remove(Integer mealId) {
        Objects.requireNonNull(mealId);
        LOGGER.info("Remove meal id: {}", mealId);
        String QUERY = "DELETE FROM meals WHERE id=? AND user_id=?";
        return namedParameterJdbcTemplate
                .getJdbcOperations()
                .update(QUERY, mealId, AuthorizedUser.getId()) != 0;
    }

    @Override
    public Meal getById(Integer id) {
        String QUERY = "SELECT m.* FROM meals m WHERE m.id=? AND m.user_id=?";
        List<Meal> meals = namedParameterJdbcTemplate
                .getJdbcOperations()
                .query(QUERY, ROW_MAPPER, id, AuthorizedUser.getId());

        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAllByUserId(Integer userId) {
        Objects.requireNonNull(userId);
        LOGGER.info("Get all user id '{}' meals", userId);
        String QUERY = "SELECT id, description, calories, datetime " +
                        "FROM meals WHERE user_id=? ORDER BY datetime";
        List<Meal> meals = namedParameterJdbcTemplate.getJdbcOperations()
                .query(QUERY, (ResultSet resultSet, int i) -> {
                            LOGGER.info("datetime = {}", resultSet.getString("datetime"));
                            LocalDateTime dateTime =
                                    LocalDateTime.parse(resultSet.getString("datetime"),
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            Meal meal = new Meal(dateTime,
                                resultSet.getObject("description", String.class),
                                    resultSet.getInt("calories"));
                                meal.setId(resultSet.getInt("id"));
                                return meal;}
                                , userId);
        LOGGER.info("meals.size() = {}", meals.size());
        return meals;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, Integer userId) {

        LOGGER.info("getBetween()");
        List<Meal> meals = namedParameterJdbcTemplate
                .getJdbcOperations()
                .query("SELECT id, description, datetime, calories " +
                        "FROM meals " +
                        "WHERE (datetime BETWEEN ? AND ?) AND user_id=?", ROW_MAPPER, startDate, endDate, userId);
        return meals;
    }
}
