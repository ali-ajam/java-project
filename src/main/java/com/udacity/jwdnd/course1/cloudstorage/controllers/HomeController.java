package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {

        User currentUser = this.userService.getUser(authentication.getName());

        model.addAttribute("notes", noteService.getNotes(currentUser.getUserId()));

        model.addAttribute("files", fileService.getFiles(currentUser.getUserId()));

        model.addAttribute("credentials", credentialService.listCredentials(currentUser.getUserId()));

        return "home";
    }

}