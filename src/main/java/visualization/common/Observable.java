/**
 * Soubor: src/main/java/visualization/common/Observable.java
 *
 * Popis:
 *
 *  Rozhraní Observable reprezentující objekty,
 *  které mohou notifikovat závislé objekty (observers) o změnách.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package visualization.common;

/** Rozhraní Observable reprezentující objekty,
 *  které mohou notifikovat závislé objekty (observers) o změnách. */
public interface Observable {
    /** Registruje nový observer. */
    void addObserver(Observer var1);

    /** Odregistruje observer.*/
    void removeObserver(Observer var1);

    /** Notifikuje (informuje) registrované observery, že došlo ke změně stavu objektu */
    void notifyObservers();

    /** Rozhraní Observer reprezentující objekty, které mohou registrovány u objektů Observable a přijímají notifikace o jejich změnách. */
    public interface Observer {
        void update(Observable var1);
    }
}
