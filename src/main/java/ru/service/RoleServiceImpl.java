package ru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.repositories.RoleRepository;
import ru.exception.NoSuchRoleException;
import ru.models.Role;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) throws NoSuchRoleException {
        return roleRepository.findByName(name).orElseThrow(() -> new NoSuchRoleException("Role "+ name+ " not found"));
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) throws NoSuchRoleException {
        return roleRepository.findById(id).orElseThrow(() -> new NoSuchRoleException("Role with id "+ id +" not found"));
    }
    @Override
    @Transactional
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

}
