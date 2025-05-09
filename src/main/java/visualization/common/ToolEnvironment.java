/**
 * Soubor: src/main/java/visualization/common/ToolEnvironment.java
 *
 * Popis:
 *
 *  Rozhraní reprezentující prostředí hry.
 *  Rozhraní definuje pouze ty operace, které jsou nutné pro
 *  prezentaci (zobrazení) prostředí třídou EnvPresenter.
 *  Prostředí je rozděleno rovnoměrnou mřížkou, každé políčko je
 *  reprezentováno rozhraním ToolField. Políčka jsou indexovaná dvojicí
 *  (řádek, sloupec), levé horní políčko má index (1,1).
 *  Prostředí, které mají být vizualizována, implementují toto rozhraní.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package visualization.common;

/** Rozhraní reprezentující prostředí hry.
 *  Rozhraní definuje pouze ty operace, které jsou nutné pro
 *  prezentaci (zobrazení) prostředí třídou EnvPresenter.
 *  Prostředí je rozděleno rovnoměrnou mřížkou, každé políčko je
 *  reprezentováno rozhraním ToolField. Políčka jsou indexovaná dvojicí
 *  (řádek, sloupec), levé horní políčko má index (1,1).
 *  Prostředí, které mají být vizualizována, implementují toto rozhraní. */
public interface ToolEnvironment {
    /** Zjišťuje rozměr prostředí - počet řádků. */
    int rows();

    /** Zjišťuje rozměr prostředí - počet sloupců.*/
    int cols();

    /** Zjišťuje konkrétní políčko na zadaných souřadnicích prostředí. */
    ToolField fieldAt(int var1, int var2);
}
