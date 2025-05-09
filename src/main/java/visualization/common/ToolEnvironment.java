package visualization.common;

public interface ToolEnvironment {
    int rows();

    int cols();

    ToolField fieldAt(int var1, int var2);
}
