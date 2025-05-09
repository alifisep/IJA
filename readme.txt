
                                    VoltMaze

 Členové týmu:
    - Yaroslav Hryn
    - Oleksandr Musiichuk


 Kompilace a spuštění:
 **Přes Maven**
    ```bash
    # přejít do kořenové složky projektu
    mvn clean install

# spustit aplikaci
 java \
  --module-path lib/javafx-sdk-24.0.1/lib \
  --add-modules=javafx.controls,javafx.graphics,javafx.swing \
  -jar target/VoltMaze-1.0.jar



Struktura
    src/
     ├─ main/
     │   ├─ java/
     │   │   ├─ ija/ijaProject/            – MainApp
     │   │   │       │─ common/            - GameNode,Position,Side
     │   │   │       │─ game/              - Game
     │   │   │       │   └─ levels/        - GameLevels,LevelManager
     │   │   │       └─ settings/          - LanguageManager,SettingsManager,SoundManager
     │   │   │
     │   │   └─ visualization/             – EnvPresenter,EnvTester
     │   │          └─ view/               – Views + Controllers
     │   └─ resources/
     │
     └