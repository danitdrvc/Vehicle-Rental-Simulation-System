# Vehicle Rental Simulation System (ePJ2)

A comprehensive JavaFX-based vehicle rental simulation system that manages electric vehicles (cars, bikes, scooters) with real-time visualization, rental processing, and business analytics.

## Features

### Core Functionality
- **Multi-Vehicle Support**: Manages automobiles, electric bikes, and electric scooters
- **Real-time Simulation**: Visual map-based simulation with vehicle movement tracking
- **Rental Management**: Complete rental processing with validation and receipt generation
- **Battery Management**: Automatic battery monitoring and charging simulation
- **Malfunction Handling**: Vehicle breakdown detection and serialization for maintenance

### Business Analytics
- **Receipt Generation**: Automated invoice creation for each rental
- **Daily Reports**: Comprehensive daily business results
- **Summary Analytics**: Overall business performance metrics
- **Dynamic Pricing**: Distance-based pricing with promotions and discounts

### Technical Features
- **Multithreading**: Concurrent rental processing for optimal performance
- **Data Persistence**: CSV data loading and object serialization
- **Interactive GUI**: JavaFX-based visual interface with grid map
- **Configuration Management**: Properties-based system configuration

## System Architecture

### Design Patterns
- **Singleton Pattern**: Used for `Simulacija` and `Mapa` classes
- **Thread-per-Rental**: Each rental runs in its own thread for concurrent processing
- **Model-View Separation**: Clear separation between business logic and UI

### Key Components

#### Core Classes
- **`Simulacija`**: Main simulation controller (Singleton)
- **`Mapa`**: 20x20 grid map for vehicle visualization (Singleton)
- **`SimulacijaIznajmljivanja`**: Individual rental simulation thread
- **`PrevoznoSredstvo`**: Abstract base class for all vehicles

#### Vehicle Types
- **`Automobil`**: Car implementation with specific attributes
- **`ElektricniBicikl`**: Electric bike with range specifications
- **`ElektricniTrotinet`**: Electric scooter with performance metrics

#### Business Logic
- **`Iznajmljivanje`**: Rental transaction model
- **`Racun`**: Invoice generation and management
- **`Utility`**: Pricing calculations and system utilities

## Requirements

- **Java 11+**
- **JavaFX 11+**
- **Maven** (for dependency management)

## Getting Started

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/danitdrvc/vehicle-rental-simulation.git
   cd vehicle-rental-simulation
   ```

2. **Ensure JavaFX is properly configured**
   ```bash
   # Add JavaFX modules to your run configuration
   --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
   ```

3. **Set up data directory**
   - Ensure `src/main/resources/data/` contains:
     - `PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv`
     - `PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv`

4. **Configure application properties**
   - Update `src/main/resources/app.properties` with your settings

### Running the Application

```bash
# Compile and run
javac -cp ".:javafx-lib/*" src/main/java/org/etf/unibl/danilo_todorovic_1156_22_pj/Main.java
java --module-path javafx-lib --add-modules javafx.controls,javafx.fxml Main
```

## Data Format

### Vehicle Data CSV Format
```csv
ID,Manufacturer,Model,Date,Price,BikeRange,ScooterRange,Description,Type
V001,Tesla,Model3,1.1.2023.,45000,,,"Electric Car",automobil
B001,Trek,Bike1,,,50,,"Electric Bike",bicikl
S001,Xiaomi,Scooter1,,,,30,"Electric Scooter",trotinet
```

### Rental Data CSV Format
```csv
DateTime,UserName,VehicleID,StartX,StartY,EndX,EndY,Duration,Malfunction,Promotion
1.1.2023 10:00,John Doe,V001,"5","10","15","5",3600,ne,da
```

## Usage

### Main Interface
1. **Launch Application**: The JavaFX window displays a 20x20 grid map
2. **Vehicle Visualization**: Vehicles appear as colored labels:
   - 🔴 **Red**: Automobiles
   - 🟢 **Green**: Electric Bikes  
   - 🔵 **Blue**: Electric Scooters
3. **Real-time Movement**: Watch vehicles move from pickup to drop-off locations
4. **Battery Monitoring**: Each vehicle shows current battery percentage

### Simulation Process
1. **Data Loading**: System loads vehicles and rentals from CSV files
2. **Validation**: All data is validated before processing
3. **Concurrent Processing**: Multiple rentals can run simultaneously
4. **Receipt Generation**: Automatic invoice creation in configured directory
5. **Business Reports**: Daily and summary reports generated

## Configuration

### Application Properties (`app.properties`)
```properties
# Pricing Configuration
CAR_UNIT_PRICE=0.5
BIKE_UNIT_PRICE=0.3
SCOOTER_UNIT_PRICE=0.2

# Distance Coefficients
DISTANCE_NARROW=0.8
DISTANCE_WIDE=1.2

# Discounts
DISCOUNT=0.1
DISCOUNT_PROM=0.15

# File Paths
RECEIPT_PATH=receipts/
```

### Map Configuration
- **Grid Size**: 20x20 (configurable via `Mapa.VELICINA`)
- **Battery Drain**: 4% per grid cell movement
- **Narrow Zone**: Coordinates (5,5) to (14,14) - reduced pricing
- **Wide Zone**: All other coordinates - standard pricing

## 🔧 Key Features Explained

### Battery Management
- Automatic charging when battery is insufficient for trip
- 4% battery drain per grid cell moved
- Prevents rentals when battery capacity is inadequate

### Pricing Algorithm
```java
// Base calculation
double basePrice = unitPrice * durationSeconds;
double distanceMultiplier = isNarrowZone ? 0.8 : 1.2;
double total = basePrice * distanceMultiplier;

// Apply discounts
if (hasDiscount) total *= 0.9;
if (hasPromotion) total *= 0.85;
```

### Malfunction Handling
- Vehicles with malfunctions are automatically serialized
- Saved to `data/kvarovi/{vehicleId}.ser`
- Zero charge applied for malfunctioned rentals
