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
package org.uberfire.ext.wires.core.grids.client.widget.dom;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.ListBox;
import org.uberfire.ext.wires.core.grids.client.model.BaseGridCellValue;
import org.uberfire.ext.wires.core.grids.client.widget.IBaseGridWidget;
import org.uberfire.ext.wires.core.grids.client.widget.IGridLayer;
import org.uberfire.ext.wires.core.grids.client.widget.context.GridBodyCellRenderContext;

/**
 * A DOMElement for TextBoxes.
 */
public class ListBoxDOMElement extends BaseDOMElement<String, ListBox> {

    private static final int HEIGHT = 16;

    public ListBoxDOMElement( final ListBox widget,
                              final IGridLayer gridLayer,
                              final IBaseGridWidget gridWidget ) {
        super( widget,
               gridLayer,
               gridWidget );
        final Style style = widget.getElement().getStyle();
        style.setWidth( 100,
                        Style.Unit.PCT );
        style.setHeight( HEIGHT,
                         Style.Unit.PX );
        style.setPaddingLeft( 2,
                              Style.Unit.PX );
        style.setPaddingRight( 2,
                               Style.Unit.PX );
        style.setFontSize( 10,
                           Style.Unit.PX );

        // --- Workaround for BS2 ---
        style.setPosition( Style.Position.RELATIVE );
        style.setPaddingTop( 0,
                             Style.Unit.PX );
        style.setPaddingBottom( 0,
                                Style.Unit.PX );
        style.setProperty( "WebkitBoxSizing",
                           "border-box" );
        style.setProperty( "MozBoxSizing",
                           "border-box" );
        style.setProperty( "boxSizing",
                           "border-box" );
        style.setProperty( "lineHeight",
                           "normal" );
        // --- End workaround ---

        getContainer().getElement().getStyle().setPaddingLeft( 5,
                                                               Style.Unit.PX );
        getContainer().getElement().getStyle().setPaddingRight( 5,
                                                                Style.Unit.PX );
        getContainer().setWidget( widget );
    }

    @Override
    public void initialise( final GridBodyCellRenderContext context ) {
        final Style style = widget.getElement().getStyle();
        style.setMarginTop( ( context.getCellHeight() - HEIGHT ) / 2,
                            Style.Unit.PX );
        transform( context );
    }

    @Override
    public void flush( final String value ) {
        final int rowIndex = context.getRowIndex();
        final int columnIndex = context.getColumnIndex();
        if ( value == null || value.trim().isEmpty() ) {
            gridWidget.getModel().deleteCell( rowIndex,
                                              columnIndex );

        } else {
            gridWidget.getModel().setCell( rowIndex,
                                           columnIndex,
                                           new BaseGridCellValue<String>( value ) );
        }
    }

}
