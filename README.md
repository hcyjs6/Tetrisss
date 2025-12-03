# COMP2042 Developing Maintainable Software
---
## Table of Contents
1. [My Github](#10-my-github)
2. [Setup and Compilation Instructions](#20-setup-and-compilation-instructions)
    - [Prerequisites and Dependencies](#21-prerequisites-and-dependencies)
    - [Step-by-Step Compilation Guide](#22-step-by-step-compilation-guide)
    - [Common Build Commands](#23-common-build-commands)
3. [Features](#30-features)
    - [Implemented and Working Properly](#31-implemented-and-working-properly)
    - [Implemented but Not Working Properly](#32-implemented-but-not-working-properly)
    - [Not Implemented](#33-not-implemented)
4. [Refactoring Process](#40-refactoring-process)
    - [New Java Classes](#41-new-java-classes)
    - [Modified Java Classes](#42-modified-java-classes)
    - [Summary and Additional Notes](#43-summary-and-additional-notes-of-refactoring-process)
5. [Unexpected Problems](#50-unexpected-problems)
---
## 1.0 My Github
**Name:** Sek Joe Rin <br>
**Student ID:** [Your Student ID] <br>
**Link:** https://github.com/hcyjs6/Tetrisss.git

---

## 2.0 Setup and Compilation Instructions

### 2.1 Prerequisites and Dependencies

**Required Software:**
- Java Development Kit (JDK) 23 or later
- Apache Maven 3.6.0 or later
- Git (for cloning the repository)

**Dependencies (Automatically Managed by Maven):**
- JavaFX Controls 21.0.6
- JavaFX FXML 21.0.6
- JavaFX Media 21.0.6
- JUnit Jupiter 5.12.1 (for testing)

**Recommended IDE:**
- IntelliJ IDEA 2023.3+ or IntelliJ IDEA Community Edition 2023.3+

**Special Settings:**
- Project requires Java 23 as source and target version (configured in `pom.xml`)
- JavaFX dependencies are automatically downloaded via Maven - no manual installation required
- Main class: `com.comp2042.app.Main`

### 2.2 Step-by-Step Compilation Guide

#### Step 1: Install Prerequisites
1. **Install JDK 23**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK
   - Verify installation: `java --version` (should show Java 23 or higher)

2. **Install Apache Maven 3.6.0+**
   - Download from [Maven Official Site](https://maven.apache.org/download.cgi)
   - Add Maven to your system PATH
   - Verify installation: `mvn --version`

3. **Install Git** (if cloning repository)
   - Download from [Git for Windows](https://gitforwindows.org/)
   - Verify installation: `git --version`

### 2.2 Step-by-Step Compilation Guide

#### Step 2: Setup Project
1. **Open the Project**
   - If you have the project files locally, navigate to the project directory
   - If cloning from GitHub (optional):
     ```bash
     git clone https://github.com/hcyjs6/Tetrisss.git
     cd Tetrisss
     ```

2. **IntelliJ IDEA Setup**
   - Open IntelliJ IDEA
   - File → Open → Select the project directory
   - IntelliJ will automatically detect it as a Maven project
   - Ensure Project SDK is set to JDK 23 (File → Project Structure → Project → SDK)
   - Wait for Maven to sync and download dependencies automatically
   - If dependencies don't load: File → Reload Project from Disk

#### Step 3: Compile the Application
1. **Clean previous builds** (optional but recommended):
   ```bash
   mvn clean
   ```

2. **Compile the project**:
   ```bash
   mvn compile
   ```
   This will:
   - Download all Maven dependencies (JavaFX, JUnit, etc.)
   - Compile all Java source files in `src/main/java`
   - Place compiled classes in `target/classes`

3. **Verify compilation**:
   - Check that `target/classes` directory contains compiled `.class` files
   - No compilation errors should appear in the console

#### Step 4: Run the Application
**Option 1: Run using Maven (Recommended)**
```bash
mvn javafx:run
```

**Option 2: Run from IntelliJ IDEA**
- Right-click on `src/main/java/com/comp2042/app/Main.java`
- Select "Run 'Main.main()'"

**Option 3: Package and Run JAR**
```bash
# Package the application
mvn package

# Run the packaged JAR
java -jar target/CW2025-1.0-SNAPSHOT.jar
```

### 2.3 Common Build Commands
```bash
    # Clean build files
    mvn clean

    # Compile project
    mvn compile

    # Run tests
    mvn test

    # Package application
    mvn package

    # Install to local repository
    mvn install

    # Run application
    mvn javafx:run
```

---

## 3.0 Features
### 3.1 Implemented and Working Properly

#### 1. Basic Tetris Gameplay
- All 7 standard Tetris pieces (I, O, T, S, Z, J, L) implemented
- Piece movement (left, right, down)
- Piece rotation (clockwise and counter-clockwise)
- Automatic piece falling with speed progression
- Line clearing when rows are completed
- Game over detection when pieces reach the top
- **Location:** `com.comp2042.logic.SimpleBoard`, `com.comp2042.logic.bricks` package

#### 2. Hold Feature
- Players can hold the current piece by pressing 'C'
- Swaps between current and held piece
- Can only hold once per piece that lands
- Visual display of held piece in the hold panel
- Sound effect plays when holding a piece
- **Location:** `com.comp2042.logic.hold.HoldLogic`, `com.comp2042.gui.HoldBrickRenderer`

#### 3. Ghost Piece Preview
- Visual preview showing where the current piece will land
- Semi-transparent ghost piece displayed on the game board
- Updates dynamically as the piece moves or rotates
- **Location:** `com.comp2042.logic.ghostpieces.GhostPieceLogic`, `com.comp2042.gui.GhostPieceRenderer`

#### 4. Next Piece Preview
- Displays the next piece that will appear
- Rendered in a dedicated panel next to the game board
- **Location:** `com.comp2042.gui.NextBrickRenderer`

#### 5. Comprehensive Scoring System
- **Line Clear Scoring:** Points awarded based on number of lines cleared (Single, Double, Triple, Tetris)
- **Move Down Scoring:** Points for soft drops and hard drops
- **Level Multiplier:** Score multiplied by current level
- **Combo System:** Bonus points for consecutive line clears
- Score, level, and lines cleared displayed in real-time
- **Location:** `com.comp2042.logic.scoring` package

#### 6. Level System
- Level progression based on lines cleared (10 lines per level)
- Level selection at game start (1-100)
- Drop speed increases with level
- Level displayed in the UI
- **Location:** `com.comp2042.logic.scoring.LevelControls`, `com.comp2042.logic.speed.DropSpeedController`

#### 7. Audio System
- **Background Music:** Looping Tetris theme music during gameplay
- **Sound Effects:** Button clicks, piece rotation, hard drop, line clear, game over, level up, hold piece, countdown, notifications
- Music pauses when game is paused
- **Location:** `com.comp2042.audio` package

#### 8. Game State Management
- Playing, Paused, Game Over, and Main Menu states
- Pause functionality (ESC key)
- Game over screen with final score
- New game creation with reset functionality
- **Location:** `com.comp2042.logic.GameStateController`

#### 9. User Interface
- Main menu with level selection
- Game board with visual feedback
- Score panel displaying score, level, and lines cleared
- Pause menu overlay
- Game over screen
- Control panel showing keyboard controls
- Countdown timer before game starts
- Notification panel for line clears and combos
- Clear row animation effects
- **Location:** `com.comp2042.gui` package

#### 10. Rotation System with Wall Kicks
- Wall kick system for rotation near walls
- Smooth rotation with collision detection
- **Location:** `com.comp2042.logic.rotation.BrickRotator`

#### 11. Collision Detection
- Accurate collision detection for all movements
- Prevents pieces from moving through walls or other pieces
- **Location:** `com.comp2042.logic.CollisionDetector`

### 3.2 Implemented but Not Working Properly

#### 1. Exit Button Sound Effect in Main Menu
- **Issue:** The sound effect does not play when clicking the exit button in the main menu
- The `Platform.exit()` method is called immediately after `buttonClickSFX.playSFX()`, which terminates the JavaFX application before the sound effect has time to play. The application closes too quickly for the audio to be heard.
- Does not affect core functionality, only missing audio feedback on exit.
- **Location:** `com.comp2042.gui.MenuController.exitGame()`

### 3.3 Not Implemented

#### 1. Settings Feature for Customizing Game Controls and Audio
- **What I Want:** 
  A settings page to:
  - Change key bindings (e.g., remap movement keys)
  - Adjust sound effects (SFX) volume
  - Adjust background music (BGM) volume
- **Reason of Not Implemented:** 
  - Requires key binding management system and persistent storage
  - Audio volume control UI and preference saving needed
  - Additional UI development required
  - Time constraints
- **Current State:** 
  - Game uses fixed key bindings (Arrow keys, Z, SPACE, C, ESC)
  - Audio volumes are hardcoded (background music at 0.15, sound effects at 1.0)

#### 2. High Score Record System
- **What I Want:** 
  Save and display best scores across multiple game sessions
- **Reason of Not Implemented:**
  - Requires file storage or database system
  - Leaderboard UI component needed
  - Time constraints
- **Current State:** 
  - Scores reset when starting a new game, no score history is saved.

#### 3. Advanced Difficulty Features Beyond Speed
- **What I Want:** 
  Garbage lines appearing at the bottom of the board at higher levels to increase difficulty
- **Reason of Not Implemented:**
  - Requires additional game mechanics and difficulty scaling system
  - Garbage line generation and insertion logic needed
  - Board state management for garbage line integration
  - Time constraints
- **Current State:** 
  - Difficulty only increases through drop speed; no other difficulty mechanics are implemented



---

