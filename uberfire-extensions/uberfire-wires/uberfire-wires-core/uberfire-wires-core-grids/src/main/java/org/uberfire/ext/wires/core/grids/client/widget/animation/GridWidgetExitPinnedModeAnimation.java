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
package org.uberfire.ext.wires.core.grids.client.widget.animation;

import java.util.Set;

import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationCallback;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.animation.TimedAnimation;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Viewport;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Transform;
import com.google.gwt.user.client.Command;
import org.uberfire.ext.wires.core.grids.client.widget.IBaseGridWidget;
import org.uberfire.ext.wires.core.grids.client.widget.pinning.IGridPinnedModeManager;

/**
 * An animation to reposition the ViewPort so the given GridWidget is centred
 * horizontally and GridWidget header aligned with the top of the ViewPort.
 */
public class GridWidgetExitPinnedModeAnimation extends TimedAnimation {

    public GridWidgetExitPinnedModeAnimation( final IGridPinnedModeManager.PinnedContext state,
                                              final Set<IBaseGridWidget> gridWidgets,
                                              final Set<IPrimitive<?>> gridWidgetConnectors,
                                              final Command onCompleteCommand ) {
        super( 500,
               new IAnimationCallback() {

                   private Point2D delta;
                   private Point2D startTranslation;
                   private IBaseGridWidget gridWidget;

                   private final double startScaleX = 1.0;
                   private final double startScaleY = 1.0;
                   private double endScaleX;
                   private double endScaleY;

                   private AnimationTweener tweener = AnimationTweener.EASE_OUT;

                   @Override
                   public void onStart( final IAnimation animation,
                                        final IAnimationHandle handle ) {
                       gridWidget = state.getGridWidget();
                       final Viewport vp = gridWidget.getViewport();
                       if ( vp.getTransform() == null ) {
                           vp.setTransform( new Transform() );
                       }
                       endScaleX = state.getScaleX();
                       endScaleY = state.getScaleY();
                       startTranslation = getViewportTranslation().mul( -1.0 );

                       final Point2D endTranslation = new Point2D( state.getTranslateX(),
                                                                   state.getTranslateY() );

                       delta = new Point2D( endTranslation.getX() - startTranslation.getX(),
                                            endTranslation.getY() - startTranslation.getY() );

                       for ( IBaseGridWidget gw : gridWidgets ) {
                           gw.setVisible( true );
                       }
                       for ( IPrimitive<?> p : gridWidgetConnectors ) {
                           p.setVisible( true );
                       }

                       gridWidget.getLayer().setListening( false );
                       gridWidget.getLayer().batch();
                   }

                   @Override
                   public void onFrame( final IAnimation animation,
                                        final IAnimationHandle handle ) {
                       final double pct = assertPct( animation.getPercent() );
                       final Viewport vp = gridWidget.getViewport();
                       final Transform transform = vp.getTransform();
                       transform.reset();

                       final Point2D frameLocation = startTranslation.add( delta.mul( pct ) );
                       final double frameScaleX = startScaleX + ( endScaleX - startScaleX ) * pct;
                       final double frameScaleY = startScaleY + ( endScaleY - startScaleY ) * pct;
                       transform.translate( frameLocation.getX(),
                                            frameLocation.getY() ).scale( frameScaleX,
                                                                          frameScaleY );

                       showGridWidgets( pct );
                       showGridWidgetConnectors( pct );

                       gridWidget.getLayer().batch();
                   }

                   @Override
                   public void onClose( final IAnimation animation,
                                        final IAnimationHandle handle ) {
                       gridWidget.getLayer().setListening( true );
                       gridWidget.getLayer().batch();

                       onCompleteCommand.execute();
                   }

                   private Point2D getViewportTranslation() {
                       final Viewport vp = gridWidget.getViewport();
                       final Transform transform = vp.getTransform();
                       final Transform t = transform.copy().getInverse();
                       final Point2D p = new Point2D( t.getTranslateX(),
                                                      t.getTranslateY() );
                       return p;
                   }

                   private double assertPct( final double pct ) {
                       if ( pct < 0 ) {
                           return 0;
                       }
                       if ( pct > 1.0 ) {
                           return 1.0;
                       }
                       return tweener.apply( pct );
                   }

                   private void showGridWidgets( final double pct ) {
                       for ( IBaseGridWidget gw : gridWidgets ) {
                           gw.setAlpha( pct );
                       }
                   }

                   private void showGridWidgetConnectors( final double pct ) {
                       for ( IPrimitive<?> p : gridWidgetConnectors ) {
                           p.setAlpha( pct );
                       }
                   }

               } );
    }

}
