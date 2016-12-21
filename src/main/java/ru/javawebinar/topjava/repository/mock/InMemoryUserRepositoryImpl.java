package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private AtomicInteger userCounter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        if(!userRepository.containsKey(id)) return false;
        userRepository.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(userCounter.incrementAndGet());
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        Comparator<User> userComparator = (u1, u2) -> u1.getName().compareTo(u2.getName());
        return userRepository.values().stream().sorted(userComparator).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);

//        for (Map.Entry<Integer, User> entry : userRepository.entrySet()) {
//            if (email.equals(entry.getValue().getEmail())) {
//                return entry.getValue();
//            }
//        }
//        return null;

        return userRepository.entrySet().stream().filter(e->e.getValue().getEmail().equals(email)).findFirst().orElse(null).getValue();


    }
}
