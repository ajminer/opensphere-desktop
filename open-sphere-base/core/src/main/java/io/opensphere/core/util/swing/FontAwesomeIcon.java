package io.opensphere.core.util.swing;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import javax.swing.Icon;

import io.opensphere.core.util.AwesomeIcon;

/**
 * A rendered text icon in which the {@link AwesomeIcon} is drawn as a
 * Swing-compatible {@link Icon} instance.
 */
public class FontAwesomeIcon extends AbstractFontIcon
{
    static
    {
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(SwingUtilities.FONT_AWESOME_FONT);
    }

    /**
     * Creates a new icon.
     *
     * @param pIcon the icon to draw.
     */
    public FontAwesomeIcon(AwesomeIcon pIcon)
    {
        super(SwingUtilities.FONT_AWESOME_FONT, pIcon);
    }

    /**
     * Creates a new icon, painted with the supplied color.
     *
     * @param pIcon the icon to draw.
     * @param pColor the color to draw the icon.
     */
    public FontAwesomeIcon(AwesomeIcon pIcon, Color pColor)
    {
        super(SwingUtilities.FONT_AWESOME_FONT, pIcon, pColor);
    }

    /**
     * Creates a new icon, painted with the supplied color, at the supplied
     * size.
     *
     * @param pIcon the icon to draw.
     * @param pColor the color to draw the icon.
     * @param pSize the size of the icon.
     */
    public FontAwesomeIcon(AwesomeIcon pIcon, Color pColor, int pSize)
    {
        super(SwingUtilities.FONT_AWESOME_FONT, pIcon, pColor, pSize);
    }
}
