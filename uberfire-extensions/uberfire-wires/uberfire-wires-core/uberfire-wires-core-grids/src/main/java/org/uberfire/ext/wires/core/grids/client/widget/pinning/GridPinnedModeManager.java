/*
 * Copyright 2016 JBoss Inc
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
package org.uberfire.ext.wires.core.grids.client.widget.pinning;

import java.util.HashSet;
import java.util.Set;

import com.ait.lienzo.client.core.mediator.IMediator;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.types.Transform;
import com.google.gwt.user.client.Command;
import org.uberfire.commons.validation.PortablePreconditions;
import org.uberfire.ext.wires.core.grids.client.widget.IBaseGridWidget;
import org.uberfire.ext.wires.core.grids.client.widget.IGridLayer;
import org.uberfire.ext.wires.core.grids.client.widget.animation.GridWidgetEnterPinnedModeAnimation;
import org.uberfire.ext.wires.core.grids.client.widget.animation.GridWidgetExitPinnedModeAnimation;

public class GridPinnedModeManager implements IGridPinnedModeManager {

    private final IGridLayer gridLayer;
    private final IRestriction defaultRestriction;

    private PinnedContext context = null;

    public GridPinnedModeManager( final IGridLayer gridLayer,
                                  final IRestriction defaultRestriction ) {
        this.gridLayer = PortablePreconditions.checkNotNull( "gridLayer",
                                                             gridLayer );
        this.defaultRestriction = PortablePreconditions.checkNotNull( "defaultRestriction",
                                                                      defaultRestriction );
    }

    @Override
    public void enterPinnedMode( final IBaseGridWidget gridWidget,
                                 final Command onStartCommand ) {
        if ( context != null ) {
            return;
        }
        final Transform transform = gridWidget.getViewport().getTransform();
        final double translateX = transform.getTranslateX();
        final double translateY = transform.getTranslateY();
        final double scaleX = transform.getScaleX();
        final double scaleY = transform.getScaleY();
        final PinnedContext newState = new PinnedContext( gridWidget,
                                                          translateX,
                                                          translateY,
                                                          scaleX,
                                                          scaleY );
        final Set<IPrimitive<?>> allGridWidgetConnectors = gridLayer.getGridWidgetConnectors();
        final Set<IBaseGridWidget> allGridWidgets = new HashSet<>( gridLayer.getGridWidgets() );
        allGridWidgets.remove( gridWidget );

        final GridWidgetEnterPinnedModeAnimation enterAnimation = new GridWidgetEnterPinnedModeAnimation( gridWidget,
                                                                                                          allGridWidgets,
                                                                                                          allGridWidgetConnectors,
                                                                                                          new Command() {
                                                                                                              @Override
                                                                                                              public void execute() {
                                                                                                                  context = newState;
                                                                                                                  onStartCommand.execute();
                                                                                                              }
                                                                                                          } );
        enterAnimation.run();
    }

    @Override
    public void exitPinnedMode( final Command onCompleteCommand ) {
        if ( context == null ) {
            return;
        }
        final Set<IPrimitive<?>> allGridWidgetConnectors = gridLayer.getGridWidgetConnectors();
        final Set<IBaseGridWidget> allGridWidgets = new HashSet<>( gridLayer.getGridWidgets() );
        allGridWidgets.remove( context.getGridWidget() );

        final GridWidgetExitPinnedModeAnimation exitAnimation = new GridWidgetExitPinnedModeAnimation( context,
                                                                                                       allGridWidgets,
                                                                                                       allGridWidgetConnectors,
                                                                                                       new Command() {
                                                                                                           @Override
                                                                                                           public void execute() {
                                                                                                               context = null;
                                                                                                               onCompleteCommand.execute();
                                                                                                           }
                                                                                                       } );
        exitAnimation.run();
    }

    @Override
    public void updatePinnedContext( final IBaseGridWidget gridWidget ) throws IllegalStateException {
        if ( context == null ) {
            throw new IllegalStateException( "'pinned' mode has not been entered." );
        }

        for ( IMediator mediator : gridLayer.getViewport().getMediators() ) {
            if ( mediator instanceof RestrictedMousePanMediator ) {
                ( (RestrictedMousePanMediator) mediator ).setRestriction( new GridRestriction( gridWidget ) );
            }
        }

        final Transform transform = gridWidget.getViewport().getTransform();
        final double scaleX = context.getScaleX();
        final double scaleY = context.getScaleY();
        final double translateX = transform.getTranslateX() * scaleX;
        final double translateY = transform.getTranslateY() * scaleY;
        context = new PinnedContext( gridWidget,
                                     translateX,
                                     translateY,
                                     context.getScaleX(),
                                     context.getScaleY() );
    }

    @Override
    public PinnedContext getPinnedContext() {
        return context;
    }

    @Override
    public boolean isGridPinned() {
        return context != null;
    }

    @Override
    public IRestriction getDefaultRestriction() {
        return defaultRestriction;
    }
}
