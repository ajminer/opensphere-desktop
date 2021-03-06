package io.opensphere.overlay;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import io.opensphere.core.UnitsRegistry;
import io.opensphere.core.control.DiscreteEventAdapter;
import io.opensphere.core.mgrs.MGRSConverter;
import io.opensphere.core.mgrs.UTM;
import io.opensphere.core.model.GeographicPosition;
import io.opensphere.core.model.LatLonAlt;
import io.opensphere.core.units.UnitsProvider;
import io.opensphere.core.units.angle.Angle;
import io.opensphere.core.units.angle.DecimalDegrees;
import io.opensphere.core.units.angle.DegDecimalMin;
import io.opensphere.core.units.angle.DegreesMinutesSeconds;
import io.opensphere.core.units.length.Length;
import io.opensphere.core.util.swing.EventQueueUtilities;

/** Manager for a popup that displays the latest cursor position. */
public class CursorPositionPopupManager
{
    /** An MGRS converter. Package visibility to prevent synthetic accessors. */
    static final MGRSConverter MGRS_CONVERTER = new MGRSConverter();

    /**
     * The units registry used to convert between different formats. Package
     * visibility to prevent synthetic accessors.
     */
    final UnitsRegistry myUnitsRegistry;

    /**
     * The location to be displayed by the popup manager. Package visibility to
     * prevent synthetic accessors.
     */
    LatLonAlt myLocation;

    /**
     * Supplier for the dialog parent. Package visibility to prevent synthetic
     * accessors.
     */
    final Supplier<? extends JFrame> myDialogParentSupplier;

    /**
     * A flag to inform the popup that an elevation provider is present. Package
     * visibility to prevent synthetic accessors.
     */
    boolean myHasElevationProvider;

    /** The key listener that displays a popup with the cursor position. */
    private final DiscreteEventAdapter myPopupListener = new DiscreteEventAdapter("Cursor Position", "Display Cursor Position",
            "Show a popup with the current mouse cursor position")
    {
        /** Counter for mouse position popups. */
        private int myCounter;

        @Override
        public void eventOccurred(InputEvent event)
        {
            if (myLocation != null)
            {
                EventQueueUtilities.invokeLater(() ->
                {
                    JDialog dialog = new JDialog(myDialogParentSupplier.get());
                    dialog.setTitle("Mouse Position " + ++myCounter);
                    JPanel detailsPanel = new JPanel(new BorderLayout());
                    detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    DecimalDegrees latitudeDD = Angle.create(DecimalDegrees.class, myLocation.getLatD());
                    DecimalDegrees longitudeDD = Angle.create(DecimalDegrees.class, myLocation.getLonD());

                    DegDecimalMin latitudeDDM = Angle.create(DegDecimalMin.class, myLocation.getLatD());
                    DegDecimalMin longitudeDDM = Angle.create(DegDecimalMin.class, myLocation.getLonD());

                    DegreesMinutesSeconds latitudeDMS = Angle.create(DegreesMinutesSeconds.class, myLocation.getLatD());
                    DegreesMinutesSeconds longitudeDMS = Angle.create(DegreesMinutesSeconds.class, myLocation.getLonD());

                    StringBuilder builder = new StringBuilder("DD:\t");
                    builder.append(latitudeDD.toShortLabelString(14, 6, 'N', 'S').trim()).append("\t");
                    builder.append(longitudeDD.toShortLabelString(14, 6, 'E', 'W').trim()).append("\n");

                    builder.append("DMS:\t");
                    builder.append(latitudeDMS.toShortLabelString(14, 6, 'N', 'S').trim()).append("\t");
                    builder.append(longitudeDMS.toShortLabelString(14, 6, 'E', 'W').trim()).append("\n");

                    builder.append("DDM:\t");
                    builder.append(latitudeDDM.toShortLabelString(14, 6, 'N', 'S').trim()).append("\t");
                    builder.append(longitudeDDM.toShortLabelString(14, 6, 'E', 'W').trim()).append("\n");

                    builder.append("MGRS:\t");
                    builder.append(MGRS_CONVERTER.createString(new UTM(new GeographicPosition(myLocation))));

                    if (myHasElevationProvider)
                    {
                        UnitsProvider<Length> lengthProvider = myUnitsRegistry.getUnitsProvider(Length.class);
                        Length alt = Length.create(lengthProvider.getPreferredUnits(), myLocation.getAltitude().getMagnitude());
                        builder.append("\nAlt:\t").append(alt.toShortLabelString(10, 0).trim());
                    }
                    JTextArea area = new JTextArea(builder.toString());
                    area.setEditable(false);
                    area.setBorder(BorderFactory.createEmptyBorder());
                    area.setBackground(detailsPanel.getBackground());
                    detailsPanel.add(area);

                    dialog.getContentPane().add(detailsPanel, BorderLayout.CENTER);
                    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    dialog.setLocationRelativeTo(dialog.getParent());
                    dialog.pack();
                    dialog.setVisible(true);
                });
            }
        }
    };

    /**
     * Constructor.
     *
     * @param dialogParentSupplier The dialog parent provider.
     * @param unitsRegistry the registry through which unit information is
     *            obtained.
     */
    public CursorPositionPopupManager(Supplier<? extends JFrame> dialogParentSupplier, UnitsRegistry unitsRegistry)
    {
        myDialogParentSupplier = dialogParentSupplier;
        myUnitsRegistry = unitsRegistry;
    }

    /**
     * Get the listener for mouse events.
     *
     * @return The listener.
     */
    public DiscreteEventAdapter getListener()
    {
        return myPopupListener;
    }

    /**
     * Stores the supplied value in the {@link #myLocation} field.
     *
     * @param location the value to store in the location field.
     * @param hasElevationProvider a flag used to inform the popup that an
     *            elevation provider is present.
     */
    public void setLocation(LatLonAlt location, boolean hasElevationProvider)
    {
        myLocation = location;
        myHasElevationProvider = hasElevationProvider;
    }
}
