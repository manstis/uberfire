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

package org.uberfire.ext.wires.core.grids.client.model;

import org.junit.Test;

public class GridRowsUnmergedTest extends BaseGridTest {

    @Test
    public void testRemoveRow() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                final String value = ( rowIndex > 0 && rowIndex < 4 ? "b" : "a" );
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( value ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false, false },
                           new boolean[]{ false, false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "a" ) }
                           } );

        data.deleteRow( 2 );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "a" ) }
                           } );
    }

    @Test
    public void testAppendRow() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                final String value = ( rowIndex < 2 ? "a" : "b" );
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( value ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );

        data.appendRow( new BaseGridRow() );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false, false },
                           new boolean[]{ false, false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, null ) }
                           } );
    }

    @Test
    public void testInsertRowAtZeroIndex() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                final String value = ( rowIndex < 2 ? "a" : "b" );
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( value ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );

        data.insertRow( 0,
                        new BaseGridRow() );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false, false },
                           new boolean[]{ false, false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, null ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );
    }

    @Test
    public void testInsertRowAtStartEndBlock() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                final String value = ( rowIndex < 2 ? "a" : "b" );
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( value ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );

        data.insertRow( 2,
                        new BaseGridRow() );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false, false },
                           new boolean[]{ false, false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, null ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );
    }

    @Test
    public void testInsertRowAtMidBlock() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( "a" ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) }
                           } );

        data.insertRow( 2,
                        new BaseGridRow() );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false, false },
                           new boolean[]{ false, false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, null ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) }
                           } );
    }

    @Test
    public void testDeleteRowAtZeroIndex() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                final String value = ( rowIndex < 2 ? "a" : "b" );
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( value ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );

        data.deleteRow( 0 );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false },
                           new boolean[]{ false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );
    }

    @Test
    public void testDeleteRowAtStartEndBlock() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                final String value = ( rowIndex < 2 ? "a" : "b" );
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( value ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );

        data.deleteRow( 2 );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false },
                           new boolean[]{ false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "b" ) }
                           } );
    }

    @Test
    public void testDeleteRowAtMidBlock() {
        final IGridData data = new BaseGridData( false );
        final IGridColumn<String> gc1 = new MockMergableGridColumn<String>( "col1",
                                                                            100 );
        data.appendColumn( gc1 );

        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );
        data.appendRow( new BaseGridRow() );

        for ( int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++ ) {
            for ( int columnIndex = 0; columnIndex < data.getColumns().size(); columnIndex++ ) {
                data.setCell( rowIndex,
                              columnIndex,
                              new BaseGridCellValue<String>( "a" ) );
            }
        }

        assertGridIndexes( data,
                           new boolean[]{ false, false, false, false },
                           new boolean[]{ false, false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) }
                           } );

        data.deleteRow( 2 );

        assertGridIndexes( data,
                           new boolean[]{ false, false, false },
                           new boolean[]{ false, false, false },
                           new Expected[][]{
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) },
                                   { Expected.build( false, 1, "a" ) }
                           } );
    }

}
