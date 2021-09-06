package com.example.haulmont;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.entities.Credit;
import com.example.haulmont.backend.entities.LoanOffer;
import com.example.haulmont.backend.repo.CreditRepo;
import com.example.haulmont.backend.repo.LoanOfferRepo;
import com.example.haulmont.backend.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

@SpringBootApplication
public class HaulmotApplication {
    static Connection con;

    private static void init(){
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("Good");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }

        try {
            con= DriverManager.getConnection("jdbc:hsqldb:mydb","SA","");
            System.out.println("Good");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        init();
        SpringApplication.run(HaulmotApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ClientService clientService, CreditRepo creditRepo, LoanOfferRepo loanOfferRepo) {
        return args -> {



            String name = "Dasha";
            String phone = "89036569606";
            String email = "dudkodasha@gmail.com";
            String passport = "2020442899";
            Client dasha = new Client(name,
                    phone,
                    email,
                    passport);
            clientService.add(dasha);

            String named = "dima";
            String phoned = "89036569606";
            String emaild = "dimamikhno@gmail.com";
            String passportd = "1234566556";
            Client dima = new Client(named,
                    phoned,
                    emaild,
                    passportd);
            clientService.add(dima);

            System.out.println(clientService.findByPassport_number(passportd));

            System.out.println(clientService.allClients());
            System.out.println(clientService.findByPassport_number("1234566556"));




            String credit_limit = "50000";
            double interest_rate = 22;
            Credit credit = new Credit(credit_limit, interest_rate);
            creditRepo.save(credit);

            System.out.println(creditRepo.findAll());



            LocalDate currentDateTime = LocalDate.now();
            System.out.println(currentDateTime);
            LoanOffer loan_offer = new LoanOffer(dima, credit, credit_limit, 12, currentDateTime);
            loanOfferRepo.save(loan_offer);

            System.out.println(loanOfferRepo.findAll().toString());


        };

    }
}
