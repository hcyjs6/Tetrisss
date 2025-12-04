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
5. [Unexpected Problems](#50-unexpected-problems)
---
## 1.0 My Github
**Name:** Sek Joe Rin <br>
**Student ID:** 20724197 <br>
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
- JUnit Jupiter 5.12.1 

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
1. **Clean previous builds (optional but recommended)**:
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
**Run using Maven**
```bash
mvn javafx:run
```

### 2.3 Common Build Commands
```bash
    # Clean build files
    mvn clean

    # Compile project
    mvn compile

    # Run tests
    mvn test

    # Install to local repository
    mvn install

    # Run application
    mvn javafx:run
```

---

## 3.0 Features
### 3.1 Implemented and Working Properly

#### 1. Basic Tetris Gameplay
- 7 standard Tetris pieces (I, O, T, S, Z, J, L) implemented 
- Pieces spawn at the top center of the game board 
- Implemented piece movement- move left or right with arrow keys
- Automatic piece falling with speed that increases as level progresses
- Implemented line clearing functionality - clear lines when rows are completed 
- Multiple line clears supported (Single, Double, Triple, Tetris)
- Game over detection when pieces reached the top of the gameboard
- Gameboard contains 20 rows and 20 columns (20x20 grid)
- **Location:** `com.comp2042.logic.SimpleBoard`, `com.comp2042.logic.bricks` package

#### 2. Rotation System with Wall Kicks
- Wall kick system for rotation near walls
- Smooth rotation with collision detection
- **Location:** `com.comp2042.logic.rotation.BrickRotator`

#### 3. Collision Detection
- Accurate collision detection for all movements
- Prevents pieces from moving through walls or other pieces
- **Location:** `com.comp2042.logic.CollisionDetector`

#### 4. Main Menu
- Created using FXML (`menuLayout.fxml`) and `MenuController`
- Click the START GAME button to start new game
- Click SHOW CONTROLS & SCORES button to display keyboard controls and scoring rules
- Click EXIT button to close application
- **Location:** `com.comp2042.gui.MenuController`, `src/main/resources/menuLayout.fxml`

#### 5. Level Selection in Main Menu
- Level selection between 1 - 100 at game start 
- Increase/decrease buttons to adjust starting level
- Level selected affects initial drop speed
- Initial drop speed increases with higher level selected
- **Location:** `com.comp2042.gui.MenuController`, `src/main/resources/menuLayout.fxml`

#### 6. Countdown
- 3-2-1-Go! countdown before game starts
- Appears after starting new game or resuming from pause
- Dark overlay with large countdown numbers
- Sound effect plays for each countdown number
- **Location:** `com.comp2042.gui.Countdown`, `com.comp2042.gui.GuiController.startCountdown()`

#### 7. Soft Drop and Hard Drop
- **Soft Drop:** Press DOWN arrow key to accelerate piece falling (1 point per cell)
- **Hard Drop:** Press SPACE key to instantly drop piece to bottom (2 points per cell)
- Points awarded based on distance dropped
- Sound effect plays for hard drop
- **Location:** `com.comp2042.logic.scoring.MoveDownScoring`, `com.comp2042.logic.SimpleBoard`

#### 8. Added Clockwise and Anti-Clockwise Rotation
- Clockwise rotation (Z key) and counter-clockwise rotation (X key)
- Rotation with wall kick system for smooth rotation near walls
- Sound effect plays when rotation succeeds
- **Location:** `com.comp2042.logic.rotation.BrickRotator`, `com.comp2042.logic.SimpleBoard`

#### 9. Hold
- Players can hold the current piece by pressing 'C'
- Swaps between current and held piece
- Can only hold once per piece that lands
- Visual display of held piece in the hold panel
- Sound effect plays when holding a piece
- **Location:** `com.comp2042.logic.hold.HoldLogic`, `com.comp2042.gui.HoldBrickRenderer`

#### 10. Show Next Brick
- Displays the next piece that will appear
- Visual display of next piece in the next brick panel
- Helps players plan their moves
- **Location:** `com.comp2042.gui.NextBrickRenderer`

#### 11. Ghost Pieces
- Visual preview showing where the current piece will land
- Semi-transparent ghost piece displayed on the game board
- Updates dynamically as the piece moves or rotates
- Helps players position pieces accurately
- **Location:** `com.comp2042.logic.ghostpieces.GhostPieceLogic`, `com.comp2042.gui.GhostPieceRenderer`

#### 12. Pause Menu
- Created using FXML in `gameLayout.fxml` with `GuiController`
- Accessible via ESC key or pause button
- Displays current score while paused
- Options: Resume, Restart, Show Control Keys, Go Back to Menu
- Dark overlay background when active
- Background music pauses when game is paused
- Bricks stop moving and no action input can be made when game is paused
- **Location:** `com.comp2042.gui.GuiController.pauseGame()`, `gameLayout.fxml` pausePanel

#### 13. Comprehensive Scoring System
- **Line Clear Scoring:** Points awarded based on number of lines cleared (Single, Double, Triple, Tetris)
- **Move Down Scoring:** Points for soft drops and hard drops
- **Level Multiplier:** Score multiplied by current level
- **Combo System:** Bonus points for consecutive line clears
- Score, level, and lines cleared displayed in real-time
- **Location:** `com.comp2042.logic.scoring` package

#### 14. Added Detailed Notification
- Displays notifications for line clears, combos, and level ups
- Animated fade-in and fade-out effects
- Shows points awarded, combo multiplier, and total combo bonus
- Appears above the game board
- **Location:** `com.comp2042.gui.NotificationPanel`

#### 15. Level Progression during Gameplay
- Level progression based on lines cleared (10 lines per level)
- Drop speed increases with level
- Level displayed in the UI
- Level up sound effect and notification
- **Location:** `com.comp2042.logic.scoring.LevelControls`, `com.comp2042.logic.speed.DropSpeedController`

#### 16. Added BGM during Gameplay
- Looping Tetris theme music during gameplay
- Music pauses when game is paused
- Music stops when game over
- Volume control (set to 0.15)
- **Location:** `com.comp2042.audio.BackgroundMusic`

#### 17. Added Sound Effects
- Button clicks, piece rotation, hard drop, line clear, game over, level up, hold piece, countdown, notifications
- Sound effects play for various game events
- Volume control (set to 1.0)
- **Location:** `com.comp2042.audio.SoundEffect`

#### 18. Added Effect during Rows Clearing
- Visual animation effects when rows are cleared
- Fade-out animation for cleared rows
- Provides visual feedback during line clearing
- **Location:** `com.comp2042.gui.ClearRowEffect`

#### 19. Game Over using FXML
- Created using FXML in `gameLayout.fxml` with `GuiController`
- Displays final score when game ends
- Options: Retry (start new game) or Go Back to Menu
- Dark overlay background
- Background music stops when game over
- **Location:** `com.comp2042.gui.GuiController.gameOver()`, `gameLayout.fxml` gameOverPanel

#### 20. Allow Users to Restart the Game from Various Screens
- Restart option available from pause menu
- Restart option available from game over screen
- Confirmation panel prevents accidental resets
- Resets all game state including score, level, and board
- **Location:** `com.comp2042.gui.GuiController.newGame()`, `com.comp2042.gui.GuiController.confirmRestart()`

#### 21. Created Control Keys and Scoring Rules Panel
- Created using FXML in `gameLayout.fxml`
- Displays keyboard controls and score values
- Accessible from pause menu or main menu
- Shows all game controls (movement, rotation, drop, hold, pause)
- Shows all scoring rules (single, double, triple, Tetris, combo bonus)
- Close button to return to previous screen
- **Location:** `com.comp2042.gui.GuiController.showControlKeys()`, `gameLayout.fxml` controlPanel

#### 22. Show Gameplay Scores on Pause Menu
- Displays current score while game is paused
- Score updates in real-time on pause panel
- Allows players to check their progress during pause
- **Location:** `com.comp2042.gui.GuiController.pauseGame()`, `gameLayout.fxml` pauseScoreLabel

#### 23. Show Final Scores in Game Over
- Displays final score when game ends
- Score shown on game over panel
- Allows players to see their final achievement
- **Location:** `com.comp2042.gui.GuiController.gameOver()`, `gameLayout.fxml` gameOverScoreLabel

#### 24. Implements 7-Bag Randomizer System
- Prevents consecutive brick repeats by ensuring all 7 brick types appear before any type repeats
- Provides fair and balanced brick distribution
- **Location:** `com.comp2042.logic.bricks.RandomBrickGenerator`

#### 25. Implements brick Color System
- Color mapping for different brick types
- Each brick type has a unique color for visual distinction
- **Location:** `com.comp2042.gui.BrickColour`

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

## 4.0 Refactoring Process
### 4.1 New Java Classes

#### <ins>4.1.1 Audio Package</ins>

1. **`BackgroundMusic`** (`com.comp2042.audio.BackgroundMusic`)
   - **Purpose:** Manages background music playback with looping functionality. Provides methods to play, pause, stop music, and control volume. Automatically loops the music track during gameplay.
   
2. **`SoundEffect`** (`com.comp2042.audio.SoundEffect`)
   - **Purpose:** Handles short sound effect playback for game events. Provides volume control and preloading functionality to avoid audio delays during gameplay.

#### <ins>4.1.2 GUI Package</ins>

3. **`BrickColour`** (`com.comp2042.gui.BrickColour`)
   - **Purpose:** Defines color for different brick types. Maps numeric values to specific colors used for visual distinction of Tetris pieces.

4. **`BrickRenderer`** (`com.comp2042.gui.BrickRenderer`)
   - **Purpose:** Renders the current falling brick on the game board. 

5. **`ClearRowEffect`** (`com.comp2042.gui.ClearRowEffect`)
   - **Purpose:** Creates visual animation effects when rows are cleared. Implements fade-out animation for cleared rows to provide visual feedback.

6. **`Countdown`** (`com.comp2042.gui.Countdown`)
   - **Purpose:** Displays countdown timer before game starts. Shows 3-2-1-Go! countdown with sound effects and dark overlay.

7. **`GameBoardRenderer`** (`com.comp2042.gui.GameBoardRenderer`)
   - **Purpose:** Renders the main game board background. Initializes and displays the game board grid.

8. **`GhostPieceRenderer`** (`com.comp2042.gui.GhostPieceRenderer`)
   - **Purpose:** Renders semi-transparent ghost piece preview showing where the current piece will land. Provides visual guidance for piece placement.

9. **`HoldBrickRenderer`** (`com.comp2042.gui.HoldBrickRenderer`)
   - **Purpose:** Renders the held brick in the hold panel and displays the currently held piece.

10. **`MenuController`** (`com.comp2042.gui.MenuController`)
    - **Purpose:** Controls the main menu UI, level selection, and game initialization. Handles level selection (1-100), start game, show controls & scoring rules, and exit functionality.

11. **`NextBrickRenderer`** (`com.comp2042.gui.NextBrickRenderer`)
    - **Purpose:** Renders the next brick preview and displays the upcoming piece that will appear.

#### <ins>4.1.3 Logic Package - Ghost Pieces</ins>

12. **`GhostPieceLogic`** (`com.comp2042.logic.ghostpieces.GhostPieceLogic`)
    - **Purpose:** Calculates the landing position for the ghost piece preview. Determines where the current piece will land and creates ViewData for ghost piece rendering.

#### <ins>4.1.4 Logic Package - Hold System</ins>

13. **`HoldLogic`** (`com.comp2042.logic.hold.HoldLogic`)
    - **Purpose:** Manages the hold feature logic for storing and swapping pieces. Allows players to hold the current piece or swap with a previously held piece, enforcing a one-hold-per-piece limit.

#### <ins>4.1.5 Logic Package - Scoring</ins>

14. **`ComboSystem`** (`com.comp2042.logic.scoring.ComboSystem`)
    - **Purpose:** Manages combo multiplier system for consecutive line clears. Tracks combo count, increments on consecutive clears, resets when no lines cleared, and calculates combo bonus.

15. **`GameScore`** (`com.comp2042.logic.scoring.GameScore`)
    - **Purpose:** Manages the game score with JavaFX property binding. Provides methods to add points, reset score, and bind score property to UI for automatic updates.

16. **`LevelControls`** (`com.comp2042.logic.scoring.LevelControls`)
    - **Purpose:** Manages level progression and level value. Handles level progression (10 lines per level), level property binding, and setting initial level.

17. **`LineClearScoring`** (`com.comp2042.logic.scoring.LineClearScoring`)
    - **Purpose:** Calculates points for line clears based on the number of lines cleared. Provides different point values for Single, Double, Triple, and Tetris (4 lines) clears.

18. **`LineTracker`** (`com.comp2042.logic.scoring.LineTracker`)
    - **Purpose:** Tracks total lines cleared for level progression. 

19. **`MoveDownScoring`** (`com.comp2042.logic.scoring.MoveDownScoring`)
    - **Purpose:** Calculates points for soft drops and hard drops. Awards 1 point per cell for soft drops and 2 points per cell for hard drops.

20. **`ScoringRules`** (`com.comp2042.logic.scoring.ScoringRules`)
    - **Purpose:** Coordinates all scoring logic and acts as the central scoring controller. Integrates all scoring components, manages level multipliers and combos, and provides unified scoring interface.

#### <ins>4.1.6 Logic Package - Speed Control</ins>

21. **`DropSpeedController`** (`com.comp2042.logic.speed.DropSpeedController`)
    - **Purpose:** Controls the falling speed of pieces based on level. Automatically adjusts falling speed as level increases, with reset functionality for new games.

#### <ins>4.1.7 Logic Package - Game State and Collision</ins>

22. **`CollisionDetector`** (`com.comp2042.logic.CollisionDetector`)
    - **Purpose:** Handles all collision detection logic for the Tetris game. Provides methods to check collisions for brick movements, rotations, spawn positions, and ghost piece calculations.

23. **`GameStateController`** (`com.comp2042.logic.GameStateController`)
    - **Purpose:** Manages the overall game state of the Tetris game. Handles state transitions between PLAYING, PAUSED, GAME_OVER, and MAIN_MENU states.
   
## 4.2 Modified Java Classes
   
1. **SimpleBoard.java** (`com.comp2042.logic.SimpleBoard`)
   - **Modifications:** Added hold feature integration `HoldLogic`, ghost piece preview `GhostPieceLogic`, sound effects for rotation and hard drop, and wall kick rotation system using `BrickRotator` and `CollisionDetector`. Added methods: `holdBrick()`, `getHoldBrickData()`, `getGhostPieceY()`, `getGhostPieceViewData()`, and `resetBoard()`. Modified `getViewData()` to include ghost piece and hold brick data, and `kickOffsets()` to support wall kicks.
   - **Why These Modifications Were Necessary:** To support new features (hold, ghost piece, audio, wall kicks) that require integration with new logic classes and additional data for the UI.

2. **Board.java** (`com.comp2042.logic.Board`)
   - **Modifications:** Extended interface with method declarations for hold (`holdBrick()`, `getHoldBrickData()`), ghost piece (`getGhostPieceY()`, `getGhostPieceViewData()`), and reset (`resetBoard()`). Updated `getViewData()` documentation to include ghost piece and hold brick information.
   - **Why These Modifications Were Necessary:** To ensure all Board implementations support the new hold and ghost piece features.

3. **ViewData.java** (`com.comp2042.logic.ViewData`)
   - **Modifications:** Added `holdBrickData` and `ghostPieceData` fields. Added constructor accepting ghost piece and hold brick data, and getter methods (`getHoldBrickData()`, `getGhostPieceData()`).
   - **Why These Modifications Were Necessary:** To carry hold brick and ghost piece data to the UI layer for rendering these visual elements.

4. **NotificationPanel.java** (`com.comp2042.gui.NotificationPanel`)
    - **Modifications:** Set notification location to the middle of the game board. Modified to change notification text dynamically according to lines cleared (Single, Double, Triple, Tetris) and Combo Bonus. Added animated fade-in and fade-out effects with transitions (`shownotification()`). Improved styling with glow effects and text formatting.
    - **Why These Modifications Were Necessary:** Positioning notifications in the center makes them more visible to players. Dynamic text helps players understand what they achieved (how many lines cleared). Smooth animations enhance user experience and provide clear visual feedback.

5. **ClearRow.java** (`com.comp2042.logic.ClearRow`)
   - **Modifications:** Added fields for scoring (`totalPointsAwarded`, `combo`, `totalComboBonus`), animation (`clearedRowIndex`),and level progression (`levelUp`).
   - **Why These Modifications Were Necessary:** To support comprehensive scoring system, combo tracking, level progression notifications, and row clearing animations.

6. **GameStateController.java** (`com.comp2042.logic.GameStateController`)
   - **Modifications:** Added `resetGameState()` method and state checking methods (`isPlaying()`, `isPaused()`, `isGameOver()`, `isMainMenu()`). Enhanced `pauseGame()` and `resumeGame()` with state validation.
   - **Why These Modifications Were Necessary:** To ensure proper game flow control, prevent invalid state transitions, and support pause/resume and game restart features.

7. **GuiController.java** (`com.comp2042.gui.GuiController`)
   - **Modifications:** Added FXML fields for new UI components (pause panel, game over panel, ghost panel, hold panel, etc.), integrated new renderer classes, added `DropSpeedController`, `Countdown`, `NotificationPanel`, `ClearRowEffect`, audio system, and `GameStateController`. Enhanced keyboard event handling for hold (C), pause (ESC), soft drop (DOWN), hard drop (SPACE). Added methods for game state management, game restart, control panel, property binding (`bindScore()`, `bindLevel()`, `bindLinesCleared()`), and animations.
   - **Why These Modifications Were Necessary:** To integrate all new features (hold, ghost piece, audio, scoring, notifications, pause menu, game over screen, countdown, animations) into the UI..

8. **Main.java** (`com.comp2042.app.Main`)
   - **Modifications:** Modified the Main class to integrate with `MenuController`.
   - **Why These Modifications Were Necessary:** To ensure resources load the Main Menu layout first when the application starts before enter the game.

9. **MatrixOperations.java** (`com.comp2042.logic.MatrixOperations`)
    - **Modifications:** Changed row clearing algorithm from queue-based approach to direct row copying which is more efficient. Enhanced `checkRemoving()` to return `ClearRow` with comprehensive scoring data (combo, level up, cleared row indices) instead of just basic score. Added `deepCopyList()` method for safe copying of brick shapes.
    - **Why These Modifications Were Necessary:** The new clearing method is faster, uses less memory, and tracks which rows were cleared (needed for animations). Enhanced return value provides all scoring information for the scoring system. `deepCopyList()` prevents bugs when copying brick data.

10. **BrickGenerator.java** (`com.comp2042.logic.bricks.BrickGenerator`)
    - **Modifications:** Added `resetBrickGenerator()` method declaration to the interface.
    - **Why These Modifications Were Necessary:** To support game reset functionality, ensuring the brick generator starts fresh for new games.

11. **RandomBrickGenerator.java** (`com.comp2042.logic.bricks.RandomBrickGenerator`)
    - **Modifications:** Refactored from pure random generation to 7-bag randomizer system using `Deque<Brick>` queue. Added `fillListOfBricks()` method that creates and shuffles bags of all 7 brick types. Modified `getBrick()` to use queue polling with automatic refill logic. Implemented `resetBrickGenerator()` method.
    - **Why These Modifications Were Necessary:** The 7-bag system ensures fairness by guaranteeing all 7 brick types appear before any repeats, preventing unfair sequences and providing balanced gameplay (standard Tetris randomizer algorithm).

12. **EventType.java** (`com.comp2042.logic.event.EventType`)
    - **Modifications:** Added `SOFT_DROP`, `HARD_DROP`, `ROTATE_LEFT`, and `ROTATE_RIGHT` enum values.
    - **Why These Modifications Were Necessary:** To support soft drop, hard drop, and separate rotation directions for proper scoring and wall kick system.

13. **InputEventListener.java** (`com.comp2042.logic.event.InputEventListener`)
    - **Modifications:** Added `onSoftDropEvent()`, `onHardDropEvent()`, `onRotateLeftEvent()`, and `onRotateRightEvent()` method declarations.
    - **Why These Modifications Were Necessary:** To support soft drop, hard drop, and separate rotation directions. The distinction is important for scoring and for proper implementation of clockwise/anti-clockwise rotation with wall kicks.

14. **BrickRotator.java** (`com.comp2042.logic.rotation.BrickRotator`)
    - **Modifications:** Refactored from single rotation method to separate `getClockwiseNextShape()` and `getAnticlockwiseNextShape()` methods. Both return `NextShapeInfo` objects containing rotated shape matrix and rotation state index.
    - **Why These Modifications Were Necessary:** To allow rotation in both directions (standard Tetris feature) and support wall kick system calculations. The `NextShapeInfo` objects provide both shape and position index needed for wall kick offsets.

15. **GameController.java** (`com.comp2042.app.GameController`)
    - **Modifications:** Integrated `GameStateController` and `ScoringRules`. Added `handleBrickLanded()` and `handleBrickMoved()` for scoring, `executeMovement()` helper method, and `createNewGame()` for game reset. Uses `EventSource` enum to distinguish USER and THREAD events for scoring.
    - **Why These Modifications Were Necessary:** To decouple UI from game logic, integrate scoring system, and support comprehensive game reset functionality.

