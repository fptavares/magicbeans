/*
 * Copyright 2005 Filipe Tavares
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devyant.magicbeans.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import org.devyant.magicbeans.MagicLayout;

/**
 * GridBagMagicLayout is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jun 24, 2005 10:16:51 PM
 */
public class GridBagMagicLayout extends GridBagLayout implements MagicLayout {
    /**
     * The <code>GridBagConstraints</code>.
     */
    private GridBagConstraints gridBagConstraints;
    /**
     * The insets for the components.
     */
    protected final Insets insets = new Insets(5, 5, 5, 5);
    /**
     * The current line being filled at the <code>GridBagLayout</code>.
     */
    protected int line = 0;

    /**
     * @see org.devyant.magicbeans.MagicLayout#addLabeledComponent(java.awt.Container, java.awt.Component, java.awt.Component)
     */
    public void addLabeledComponent(final Container container,
            final Component label, final Component component) {
        // add label
        addComponent(container, label, 0, line);
        // add magic component
        addComponent(container, component, 1, line++);
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addUnlabeledComponent(java.awt.Container, java.awt.Component)
     */
    public void addUnlabeledComponent(Container container, Component component) {
        // add magic component
        addComponent(container, component, 0, line++,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, 2, 1, insets);
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addControledComponent(java.awt.Container, java.awt.Component, java.awt.Component[])
     */
    public void addControledComponent(Container container, Component component,
            Component[] controllers) {
        // add main component
        addComponent(container, component, 1, line, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 3, insets);
        // add controllers
        for (int i = 0; i < controllers.length; i++) {
            addComponent(container, controllers[i], 2, line++,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                    1, 1, insets);
        }
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addButton(java.awt.Container, java.awt.Component)
     */
    public void addButton(final Container container, final Component button) {
        addComponent(container, button, 1, line++,
                GridBagConstraints.EAST, GridBagConstraints.NONE);
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addStatus(java.awt.Container, java.awt.Component)
     */
    public void addStatus(final Container container, final Component status) {
        addComponent(container, status, 0, line++, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, GridBagConstraints.REMAINDER, 1,
                new Insets(0, 5, 0, 5));
    }
    
    // utility methods
    
    /**
     * Add a component to the panel.
     * @param container The container to add to
     * @param component The component to add
     * @param x The x coordinate
     * @param y The y coordinate
     */
    private final void addComponent(final Container container,
            final Component component, int x, int y) {
        addComponent(container, component, x, y,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);
    }
    
    /**
     * @param container The container to add to
     * @param component The component to add
     * @param x The x coordinate
     * @param y The y coordinate
     * @param anchor The <code>GridBagConstraints</code>'s anchor to use
     */
    private final void addComponent(final Container container,
            final Component component, int x, int y, int anchor, int fill) {
        addComponent(container, component, x, y, anchor, fill, 1, 1, insets);
    }
    
    /**
     * @param container The container to add to
     * @param component The component to add
     * @param x The x coordinate
     * @param y The y coordinate
     * @param anchor The <code>GridBagConstraints</code>'s anchor to use
     */
    protected final void addComponent(final Container container,
            final Component component, int x, int y, int anchor, int fill,
            int gridwidth, int gridheight, Insets insets) {
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = anchor;
        gridBagConstraints.fill = fill;
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.gridheight = gridheight;
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.insets = insets;
        container.add(component, gridBagConstraints);
    }

}
