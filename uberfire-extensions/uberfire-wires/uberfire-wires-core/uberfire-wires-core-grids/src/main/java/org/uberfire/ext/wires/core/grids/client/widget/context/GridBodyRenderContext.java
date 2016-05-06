/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
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
import org.uberfire.ext.wires.core.grids.client.model.IGridColumn;
import org.uberfire.ext.wires.core.grids.client.widget.renderers.grids.IGridRenderer;
import org.uberfire.ext.wires.core.grids.client.widget.renderers.grids.ISelectionsTransformer;

/**
 * The context of a Grid's body during the rendering phase.
 */
public class GridBodyRenderContext {

    private final double absoluteGridX;
    private final double absoluteGridY;
    private final double clipMinY;
    private final double clipMinX;
    private final List<IGridColumn<?>> blockColumns;
    private final double absoluteColumnOffsetX;
    private final int minVisibleRowIndex;
    private final int maxVisibleRowIndex;
    private final boolean isSelectionLayer;
    private final Transform transform;
    private final IGridRenderer renderer;
    private final ISelectionsTransformer transformer;

    public GridBodyRenderContext( final double absoluteGridX,
                                  final double absoluteGridY,
                                  final double absoluteColumnOffsetX,
                                  final double clipMinY,
                                  final double clipMinX,
                                  final int minVisibleRowIndex,
                                  final int maxVisibleRowIndex,
                                  final List<IGridColumn<?>> blockColumns,
                                  final boolean isSelectionLayer,
                                  final Transform transform,
                                  final IGridRenderer renderer,
                                  final ISelectionsTransformer transformer ) {
        this.absoluteGridX = absoluteGridX;
        this.absoluteGridY = absoluteGridY;
        this.absoluteColumnOffsetX = absoluteColumnOffsetX;
        this.clipMinY = clipMinY;
        this.clipMinX = clipMinX;
        this.minVisibleRowIndex = minVisibleRowIndex;
        this.maxVisibleRowIndex = maxVisibleRowIndex;
        this.blockColumns = blockColumns;
        this.isSelectionLayer = isSelectionLayer;
        this.transform = transform;
        this.renderer = renderer;
        this.transformer = transformer;
    }

    /**
     * The canvas x-coordinate of the Grid; not transformed.
     * @return
     */
    public double getAbsoluteGridX() {
        return absoluteGridX;
    }

    /**
     * The canvas y-coordinate of the Grid; not transformed
     * @return
     */
    public double getAbsoluteGridY() {
        return absoluteGridY;
    }

    /**
     * The absolute offset from absoluteGridX that the first column needs to be rendered.
     * Only visible columns are rendered; and so the first column to be rendered needs to
     * be offset from the Grid's absolute X co-ordinate.
     * @return
     */
    public double getAbsoluteColumnOffsetX() {
        return absoluteColumnOffsetX;
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
     * The columns to render for the block.
     * @return
     */
    public List<IGridColumn<?>> getBlockColumns() {
        return blockColumns;
    }

    /**
     * Is the SelectionLayer being rendered.
     * @return
     */
    public boolean isSelectionLayer() {
        return isSelectionLayer;
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

    /**
     * The Column Index transformer supporting Floating Columns.
     * @return
     */
    public ISelectionsTransformer getTransformer() {
        return transformer;
    }

}
