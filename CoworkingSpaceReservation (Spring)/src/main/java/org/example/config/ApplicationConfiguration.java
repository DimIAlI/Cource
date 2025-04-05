package org.example.config;

import jakarta.persistence.EntityManager;
import org.example.entity.BaseEntity;
import org.example.entity.account.AdminEntity;
import org.example.entity.account.CustomerEntity;
import org.example.entity.account.UserEntity;
import org.example.entity.space.ReservationEntity;
import org.example.entity.space.SpaceTypeEntity;
import org.example.entity.space.WorkspaceEntity;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:application.properties")
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

        configuration.configure("hibernate.cfg.xml");

        return configuration.buildSessionFactory();
    }

    @Bean
    public EntityManager manager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }
}
