package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Student Entity - Öğrenci bilgilerini temsil eder
 * @Entity annotation ile Hibernate entity olarak işaretlendi
 */
@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "department")
    private String department;
    
    // Bir öğrencinin birden fazla ödünç alma kaydı olabilir
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();
    
    // Default constructor (Hibernate için gerekli)
    public Student() {}
    
    // Parametreli constructor
    public Student(String name, String department) {
        this.name = name;
        this.department = department;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public List<Loan> getLoans() {
        return loans;
    }
    
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
    
    // Helper method to add loan
    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setStudent(this);
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
