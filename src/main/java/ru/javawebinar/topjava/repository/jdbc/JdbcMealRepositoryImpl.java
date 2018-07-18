package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository("mealDao")
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
//        LOGGER.info("Save meal: {}", meal);
//        MapSqlParameterSource parameterMap = new MapSqlParameterSource();
//
//        parameterMap.addValue("user_id", AuthorizedUser.getId());
//        parameterMap.addValue("description", meal.getDescription());
//        parameterMap.addValue("calories", meal.getCalories());
//        parameterMap.addValue("datetime", meal.getMealDate());
//
//        if (meal.isNew()) {
//            Number mealId = simpleJdbcInsert.executeAndReturnKey(parameterMap);
//            meal.setId(mealId.intValue());
//        } else {
//            simpleJdbcInsert.execute(parameterMap);
//        }

        return null;
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
        Objects.requireNonNull(id);
        String QUERY = "SELECT m.* FROM meals m WHERE m.id=:mealId AND m.user_id=:userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mealId", id);
        paramMap.put("userId", AuthorizedUser.getId());
        return namedParameterJdbcTemplate.queryForObject(QUERY, paramMap, new MealMapper());
    }

    @Override
    public List<Meal> getAllByUserId(Integer userId) {
        Objects.requireNonNull(userId);
        LOGGER.info("Get all user id '{}' meals", userId);
        String QUERY = "SELECT id, description, calories, meal_date \n" +
                        "FROM meals WHERE user_id=:userId ORDER BY meal_date";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", AuthorizedUser.getId());
        List<Meal> meals = namedParameterJdbcTemplate.query(QUERY, paramMap, new MealMapper());
        LOGGER.info("meals.size() = {}", meals.size());
        return meals;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, Integer userId) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(userId);

        String QUERY = "SELECT id, description, meal_date, calories \n" +
                        "FROM meals \n" +
                        "WHERE (meal_date BETWEEN :startDate AND :endDate) AND user_id=:userId";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("userId", userId);
        LOGGER.info("Get meal between {} and {} for user id {}", startDate, endDate, userId);
        return namedParameterJdbcTemplate.query(QUERY, paramMap, new MealMapper());
    }

    private static final class MealMapper implements RowMapper<Meal> {
        @Override
        public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Meal m = new Meal(
                    rs.getInt("id"),
                    rs.getTimestamp("meal_date").toLocalDateTime(),
                    rs.getString("description"),
                    rs.getInt("calories")
            );

            return m;
        }
    }
}
