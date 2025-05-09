/**
 * Soubor: src/main/java/visualization/EnvTester.java
 *
 * Popis:
 *
 * Třída EnvTester slouží k testování a ověřování notifikací
 *   mezi modelem prostředí (ToolEnvironment) a jeho prezentací
 *   (EnvPresenter). Umožňuje zkontrolovat, že při změnách polí
 *   skutečně dochází k očekávaným notifikacím a poté resetovat
 *   flagy změn.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package visualization;

import ija.ijaProject.game.Game;
import visualization.common.ToolEnvironment;
import visualization.common.ToolField;
import visualization.view.FieldView;
import java.util.List;
import java.util.stream.Collectors;

/**  Slouží k testování a ověřování notifikací
 *   mezi modelem prostředí (ToolEnvironment) a jeho prezentací
 *   (EnvPresenter). Umožňuje zkontrolovat, že při změnách polí
 *   skutečně dochází k očekávaným notifikacím a poté resetovat
 *   flagy změn. */
public class EnvTester {
    private final ToolEnvironment maze;
    private EnvPresenter presenter;

    /**
     * Vytvoří nový EnvTester pro dané prostředí.
     *
     * @param maze instanci prostředí, jehož změny chceme testovat
     */
    public EnvTester(ToolEnvironment maze) {
        this.presenter = new EnvPresenter(maze);
        this.presenter.init();
        this.maze = maze;
    }

    /**
     * Zkontroluje, zda po provedení nějaké akce na poli {@code var2}
     * přišla odpovídající notifikace (do StringBuilderu {@code var1}),
     * a poté vyčistí (resetuje) počitadla změn ve všech polích prezentéru.
     *
     * @param var1   StringBuilder, do kterého se zapisují chybová hlášení
     * @param var2  právě změněné pole, jehož notifikaci ověřujeme
     * @return {@code true}, pokud notifikace odpovídala očekávání, jinak {@code false}
     */
    public boolean checkNotification(StringBuilder var1, ToolField var2) {
        boolean var3 = this.privCheckNotification(var1, var2);
        this.presenter.fields().forEach((var0) -> var0.clearChanged());
        return var3;
    }

    /**
     * Vlastní privátní logika kontroly, může být přepsána
     * tak, aby ověřovala specifické scénáře.
     *
     * @param var1        StringBuilder pro případné hlášky
     * @param var2   pole, na kterém proběhla změna
     * @return vždy {@code true} (výchozí implementace)
     */
    private boolean privCheckNotification(StringBuilder var1, ToolField var2) {
        return true;
    }

    /**
     * Vrátí seznam všech FieldView, které od poslední kontroly
     * zaznamenaly alespoň jednu změnu (numberUpdates() > 0).
     *
     * @return seznam FieldView s nenulovým počtem update() volání
     */
    private List<FieldView> check() {
        List var1 = (List)this.presenter.fields().stream().filter((var0) -> var0.numberUpdates() > 0).collect(Collectors.toList());
        return var1;
    }
}
