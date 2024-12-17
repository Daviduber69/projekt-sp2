package se.yrgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se.yrgo.domain.UserEntity;
import se.yrgo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/website/users")
public class UserViewController {
    private final UserService userService;

    @Autowired
    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list.html")
    public String getAllUsers(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        System.out.println("Users retrieved: " + users);  // Log the list
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/{id}")
    public String showUserDetails(@PathVariable Long id, Model model) {
        UserEntity user = userService.getUserById(id);  // Add getUserById to UserService
        model.addAttribute("user", user);
        return "userDetails";  // Create a userDetails.jsp for detailed view
    }
}
