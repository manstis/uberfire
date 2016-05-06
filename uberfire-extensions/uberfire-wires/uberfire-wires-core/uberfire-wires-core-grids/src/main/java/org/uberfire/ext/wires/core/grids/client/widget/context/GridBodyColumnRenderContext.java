/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.uberfire.ext.wires.core.grids.client.widget.context;

import java.util.List;

import com.ait.lienzo.client.core.types.Transform;
import org.uberfire.ext.wires.core.grids.client.model.IGridData;
import org.uberfire.ext.wires.core.grids.client.widget.renderers.grids.IGridRenderer;

/**
 * The context of a Grid's column during the rendering phase.
 */
public class GridBodyColumnRenderContext {

    private final double absoluteGridX;
    private final double absoluteGridY;
    private final double absoluteColumnX;
    private final double clipMinY;
    private final double clipMinX;
    private final int minVisibleRowIndex;
    private final int maxVisibleRowIndex;
    private final List<Double> rowOffsets;
    private final boolean isSelectionLayer;
    private final boolean isFloating;
    private final IGridData model;
    private final Transform transform;
    private final IGridRenderer renderer;

    public GridBodyColumnRenderContext( final double absoluteGridX,
                                        final double absoluteGridY,
                                        final double absoluteColumnX,
                                        final double clipMinY,
                                        final double clipMinX,
                                        final int minVisibleRowIndex,
                                        final int maxVisibleRowIndex,
                                        final List<Double> rowOffsets,
                                        final boolean isSelectionLayer,
                                        final boolean isFloating,
                                        final IGridData model,
                                        final Transform transform,
                                        final IGridRenderer renderer ) {
        this.absoluteGridX = absoluteGridX;
        this.absoluteGridY = absoluteGridY;
        this.absoluteColumnX = absoluteColumnX;
        this.clipMinY = clipMinY;
        this.clipMinX = clipMinX;
        this.minVisibleRowIndex = minVisibleRowIndex;
        this.maxVisibleRowIndex = maxVisibleRowIndex;
        this.rowOffsets = rowOffsets;
        this.isSelectionLayer = isSelectionLayer;
        this.isFloating = isFloating;
        this.model = model;
        this.transform = transform;
        this.renderer = renderer;
    }

    /**
     * The canvas x-coordinate of the Grid; not transformed.
     * @return
     */
    public double getAbsoluteGridX() {
        return absoluteGridX;
    }

    /**
     * The canvas y-coordinate of the Grid; not transformed.
     * @return
     */
    public double getAbsoluteGridY() {
        return absoluteGridY;
    }

    /**
     * The canvas x-coordinate of the Column; not transformed.
     * @return
     */
    public double getAbsoluteColumnX() {
        return absoluteColumnX;
    }

    /**
     * The minimum Y coordinate for visible content. Content outside the region should be clipped.
     * @return
     */
    public double getClipMinY() {
        return clipMinY;
    }

    /**
     * The minimum X coordinate for visible content. Content outside the region should be clipped.
     * @return
     */
    public double getClipMinX() {
        return clipMinX;
    }

    /**
     * The index of the first row being rendered.
     * @return
     */
    public int getMinVisibleRowIndex() {
        return minVisibleRowIndex;
    }

    /**
     * The index of the last row being rendered.
     * @return
     */
    public int getMaxVisibleRowIndex() {
        return maxVisibleRowIndex;
    }

    /**
     * The row offsets based from zero; for each row to be rendered. The minVisibleRowIndex corresponds to
     * index zero and maxVisibleRowIndex corresponds to rowOffsets.size()-1. This is useful to calculate
     * the Y co-ordinate of each Row's top. It is calculated once and passed to each column as an
     * optimissation to prevent each column from recalculating the same values.
     * @return
     */
    public List<Double> getRowOffsets() {
        return rowOffsets;
    }

    /**
     * Is the SelectionLayer being rendered.
     * @return
     */
    public boolean isSelectionLayer() {
        return isSelectionLayer;
    }

    public boolean isFloating() {
        return isFloating;
    }

    public IGridData getModel() {
        return model;
    }

    /**
     * The transformation of the Grid Widget.
     * @return
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     * The Renderer for the Grid Widget.
     * @return
     */
    public IGridRenderer getRenderer() {
        return renderer;
    }

}
