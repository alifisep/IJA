/**
 * Soubor: src/main/java/visualization/common/AbstractObservableField.java
 *
 * Popis:
 *
 *  Abstraktní třída reprezentující pole (ToolField),
 *  který je Observable. Implementuje metody z rozhraní Observable,
 *  tj. umožňuje vkládat a rušit observery a notifikovat registrované
 *  observery o změnách.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package visualization.common;

import java.util.HashSet;
import java.util.Set;

/** Abstraktní třída reprezentující pole (ToolField),
 *  který je Observable. Implementuje metody z rozhraní Observable,
 *  tj. umožňuje vkládat a rušit observery a notifikovat registrované
 *  observery o změnách. */
public abstract class AbstractObservableField implements ToolField {
    private final Set<Observable.Observer> observers = new HashSet();

    /** Konstruktor. */
    public AbstractObservableField() {
    }

    /** Registruje nový observer. */
    public void addObserver(Observable.Observer var1) {
        this.observers.add(var1);
    }
    /** Odregistruje observer. */
    public void removeObserver(Observable.Observer var1) {
        this.observers.remove(var1);
    }

    /** Notifikuje (informuje) registrované observery, že došlo ke změně stavu objektu.*/
    public void notifyObservers() {
        this.observers.forEach((var1) -> var1.update(this));
    }
}