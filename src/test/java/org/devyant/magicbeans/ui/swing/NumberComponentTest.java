/*
 * Magic Beans: a library for GUI generation and component-bean mapping.
 * Copyright (C) 2005  Filipe Tavares
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * devYant, devyant@devyant.org
 * Rua Simao Bolivar 203 6C, 4470-214 Maia, Portugal.
 *
 */
package org.devyant.magicbeans.ui.swing;

import java.awt.Component;

import org.devyant.magicbeans.BasicTestCase;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.tester.JTextComponentTester;

/**
 * NumberComponentTest is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 5, 2005 1:10:16 AM
 */
public class NumberComponentTest extends BasicTestCase {

    /**
     * Creates a new <code>NumberComponentTest</code> instance.
     * @param testName
     */
    public NumberComponentTest(String testName) {
        super(testName);
    }
    
    /**
     * Tests the NumberComponent
     * @throws Exception
     */
    public void testComponent() throws Exception {
        testComponent(new IntWrapper(1),
                SwingNumberComponent.class, new IntWrapper(6));
    }

    /**
     * @see org.devyant.magicbeans.AbstractTestCase#messWithComponent(java.awt.Component, java.lang.Object)
     */
    protected void messWithComponent(Component component, Object expected)
            throws ComponentNotFoundException, MultipleComponentsFoundException {
        final JTextComponentTester textTester = new JTextComponentTester();
        textTester.actionSelectText(component, 0, 1);
        textTester.actionEnterText(component, expected.toString());
    }

    public static class IntWrapper {
        private int myInt;
        
        /**
         * Creates a new <code>NumberComponentTest.IntWrapper</code> instance.
         */
        public IntWrapper(int i) {
            myInt = i;
        }

        /**
         * The getter method for the myInt property.
         * @return The property's <code>NumberComponentTest.IntWrapper</code> value
         */
        public int getMyInt() {
            return this.myInt;
        }

        /**
         * The setter method for the myInt property.
         * @param myInt The <code>int</code> to set
         */
        public void setMyInt(int myInt) {
            this.myInt = myInt;
        }
        
        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj) {
            return this.myInt == ((IntWrapper) obj).myInt;
        }
        
        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return Integer.toString(myInt);
        }
    }
}
