/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uberfire.ext.wires.core.grids.client.widget.pinning;

import com.ait.lienzo.client.core.types.Transform;
import org.uberfire.ext.wires.core.grids.client.model.IBounds;

public class BoundaryRestriction implements IRestriction {

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public BoundaryRestriction() {
        this.minX = Double.NEGATIVE_INFINITY;
        this.minY = Double.NEGATIVE_INFINITY;
        this.maxX = Double.POSITIVE_INFINITY;
        this.maxY = Double.POSITIVE_INFINITY;
    }

    public BoundaryRestriction( final IBounds bounds ) {
        setBounds( bounds );
    }

    public void setBounds( final IBounds bounds ) {
        this.minX = bounds.getX();
        this.minY = bounds.getY();
        this.maxX = minX + bounds.getWidth();
        this.maxY = minY + bounds.getHeight();
    }

    @Override
    public Transform adjust( final Transform transform,
                             final IBounds visibleBounds ) {
        Transform newTransform = transform.copy();

        final double scaleX = transform.getScaleX();
        final double scaleY = transform.getScaleY();
        final double translateX = newTransform.getTranslateX();
        final double translateY = newTransform.getTranslateY();
        final double scaledTranslateX = translateX / scaleX;
        final double scaledTranslateY = translateY / scaleY;
        final double visibleBoundsWidth = visibleBounds.getWidth();
        final double visibleBoundsHeight = visibleBounds.getHeight();

        if ( -scaledTranslateX < minX ) {
            newTransform = newTransform.translate( -scaledTranslateX - minX,
                                                   0 );
        }
        if ( -scaledTranslateY < minY ) {
            newTransform = newTransform.translate( 0,
                                                   -scaledTranslateY - minY );
        }
        if ( -scaledTranslateX + visibleBoundsWidth > maxX ) {
            newTransform = newTransform.translate( -scaledTranslateX + visibleBoundsWidth - maxX,
                                                   0 );

        }
        if ( -scaledTranslateY + visibleBoundsHeight > maxY ) {
            newTransform = newTransform.translate( 0,
                                                   -scaledTranslateY + visibleBoundsHeight - maxY );

        }

        return newTransform;
    }

}
