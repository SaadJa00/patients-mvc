package ma.emsi.patientsmvc;

import  ma.emsi.patientsmvc.entities.Patient;
import ma.emsi.patientsmvc.repositories.PatientRepository;
import ma.emsi.patientsmvc.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
       return args -> {
           patientRepository.save(new Patient(null,"Saad",new Date(),false,112));
           patientRepository.save(new Patient(null,"Walid",new Date(),true,117));
           patientRepository.save(new Patient(null,"Omar",new Date(),false,123));

           patientRepository.findAll().forEach(patient -> {
               System.out.println(patient.getNom());
           });
       };
    }
    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("Saad","123","123");
            securityService.saveNewUser("Walid","123","123");
            securityService.saveNewUser("Omar","123","123");


            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");

            securityService.addRoleToUser("Saad","USER");
            securityService.addRoleToUser("Saad","ADMIN");
            securityService.addRoleToUser("Walid","USER");
            securityService.addRoleToUser("Walid","USER");

        };
    }
}
