package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;


import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {
        User us = userDao.getById(id);
        us.setUserName(user.getUserName());
        us.setUserLastname(user.getUserLastname());
        us.setUserMail(user.getUserMail());
        us.setUserAge(user.getUserAge());
        us.setPassword(user.getPassword());
        us.setRoles(user.getRoles());
        userDao.save(us);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        userDao.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        Optional<User> founduser = userDao.findById(id);
        return founduser.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> listUser() {
        return userDao.findAll();
    }

    @Override
    public List<Role> listRole() {
        return roleDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Optional<User> user = userDao.findByuserName(username);
        Optional<User> user = userDao.findByuserMail(username); //если mail вместо имени аутентификации
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not find !");
        }
        return user.get();
    }
}
