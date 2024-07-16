//package com.example.keyclock.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/keycloak")
//public class KeycloakController {
//
//    @Autowired
//    private KeycloakAdminService keycloakAdminService;
//
//    @PostMapping("/user")
//    public void createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
//        keycloakAdminService.createUser(username, password, email);
//    }
//
//    @PostMapping("/role")
//    public void createRole(@RequestParam String roleName) {
//        keycloakAdminService.createRole(roleName);
//    }
//
//    @PostMapping("/assign-role")
//    public void assignRoleToUser(@RequestParam String username, @RequestParam String roleName) {
//        keycloakAdminService.assignRoleToUser(username, roleName);
//    }
//}