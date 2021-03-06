package io.opensphere.core.geometry;

import org.junit.Assert;
import org.junit.Test;

import io.opensphere.core.geometry.constraint.Constraints;
import io.opensphere.core.geometry.constraint.TimeConstraint;
import io.opensphere.core.geometry.renderproperties.ColorRenderProperties;
import io.opensphere.core.geometry.renderproperties.DefaultPolygonRenderProperties;
import io.opensphere.core.geometry.renderproperties.PolygonRenderProperties;
import io.opensphere.core.model.GeographicPosition;
import io.opensphere.core.model.LatLonAlt;
import io.opensphere.core.model.LineType;
import io.opensphere.core.model.time.TimeSpan;

/** Test for {@link EllipseGeometry}. */
@SuppressWarnings("boxing")
public class EllipseGeometryTest
{
    /**
     * Test for {@link EllipseGeometry#clone()} .
     */
    @Test
    public void testClone()
    {
        EllipseGeometry.AngleBuilder<GeographicPosition> builder = new EllipseGeometry.AngleBuilder<GeographicPosition>();
        builder.setDataModelId(3423L);
        GeographicPosition position = new GeographicPosition(LatLonAlt.createFromDegrees(23., 56.));
        builder.setCenter(position);
        builder.setAngle(45.);
        builder.setSemiMajorAxis(23f);
        builder.setSemiMinorAxis(26f);
        builder.setLineSmoothing(true);
        builder.setLineType(LineType.GREAT_CIRCLE);
        builder.setRapidUpdate(true);

        PolygonRenderProperties renderProperties1 = new DefaultPolygonRenderProperties(65763, false, true);

        TimeConstraint timeConstraint = TimeConstraint.getTimeConstraint(TimeSpan.get(10L, 20L));
        Constraints constraints1 = new Constraints(timeConstraint);

        EllipseGeometry geom = new EllipseGeometry(builder, renderProperties1, constraints1);

        EllipseGeometry clone = geom.clone();

        Assert.assertNotSame(geom, clone);
        Assert.assertEquals(geom.getDataModelId(), clone.getDataModelId());
        Assert.assertEquals(geom.isLineSmoothing(), clone.isLineSmoothing());
        Assert.assertEquals(geom.getLineType(), clone.getLineType());
        Assert.assertEquals(geom.getVertices(), clone.getVertices());
        Assert.assertEquals(geom.isRapidUpdate(), clone.isRapidUpdate());
        Assert.assertNotSame(geom.getRenderProperties(), clone.getRenderProperties());
        Assert.assertEquals(geom.getRenderProperties(), clone.getRenderProperties());
        Assert.assertNotSame(geom.getConstraints(), clone.getConstraints());
        Assert.assertEquals(geom.getConstraints(), clone.getConstraints());
    }

    /**
     * Test for
     * {@link EllipseGeometry#derive(io.opensphere.core.geometry.renderproperties.BaseRenderProperties, io.opensphere.core.geometry.constraint.Constraints)}
     * .
     */
    @Test
    public void testDerive()
    {
        EllipseGeometry.AngleBuilder<GeographicPosition> builder = new EllipseGeometry.AngleBuilder<GeographicPosition>();
        builder.setDataModelId(3423L);
        GeographicPosition position = new GeographicPosition(LatLonAlt.createFromDegrees(23., 56.));
        builder.setCenter(position);
        builder.setAngle(45.);
        builder.setSemiMajorAxis(23f);
        builder.setSemiMinorAxis(26f);
        builder.setLineSmoothing(true);
        builder.setLineType(LineType.GREAT_CIRCLE);
        builder.setRapidUpdate(true);

        PolygonRenderProperties renderProperties1 = new DefaultPolygonRenderProperties(65763, false, true);

        TimeConstraint timeConstraint = TimeConstraint.getTimeConstraint(TimeSpan.get(10L, 20L));
        Constraints constraints1 = new Constraints(timeConstraint);

        EllipseGeometry geom = new EllipseGeometry(builder, renderProperties1, constraints1);
        AbstractRenderableGeometry absGeom = geom;

        ColorRenderProperties renderProperties2 = renderProperties1.clone();
        Constraints constraints2 = new Constraints(timeConstraint);
        AbstractRenderableGeometry derived = absGeom.derive(renderProperties2, constraints2);

        Assert.assertNotSame(geom, derived);
        Assert.assertEquals(geom.getDataModelId(), derived.getDataModelId());
        Assert.assertEquals(geom.isLineSmoothing(), ((EllipseGeometry)derived).isLineSmoothing());
        Assert.assertEquals(geom.getLineType(), ((EllipseGeometry)derived).getLineType());
        Assert.assertEquals(geom.getVertices(), ((EllipseGeometry)derived).getVertices());
        Assert.assertEquals(geom.isRapidUpdate(), derived.isRapidUpdate());
        Assert.assertNotSame(geom.getRenderProperties(), derived.getRenderProperties());
        Assert.assertSame(renderProperties2, derived.getRenderProperties());
        Assert.assertNotSame(geom.getConstraints(), derived.getConstraints());
        Assert.assertSame(constraints2, derived.getConstraints());
    }
}
