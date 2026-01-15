package dao;

import entity.Loan;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

/**
 * LoanDao - Ödünç alma CRUD işlemleri için DAO sınıfı
 */
public class LoanDao {

    /**
     * Yeni ödünç kaydı kaydet
     */
    public void save(Loan loan) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Ödünç kaydı güncelle
     */
    public void update(Loan loan) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Ödünç kaydı sil
     */
    public void delete(Loan loan) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * ID ile ödünç kaydı getir
     */
    public Loan getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Loan.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Tüm ödünç kayıtlarını getir
     */
    @SuppressWarnings("unchecked")
    public List<Loan> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Loan").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Kitap ID'sine göre aktif ödünç kaydını getir
     */
    public Loan getActiveByBookId(Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Loan) session.createQuery("FROM Loan l WHERE l.book.id = :bookId AND l.returnDate IS NULL")
                    .setParameter("bookId", bookId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
