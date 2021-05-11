# Bayrak Quiz App

## İçerik

1. [Kullanılan Teknolojiler](https://github.com/mehmetaydintr/Bayrak_Quiz_App/blob/main/README.md#kullan%C4%B1lan-teknolojiler)
2. [Proje Tanımı](https://github.com/mehmetaydintr/Bayrak_Quiz_App/blob/main/README.md#proje-tan%C4%B1m%C4%B1)
3. [Örnek Ekran Görüntüleri](https://github.com/mehmetaydintr/Bayrak_Quiz_App/blob/main/README.md#%C3%B6rnek-ekran-g%C3%B6r%C3%BCnt%C3%BCleri)


## Kullanılan Teknolojiler

  + Android Studio

![Image of Android Studio](https://www.xda-developers.com/files/2017/04/android-studio-logo.png)

  + Java

![Image of Java](https://yazilimamelesi.files.wordpress.com/2013/03/java_logo.jpg)

  + SQLite

![Image of SQLite](https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/SQLite370.svg/1280px-SQLite370.svg.png)


## Proje Tanımı

Ülkelerin bayrakları üzerinden 5 sorudan oluşan **Android** tabanlı geliştirilmiş Bayrak Quiz App bir örnek projedir.

**_SQLite_** kurulumu ve kullanımı oldukça kolay olan bir veritabanı kütüphanesidir. Kullanmak için kurulum yapmanız gerekmez. Diğer birçok veritabanı yönetim sisteminin aksine, SQLite bir istemci-sunucu veritabanı motoru değildir. Aksine, son programın içine yerleştirilmiştir. Veritabanına erişmek için sunucuya istek göndermek ve sonuç almak için ara işlem iletişimi kurmak gerekmez. Bu program doğrudan diskteki veritabanı dosyalarını okur ve yazar. Web tarayıcıları gibi uygulama yazılımlarında yerel / istemci depolaması için yerleşik bir veritabanı yazılımı olarak popüler bir seçimdir. Günümüzde birçok yaygın tarayıcı, işletim sistemi ve gömülü sistem (cep telefonları gibi) tarafından kullanıldığı için tartışmasız en yaygın kullanılan veritabanı motorudur.

+ İlk olarak tasarımımızla başlayalım. 3 adet ekranımız olacaktır.

![1](https://user-images.githubusercontent.com/37263322/117825352-efdca800-b277-11eb-865e-d343cd6d3b58.png "Giriş Ekranı")
![2](https://user-images.githubusercontent.com/37263322/117825356-f0753e80-b277-11eb-9f1c-7a8dcf58650c.png "Oyun Ekranı")
![3](https://user-images.githubusercontent.com/37263322/117825358-f10dd500-b277-11eb-9bba-02fda600f5c7.png "Sonuç Ekranı")


+ Daha verimli çalışmak için işlevlerine göre paketlerimizi oluşturalım

![5](https://user-images.githubusercontent.com/37263322/117826056-8610ce00-b278-11eb-8ccc-e448693d340d.png)

+ **Database** Paketinin içine veritabanını oluşturması için **SQLiteOpenHelper** sınıfından *extend* ettiğimiz **VeriTabani** sınıfını oluşturuyoruz.

```
public class VeriTabani extends SQLiteOpenHelper {
    public VeriTabani(@Nullable Context context) {
        super(context, "bayrakquiz.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS bayraklar (" +
                "bayrak_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bayrak_ad TEXT," +
                "bayrak_resim TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS bayraklar");
        onCreate(sqLiteDatabase);
    }
}
```

+ **Assets** klasörü oluşturmak için proje dizininde `app`'in üzerine sağ click yapıyoruz ve sonra `new -> Folder -> Assets Folder` seçeneğini seçiyoruz.

![6](https://user-images.githubusercontent.com/37263322/117828808-d8eb8500-b27a-11eb-91e3-4679089ee547.png)

+ Hazır olan [veritabanımızı](https://github.com/mehmetaydintr/Bayrak_Quiz_App/blob/main/BayrakQuiz/app/src/main/assets/bayrakquiz.sqlite) `assets` klasörü içine kopyalayalım.

![7](https://user-images.githubusercontent.com/37263322/117829169-2cf66980-b27b-11eb-84db-569cbc107f58.PNG)

+ Hazır Veritabanını cihazımıza kopyalamak için **[DatabaseCopyHelper](https://github.com/mehmetaydintr/Bayrak_Quiz_App/blob/main/BayrakQuiz/app/src/main/java/com/info/bayrakquiz/Database/DatabaseCopyHelper.java)** hazır sınıfımızı **Database** paketinin içine yapıştırıyoruz ve içerisindeki `DB_NAME` özelliğini kendi veritabanı ismimiz ile değiştiriyoruz. Daha sonra veritabanı kopyalama işlemini **MainActivity** içerisinde aktifleştiriyoruz.

```
private Button buttonBasla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBasla = findViewById(R.id.buttonBasla);

        veritabaniKopyala();

        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OyunActivity.class));
            }
        });
    }

    private void veritabaniKopyala(){

        DatabaseCopyHelper helper = new DatabaseCopyHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }
```

+ Nesne olarak sadece bayrak verimiz olduğu için **Objects** paketi içerisinde **Bayraklar** sınıfı oluşturalım.

```
public class Bayraklar {

  private int bayrak_id;
  private String bayrak_ad;
  private String bayrak_resim;

  public Bayraklar() {
  }

  public Bayraklar(int bayrak_id, String bayrak_ad, String bayrak_resim) {
    this.bayrak_id = bayrak_id;
    this.bayrak_ad = bayrak_ad;
    this.bayrak_resim = bayrak_resim;
  }

  public int getBayrak_id() {
    return bayrak_id;
  }

  public void setBayrak_id(int bayrak_id) {
    this.bayrak_id = bayrak_id;
  }

  public String getBayrak_ad() {
    return bayrak_ad;
  }

  public void setBayrak_ad(String bayrak_ad) {
    this.bayrak_ad = bayrak_ad;
  }

  public String getBayrak_resim() {
    return bayrak_resim;
  }

  public void setBayrak_resim(String bayrak_resim) {
    this.bayrak_resim = bayrak_resim;
  }
}
```

+ Veritabanından veri çekme işlemleri için **Database** paketinin içerisine **BayraklarDao** sınıfı oluşturalım. Bu sınıf içerisinde veri çekmek için metotlarımız olacak.
Metotlar içerisindeki `Cursor` sql kodlarımızı işlememizi sağlıyor.

```
public class BayraklarDao {

  public ArrayList<Bayraklar> rastgele5Getir(VeriTabani vt){
      ArrayList<Bayraklar> bayraklarArrayList = new ArrayList<>();

      SQLiteDatabase database = vt.getWritableDatabase();
      Cursor c = database.rawQuery("SELECT * FROM bayraklar ORDER BY RANDOM() LIMIT 5", null);

      while (c.moveToNext()){
          Bayraklar b = new Bayraklar(c.getInt(c.getColumnIndex("bayrak_id")),
          c.getString(c.getColumnIndex("bayrak_ad")),
          c.getString(c.getColumnIndex("bayrak_resim")));
          bayraklarArrayList.add(b);
      }

      return bayraklarArrayList;
  }

  public ArrayList<Bayraklar> rastgele3YanlisSecenekGetir(VeriTabani vt, int bayrak_id){
      ArrayList<Bayraklar> bayraklarArrayList = new ArrayList<>();

      SQLiteDatabase database = vt.getWritableDatabase();
      Cursor c = database.rawQuery("SELECT * FROM bayraklar WHERE bayrak_id != "+bayrak_id+" ORDER BY RANDOM() LIMIT 3", null);

      while (c.moveToNext()){
          Bayraklar b = new Bayraklar(c.getInt(c.getColumnIndex("bayrak_id")),
              c.getString(c.getColumnIndex("bayrak_ad")),
              c.getString(c.getColumnIndex("bayrak_resim")));
          bayraklarArrayList.add(b);
      }

      return bayraklarArrayList;
  }
}
```

Şimdi bu yaptığımız tüm işlemleri aktifleştirelim.

+  OyunActivity

İlk olarak gerekli tanımlamaları yapalım.

```
    private ImageView imageViewBayrak;
    private TextView textViewDogru, textViewYanlis, textViewSoru;
    private Button buttonCevap1, buttonCevap2, buttonCevap3, buttonCevap4;

    private ArrayList<Bayraklar> sorularListe;
    private ArrayList<Bayraklar> yanlisSeceneklerListe;
    private Bayraklar dogruSoru;
    private VeriTabani veriTabani;

    private int soruSayac = 0, dogruSayac = 0, yanlisSayac = 0;

    private HashSet<Bayraklar> secenekleriKaristirmaListe = new HashSet<>();
    private ArrayList<Bayraklar> secenekler = new ArrayList<>();  
```

Daha sonra `veriTabani` nesnesi oluşturup `sorularListe` array listemizi `BayraklarDao` sınıfındaki `rastgele5Getir` metodu ile dolduruyoruz ve ilk soruyu yüklemek için `soruyuYukle()` metodunu çağırıyoruz.

```
veriTabani = new VeriTabani(this);
sorularListe = new BayraklarDao().rastgele5Getir(veriTabani);
soruyuYukle();
```

`soruyuYukle()` metodu içinde öncelikle doğru soruyu alıp `dogruSoru` nesnemizin içine atıyoruz. `yanlisSeceneklerListe` içini `BayraklarDao` sınıfındaki **rastgele3YanlisSecenekGetir** metodu ile dolduruyoruz. Burada dikkat etmemiz gereken nokta doğru şıkkı tekrar bu listeye eklememektir ve bunun için gerekli sql kodları **rastgele3YanlisSecenekGetir** metodu içerisinde yazılmıştır.

Şıkların yerlerini karıştırmak için `secenekleriKaristirmaListe` isimli bir **_HashSet_** kullanıyoruz ve doğru ve yanlış seçenekleri hashset'imize ekliyoruz. HashSet verileri birbirinden bağımsız tuttuğu için verilerin indexleri değişecektir.

```
public void soruyuYukle(){
    textViewSoru.setText((soruSayac+1)+". SORU");

    dogruSoru = sorularListe.get(soruSayac);
    yanlisSeceneklerListe = new BayraklarDao().rastgele3YanlisSecenekGetir(veriTabani, dogruSoru.getBayrak_id());

    imageViewBayrak.setImageResource(getResources().getIdentifier(dogruSoru.getBayrak_resim(),"drawable",getPackageName()));

    secenekleriKaristirmaListe.clear();
    secenekleriKaristirmaListe.add(dogruSoru);
    secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(0));
    secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(1));
    secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(2));

    secenekler.clear();
    for(Bayraklar b:secenekleriKaristirmaListe){
        secenekler.add(b);
    }

    buttonCevap1.setText(secenekler.get(0).getBayrak_ad());
    buttonCevap2.setText(secenekler.get(1).getBayrak_ad());
    buttonCevap3.setText(secenekler.get(2).getBayrak_ad());
    buttonCevap4.setText(secenekler.get(3).getBayrak_ad());
}
```

En son olarak herhangi bir seçenek seçildiğinde cevabın doğruluğunu ve soru sayısını kontrol etmeliyiz. Bunun için 2 adet `dogruKontrol()` ve `sayacKontrol()` metotlarımız mevcut.

```
public void dogruKontrol(Button button){
    String buttonYazi = button.getText().toString();
    String dogruCevap = dogruSoru.getBayrak_ad();

    if (buttonYazi.equalsIgnoreCase(dogruCevap)){
        dogruSayac++;
    }else {
        yanlisSayac++;
        Snackbar.make(button, "Doğru cevap : " + dogruCevap, Snackbar.LENGTH_LONG).show();
    }

    textViewDogru.setText("Doğru : " + dogruSayac);
    textViewYanlis.setText("Yanlış : " + yanlisSayac);
}
```

```
public void sayacKontrol(){
  soruSayac++;

  if (soruSayac != 5){
      soruyuYukle();
  }else {

      Intent intent = new Intent(OyunActivity.this, SonucActivity.class);
      intent.putExtra("dogruSayac",dogruSayac);

      startActivity(intent);
      finish();
  }
}
```

+ SonucActivity

Bu ekranda sadece gelen sonuç verilerini işleyip başarı oranını bulup ekrana yazdırıyoruz.

```
int dogruSayac = getIntent().getIntExtra("dogruSayac",0);
textViewDogruYanlis.setText("Doğru : " + dogruSayac + "\nYanlış : " +(5-dogruSayac));
textViewSonuc.setText("%" + (dogruSayac*100)/5 + "BAŞARI");
```

## Örnek Ekran Görüntüleri

![21](https://user-images.githubusercontent.com/37263322/117852734-5077df00-b290-11eb-98c5-c72abf551387.png)
![22](https://user-images.githubusercontent.com/37263322/117852735-51107580-b290-11eb-8546-10ad9dd163a2.png)
![23](https://user-images.githubusercontent.com/37263322/117852741-5241a280-b290-11eb-876b-859241f8c689.png)

