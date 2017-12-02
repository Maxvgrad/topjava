package ru.javawebinar.topjava.service;

import org.hsqldb.lib.StopWatch;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);
    private static Map<String, Long> map = new HashMap<>();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();


    @Rule
    public TestWatcher watcher = new TestWatcher() {
        private StopWatch stopWatch = new StopWatch();

        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void succeeded(Description description) {
            super.succeeded(description);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            super.failed(e, description);
        }

        @Override
        protected void starting(Description description) {
            stopWatch.start();
//            System.out.println("starting");
//            super.starting(description);
        }

        @Override
        protected void finished(Description description) {
//            System.out.println("TIME:  " + );
//            super.finished(description);

            map.put(testName.getMethodName(), stopWatch.currentElapsedTime());
        }
    };

    @Rule
    public TestName testName = new TestName();

    @AfterClass
    public static void afterClass() {
        for(Map.Entry<String, Long> entry : map.entrySet()) {
            log.info("===========================================");
            log.info("Test Name : {} Time: {}", entry.getKey(), entry.getValue());
            log.info("===========================================");
            log.info("\n");
        }
    }

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void testDelete() throws Exception {
//        log.info("testDelete()");
//        log.info("service.getAll().size() {}", service.getAll(USER_ID).size());

//        service.getAll(USER_ID).forEach(meal -> log.debug(" " + meal));
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

//    @Test(expected = NotFoundException.class)
    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void testSave() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testGet() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void testGetBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}