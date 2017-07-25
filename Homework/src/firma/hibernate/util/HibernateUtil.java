package firma.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by rstet on 19.07.2017.
 */
public class HibernateUtil {
    private static final SessionFactory factory = build();
    private static StandardServiceRegistry registry;

    private static SessionFactory build(){
        registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static SessionFactory getFactory(){
        return factory;
    }
}
