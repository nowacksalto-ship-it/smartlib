package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtil - Hibernate SessionFactory yönetimi için singleton sınıf
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // hibernate.cfg.xml dosyasından konfigürasyonu yükle
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory oluşturma hatası: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // SessionFactory'yi kapat
        getSessionFactory().close();
    }
}
