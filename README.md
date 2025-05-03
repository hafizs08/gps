# 🚗 GPS Tracking API

API backend untuk manajemen kendaraan dan pelacakan lokasi GPS secara real-time menggunakan **Spring Boot**, **PostgreSQL**, dan **JWT**.

---

## 📦 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- Swagger (OpenAPI 3)
- JWT (JSON Web Token)

---

## 🚀 Setup dan Menjalankan Aplikasi

### 1. Clone Project

```bash
git clone https://github.com/username/gps-demo.git
cd gps-demo
```

### 2. Buat Database PostgreSQL

```sql
CREATE DATABASE gps;
```

### 3. Atur Konfigurasi di application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gps
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your-very-secure-secret-key
jwt.expiration=86400000
```

### 4. Jalankan Aplikasi

```bash
./mvnw spring-boot:run
```

### 5. Ekstrak File Pendukung
- Unduh file `gps_setup.rar` dari repositori (misalnya di folder `docs`).
- Ekstrak file RAR menggunakan alat seperti WinRAR atau 7-Zip:
  ```bash
  unzip gps_setup.rar -d docs
  ```
- Isi file:
  - `postman_collection.json`: Koleksi Postman untuk menguji endpoint API.
  - `init_schema.sql`: Skrip SQL untuk menginisialisasi tabel PostgreSQL.

### 6. Jalankan Skrip SQL
- Impor `init_schema.sql` ke database PostgreSQL Anda menggunakan perintah:
  ```sql
  psql -U postgres -d gps -f docs/init_schema.sql
  ```

📂 Struktur Folder Penting

```bash
src/
├── main/
│   ├── java/com/example/gps/
│   │   ├── controller/       # REST API endpoints (AuthController, TestController)
│   │   ├── security/         # Security configurations (JWT, Spring Security)
│   │   ├── util/             # Utility classes (JwtUtil)
│   │   ├── exception/        # Global exception handling
│   │   └── dto/              # Data Transfer Objects (ErrorResponse)
│   └── resources/
│       ├── application.properties
│       └── messages.properties  # Locale messages
```

🧪 Testing (Manual)

Karena unit test belum tersedia, kamu bisa menggunakan:

- 🌐 **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- 📮 **Postman**: Impor `postman_collection.json` dari folder `docs` untuk menguji endpoint.
  - Gunakan endpoint `/auth/login` untuk mendapatkan token JWT:
    ```bash
    curl -X POST "http://localhost:8080/auth/login?username=admin&password=admin"
    ```
  - Gunakan token di header `Authorization: Bearer <your-jwt-token>` untuk mengakses endpoint lainnya.

🔖 Asumsi & Catatan

- Nomor plat kendaraan (`plate_number`) harus unik.
- Locale digunakan untuk pesan sukses dan error (Indonesia).
- Unit testing dan scheduled cleanup log belum tersedia.
- Default user: `admin` / `admin` (hard-coded, ganti dengan database di produksi).

## 📄 API Endpoints

| Method | Endpoint                            | Deskripsi                       |
|--------|-------------------------------------|---------------------------------|
| GET    | `/api/vehicles`                     | Ambil semua kendaraan           |
| POST   | `/api/vehicles`                     | Tambah kendaraan baru           |
| GET    | `/api/vehicles/{id}`                | Ambil kendaraan berdasarkan ID  |
| PUT    | `/api/vehicles/{id}`                | Update data kendaraan           |
| DELETE | `/api/vehicles/{id}`                | Hapus kendaraan                 |
| GET    | `/api/vehicles/{id}/last-location`  | Lokasi terakhir kendaraan       |
| GET    | `/api/vehicles/{id}/history?from=...&to=...` | Riwayat lokasi berdasarkan waktu |

<img width="443" alt="erd" src="https://github.com/user-attachments/assets/1ac376e9-1e37-48bf-a76c-3da96b7481c2" />


<img width="750" alt="swagger" src="https://github.com/user-attachments/assets/1f7fdbea-77e3-4ac6-83cc-253fa14783f7" />
