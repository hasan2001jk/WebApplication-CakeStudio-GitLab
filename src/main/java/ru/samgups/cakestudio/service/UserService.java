package ru.samgups.cakestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.samgups.cakestudio.dto.LoginDto;
import ru.samgups.cakestudio.dto.UserDto;
import ru.samgups.cakestudio.entity.User;
import ru.samgups.cakestudio.entity.enums.Role;
import ru.samgups.cakestudio.repository.UserRepository;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRegistry sessionRegistry;

    public User getUserByUsername(String username) {
        User user = userRepository.findByName(username);
        return  user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }



    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    public User registerUser(UserDto userDto) {
        // Convert UserDto to User entity
        String[] names = userDto.getFio().split(" ");
        User user = new User();
        user.setName(names[1]);
        user.setSurname(names[0]);
        // Encrypt the user's password
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        user.setPassword(encryptedPassword);
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setActive(true);
        Set<Role> roles =new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        // Set other user properties from the userDto

        // Perform any additional validation or business logic
        if(existsByEmail(user.getEmail())){
            System.out.println("This email is already registered!");
        }else{
            System.out.println("The email is unique!");
            createUser(user);
        }
        // Return the registered user
        return user;
    }

    public User loginUser(LoginDto loginDto) {
        // Retrieve the login credentials from the LoginDto
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // Perform the login logic
        // You can retrieve the user from the database based on the email or perform any necessary checks

        // Example: Retrieve the user from the database based on the email
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Valid login credentials, return the user
            loadUserByUsername(email);
            System.out.println("Password is correct!");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isLoggedIn = (authentication != null && authentication.isAuthenticated());
            System.out.println(isAuthenticated());
            if (isLoggedIn) {
                List<String> sessions =sessionRegistry.getAllPrincipals().stream()
                        .filter(u -> !sessionRegistry.getAllSessions(u, true).isEmpty())
                        .map(Object::toString)
                        .collect(Collectors.toList());
                System.out.println("Size:" + sessions.size());
                System.out.println(sessionRegistry.getAllPrincipals().isEmpty());
                List<Object> allPrincipals =sessionRegistry.getAllPrincipals();
                for(Object ses : allPrincipals){
                    System.out.println(ses);
                }
                // Perform actions with the session information, such as getting the session ID or checking session status
            }
            return user;
        } else {
            // Invalid login credentials, return null or throw an exception
            System.out.println("Password is incorrect!");
            System.out.println(isAuthenticated());
            return null;
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}




