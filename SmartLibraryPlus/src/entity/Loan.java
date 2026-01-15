package entity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Loan Entity - Ödünç alma kayıtlarını temsil eder
 * @Entity annotation ile Hibernate entity olarak işaretlendi
 */
@Entity
@Table(name = "loans")
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    // Book -> Loan : OneToOne
    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    // Student -> Loan : ManyToOne (çünkü bir öğrenci birden fazla ödünç alabilir)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    // Default constructor (Hibernate için gerekli)
    public Loan() {}
    
    // Parametreli constructor
    public Loan(LocalDate borrowDate, Book book, Student student) {
        this.borrowDate = borrowDate;
        this.book = book;
        this.student = student;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", book=" + (book != null ? book.getTitle() : "null") +
                ", student=" + (student != null ? student.getName() : "null") +
                '}';
    }
}
