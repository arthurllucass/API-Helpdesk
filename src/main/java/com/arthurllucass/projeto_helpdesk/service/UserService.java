package com.arthurllucass.projeto_helpdesk.service;

import com.arthurllucass.projeto_helpdesk.dto.CreateUserDTO;
import com.arthurllucass.projeto_helpdesk.exceptions.InvalidDataException;
import com.arthurllucass.projeto_helpdesk.exceptions.ObjectNotFoundException;
import com.arthurllucass.projeto_helpdesk.exceptions.UserAlreadyExistsException;
import com.arthurllucass.projeto_helpdesk.model.entites.User;
import com.arthurllucass.projeto_helpdesk.model.enums.UserRole;
import com.arthurllucass.projeto_helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        List<User> listUsers = userRepository.findAllByOrderByIdAsc();
        return listUsers;
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Id não encontrado"));
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByNameIgnoreCase(name);
        return user.orElseThrow(() -> new ObjectNotFoundException("O nome " + name + " não foi encontrado!"));
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        return  user.orElseThrow(() -> new ObjectNotFoundException("O endereço de e-mail " + email + " não foi encontrado!"));
    }

    public User findByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        return  user.orElseThrow(() -> new ObjectNotFoundException("O número de telefone " + phone + " não foi encontrado!"));
    }

    public User insert(User user) {
        checkUserExists(user);
        if (user.getRole() == null) user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public User update(User user) {
        checkUserExistsUpdate(user);
        if (user.getRole() == null) throw new InvalidDataException("A função do usuário não pode ser nula ou vazia!");
        User newUser = findById(user.getId());
        updateData(user, newUser);
        return userRepository.save(newUser);
    }

    public User updateData (User user, User newUser) {
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        return newUser;
    }

    public User fromCreateUserDTO(CreateUserDTO createUserDTO) {
        return new User(null, createUserDTO.name(), createUserDTO.email(), createUserDTO.phone(),
                createUserDTO.password(), createUserDTO.role(), null);
    }

    private void checkUserExists(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) throw new UserAlreadyExistsException("Email já cadastrado!");
        if (userRepository.existsByPhone(user.getPhone())) throw new UserAlreadyExistsException("Telefone já cadastrado!");
    }

    private void checkUserExistsUpdate(User user) {
        Optional<User> emailUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        Optional<User> phoneUser = userRepository.findByPhone(user.getPhone());

        if (emailUser.isPresent() && !emailUser.get().getId().equals(user.getId())) throw new UserAlreadyExistsException("Email já cadastrado!");
        if (phoneUser.isPresent() && !phoneUser.get().getId().equals(user.getId())) throw  new UserAlreadyExistsException("Telefone já cadastrado!");
    }
}

