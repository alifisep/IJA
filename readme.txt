
                                    VoltMaze

 Členové týmu:
    - Yaroslav Hryn
    - Oleksandr Musiichuk


 Kompilace a spuštění:
 **Přes Maven**
    mvn clean install

  # Spuštění
     mvn javafx:run
   nebo
      java \
       --module-path lib/javafx-sdk-24.0.1/lib \
       --add-modules=javafx.controls,javafx.graphics,javafx.swing \
       -jar target/VoltMaze-1.0.jar



Struktura
    VoltMaze/
      ├─src/
      │   ├─ main/
      │   │   ├─ java/
      │   │   │   ├─ ija/ijaProject/            – MainApp
      │   │   │   │       │─ common/            - GameNode,Position,Side
      │   │   │   │       └─ game/              - Game
      │   │   │   │           └─ levels/        - GameLevels,LevelManager,NodeStageManager
      │   │   │   │
      │   │   │   │
      │   │   │   └─ visualization/             – EnvPresenter,EnvTester
      │   │   │          └─ view/               – Views + Controllers
      │   │   └─ resources/
      │   └─
      ├─ lib/                                   -  Modules
      ├─ pom.xml
      ├─ readme.txt
      └─ requirements.pdf