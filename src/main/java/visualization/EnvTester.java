package visualization;

import ija.ijaProject.game.Game;
import visualization.common.ToolEnvironment;
import visualization.common.ToolField;
import visualization.view.FieldView;
import java.util.List;
import java.util.stream.Collectors;

public class EnvTester {
    private final ToolEnvironment maze;
    private EnvPresenter presenter;

    public EnvTester(ToolEnvironment var1) {
        this.presenter = new EnvPresenter(var1);
        this.presenter.init();
        this.maze = var1;
    }

    public boolean checkNotification(StringBuilder var1, ToolField var2) {
        boolean var3 = this.privCheckNotification(var1, var2);
        this.presenter.fields().forEach((var0) -> var0.clearChanged());
        return var3;
    }

    private boolean privCheckNotification(StringBuilder var1, ToolField var2) {
        return true;
    }

    private List<FieldView> check() {
        List var1 = (List)this.presenter.fields().stream().filter((var0) -> var0.numberUpdates() > 0).collect(Collectors.toList());
        return var1;
    }
}
