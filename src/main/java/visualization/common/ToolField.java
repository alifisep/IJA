/**
 * Soubor: src/main/java/visualization/common/ToolField.java
 *
 * Popis:
 *
 *  Rozhraní reprezentující pole hry.
 *  Rozhraní definuje pouze ty operace, které jsou nutné pro prezentaci
 *  (zobrazení) v prostředí třídou EnvPresenter.
 *  Objekty polí, které jsou součástí vizualizovaného prostředí,
 *  implementují toto rozhraní.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package visualization.common;

/** Rozhraní reprezentující pole hry.
 *  Rozhraní definuje pouze ty operace, které jsou nutné pro prezentaci
 *  (zobrazení) v prostředí třídou EnvPresenter.
 *  Objekty polí, které jsou součástí vizualizovaného prostředí,
 *  implementují toto rozhraní. */
public interface ToolField extends Observable {
    /** Otočení políčka po směru hodinových ručiček o 90 stupňů. */
    void turn();

    /** Ověřuje, zda existuje konektor na severní straně políčka. */
    boolean north();

    /** Ověřuje, zda existuje konektor na východní straně políčka. */
    boolean east();

    /** Ověřuje, zda existuje konektor na jižní straně políčka. */
    boolean south();

    /** Ověřuje, zda existuje konektor na západní straně políčka. */
    boolean west();

    /** Ověřuje, zda existuje je políčko pod proudem (je napojeno na zdroj energie). */
    boolean light();

    /** Ověřuje typ políčka (vodič) kvůli zobrazení.*/
    boolean isLink();

    /** Ověřuje typ políčka (žárovka) kvůli zobrazení.*/
    boolean isBulb();

    /** Ověřuje typ políčka (zdroj) kvůli zobrazení. */
    boolean isPower();
}