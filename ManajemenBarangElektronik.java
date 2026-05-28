import java.io.File;
import java.util.Scanner;

public class ManajemenBarangElektronik {

    static Scanner sc = new Scanner(System.in);

    // struktur data
    static final int MAX = 100;

    static int[]     id       = new int[MAX];
    static String[]  nama     = new String[MAX];
    static String[]  kategori = new String[MAX];
    static int[]     stok     = new int[MAX];
    static double[]  harga    = new double[MAX];
    static boolean[] aktif    = new boolean[MAX];

    static int jumlahData = 0;
    static int nextId     = 1;

    // log soft delete
    static String[] logHapus  = new String[MAX];
    static int      jumlahLog = 0;

    // main menu
    public static void main(String[] args) {
        initDataAwal();

        int pilihan;
        do {
            System.out.println("\n|==============================================|");
            System.out.println("|     SISTEM MANAJEMEN STOK TOKO ELEKTRONIK    |");
            System.out.println("|==============================================|");
            System.out.println("|  [CRUD]                                      |");
            System.out.println("|  1. Tambah Produk Baru                       |");
            System.out.println("|  2. Tampilkan Semua Produk                   |");
            System.out.println("|  3. Edit Produk (berdasarkan ID)             |");
            System.out.println("|  4. Hapus Produk (Soft Delete)               |");
            System.out.println("|  5. Lihat Log Penghapusan                    |");
            System.out.println("|==============================================|");
            System.out.println("|  [SEARCHING]                                 |");
            System.out.println("|  6. Cari berdasarkan Nama (Linear Search)    |");
            System.out.println("|  7. Cari berdasarkan ID   (Binary Search)    |");
            System.out.println("|  8. Cari berdasarkan Kategori                |");
            System.out.println("|==============================================|");
            System.out.println("|  [SORTING]                                   |");
            System.out.println("|  9.  Urutkan ID Ascending  (Bubble Sort)     |");
            System.out.println("|  10. Urutkan Nama A-Z      (Selection Sort)  |");
            System.out.println("|  11. Urutkan Stok Terbanyak (Insertion Sort) |");
            System.out.println("|==============================================|");
            System.out.println("|  [STATISTIK]                                 |");
            System.out.println("|  12. Tampilkan Statistik Data                |");
            System.out.println("|==============================================|");
            System.out.println("|  [FILE]                                      |");
            System.out.println("|  13. Load Data dari File                     |");
            System.out.println("|  0. Keluar                                   |");
            System.out.println("|==============================================|");
            System.out.print("  Pilih menu: ");
            pilihan = sc.nextInt();
            sc.nextLine();

            switch (pilihan) {
                case 1:  tambah();              break;
                case 2:  tampilkanSemua();      break;
                case 3:  edit();                break;
                case 4:  hapus();               break;
                case 5:  lihatLog();            break;
                case 6:  cariNama();            break;
                case 7:  cariId();              break;
                case 8:  cariKategori();        break;
                case 9:  sortById();            break;
                case 10: sortByNama();          break;
                case 11: sortByStok();          break;
                case 12: tampilkanStatistik();  break;
                case 13: loadDariFile();        break;
                case 0:  System.out.println("\n  Terima kasih! Program selesai."); break;
                default: System.out.println("\n  [!] Pilihan tidak valid.");
            }
        } while (pilihan != 0);
    }

    // data awal
    static void initDataAwal() {
        String[][] sample = {
            {"Laptop ASUS VivoBook",    "Laptop",       "45",  "7500000"},
            {"Mouse Logitech M235",     "Aksesori",     "120", "250000"},
            {"Samsung Galaxy A54",      "Smartphone",   "60",  "4200000"},
            {"Keyboard Mechanical RGB", "Aksesori",     "35",  "850000"},
            {"Monitor LG 24 inch",      "Monitor",      "20",  "2300000"},
            {"Headphone Sony WH-1000",  "Audio",        "15",  "3500000"},
            {"Flash Drive SanDisk 64GB","Storage",      "200", "85000"},
            {"Laptop Lenovo IdeaPad",   "Laptop",       "30",  "6800000"},
            {"Charger USB-C 65W",       "Aksesori",     "90",  "175000"},
            {"SSD Samsung 500GB",       "Storage",      "55",  "950000"},
            {"Printer Epson L3210",     "Printer",      "25",  "2800000"},
            {"iPhone 13 128GB",         "Smartphone",   "18",  "9500000"},
            {"Router TP-Link Archer C6","Networking",   "40",  "650000"},
            {"Webcam Logitech C270",    "Aksesori",     "70",  "320000"},
            {"Hard Disk Seagate 1TB",   "Storage",      "45",  "780000"},
            {"Smart TV Samsung 43 Inch","Elektronik",   "12",  "5200000"},
            {"Tablet Xiaomi Pad 6",     "Tablet",       "28",  "4900000"},
            {"Speaker JBL Flip 6",      "Audio",        "22",  "2100000"},
            {"Kabel HDMI 2 Meter",      "Aksesori",     "150", "75000"},
            {"Canon EOS 1500D",         "Kamera",       "10",  "7200000"},
            {"Laptop HP Pavilion",      "Laptop",       "27",  "8900000"},
            {"Mouse Razer DeathAdder",  "Aksesori",     "65",  "450000"},
            {"Smartwatch Xiaomi Band 8","Wearable",     "85",  "550000"},
            {"Monitor Samsung 27 Inch", "Monitor",      "17",  "3100000"},
            {"Power Bank 20000mAh",     "Aksesori",     "95",  "300000"},
            {"AirPods Pro 2",           "Audio",        "20",  "4200000"},
            {"SSD Kingston 1TB",        "Storage",      "33",  "1450000"},
            {"Nintendo Switch OLED",    "Gaming",       "14",  "5800000"},
            {"Keyboard Logitech K120",  "Aksesori",     "110", "180000"},
            {"Drone DJI Mini 3",        "Drone",        "8",   "8900000"},
        };

        for (int i = 0; i < sample.length; i++) {
            id[jumlahData]       = nextId++;
            nama[jumlahData]     = sample[i][0];
            kategori[jumlahData] = sample[i][1];
            stok[jumlahData]     = Integer.parseInt(sample[i][2]);
            harga[jumlahData]    = Double.parseDouble(sample[i][3]);
            aktif[jumlahData]    = true;
            jumlahData++;
        }
    }

    // crud tambah
    static void tambah() {
        if (jumlahData >= MAX) {
            System.out.println("\n  [!] Kapasitas penuh!");
            return;
        }
        System.out.println("\n TAMBAH PRODUK BARU");
        System.out.print("  Nama Produk  : ");
        String nm = sc.nextLine();
        System.out.print("  Kategori     : ");
        String kat = sc.nextLine();
        System.out.print("  Stok         : ");
        int stk = sc.nextInt();
        System.out.print("  Harga (Rp)   : ");
        double hrg = sc.nextDouble();
        sc.nextLine();

        id[jumlahData]       = nextId++;
        nama[jumlahData]     = nm;
        kategori[jumlahData] = kat;
        stok[jumlahData]     = stk;
        harga[jumlahData]    = hrg;
        aktif[jumlahData]    = true;
        jumlahData++;

        System.out.println("\n  [v] Produk berhasil ditambahkan! ID: " + (nextId - 1));
    }

// crud tampilkan
    static void tampilkanSemua() {
        System.out.println("\n DAFTAR SEMUA PRODUK");
        printHeader();
        int count = 0;
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i]) {
                printRow(i);
                count++;
            }
        }
        if (count == 0) System.out.println("  (Tidak ada data produk aktif)");
        System.out.println("  Total: " + count + " produk aktif.");
    }

    // crud edit
    static void edit() {
        System.out.println("\n EDIT PRODUK");
        System.out.print("  Masukkan ID produk yang akan diedit: ");
        int targetId = sc.nextInt();
        sc.nextLine();

        int idx = cariIndexById(targetId);
        if (idx == -1 || !aktif[idx]) {
            System.out.println("\n  [!] Produk ID " + targetId + " tidak ditemukan.");
            return;
        }

        System.out.println("\n  Data saat ini:");
        printHeader();
        printRow(idx);

        System.out.println("\n  Masukkan data baru (Enter = tidak berubah):");

        System.out.print("  Nama [" + nama[idx] + "]: ");
        String nm = sc.nextLine();
        if (!nm.isEmpty()) nama[idx] = nm;

        System.out.print("  Kategori [" + kategori[idx] + "]: ");
        String kat = sc.nextLine();
        if (!kat.isEmpty()) kategori[idx] = kat;

        System.out.print("  Stok [" + stok[idx] + "]: ");
        String stokInput = sc.nextLine();
        if (!stokInput.isEmpty()) stok[idx] = Integer.parseInt(stokInput);

        System.out.print("  Harga [" + harga[idx] + "]: ");
        String hargaInput = sc.nextLine();
        if (!hargaInput.isEmpty()) harga[idx] = Double.parseDouble(hargaInput);

        System.out.println("\n  [v] Data produk ID " + targetId + " berhasil diperbarui.");
    }

    // crud hapus
    static void hapus() {
        System.out.println("\n HAPUS PRODUK (SOFT DELETE)");
        System.out.print("  Masukkan ID produk yang akan dihapus: ");
        int targetId = sc.nextInt();
        sc.nextLine();

        int idx = cariIndexById(targetId);

        if (idx == -1 || !aktif[idx]) {
            System.out.println("\n  [!] Produk ID " + targetId + " tidak ditemukan atau sudah dihapus.");
            return;
        }

        System.out.println("\n  Produk yang akan dihapus:");
        printHeader();
        printRow(idx);

        System.out.print("\n  Yakin ingin menghapus? (y/n): ");
        String konfirmasi = sc.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
            logHapus[jumlahLog++] = "[LOG] ID=" + id[idx]
                + " | Nama=" + nama[idx]
                + " | Kategori=" + kategori[idx]
                + " | Stok=" + stok[idx]
                + " | Harga=" + hargaFormatted(idx)
                + " | STATUS: DIHAPUS";
            aktif[idx] = false;
            System.out.println("\n  [v] Produk ID " + targetId + " berhasil dihapus (soft delete).");
        } else {
            System.out.println("\n  [!] Penghapusan dibatalkan.");
        }
    }

    static void lihatLog() {
        System.out.println("\n LOG PENGHAPUSAN PRODUK");
        if (jumlahLog == 0) {
            System.out.println("  (Belum ada produk yang dihapus)");
            return;
        }
        for (int i = 0; i < jumlahLog; i++) {
            System.out.println("  " + (i + 1) + ". " + logHapus[i]);
        }
    }

    // cari berdasarkan nama (linear)
    static void cariNama() {
        System.out.println("\n CARI PRODUK BERDASARKAN NAMA (Linear Search)");
        System.out.print("  Masukkan kata kunci nama: ");
        String keyword = sc.nextLine().toLowerCase();

        System.out.println("\n  Hasil pencarian untuk \"" + keyword + "\":");
        printHeader();
        int count = 0;
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i] && nama[i].toLowerCase().contains(keyword)) {
                printRow(i);
                count++;
            }
        }
        if (count == 0) System.out.println("  (Tidak ada produk yang cocok)");
        else System.out.println("  Ditemukan: " + count + " produk.");
    }

    // cari berdasarkan ID (bubble sort dan binary search)
    static void cariId() {
        System.out.println("\n CARI PRODUK BERDASARKAN ID (Binary Search)");
        System.out.print("  Masukkan ID yang dicari: ");
        int targetId = sc.nextInt();
        sc.nextLine();

        // Kumpulkan index produk yang aktif
        int[] idx = new int[jumlahData];
        int n = 0;
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i]) idx[n++] = i;
        }

        // Urutkan idx berdasarkan id (bubble sort) sebagai prasyarat binary search
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (id[idx[j]] > id[idx[j + 1]]) {
                    int tmp    = idx[j];
                    idx[j]     = idx[j + 1];
                    idx[j + 1] = tmp;
                }
            }
        }

        // Binary Search
        int kiri  = 0;
        int kanan = n - 1;
        int hasil = -1;
        while (kiri <= kanan) {
            int tengah   = (kiri + kanan) / 2;
            int idTengah = id[idx[tengah]];
            if (idTengah == targetId) {
                hasil = idx[tengah];
                break;
            } else if (idTengah < targetId) {
                kiri = tengah + 1;
            } else {
                kanan = tengah - 1;
            }
        }

        if (hasil == -1) {
            System.out.println("\n  [!] Produk dengan ID " + targetId + " tidak ditemukan.");
        } else {
            System.out.println("\n  Produk ditemukan:");
            printHeader();
            printRow(hasil);
        }
    }

    // cari berdasarkan kategori (keyword)
    static void cariKategori() {
        System.out.println("\n CARI PRODUK BERDASARKAN KATEGORI");
        System.out.print("  Masukkan nama kategori: ");
        String keyword = sc.nextLine().toLowerCase();

        System.out.println("\n  Produk dalam kategori \"" + keyword + "\":");
        printHeader();
        int count = 0;
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i] && kategori[i].toLowerCase().contains(keyword)) {
                printRow(i);
                count++;
            }
        }
        if (count == 0) System.out.println("  (Tidak ada produk dalam kategori tersebut)");
        else System.out.println("  Ditemukan: " + count + " produk.");
    }

    // sorting ID (bubble sort)
    static void sortById() {
        System.out.println("\n URUTKAN BERDASARKAN ID ASCENDING (Bubble Sort)");
        for (int i = 0; i < jumlahData - 1; i++) {
            for (int j = 0; j < jumlahData - i - 1; j++) {
                if (id[j] > id[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
        System.out.println("  [v] Data berhasil diurutkan berdasarkan ID (Ascending).");
        tampilkanSemua();
    }

    static void sortByNama() {
        System.out.println("\n URUTKAN BERDASARKAN NAMA A-Z (Selection Sort)");
        for (int i = 0; i < jumlahData - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < jumlahData; j++) {
                if (nama[j].compareToIgnoreCase(nama[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(i, minIdx);
            }
        }
        System.out.println("  [v] Data berhasil diurutkan berdasarkan Nama (A-Z).");
        tampilkanSemua();
    }

    // sort berdasarkan stok (insertion)
    static void sortByStok() {
        System.out.println("\nURUTKAN BERDASARKAN STOK TERBANYAK (Insertion Sort)");
        for (int i = 1; i < jumlahData; i++) {
            // Simpan elemen ke-i sebagai key
            int     keyId       = id[i];
            String  keyNama     = nama[i];
            String  keyKategori = kategori[i];
            int     keyStok     = stok[i];
            double  keyHarga    = harga[i];
            boolean keyAktif    = aktif[i];

            int j = i - 1;
            // Geser elemen yang stoknya lebih kecil dari key ke kanan
            while (j >= 0 && stok[j] < keyStok) {
                id[j + 1]       = id[j];
                nama[j + 1]     = nama[j];
                kategori[j + 1] = kategori[j];
                stok[j + 1]     = stok[j];
                harga[j + 1]    = harga[j];
                aktif[j + 1]    = aktif[j];
                j--;
            }
            id[j + 1]       = keyId;
            nama[j + 1]     = keyNama;
            kategori[j + 1] = keyKategori;
            stok[j + 1]     = keyStok;
            harga[j + 1]    = keyHarga;
            aktif[j + 1]    = keyAktif;
        }
        System.out.println("  [v] Data berhasil diurutkan berdasarkan Stok Terbanyak (Descending).");
        tampilkanSemua();
    }

    // statistik
    static void tampilkanStatistik() {
        System.out.println("\n STATISTIK DATA PRODUK");
        System.out.println("================================================");

        int    totalProduk       = 0;
        int    totalStok         = 0;
        int    stokTertinggi     = -1;
        int    stokTerendah      = Integer.MAX_VALUE;
        double hargaTertinggi    = -1;
        double hargaTerendah     = Double.MAX_VALUE;
        String namaStokTertinggi = "";
        String namaStokTerendah  = "";
        String namaHargaTertinggi = "";
        String namaHargaTerendah  = "";

        for (int i = 0; i < jumlahData; i++) {
            if (!aktif[i]) continue;
            totalProduk++;
            totalStok += stok[i];

            if (stok[i] > stokTertinggi) {
                stokTertinggi     = stok[i];
                namaStokTertinggi = nama[i];
            }
            if (stok[i] < stokTerendah) {
                stokTerendah     = stok[i];
                namaStokTerendah = nama[i];
            }
            if (harga[i] > hargaTertinggi) {
                hargaTertinggi     = harga[i];
                namaHargaTertinggi = nama[i];
            }
            if (harga[i] < hargaTerendah) {
                hargaTerendah     = harga[i];
                namaHargaTerendah = nama[i];
            }
        }

        if (totalProduk == 0) {
            System.out.println("  (Tidak ada data produk aktif)");
            return;
        }

        double rataStok = (double) totalStok / totalProduk;

        System.out.println("  Total Produk Aktif  : " + totalProduk);
        System.out.println("  Total Stok          : " + totalStok);
        System.out.printf( "  Rata-rata Stok      : %.2f%n", rataStok);
        System.out.println("------------------------------------------------");
        System.out.println("  Stok Terbanyak      : " + namaStokTertinggi + " (" + stokTertinggi + ")");
        System.out.println("  Stok Tersedikit     : " + namaStokTerendah  + " (" + stokTerendah  + ")");
        System.out.println("------------------------------------------------");
        System.out.println("  Harga Tertinggi     : " + namaHargaTertinggi + " (Rp" + (long) hargaTertinggi + ")");
        System.out.println("  Harga Terendah      : " + namaHargaTerendah  + " (Rp" + (long) hargaTerendah  + ")");
        System.out.println("================================================");
    }

    // load data dari file
    static void loadDariFile() {
        System.out.println("\n LOAD DATA DARI FILE");
        System.out.print("  Masukkan nama file (contoh: produk.txt): ");
        String namaFile = sc.nextLine();

        int berhasil = 0;
        int gagal    = 0;

        try {
            Scanner fileSc = new Scanner(new File(namaFile));
            while (fileSc.hasNextLine()) {
                String baris = fileSc.nextLine().trim();
                if (baris.isEmpty()) continue;

                String[] bagian = baris.split(",");
                if (bagian.length != 4) {
                    gagal++;
                    continue;
                }

                if (jumlahData >= MAX) {
                    System.out.println("  [!] Kapasitas penuh, load dihentikan.");
                    break;
                }

                id[jumlahData]       = nextId++;
                nama[jumlahData]     = bagian[0].trim();
                kategori[jumlahData] = bagian[1].trim();
                stok[jumlahData]     = Integer.parseInt(bagian[2].trim());
                harga[jumlahData]    = Double.parseDouble(bagian[3].trim());
                aktif[jumlahData]    = true;
                jumlahData++;
                berhasil++;
            }
            fileSc.close();
            System.out.println("  [v] Load selesai. Berhasil: " + berhasil + ", Gagal: " + gagal);
        } catch (Exception e) {
            System.out.println("  [!] File tidak ditemukan atau gagal dibaca.");
        }
    }

    // swap helper
    static int cariIndexById(int targetId) {
        for (int i = 0; i < jumlahData; i++) {
            if (id[i] == targetId) return i;
        }
        return -1;
    }

    static void swap(int i, int j) {
        int     tmpId       = id[i];
        String  tmpNama     = nama[i];
        String  tmpKategori = kategori[i];
        int     tmpStok     = stok[i];
        double  tmpHarga    = harga[i];
        boolean tmpAktif    = aktif[i];

        id[i]       = id[j];
        nama[i]     = nama[j];
        kategori[i] = kategori[j];
        stok[i]     = stok[j];
        harga[i]    = harga[j];
        aktif[i]    = aktif[j];

        id[j]       = tmpId;
        nama[j]     = tmpNama;
        kategori[j] = tmpKategori;
        stok[j]     = tmpStok;
        harga[j]    = tmpHarga;
        aktif[j]    = tmpAktif;
    }

    static String hargaFormatted(int i) {
        String s = Long.toString((long) harga[i]);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int k = s.length() - 1; k >= 0; k--) {
            if (count > 0 && count % 3 == 0) sb.insert(0, '.');
            sb.insert(0, s.charAt(k));
            count++;
        }
        return "Rp" + sb.toString();
    }

    static String truncate(String s, int max) {
        if (s.length() <= max) return s;
        return s.substring(0, max - 2) + "..";
    }

    static void printHeader() {
    System.out.println("--------------------------------------------------------------------------------------");
    System.out.printf("| %-4s | %-30s | %-15s | %-5s | %-15s |\n",
            "ID", "Nama Produk", "Kategori", "Stok", "Harga");
    System.out.println("--------------------------------------------------------------------------------------");
}

static void printRow(int i) {
    System.out.printf("| %-4d | %-30s | %-15s | %-5d | %-15s |\n",
            id[i],
            nama[i],
            kategori[i],
            stok[i],
            harga[i]);
    }
}