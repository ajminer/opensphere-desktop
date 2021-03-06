package io.opensphere.myplaces.specific.regions;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import io.opensphere.core.Toolbox;
import io.opensphere.core.control.action.ContextActionManager;
import io.opensphere.core.control.action.ContextMenuProvider;
import io.opensphere.core.control.action.context.ContextIdentifiers;
import io.opensphere.core.control.action.context.GeometryContextKey;
import io.opensphere.core.geometry.Geometry;
import io.opensphere.core.geometry.GeometryGroupGeometry;
import io.opensphere.core.geometry.MultiPolygonGeometry;
import io.opensphere.core.geometry.PolygonGeometry;
import io.opensphere.core.geometry.PolylineGeometry;
import io.opensphere.core.model.Altitude;
import io.opensphere.core.model.LatLonAlt;
import io.opensphere.core.model.Position;
import io.opensphere.core.util.AwesomeIconSolid;
import io.opensphere.core.util.Utilities;
import io.opensphere.core.util.collections.New;
import io.opensphere.core.util.lang.StringUtilities;
import io.opensphere.core.util.swing.GenericFontIcon;
import io.opensphere.mantle.data.MapVisualizationType;
import io.opensphere.myplaces.models.MyPlacesDataTypeInfo;
import io.opensphere.myplaces.models.MyPlacesModel;
import io.opensphere.myplaces.specific.PlaceTypeController;
import io.opensphere.myplaces.specific.regions.utils.RegionUtils;

/**
 * Controls specific interactions related to regions.
 */
public class RegionTypeController extends PlaceTypeController
{
    /** Menu provider for geometry context. */
    private GeometryContextMenuProvider myGeometryContextMenuProvider;

    /** The Places model. */
    private MyPlacesModel myPlacesModel;

    /**
     * The toolbox.
     */
    private Toolbox myToolbox;

    @Override
    public void close()
    {
    }

    @Override
    public MapVisualizationType getVisualizationType()
    {
        return MapVisualizationType.ANNOTATION_REGIONS;
    }

    @Override
    public void initialize(Toolbox toolbox, MyPlacesModel model)
    {
        super.initialize(toolbox, model);
        myToolbox = toolbox;
        myPlacesModel = Utilities.checkNull(model, "model");

        if (myToolbox.getUIRegistry() != null)
        {
            ContextActionManager actionManager = toolbox.getUIRegistry().getContextActionManager();

            myGeometryContextMenuProvider = new GeometryContextMenuProvider();
            actionManager.registerContextMenuItemProvider(ContextIdentifiers.GEOMETRY_COMPLETED_CONTEXT, GeometryContextKey.class,
                    myGeometryContextMenuProvider);
            actionManager.registerContextMenuItemProvider(ContextIdentifiers.GEOMETRY_SELECTION_CONTEXT, GeometryContextKey.class,
                    myGeometryContextMenuProvider);
        }
    }

    @Override
    public void setLocationInformation(MyPlacesDataTypeInfo dataType)
    {
        Placemark placemark = dataType.getKmlPlacemark();
        if (placemark.getGeometry() instanceof Polygon)
        {
            Polygon polygon = (Polygon)placemark.getGeometry();
            List<Coordinate> coords = polygon.getOuterBoundaryIs().getLinearRing().getCoordinates();
            if (!coords.isEmpty())
            {
                Collection<LatLonAlt> llas = new LinkedList<>();
                for (int index = 0; index < coords.size(); ++index)
                {
                    Coordinate position = coords.get(index);
                    LatLonAlt location = LatLonAlt.createFromDegreesMeters(position.getLatitude(), position.getLongitude(), 0,
                            Altitude.ReferenceLevel.TERRAIN);
                    llas.add(location);
                }
                dataType.addLocations(llas);
            }
        }
    }

    /**
     * Get a name for a new region from the user.
     *
     * @param parentComponent The parent component for the dialog.
     * @return The name, or {@code null} if the dialog was cancelled.
     */
    protected String getRegionName(JFrame parentComponent)
    {
        while (true)
        {
            String name = JOptionPane.showInputDialog(parentComponent, "Please select a name for the region of interest");
            if (name == null || !name.isEmpty())
            {
                return name;
            }
            JOptionPane.showMessageDialog(parentComponent, "Please enter a name for the region.", "Need to provide a name",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Creates the save roi from the geometry.
     *
     * @param geometry the geometry
     */
    public void createRegion(Geometry geometry)
    {
        if (geometry instanceof MultiPolygonGeometry)
        {
            createRegionFromMultiPolygonGeometry((MultiPolygonGeometry)geometry);
        }
        else if (geometry instanceof PolygonGeometry)
        {
            createRegionFromGeometry((PolygonGeometry)geometry);
        }
        else if (geometry instanceof GeometryGroupGeometry)
        {
            createRegionFromMultiGeometry((GeometryGroupGeometry)geometry);
        }
        else
        {
            throw new UnsupportedOperationException(
                    "Geometries of type '" + geometry.getClass().getName() + "' are not supported.");
        }
    }

    /**
     * Creates the save roi from multiple geometries.
     *
     * @param geometries the group of geometries
     */
    protected void createRegionFromMultiGeometry(GeometryGroupGeometry geometries)
    {
        assert EventQueue.isDispatchThread();

        List<Position> vertices = New.list();
        geometries.getGeometries().stream().filter(g -> g instanceof PolygonGeometry)
                .forEach(g -> vertices.addAll(((PolygonGeometry)g).getVertices()));

        Collection<List<? extends Position>> holes = New.collection();
        geometries.getGeometries().stream().filter(g -> g instanceof PolygonGeometry)
                .forEach(g -> ((PolygonGeometry)g).getHoles().forEach(e -> holes.add(e)));

        Placemark placemark = RegionUtils.createRegionFromPositions(new Folder(), getDefaultName(), vertices, holes);
        launchEditor(placemark, myPlacesModel.getDataGroups());
    }

    /**
     * Creates the save roi from a multi polygon geometry.
     *
     * @param geometry the geometry
     */
    protected void createRegionFromMultiPolygonGeometry(MultiPolygonGeometry geometry)
    {
        assert EventQueue.isDispatchThread();

        Placemark placemark = RegionUtils.multiPolygonPlacemark(getDefaultName(), geometry.getGeometries());
        launchEditor(placemark, myPlacesModel.getDataGroups());
    }

    /**
     * Creates the save roi from geometry.
     *
     * @param geometry the geometry
     */
    protected void createRegionFromGeometry(PolygonGeometry geometry)
    {
        assert EventQueue.isDispatchThread();

        Placemark placemark = RegionUtils.createRegionFromPositions(new Folder(), getDefaultName(), geometry.getVertices(),
                geometry.getHoles());
        launchEditor(placemark, myPlacesModel.getDataGroups());
    }

    /**
     * Creates the save roi from geometry.
     *
     * @param geometry the geometry
     */
    protected void createLineFromGeometry(PolylineGeometry geometry)
    {
        assert EventQueue.isDispatchThread();

        Placemark placemark = RegionUtils.createLineFromPositions(new Folder(), getDefaultName(), geometry.getVertices());
        launchEditor(placemark, myPlacesModel.getDataGroups());
    }

    /**
     * Gets a default name for the next region.
     *
     * @return the name
     */
    private String getDefaultName()
    {
        Set<String> names = new TreeSet<>();
        Function<Placemark, Void> func = input ->
        {
            if (input.getGeometry() instanceof Polygon && input.getName().startsWith("Region-"))
            {
                names.add(input.getName());
            }
            return null;
        };

        myPlacesModel.applyToEachPlacemark(func);

        return StringUtilities.getUniqueName("Region-", names);
    }

    /**
     * Geometry ContextMenuProvider.
     */
    private class GeometryContextMenuProvider implements ContextMenuProvider<GeometryContextKey>
    {
        @Override
        public List<Component> getMenuItems(String contextId, GeometryContextKey key)
        {
            List<Component> options = new LinkedList<>();
            if ((ContextIdentifiers.GEOMETRY_COMPLETED_CONTEXT.equals(contextId)
                    || ContextIdentifiers.GEOMETRY_SELECTION_CONTEXT.equals(contextId))
                    && (key.getGeometry() instanceof PolygonGeometry || key.getGeometry() instanceof PolylineGeometry))
            {
                JMenuItem saveMI = new JMenuItem("Save as Place");
                saveMI.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                saveMI.setIcon(new GenericFontIcon(AwesomeIconSolid.MAP_MARKER_ALT, Color.WHITE));
                if (key.getGeometry() instanceof PolygonGeometry)
                {
                    saveMI.addActionListener(e -> createRegionFromGeometry((PolygonGeometry)key.getGeometry()));
                }
                else if (key.getGeometry() instanceof PolylineGeometry)
                {
                    saveMI.addActionListener(e -> createLineFromGeometry((PolylineGeometry)key.getGeometry()));
                }
                options.add(saveMI);
            }

            return options;
        }

        @Override
        public int getPriority()
        {
            return 11400;
        }
    }
}
