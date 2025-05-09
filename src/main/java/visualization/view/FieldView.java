package visualization.view;

import visualization.common.Observable;
import visualization.common.ToolField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

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
        int bodyW = 30;
        int bodyH = 16;
        int x = width / 2 - bodyW / 2;
        int y = height / 2 - bodyH / 2;


        Color glowColor = new Color(0x1F8A70);

        for (int i = 4; i > 0; i--) {
            int pad = i * 3;
            int alpha = 50 / i;
            g2d.setColor(new Color(
                    glowColor.getRed(),
                    glowColor.getGreen(),
                    glowColor.getBlue(),
                    alpha
            ));
            g2d.fillRoundRect(
                    x - pad,
                    y - pad,
                    bodyW + pad * 2,
                    bodyH + pad * 2,
                    6 + pad,
                    6 + pad
            );
        }


        g2d.setColor(glowColor);
        g2d.fillRoundRect(x, y, bodyW, bodyH, 4, 4);

        g2d.setColor(glowColor.darker());
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, bodyW, bodyH, 4, 4);

        int termH = bodyH / 3;
        int termW = 4;
        int tx = x - termW;
        int ty = height / 2 - termH / 2;
        g2d.fillRect(tx, ty, termW, termH);

        int px = x + bodyW + 1;
        int py = height / 2 - termH / 2;
        g2d.fillRect(px, py, termW, termH);
        int dotR = 3;
        g2d.fillOval(px + termW/2 - dotR/2, height/2 - dotR/2, dotR, dotR);

        drawConnections(g2d, width, height, glowColor);
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
        Color baseColor = color;

        int centerX = width  / 2;
        int centerY = height / 2;
        int thickness = 4;

        g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Point2D start = new Point2D.Float(centerX, centerY);
        Point2D end;
        LinearGradientPaint paint;

        if (field.north()) {
            end = new Point2D.Float(centerX, 0);
            paint = new LinearGradientPaint(
                    start, end,
                    new float[]{0f, 1f},
                    new Color[]{baseColor.brighter(), baseColor.darker()}
            );
            g2d.setPaint(paint);
            g2d.draw(new Line2D.Float(centerX, centerY, centerX, 0));
        }
        if (field.south()) {
            end = new Point2D.Float(centerX, height);
            paint = new LinearGradientPaint(
                    start, end,
                    new float[]{0f, 1f},
                    new Color[]{baseColor.brighter(), baseColor.darker()}
            );
            g2d.setPaint(paint);
            g2d.draw(new Line2D.Float(centerX, centerY, centerX, height));
        }
        if (field.east()) {
            end = new Point2D.Float(width, centerY);
            paint = new LinearGradientPaint(
                    start, end,
                    new float[]{0f, 1f},
                    new Color[]{baseColor.brighter(), baseColor.darker()}
            );
            g2d.setPaint(paint);
            g2d.draw(new Line2D.Float(centerX, centerY, width, centerY));
        }
        if (field.west()) {
            end = new Point2D.Float(0, centerY);
            paint = new LinearGradientPaint(
                    start, end,
                    new float[]{0f, 1f},
                    new Color[]{baseColor.brighter(), baseColor.darker()}
            );
            g2d.setPaint(paint);
            g2d.draw(new Line2D.Float(centerX, centerY, 0, centerY));
        }

        g2d.setPaint(baseColor);
    }

    private void drawDirLines(Graphics2D g2d, int cx, int cy, int w, int h) {
        if (field.north()) g2d.draw(new Line2D.Float(cx, cy, cx, 0));
        if (field.south()) g2d.draw(new Line2D.Float(cx, cy, cx, h));
        if (field.east()) g2d.draw(new Line2D.Float(cx, cy, w, cy));
        if (field.west()) g2d.draw(new Line2D.Float(cx, cy, 0, cy));
    }
    public ToolField getField() {
        return field;
    }
}