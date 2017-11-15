package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static Map<Integer, User> userMap;

    {
        //TODO initialize the userMap
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if (userMap.containsKey(id)) {
            userMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        return userMap.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userMap.values()
                .stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .get();
    }
}
