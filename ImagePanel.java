/**
 * 
 */
package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * @author sonng
 *
 */
class ImagePanel extends JPanel {
    private Image img;
    public ImagePanel(String img) {
        this(Toolkit.getDefaultToolkit().getImage(ImagePanel.class.getResource(img)));
    }
    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}
