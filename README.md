# Taiwan Zipcode & Address Tool

A lightweight Spring Boot service designed for Taiwan 3+3 zipcode lookup and address autocomplete. This tool is built to support real estate management systems by providing precise address-to-zipcode mapping.

## 🚀 Features

- **3+3 Zipcode Support**: Comprehensive database for Taiwan's latest 6-digit zipcode system.
- **Address Autocomplete**: API endpoints for fetching Cities, Areas, and Roads with high performance.
- **Data Initialization**: Automated JSON-to-PostgreSQL ingestion logic with scope parsing (Single/Double/Range house numbers).

## 🛠️ Tech Stack

- **Backend**: Java 21, Spring Boot 3.3.x
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA (Hibernate)
- **Utilities**: Lombok, Jackson (JSON Parsing), Regex (Scope Parsing)

## 📦 Getting Started

### Prerequisites
- Docker & Docker Compose
- JDK 21

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone [https://github.com/your-username/taiwan-zipcode.git](https://github.com/your-username/taiwan-zipcode.git)
   cd taiwan-zipcode
2. **Spin up the database**
```docker-compose up -d```
3. Configure Environment Variables
Create a ``.env`` file or set the following variables in your shell:
- DB_HOST: Your database host (default: localhost)
- DB_PASSWORD: Your secret database password
4. Run the application 
```./mvnw spring-boot:run```
The ``DataInitializer`` will automatically detect an empty database and import ``zipcode.json`` on the first run.

### 🛣️ API Endpoints
## 🛣️ API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/address/cities` | List all cities in Taiwan |
| `GET` | `/api/v1/address/areas?city=...` | List areas for a specific city |
| `GET` | `/api/v1/address/roads?city=...&area=...&keyword=...` | Search for roads (Autocomplete) |

### 📜 Data Source & Credits
- Address Data: Provided by Chunghwa Post (中華郵政) Open Data.
- Conversion Utility: Processed using gnehs/TaiwanZipcode database converter (MIT License).
