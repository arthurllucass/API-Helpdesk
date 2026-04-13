package com.arthurllucass.projeto_helpdesk.controller;

import com.arthurllucass.projeto_helpdesk.dto.CreateUserDTO;
import com.arthurllucass.projeto_helpdesk.dto.UserDTO;
import com.arthurllucass.projeto_helpdesk.model.entites.User;
import com.arthurllucass.projeto_helpdesk.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> list = userService.findAll();
        List<UserDTO> listDTO = list.stream().map(u -> new UserDTO(u)).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<UserDTO> findByName(@PathVariable String name) {
        User user = userService.findByName(name);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @GetMapping(value = "/phone/{phone}")
    public ResponseEntity<UserDTO> findByPhone(@PathVariable String phone) {
        User user = userService.findByPhone(phone);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User user = userService.fromCreateUserDTO(createUserDTO);
        user = userService.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CreateUserDTO createUserDTO, @PathVariable Long id) {
        User user = userService.fromCreateUserDTO(createUserDTO);
        user.setId(id);
        userService.update(user);
        return ResponseEntity.noContent().build();
    }
}
