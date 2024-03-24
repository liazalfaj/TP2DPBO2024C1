# TP2DPBO2024C1
## Janji
Saya Amelia Zalfa Julianti dengan NIM 2203999 mengerjakan Tugas Praktikum 2 mata kuliah DPBO untuk keberkahanNya maka saya tidak melakukan kecurangan sesuai dengan apa yang telah dispesifikasikan. Aamiin.
## Desain Program
* Kelas Mahasiswa : Kelas ini untuk merepresentasikan objek mahasiswa dengan atribut NIM, Nama, jenis kelamin, dan status mahasiswa. Setiap properti memiliki getter dan setter untuk mengakses dan mengubah nilainya.
* Kelas Menu : Kelas ini adalah tempat program utama dijalankan. Ini adalah sebuah frame GUI (Graphical User Interface) menggunakan Java swing. Berikut adalah beberapa komponen yang terdapat dalam program :
  1. **Variabel dan ArrayList** : **'selectedIndex'** digunakan untuk menyimpan indeks baris yang di klik dalam tabel. 'listmahasiswa' adalah ArrayList yang berisi objek Mahasiswa yang akan ditampilkan di tabel.
  2. **Komponen GUI** : Terdapat JTextField untuk NIM dan Nama, JComboBox untuk jenis kelamin dan status mahasiswa, JTable untuk menampilkan data mahasiswa, dan beberapa JButton untuk menambah, mengubah, dan menghapus data.
  3. **Metode setTable()** : Metode ini digunakan untuk mengisi tabel dengan data dari 'listmahasiswa' menggunakan DefaultTableModel.
  4. **Metode insertData(), updateData(), deleteData(), clearForm()** : Metode-metode ini digunakan untuk menambah, mengubah, menghapus data, dan membersihkan formulir.
  5. **Metode populateList()**: Metode ini digunakan untuk mengisi 'listMahasiswa' dengan data mahasiswa awal.
  
## Alur Program
User akan melihat antarmuka GUI yang berisi tabel untuk menampilkan data mahasiswa dan beberapa kompnen input seperti JTextField dan JComboBox untuk menambah, mengubah, dan menghapus data mahasiswa.
* **Tambah Data** : User mengisi form yang kosong dengan data mahasiswa yang baru. Saat user menekan tombol "Add", muncul pesan yang berisi bahwa data berhasil ditambahkan. Setelah itu, tabel data akan otomatis memperbaharui data dan terdapat data yang baru saja dimasukkan oleh user.
* **Edit Data** : User memilih data mana yang akan diubah. Setelah itu, data akan muncul di form dan user bisa mengubah data tersebut. Lalu klik tombol "Update" pada bagian kanan. Setelah itu akan muncul pesan yang berisi "Data berhasil diubah!". Data yang telah dipilih tadi akan otomatis berubah pada tabel.
* **Hapus Data** : User memilih data mana yang akan dihapus. Setelah itu, data akan muncul di form. Lalu klik tombol "Delete" pada bagian kanan. Setelah itu akan muncul kotak pesan konfirmasi kembali terkait penghapusan data. Terdapat dua tombol, yaitu tombol "Yes" dan "No". Jika user memilih tombol "Yes", maka data akan terhapus dan hilang dari tabel. Jika user memilih tombol "No", maka data tidak jadi dihapus dan masih ada di dalam tabel.
* **Verifikasi** : User akan mendapatkan pesan error jika form input atau update masih kosong dan jika data nim sudah terdapat di dalam database

## Dokumentasi

### Pesan setelah Tambah Data
![Screenshot 2024-03-24 221057](https://github.com/liazalfaj/TP2DPBO2024C1/assets/114666885/232d08b3-886d-4532-a0c9-09da89e8110b)

### Konfirmasi Hapus Data
![Screenshot 2024-03-24 221132](https://github.com/liazalfaj/TP2DPBO2024C1/assets/114666885/2acbb8b4-d9a9-4213-a6f8-8f77410533a0)
### Pesan setelah Hapus data
![deleteMessage](https://github.com/liazalfaj/LP5DPBO2024C1/assets/114666885/e6e67cf2-63d3-451a-97e2-cd2a6fc624c4)
### Pesan jika kolom form ada yang kosong
![Screenshot 2024-03-24 221044](https://github.com/liazalfaj/TP2DPBO2024C1/assets/114666885/d982ff45-9403-4a97-bd7b-3c4bc74f3302)
### Pesan jika nim sudah ada di database
![Screenshot 2024-03-24 221119](https://github.com/liazalfaj/TP2DPBO2024C1/assets/114666885/d5fac1ac-58df-4091-9cc0-2c881d441eeb)

### Record
https://github.com/liazalfaj/TP2DPBO2024C1/assets/114666885/caf1bc24-9dc1-4c0a-b686-21cbb2708456



