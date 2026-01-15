package app;

import dao.BookDao;
import dao.StudentDao;
import dao.LoanDao;
import entity.Book;
import entity.Book.BookStatus;
import entity.Student;
import entity.Loan;
import util.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * SmartLibrary2 - Ana Uygulama Sınıfı
 * Konsol tabanlı kütüphane yönetim sistemi
 * 
 * @author Ahmet Emre Yıldız
 * @studentNo 20240108042
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BookDao bookDao = new BookDao();
    private static final StudentDao studentDao = new StudentDao();
    private static final LoanDao loanDao = new LoanDao();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   SmartLibrary2 - Akıllı Kütüphane Sistemi");
        System.out.println("   Hibernate ORM + SQLite");
        System.out.println("===========================================");
        System.out.println();

        boolean running = true;

        while (running) {
            showMenu();
            int choice = getIntInput("Seçiminiz: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    listStudents();
                    break;
                case 5:
                    borrowBook();
                    break;
                case 6:
                    listLoans();
                    break;
                case 7:
                    returnBook();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nProgram sonlandırılıyor...");
                    HibernateUtil.shutdown();
                    break;
                default:
                    System.out.println("\n[HATA] Geçersiz seçim! Lütfen 0-7 arasında bir değer girin.\n");
            }
        }

        scanner.close();
    }

    /**
     * Ana menüyü gösterir
     */
    private static void showMenu() {
        System.out.println("-------------------------------------------");
        System.out.println("                ANA MENÜ");
        System.out.println("-------------------------------------------");
        System.out.println("1 - Kitap Ekle");
        System.out.println("2 - Kitapları Listele");
        System.out.println("3 - Öğrenci Ekle");
        System.out.println("4 - Öğrencileri Listele");
        System.out.println("5 - Kitap Ödünç Ver");
        System.out.println("6 - Ödünç Listesini Görüntüle");
        System.out.println("7 - Kitap Geri Teslim Al");
        System.out.println("0 - Çıkış");
        System.out.println("-------------------------------------------");
    }

    /**
     * 1 - Kitap Ekle
     */
    private static void addBook() {
        System.out.println("\n=== KİTAP EKLE ===\n");

        System.out.print("Kitap Başlığı: ");
        String title = scanner.nextLine().trim();

        System.out.print("Yazar: ");
        String author = scanner.nextLine().trim();

        int year = getIntInput("Yayın Yılı: ");

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("\n[HATA] Başlık ve yazar boş olamaz!\n");
            return;
        }

        Book book = new Book(title, author, year);
        bookDao.save(book);

        System.out.println("\n[BAŞARILI] Kitap başarıyla eklendi!");
        System.out.println("Kitap ID: " + book.getId());
        System.out.println("Durum: " + book.getStatus());
        System.out.println();
    }

    /**
     * 2 - Kitapları Listele
     */
    private static void listBooks() {
        System.out.println("\n=== KİTAP LİSTESİ ===\n");

        List<Book> books = bookDao.getAll();

        if (books == null || books.isEmpty()) {
            System.out.println("Henüz kayıtlı kitap bulunmamaktadır.\n");
            return;
        }

        System.out.println(String.format("%-5s %-30s %-20s %-6s %-10s",
                "ID", "BAŞLIK", "YAZAR", "YIL", "DURUM"));
        System.out.println("------------------------------------------------------------------------");

        for (Book book : books) {
            String status = book.getStatus() == BookStatus.AVAILABLE ? "Müsait" : "Ödünç";
            System.out.println(String.format("%-5d %-30s %-20s %-6d %-10s",
                    book.getId(),
                    truncate(book.getTitle(), 28),
                    truncate(book.getAuthor(), 18),
                    book.getYear(),
                    status));
        }

        System.out.println("\nToplam: " + books.size() + " kitap\n");
    }

    /**
     * 3 - Öğrenci Ekle
     */
    private static void addStudent() {
        System.out.println("\n=== ÖĞRENCİ EKLE ===\n");

        System.out.print("Öğrenci Adı: ");
        String name = scanner.nextLine().trim();

        System.out.print("Bölüm: ");
        String department = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("\n[HATA] Öğrenci adı boş olamaz!\n");
            return;
        }

        Student student = new Student(name, department);
        studentDao.save(student);

        System.out.println("\n[BAŞARILI] Öğrenci başarıyla eklendi!");
        System.out.println("Öğrenci ID: " + student.getId());
        System.out.println();
    }

    /**
     * 4 - Öğrencileri Listele
     */
    private static void listStudents() {
        System.out.println("\n=== ÖĞRENCİ LİSTESİ ===\n");

        List<Student> students = studentDao.getAll();

        if (students == null || students.isEmpty()) {
            System.out.println("Henüz kayıtlı öğrenci bulunmamaktadır.\n");
            return;
        }

        System.out.println(String.format("%-5s %-30s %-30s", "ID", "AD SOYAD", "BÖLÜM"));
        System.out.println("-----------------------------------------------------------------");

        for (Student student : students) {
            System.out.println(String.format("%-5d %-30s %-30s",
                    student.getId(),
                    truncate(student.getName(), 28),
                    truncate(student.getDepartment(), 28)));
        }

        System.out.println("\nToplam: " + students.size() + " öğrenci\n");
    }

    /**
     * 5 - Kitap Ödünç Ver
     */
    private static void borrowBook() {
        System.out.println("\n=== KİTAP ÖDÜNÇ VER ===\n");

        // Önce mevcut kitapları ve öğrencileri göster
        listBooks();
        listStudents();

        Long studentId = getLongInput("Öğrenci ID: ");
        Long bookId = getLongInput("Kitap ID: ");

        // Öğrenciyi kontrol et
        Student student = studentDao.getById(studentId);
        if (student == null) {
            System.out.println("\n[HATA] Belirtilen ID'ye sahip öğrenci bulunamadı!\n");
            return;
        }

        // Kitabı kontrol et
        Book book = bookDao.getById(bookId);
        if (book == null) {
            System.out.println("\n[HATA] Belirtilen ID'ye sahip kitap bulunamadı!\n");
            return;
        }

        // Kitabın müsait olup olmadığını kontrol et
        if (book.getStatus() == BookStatus.BORROWED) {
            System.out.println("\n[HATA] Bu kitap zaten ödünç verilmiş durumda!\n");
            return;
        }

        // Ödünç tarihi
        System.out.print("Ödünç Tarihi (gg/aa/yyyy, boş bırakın: bugün): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate borrowDate;

        if (dateStr.isEmpty()) {
            borrowDate = LocalDate.now();
        } else {
            try {
                borrowDate = LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("\n[HATA] Geçersiz tarih formatı! gg/aa/yyyy kullanın.\n");
                return;
            }
        }

        // Ödünç kaydı oluştur
        Loan loan = new Loan(borrowDate, book, student);
        loanDao.save(loan);

        // Kitap durumunu güncelle
        book.setStatus(BookStatus.BORROWED);
        bookDao.update(book);

        System.out.println("\n[BAŞARILI] Kitap başarıyla ödünç verildi!");
        System.out.println("Öğrenci: " + student.getName());
        System.out.println("Kitap: " + book.getTitle());
        System.out.println("Ödünç Tarihi: " + borrowDate.format(dateFormatter));
        System.out.println();
    }

    /**
     * 6 - Ödünç Listesini Görüntüle
     */
    private static void listLoans() {
        System.out.println("\n=== ÖDÜNÇ LİSTESİ ===\n");

        List<Loan> loans = loanDao.getAll();

        if (loans == null || loans.isEmpty()) {
            System.out.println("Henüz ödünç kaydı bulunmamaktadır.\n");
            return;
        }

        System.out.println(String.format("%-5s %-20s %-25s %-12s %-12s %-10s",
                "ID", "ÖĞRENCİ", "KİTAP", "ALIŞ TAR.", "İADE TAR.", "DURUM"));
        System.out.println("-----------------------------------------------------------------------------------------");

        for (Loan loan : loans) {
            String borrowDateStr = loan.getBorrowDate().format(dateFormatter);
            String returnDateStr = loan.getReturnDate() != null ? loan.getReturnDate().format(dateFormatter) : "-";
            String status = loan.getReturnDate() != null ? "İade Edildi" : "Devam Ediyor";

            System.out.println(String.format("%-5d %-20s %-25s %-12s %-12s %-10s",
                    loan.getId(),
                    truncate(loan.getStudent().getName(), 18),
                    truncate(loan.getBook().getTitle(), 23),
                    borrowDateStr,
                    returnDateStr,
                    status));
        }

        System.out.println("\nToplam: " + loans.size() + " kayıt\n");
    }

    /**
     * 7 - Kitap Geri Teslim Al
     */
    private static void returnBook() {
        System.out.println("\n=== KİTAP GERİ TESLİM AL ===\n");

        // Aktif ödünçleri göster
        List<Loan> loans = loanDao.getAll();
        if (loans != null) {
            boolean hasActiveLoans = false;
            System.out.println("Aktif Ödünçler:");
            System.out.println(String.format("%-5s %-20s %-25s %-12s",
                    "ID", "ÖĞRENCİ", "KİTAP", "ALIŞ TAR."));
            System.out.println("--------------------------------------------------------------");

            for (Loan loan : loans) {
                if (loan.getReturnDate() == null) {
                    hasActiveLoans = true;
                    System.out.println(String.format("%-5d %-20s %-25s %-12s",
                            loan.getId(),
                            truncate(loan.getStudent().getName(), 18),
                            truncate(loan.getBook().getTitle(), 23),
                            loan.getBorrowDate().format(dateFormatter)));
                }
            }

            if (!hasActiveLoans) {
                System.out.println("Aktif ödünç kaydı bulunmamaktadır.\n");
                return;
            }
            System.out.println();
        }

        Long loanId = getLongInput("Ödünç ID: ");

        Loan loan = loanDao.getById(loanId);
        if (loan == null) {
            System.out.println("\n[HATA] Belirtilen ID'ye sahip ödünç kaydı bulunamadı!\n");
            return;
        }

        if (loan.getReturnDate() != null) {
            System.out.println("\n[HATA] Bu kitap zaten iade edilmiş!\n");
            return;
        }

        // İade tarihi
        System.out.print("İade Tarihi (gg/aa/yyyy, boş bırakın: bugün): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate returnDate;

        if (dateStr.isEmpty()) {
            returnDate = LocalDate.now();
        } else {
            try {
                returnDate = LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("\n[HATA] Geçersiz tarih formatı! gg/aa/yyyy kullanın.\n");
                return;
            }
        }

        // İade tarihini güncelle
        loan.setReturnDate(returnDate);
        loanDao.update(loan);

        // Kitap durumunu AVAILABLE yap
        Book book = loan.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookDao.update(book);

        System.out.println("\n[BAŞARILI] Kitap başarıyla iade alındı!");
        System.out.println("Kitap: " + book.getTitle());
        System.out.println("Öğrenci: " + loan.getStudent().getName());
        System.out.println("İade Tarihi: " + returnDate.format(dateFormatter));
        System.out.println();
    }

    // =============== YARDIMCI METODLAR ===============

    /**
     * Tam sayı girişi al
     */
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Long tipinde sayı girişi al
     */
    private static Long getLongInput(String prompt) {
        System.out.print(prompt);
        try {
            String input = scanner.nextLine().trim();
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    /**
     * Metni belirli uzunlukta kes
     */
    private static String truncate(String str, int length) {
        if (str == null)
            return "";
        if (str.length() <= length)
            return str;
        return str.substring(0, length - 2) + "..";
    }
}
