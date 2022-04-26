package ma.emsi.patientsmvc.security.service;


import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.security.entities.AppRole;
import ma.emsi.patientsmvc.security.entities.AppUser;
import ma.emsi.patientsmvc.security.repositories.AppRoleRepository;
import ma.emsi.patientsmvc.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional

public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    // private com.example.patinetsmvc.security.entities.AppUser AppUser;


    @Override
    public AppUser saveUser(String username, String password, String rePassword) {
        if (!password.equals(rePassword))throw new RuntimeException("Password NOT match");
        String hashedPWD=passwordEncoder.encode(password);
        AppUser appUser=new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPWD);
        appUser.setActive(true);
        AppUser savedAppUser=appUserRepository.save(appUser);

        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if(appRole!=null) throw new RuntimeException("Role "+roleName+"Already exist");
        appRole=new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole=appRoleRepository.save(appRole);

        return savedAppRole;
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser==null) throw new RuntimeException("User not found ");

        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("Role not found ");
        appUser.getAppRoles().add(appRole);

    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser==null) throw new RuntimeException("User not found ");

        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("Role not found ");
        appUser.getAppRoles().remove(appRole);

    }



    @Override
    public AppUser loadUserByUserName(String username) {

        return appUserRepository.findByUsername(username);
    }

    @Override
    public void saveNewUser(String saad, String s, String s1) {

    }
}
