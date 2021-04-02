package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CredentialController {

    private NoteService noteService;
    private UserService userService;
    private FileService fileService;
    private CredentialService credentialService;

    public CredentialController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }


    @PostMapping("/credential-upload")
    public String uploadCredential(@RequestParam("credentialId") Integer credentialId,
                                   @RequestParam("username") String credentialUsername,
                                   @RequestParam("password") String credentialPassword,
                                   @RequestParam("url") String credentialUrl,
                                   Authentication auth,
                                   Model model) {

        User currentUser = this.userService.getUser(auth.getName());


        if (credentialId == null) {
            try {
                this.credentialService.addCredential(
                        credentialUrl,
                        credentialUsername,
                        credentialPassword,
                        currentUser.getUserId());
                model.addAttribute("credentialSuccess", "Credential successfully added");
            } catch (Exception e) {
                model.addAttribute("credentialError", "Error adding new Credential");
            }


        } else {
            try {
                this.credentialService.updateCredential(
                        credentialId,
                        credentialUsername,
                        credentialPassword,
                        credentialUrl);
                model.addAttribute("credentialSuccess", "Credential successfully updated");
            } catch (Exception e) {
                model.addAttribute("credentialError", "Error updating new Credential");
            }

        }

        // Refresh data
        model.addAttribute("files", this.fileService.getFiles(currentUser.getUserId()));
        model.addAttribute("notes", this.noteService.getNotes(currentUser.getUserId()));
        model.addAttribute("credentials", this.credentialService.listCredentials(currentUser.getUserId()));
        return "home";
    }


    @RequestMapping("/credential-delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId,
                                   Authentication auth,
                                   Model model) {

        User currentUser = this.userService.getUser(auth.getName());

        try {
            this.credentialService.deleteCredential(credentialId);
            model.addAttribute("credentialSuccess", "Credential successfully deleted");
        } catch (Exception err) {
            model.addAttribute("credentialError", "Error deleting Credential");
        }


        model.addAttribute("files", this.fileService.getFiles(currentUser.getUserId()));
        model.addAttribute("notes", this.noteService.getNotes(currentUser.getUserId()));
        model.addAttribute("credentials", this.credentialService.listCredentials(currentUser.getUserId()));
        return "home";
    }


    @GetMapping(value = "/decode-password")
    @ResponseBody
    public Map<String, String> decodePassword(@RequestParam Integer credentialId) {
        Credential credential = credentialService.getCredentialById(credentialId);
        String encryptedPassword = credential.getPassword();
        String encodedKey = credential.getKey();
        EncryptionService encryptionService = new EncryptionService();
        String decodedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        Map<String, String> response = new HashMap<>();
        response.put("decodedPassword", decodedPassword);
        return response;
    }
}
