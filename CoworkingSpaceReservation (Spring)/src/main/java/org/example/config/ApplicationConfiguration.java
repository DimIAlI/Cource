package org.example.config;

import org.example.model.entity.BaseEntity;
import org.example.model.entity.account.AdminEntity;
import org.example.model.entity.account.CustomerEntity;
import org.example.model.entity.account.UserEntity;
import org.example.model.entity.space.ReservationEntity;
import org.example.model.entity.space.SpaceTypeEntity;
import org.example.model.entity.space.WorkspaceEntity;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManager;
import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "org.example")
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionManager() {

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration.addAnnotatedClass(BaseEntity.class);
        configuration.addAnnotatedClass(ReservationEntity.class);
        configuration.addAnnotatedClass(SpaceTypeEntity.class);
        configuration.addAnnotatedClass(WorkspaceEntity.class);
        configuration.addAnnotatedClass(AdminEntity.class);
        configuration.addAnnotatedClass(CustomerEntity.class);
        configuration.addAnnotatedClass(UserEntity.class);

        configuration.configure();

        return configuration.buildSessionFactory();
    }

    @Bean
    public EntityManager manager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
