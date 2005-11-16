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
import java.util.Date;

import org.devyant.magicbeans.BasicTestCase;


/**
 * DateComponentTest is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 3, 2005 5:10:22 AM
 */
public class DateComponentTest extends BasicTestCase {
    /**
     * Creates a new <code>StringComponentTest</code> instance.
     * @param name
     */
    public DateComponentTest(String name) {
        super(name);
    }
    
    /**
     * Tests the SwingDateComponent
     * @throws Exception
     */
    public void testComponent() throws Exception {
        testComponent(new Date(),
                SwingDateComponent.class, new Date(0));
    }
    
    /**
     * @see org.devyant.magicbeans.AbstractTestCase#messWithComponent(java.awt.Component, java.lang.Object)
     */
    protected void messWithComponent(Component component, Object expected) {
        ((SwingDateComponent) component).setValue(expected);
    }
}
