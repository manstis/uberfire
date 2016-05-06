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
package org.uberfire.ext.wires.core.grids.client.model;

import org.uberfire.ext.wires.core.grids.client.widget.selections.CellRangeSelectionManager;
import org.uberfire.ext.wires.core.grids.client.widget.selections.ICellSelectionManager;

/**
 * Base implementation of a grid cell to avoid boiler-plate for more specific implementations.
 * @param <T> The Type of value
 */
public class BaseGridCell<T> implements IGridCell<T> {

    private int collapseLevel = 0;
    private int mergedCellCount = 1;
    private ICellSelectionManager selectionManager = CellRangeSelectionManager.INSTANCE;

    protected IGridCellValue<T> value;

    public BaseGridCell( final IGridCellValue<T> value ) {
        this.value = value;
    }

    @Override
    public IGridCellValue<T> getValue() {
        return value;
    }

    @Override
    public boolean isMerged() {
        return getMergedCellCount() != 1;
    }

    @Override
    public int getMergedCellCount() {
        return mergedCellCount;
    }

    @Override
    public boolean isCollapsed() {
        return collapseLevel > 0;
    }

    @Override
    public void collapse() {
        collapseLevel++;
    }

    @Override
    public void expand() {
        collapseLevel--;
    }

    @Override
    public void reset() {
        mergedCellCount = 1;
        collapseLevel = 0;
    }

    @Override
    public ICellSelectionManager getSelectionManager() {
        return selectionManager;
    }

    @Override
    public void setSelectionManager( final ICellSelectionManager selectionManager ) {
        this.selectionManager = selectionManager;
    }

    //This is not part of the IGridCell interface as we don't want to expose this for general use
    void setValue( final IGridCellValue<T> value ) {
        this.value = value;
    }

    //This is not part of the IGridCell interface as we don't want to expose this for general use
    void setMergedCellCount( final int mergedCellCount ) {
        this.mergedCellCount = mergedCellCount;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof BaseGridCell ) ) {
            return false;
        }

        BaseGridCell<?> that = (BaseGridCell<?>) o;

        return !( value != null ? !value.equals( that.value ) : that.value != null );
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        return result;
    }

}
