# Getting Started

### Reference Documentation

n11 Üzerinden Aşağıdaki Senaryo Adımları Uygulandı.

1. www.n11.com/magazalar sayfası açılır.
2. “S” harfi ile başlayan mağazalar filtrelenir
3. Rastgele bir mağazaya tıklanır

1. Arama motorunda “iPhone” kelimesi aratılır
2. Gelen sonuçlardan ilk sayfadaki birinci ve sonuncu sepete eklenir

1. Arama motorunda “telefon” kelimesi aratılır
2. Ürün listeleme (sonuç) ekranında filtrelerden ikinci marka seçilir
3. Gelen sonuçlar "yorum sayısına" göre sıralanır
4. Gelen ürünlerden "ücretsiz kargo" olanlar listelenir
5. n11 Akış Otomasyonu İçin Aşağıdaki Alanlar Kullanıldı.

java 17
İntellij IDEA Ultimate 2025
Spring Boot
Maven
Selenium
Cucumber
JSoup
JavaScript

Proje nin ilk kurulumunda cucumber, selenium, jsoup direk gelmeyebilir bu paketler daha sonra manuel eklenerek entegrasyon yapılabilir.
Google Driver kullanıldı ama başka driver lara geçiş kolaylıkla yapılabilir.
Linux bir makinede Cache veya No Enough Space hatası varsa bilgisayarı restart etmek ve açınca sudo apt autoremove ideal bir komut olabilir, işletim sistemi bir yerden sonra fazla yüklenmiş olabilir.
Raporlama için proje dizininde target altında cucumber-reports altında htmlReport.html ve jsonReport.json olarak ekli durumdadır.
Bu dosyaları Web sayfası veya uygulama ile açmak için sağ tıklayıp OpenIn denilerek ilişkili tarayıcı veya uygulama ile açılabilir.
Parse TimeOut hatası için hata alınan yerin timeOut ını arttırmak çözüm olabilir.

***Çalıştırma Dosyası(runner/N11TestRunner)
    Cucumber alanları burada ekli
    Senaryolar için features path burada belirtilir
    Senaryo adımları için path burada belirtilir
    Plugin ler ile html ve json olarak rapor çıktıları alınabilir

*** features/n11_features_secenario.feature
    Senaryolar burada ekli
    Açıklama ve işlemleri StepDefinitons altından yapılmakta.

*** N11TestDriver
    Driver Setup Ve Kapatma Eklendi.
    Ayrıca PopUp Kapama Gereksinimi İçin Metod Eklendi.

*** stepDefinitions/N11StepDefinitions
    Senaryo detayları ve genel işlemler burada.

Pages kısmı POM mimarisi ile sayfalar oluşturmak için kullanılabilir ama burada yapıda az nesne olduğu ve yönetimi yapılabildiği için gereksinim duyulmadı gerekirse nesne tipi verilerek (By) ekleme ve entegrasyon yapılabilir.


