package com.github.nandaarya.cleanarchitecturetemplate.source

fun readmeKt() = """
# Project Template: Clean Architecture

## Deskripsi

Template ini memberikan struktur dasar untuk membangun aplikasi Android menggunakan pendekatan Clean Architecture dengan pemisahan yang jelas antara lapisan Domain, Data, dan Presentation. Anda dapat dengan mudah menyesuaikan dan memperluas aplikasi sesuai kebutuhan.

## Panduan Pengguna

### 1. File README ini bukan bagian dari proyek dan dapat dihapus

- File ini hanya disertakan sebagai panduan awal saat pertama kali proyek dibuat. Anda dapat menghapus file ini setelah Anda merasa sudah cukup dengan dokumentasi atau jika tidak dibutuhkan lagi.

### 2. Fungsi di Proyek Ini adalah Contoh Implementasi Saja

- Setiap fungsi yang ada di proyek ini hanya merupakan contoh implementasi dari pola dan struktur yang digunakan dalam aplikasi. Anda bebas untuk memodifikasi atau mengganti fungsi ini kapan saja sesuai dengan kebutuhan proyek Anda.

### 3. Pengubahan Nama Variabel, Fungsi, Kelas, Object, dan Interface

- Jika Anda ingin mengubah nama variabel, fungsi, kelas, object, atau interface, pastikan untuk menggunakan fitur Refactoring dari Android Studio. Fitur ini akan memastikan bahwa perubahan nama diterapkan serentak di seluruh file proyek, menghindari kesalahan referensi dan ketidakcocokan nama.
- Untuk menggunakan refactoring, cukup klik kanan pada elemen yang ingin diubah dan pilih `Refactor > Rename`. Android Studio akan secara otomatis memperbarui semua referensi terkait.

### 4. Masalah Retrofit: "Http and https not found"

- Jika Anda memilih untuk menggunakan fitur Retrofit saat membuat proyek baru, Anda mungkin akan menemukan error berikut saat pertama kali menjalankan aplikasi:
  ```
  "Http and https not found"
  ```
- Ini adalah error yang wajar dan terjadi karena URL base untuk Retrofit belum diatur. Untuk
  mengatasi masalah ini, ikuti langkah-langkah berikut:
    1. Buka file `RetrofitModule.kt` yang terletak di `data/di/RetrofitModule.kt`.
    2. Atur base URL sesuai dengan API endpoint yang Anda gunakan.
       ```
       val retrofit = Retrofit.Builder()
           .baseUrl("https://your-api-endpoint.com/")  // Pastikan untuk mengganti dengan base URL yang benar
           .addConverterFactory(GsonConverterFactory.create())
           .build()
       ```
    3. Setelah mengatur base URL, pastikan untuk menyesuaikan file-file lainnya seperti `ApiService.kt`, `RemoteDataSource.kt`, dan model response yang Anda gunakan dalam aplikasi Anda.
""".trimIndent()