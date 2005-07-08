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
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JFileChooser;

import org.devyant.magicbeans.WrapperTestCase;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.Matcher;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;

/**
 * FileComponentTest is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 4, 2005 5:13:23 AM
 */
public class FileComponentTest extends WrapperTestCase {

    /**
     * Creates a new <code>FileComponentTest</code> instance.
     * @param testName
     */
    public FileComponentTest(String testName) {
        super(testName);
    }
    
    /**
     * Tests the SwingFileComponent
     * @throws Exception
     */
    public void testComponent() throws Exception {
        testComponent(new File("src/conf/magic.properties"),
                SwingFileComponent.class, new File("src/conf/magic.xml").getAbsoluteFile());
    }

    /**
     * @throws MultipleComponentsFoundException 
     * @throws ComponentNotFoundException 
     * @see org.devyant.magicbeans.AbstractTestCase#messWithComponent(java.awt.Component, java.lang.Object)
     */
    protected void messWithComponent(Component component, Object expected)
            throws ComponentNotFoundException, MultipleComponentsFoundException {
        final Component button = getFinder().find(new Matcher() {
            public boolean matches(Component c) {
                return AbstractButton.class.isAssignableFrom(c.getClass())
                    && c.getParent().getClass() == SwingFileComponent.class;
            }
        });
        tester.actionClick(button);
        
        final JFileChooser fileChooser = (JFileChooser) getFinder()
                .find(null, new ClassMatcher(JFileChooser.class));
        fileChooser.setSelectedFile(((File) expected));
        fileChooser.approveSelection();
        /*final JFileChooserTester fileTester = new JFileChooserTester();
        fileTester.actionSetDirectory(fileChooser, ((File) expected).toString());
        fileTester.actionSetFilename(fileChooser,
                ((File) expected).toString());
        fileTester.actionApprove(fileChooser);*/
        /*final Component openButton = getFinder().find(new Matcher() {
            public boolean matches(Component c) {
                return c instanceof AbstractButton
                    && c != null
                    && "Open".equals(((AbstractButton) c).getText());
            }
        });
        tester.actionClick(openButton);*/
    }
}
