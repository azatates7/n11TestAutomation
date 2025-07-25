Feature: n11 Akış Otomasyonu

  Scenario: n11 Mağaza ve Arama İşlemleri
    Given n11 anasayfasına gidilir
    When Mağazalar sayfasına gidilir
    When S harfi ile başlayan mağazalar filtrelenir
    And Rastgele bir mağazaya tıklanır
    Then İstenilen mağazanın sayfasına yönlendirildiği görülür
    When Arama motorunda iphone kelimesi aratılır
    And Gelen sonuçlardan ilk sayfadaki birinci ve sonuncu sepete eklenir
    Then Ürünlerin sepete eklendiği kontrol edilir
    And Aranan kelimenin düzeltildiği ve sonuçların ona göre geldiği görülür
    When Arama motorunda telefon kelimesi aratılır
    And Ürün listeleme ekranında ikinci marka seçilir
    And Gelen sonuçlar yorum sayısına göre sıralanır
    And Gelen ürünlerden ücretsiz kargo olanlar listelenir
    Then Sıralamanın doğru yapıldığı görülür
    And Tüm ürünlerin ücretsiz kargo özelliğinin geldiği görülür