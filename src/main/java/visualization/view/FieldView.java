package visualization.view;

import visualization.common.Observable;
import visualization.common.ToolField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class FieldView extends JPanel implements Observable.Observer {
    private final ToolField field;
    private boolean isHighlighted = false;
    private int updateCount = 0; // Add counter for updates

    public FieldView(ToolField field) {
        this.field = field;
        this.field.addObserver(this);

        setPreferredSize(new Dimension(50, 50));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        setBackground(new Color(15, 23, 42)); // Dark background

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                field.turn();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHighlighted = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHighlighted = false;
                repaint();
            }
        });
    }

    // Add this method to fix the first error
    public void clearChanged() {
        updateCount = 0;
    }

    // Add this method to fix the second error
    public int numberUpdates() {
        return updateCount;
    }

    @Override
    public void update(Observable observable) {
        // Increment update count when field changes
        updateCount++;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw based on field type
        if (field.isLink()) {
            drawLinkNode(g2d, width, height);
        } else if (field.isBulb()) {
            drawBulbNode(g2d, width, height);
        } else if (field.isPower()) {
            drawPowerNode(g2d, width, height);
        }

        // Draw highlight if mouse is over the field
        if (isHighlighted) {
            g2d.setColor(new Color(14, 165, 233, 50));
            g2d.fillRect(0, 0, width, height);
        }

        g2d.dispose();
    }

    /**
     * Draws a link node.
     *
     * @param g2d The graphics context
     * @param width The width of the component
     * @param height The height of the component
     */
    private void drawLinkNode(Graphics2D g2d, int width, int height) {
        boolean isPowered = field.light();

        // Set colors based on power state
        Color mainColor = isPowered ? new Color(14, 165, 233) : new Color(100, 116, 139);
        Color bgColor = new Color(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), 50);

        // Draw background rectangle
        int padding = 5;
        Rectangle2D rect = new Rectangle2D.Double(padding, padding, width - 2 * padding, height - 2 * padding);
        g2d.setColor(bgColor);
        g2d.fill(rect);

        // Draw border
        g2d.setColor(mainColor);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(rect);

        // Draw center circle
        int centerSize = 8;
        Ellipse2D center = new Ellipse2D.Double(
                width / 2 - centerSize / 2,
                height / 2 - centerSize / 2,
                centerSize,
                centerSize
        );
        g2d.fill(center);

        // Draw connections based on sides
        drawConnections(g2d, width, height, mainColor);
    }

    /**
     * Draws a bulb node.
     *
     * @param g2d The graphics context
     * @param width The width of the component
     * @param height The height of the component
     */
    private void drawBulbNode(Graphics2D g2d, int width, int height) {
        boolean isLit = field.light();

        // Draw bulb base
        int baseWidth = 16;
        int baseHeight = 10;
        Rectangle2D base = new Rectangle2D.Double(
                width / 2 - baseWidth / 2,
                height / 2 + 5,
                baseWidth,
                baseHeight
        );
        g2d.setColor(new Color(100, 116, 139));
        g2d.fill(base);

        // Draw bulb glass
        int glassSize = 20;
        Ellipse2D glass = new Ellipse2D.Double(
                width / 2 - glassSize / 2,
                height / 2 - glassSize / 2 - 5,
                glassSize,
                glassSize
        );

        if (isLit) {
            // Draw glow for lit bulb
            for (int i = 3; i >= 0; i--) {
                float alpha = 0.2f - (i * 0.05f);
                g2d.setColor(new Color(250, 204, 21, (int)(alpha * 255)));
                int glowSize = glassSize + (i * 6);
                Ellipse2D glow = new Ellipse2D.Double(
                        width / 2 - glowSize / 2,
                        height / 2 - glowSize / 2 - 5,
                        glowSize,
                        glowSize
                );
                g2d.fill(glow);
            }
            g2d.setColor(new Color(250, 204, 21));
        } else {
            g2d.setColor(new Color(203, 213, 225));
        }

        g2d.fill(glass);
        g2d.setColor(new Color(100, 116, 139));
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(glass);

        // Draw connections based on sides
        Color connectionColor = isLit ? new Color(250, 204, 21) : new Color(100, 116, 139);
        drawConnections(g2d, width, height, connectionColor);
    }

    /**
     * Draws a power node.
     *
     * @param g2d The graphics context
     * @param width The width of the component
     * @param height The height of the component
     */
    private void drawPowerNode(Graphics2D g2d, int width, int height) {
        // Draw outer circle
        int outerSize = 30;
        Ellipse2D outer = new Ellipse2D.Double(
                width / 2 - outerSize / 2,
                height / 2 - outerSize / 2,
                outerSize,
                outerSize
        );
        g2d.setColor(new Color(14, 165, 233));
        g2d.fill(outer);

        // Draw inner circle
        int innerSize = 20;
        Ellipse2D inner = new Ellipse2D.Double(
                width / 2 - innerSize / 2,
                height / 2 - innerSize / 2,
                innerSize,
                innerSize
        );
        g2d.setColor(new Color(14, 165, 233, 128));
        g2d.fill(inner);

        // Draw lightning bolt
        Path2D bolt = new Path2D.Double();
        bolt.moveTo(width / 2, height / 2 - 10);  // Top
        bolt.lineTo(width / 2 - 5, height / 2);   // Middle left
        bolt.lineTo(width / 2, height / 2);       // Middle center
        bolt.lineTo(width / 2 + 5, height / 2 + 10); // Bottom
        bolt.closePath();

        g2d.setColor(Color.WHITE);
        g2d.fill(bolt);

        // Draw connections based on sides
        drawConnections(g2d, width, height, new Color(14, 165, 233));
    }

    /**
     * Draws connections based on the field's sides.
     *
     * @param g2d The graphics context
     * @param width The width of the component
     * @param height The height of the component
     * @param color The color to use for connections
     */
    private void drawConnections(Graphics2D g2d, int width, int height, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(3));

        int centerX = width / 2;
        int centerY = height / 2;

        // Draw connections based on the field's direction methods
        if (field.north()) {
            g2d.draw(new Line2D.Double(centerX, centerY, centerX, 0));
        }

        if (field.east()) {
            g2d.draw(new Line2D.Double(centerX, centerY, width, centerY));
        }

        if (field.south()) {
            g2d.draw(new Line2D.Double(centerX, centerY, centerX, height));
        }

        if (field.west()) {
            g2d.draw(new Line2D.Double(centerX, centerY, 0, centerY));
        }
    }

    public ToolField getField() {
        return field;
    }
}