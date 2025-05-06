package ija.ijaProject.game;

import java.util.ArrayList;
import java.util.List;

public class GameReplayer {
    private final List<GameEvent> log = new ArrayList<>();
    private int cursor = -1;          // index posledně „aplikované“ události
    private Game baseGameTemplate;     // prázdná hra v počátečním stavu
    private Game currentGame;          // stav po všech applovaných eventech až do cursor

    public GameReplayer(Game template) {
        this.baseGameTemplate = template;
        resetTo( -1 );
    }

    /** Přidá event do logu a aplikuje ho okamžitě */
    public void recordAndApply(GameEvent ev) {
        // při live režimu, pokud jsme odskočili zpět, zahodíme zbytek
        if (cursor < log.size()-1) {
            log.subList(cursor+1, log.size()).clear();
        }
        log.add(ev);
        ev.apply(currentGame);
        cursor++;
    }

    /** Krokuje o jeden vpřed (pokud existuje) */
    public void stepForward() {
        if (cursor < log.size()-1) {
            log.get(++cursor).apply(currentGame);
        }
    }

    /** Krokuje o jeden zpět: prostouše se znovu od začátku */
    public void stepBackward() {
        if (cursor >= 0) {
            resetTo(cursor-1);
        }
    }

    /** Přeskočí na libovolný index 0..log.size()-1 */
    public void jumpTo(int index) {
        if (index < -1 || index > log.size()-1) throw new IllegalArgumentException();
        resetTo(index);
    }

    private void resetTo(int index) {
        // obnovíme hru do výchozího stavu
        this.currentGame = baseGameTemplate.deepCopy();
        // a aplikujeme události 0..index
        for (int i = 0; i <= index; i++) {
            log.get(i).apply(currentGame);
        }
        this.cursor = index;
    }

    /** Přejde do live režimu: od tohoto okamžiku se bude nový logdokonce doplňovat */
    public void switchToLiveMode() {
        // pokud jsme odskočili zpět, zahodíme zbytek
        if (cursor < log.size()-1) {
            log.subList(cursor+1, log.size()).clear();
        }
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
