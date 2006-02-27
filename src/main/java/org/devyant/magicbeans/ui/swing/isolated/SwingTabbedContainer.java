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
package org.devyant.magicbeans.ui.swing.isolated;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.devyant.magicbeans.ui.isolated.TabbedContainer;

/**
 * SwingTabbedComponent is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Aug 23, 2005 12:58:33 PM
 * @todo I'm starting to think this logic would be better off on the layout.
 * It makes no sense to spread the toolkit specific logic around the block...
 */
public class SwingTabbedContainer extends JTabbedPane implements TabbedContainer {
    /**
     * The serialVersionUID <code>long</code>.
     */
    private static final long serialVersionUID = -1143189271363061352L;
    
    /**
     * The mainPanel <code>JPanel</code>.
     */
    private final JPanel mainPanel = new JPanel();
    
    /**
     * Creates a new <code>SwingTabbedContainer</code> instance.
     */
    public SwingTabbedContainer() {
        this.addTab("Main?", mainPanel); //TODO
    }

    /**
     * @see org.devyant.magicbeans.ui.isolated.TabbedContainer#getMainPanel()
     */
    public Object getMainPanel() {
        return mainPanel;
    }

    /**
     * @see org.devyant.magicbeans.ui.isolated.TabbedContainer#addSecondaryPanel(String, Object)
     */
    public void addSecondaryPanel(String title, Object component) {
        this.addTab(title, (Component) component);
    }

}
