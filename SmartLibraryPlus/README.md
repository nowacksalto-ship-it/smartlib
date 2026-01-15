# SmartLibrary2 - Hibernate ORM Tabanlı Akıllı Kütüphane Sistemi

## Proje Bilgileri

- **Ad-Soyad:** Ahmet Emre Yıldız
- **Öğrenci No:** 20240108042
- **Ders:** Nesneye Yönelik Programlama

## Proje Açıklaması

SmartLibrary2, Java, Hibernate ORM ve SQLite kullanılarak geliştirilmiş konsol tabanlı bir kütüphane yönetim sistemidir. Bu sistem, kitap ekleme, öğrenci kayıt, ödünç verme ve iade işlemlerini yönetir.

## Teknolojiler

- **Java 11+**
- **Hibernate ORM 5.6**
- **SQLite** (Veritabanı)
- **Maven** (Bağımlılık Yönetimi)

## Proje Yapısı

```
SmartLibraryPlus/
├── src/
│   ├── entity/
│   │   ├── Book.java        # Kitap entity (@OneToOne)
│   │   ├── Student.java     # Öğrenci entity (@OneToMany)
│   │   └── Loan.java        # Ödünç entity (@ManyToOne, @OneToOne)
│   ├── dao/
│   │   ├── BookDao.java     # Kitap CRUD işlemleri
│   │   ├── StudentDao.java  # Öğrenci CRUD işlemleri
│   │   └── LoanDao.java     # Ödünç CRUD işlemleri
│   ├── util/
│   │   └── HibernateUtil.java  # SessionFactory yönetimi
│   └── app/
│       └── Main.java        # Ana uygulama (konsol menü)
├── hibernate.cfg.xml        # Hibernate konfigürasyonu
├── pom.xml                  # Maven bağımlılıkları
└── README.md
```

## Entity İlişkileri

| İlişki | Tür |
|--------|-----|
| Student → Loan | @OneToMany |
| Loan → Student | @ManyToOne |
| Loan → Book | @OneToOne |
| Book → Loan | @OneToOne (mappedBy) |

## Kurulum ve Çalıştırma

### Gereksinimler
- Java 11 veya üzeri
- Maven 3.6 veya üzeri

### Derleme
```bash
cd SmartLibraryPlus
mvn clean compile
```

### Çalıştırma
```bash
mvn exec:java -Dexec.mainClass="app.Main"
```

## Menü İşlevleri

| No | İşlev | Açıklama |
|----|-------|----------|
| 1 | Kitap Ekle | Yeni kitap kaydı oluşturur (status=AVAILABLE) |
| 2 | Kitapları Listele | Tüm kitapları durum bilgisiyle listeler |
| 3 | Öğrenci Ekle | Yeni öğrenci kaydı oluşturur |
| 4 | Öğrencileri Listele | Tüm öğrencileri listeler |
| 5 | Kitap Ödünç Ver | Müsait kitabı öğrenciye ödünç verir |
| 6 | Ödünç Listesi | Tüm ödünç kayıtlarını görüntüler |
| 7 | Kitap Geri Al | Ödünç alınan kitabı iade alır |
| 0 | Çıkış | Programı sonlandırır |

## DAO Metodları

Her DAO sınıfı aşağıdaki CRUD metodlarını içerir:

- `save(entity)` - Yeni kayıt ekle
- `update(entity)` - Kayıt güncelle
- `delete(entity)` - Kayıt sil
- `getById(id)` - ID ile kayıt getir
- `getAll()` - Tüm kayıtları listele

## Veritabanı

- SQLite kullanılmaktadır
- Tablolar Hibernate tarafından otomatik oluşturulur (`hbm2ddl.auto=update`)
- Veritabanı dosyası: `smartlibrary.db`

## Notlar

- JDBC/SQL doğrudan yazılmamıştır, tüm veritabanı işlemleri Hibernate ORM üzerinden yapılmaktadır
- Annotation tabanlı mapping kullanılmıştır (@Entity, @Table, @Column, vb.)
- Her entity için ayrı DAO sınıfı oluşturulmuştur
