package entity;

import javax.persistence.*;

/**
 * Book Entity - Kitap bilgilerini temsil eder
 * @Entity annotation ile Hibernate entity olarak işaretlendi
 */
@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "author", nullable = false)
    private String author;
    
    @Column(name = "year")
    private int year;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;
    
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Loan loan;
    
    // Enum for book status
    public enum BookStatus {
        AVAILABLE,
        BORROWED
    }
    
    // Default constructor (Hibernate için gerekli)
    public Book() {}
    
    // Parametreli constructor
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = BookStatus.AVAILABLE;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public BookStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookStatus status) {
        this.status = status;
    }
    
    public Loan getLoan() {
        return loan;
    }
    
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", status=" + status +
                '}';
    }
}
