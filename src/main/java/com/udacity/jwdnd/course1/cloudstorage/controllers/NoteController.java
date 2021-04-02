package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public NoteController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping("/note-upload")
    public String uploadNote(@RequestParam("noteId") Integer noteId,
                             @RequestParam("noteTitle") String noteTitle,
                             @RequestParam("noteDescription") String noteDescription,
                             Authentication auth,
                             Model model) throws IOException {

        User currentUser = this.userService.getUser(auth.getName());
        // Add a Note
        if (noteId == null) {
            try {
                this.noteService.addNote(new Note(noteTitle, noteDescription, currentUser.getUserId()));
                model.addAttribute("noteSuccess", "Note successfully uploaded.");
            } catch (Exception e) {
                model.addAttribute("noteError", "Error adding Note");
            }
            // Update a Note
        } else {
            try {
                this.noteService.updateNote(noteId, noteTitle, noteDescription, currentUser.getUserId());
                model.addAttribute("noteSuccess", "Note successfully updated.");
            } catch (Exception err) {
                model.addAttribute("noteError", "Error updating Note");
            }
        }

        // Refresh data
        model.addAttribute("files", this.fileService.getFiles(currentUser.getUserId()));
        model.addAttribute("notes", this.noteService.getNotes(currentUser.getUserId()));
        model.addAttribute("credentials", this.credentialService.listCredentials(currentUser.getUserId()));
        return "home";
    }

    @RequestMapping("/note-delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId,
                             Authentication auth,
                             Model model) {

        try {
            this.noteService.deleteNote(noteId);
            model.addAttribute("noteSuccess", "Successfully deleted Note");
        } catch (Exception err) {
            model.addAttribute("noteError", "Error deleting Note");
        }

        // Refresh data
        User currentUser = this.userService.getUser(auth.getName());
        model.addAttribute("files", this.fileService.getFiles(currentUser.getUserId()));
        model.addAttribute("notes", this.noteService.getNotes(currentUser.getUserId()));
        model.addAttribute("credentials", this.credentialService.listCredentials(currentUser.getUserId()));
        return "home";
    }
}

