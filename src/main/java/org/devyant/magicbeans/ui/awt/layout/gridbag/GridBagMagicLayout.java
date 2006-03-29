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
package org.devyant.magicbeans.ui.awt.layout.gridbag;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.AbstractMagicLayout;
import org.devyant.magicbeans.layout.LayoutIsolatedBehaviour;

/**
 * GridBagMagicLayout is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jun 24, 2005 10:16:51 PM
 */
public class GridBagMagicLayout extends AbstractMagicLayout<Component> {
    
    /**
     * The serialVersionUID <code>long</code>.
     */
    private static final long serialVersionUID = -8635208882682826756L;

    /**
     * The layout <code>GridBagLayout</code>.
     */
    private final GridBagLayout layout = new GridBagLayout();
    
    /**
     * Creates a new <code>GridBagMagicLayout</code> instance.
     * @param type
     */
    public GridBagMagicLayout(IsolatedBehaviourType type) {
        super(type);
    }
    
    /**
     * The DEFAULT_ANCHOR <code>int</code>.
     */
    public static final int DEFAULT_ANCHOR = GridBagConstraints.LINE_END;
    /**
     * The <code>GridBagConstraints</code>.
     */
    private final GridBagConstraints gridBagConstraints =
        new GridBagConstraints();
    /**
     * The top value for the default defaultInsets.
     */
    private static final int INSETS_TOP = 5;
    /**
     * The left value for the default defaultInsets.
     */
    private static final int INSETS_LEFT = 5;
    /**
     * The bottom value for the default defaultInsets.
     */
    private static final int INSETS_BOTTOM = 5;
    /**
     * The right value for the default defaultInsets.
     */
    private static final int INSETS_RIGHT = 5;
    /**
     * The defaultInsets for the components.
     */
    public static final Insets defaultInsets =
        new Insets(INSETS_TOP, INSETS_LEFT, INSETS_BOTTOM, INSETS_RIGHT);
    /**
     * The current line being filled at the <code>GridBagLayout</code>.
     */
    private int line = 0;
    /**
     * The STANDARD_WEIGHTX <code>double</code>.
     */
    public final static double STANDARD_WEIGHTX = 1.0;
    /**
     * The NO_WEIGHTX <code>double</code>.
     */
    public final static double NO_WEIGHTX = 0.0;

    /**
     * @see org.devyant.magicbeans.MagicLayout#addLabeledComponent(Object, java.lang.Object, MagicComponent)
     */
    public void addLabeledComponent(final Object container, final Object label,
            final MagicComponent<?> component) throws MagicException {
        // add label
        addComponent(container, label, 0, NO_WEIGHTX);
        // add magic component
        addComponent(container, component.render(), 1, STANDARD_WEIGHTX);
        nextRow();
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addLabeledIsolatedComponent(Object, Object, MagicComponent)
     */
    /*public void addLabeledIsolatedComponent(final Object container,
            final Object label, final MagicComponent<?> component)
            throws MagicException {
        // add label
        addComponent(container, label, 0, line, NO_WEIGHTX);
        // add magic component
        addComponent(container, component.render(), 1, line++, NO_WEIGHTX,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL);
    }*/

    /**
     * @see org.devyant.magicbeans.MagicLayout#addUnlabeledComponent(Object, MagicComponent)
     */
    public void addUnlabeledComponent(Object container,
            MagicComponent<?> component) throws MagicException {
        // add magic component
        addComponent(container, component.render(), 0, STANDARD_WEIGHTX,
                DEFAULT_ANCHOR, GridBagConstraints.BOTH, 2, 1, defaultInsets);
        nextRow();
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addControledComponent(java.lang.Object, java.lang.Object, java.lang.Object[], boolean)
     */
    public void addControledComponent(Object container, Object component,
            Object[] controllers, boolean expand) {
        final int fill;
        if (expand) {
            fill = GridBagConstraints.BOTH;
        } else {
            fill = GridBagConstraints.HORIZONTAL;
        }
        // add main component
        addComponent(container, component, 1, STANDARD_WEIGHTX,
                DEFAULT_ANCHOR, fill, 1, controllers.length, defaultInsets);
        // add controllers
        for (int i = 0; i < controllers.length; i++) {
            addComponent(container, controllers[i], 2, NO_WEIGHTX,
                    DEFAULT_ANCHOR, GridBagConstraints.HORIZONTAL,
                    1, 1, defaultInsets);
            nextRow();
        }
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addButton(Object, java.lang.Object)
     */
    public void addButton(final Object container, final Object button) {
        addComponent(container, button, 0, NO_WEIGHTX,
                GridBagConstraints.LINE_END, GridBagConstraints.NONE,
                GridBagConstraints.REMAINDER, 1, new Insets(INSETS_TOP + 10,
                        INSETS_LEFT, INSETS_BOTTOM, INSETS_RIGHT));
        nextRow();
    }

    /**
     * @see org.devyant.magicbeans.MagicLayout#addStatus(Object, Object)
     */
    public void addStatus(final Object container, final Object status) {
        addComponent(container, status, 0, STANDARD_WEIGHTX,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                GridBagConstraints.REMAINDER, 1,
                new Insets(0, INSETS_LEFT, 0, INSETS_RIGHT));
        nextRow();
    }
    
    /**
     * @see org.devyant.magicbeans.MagicLayout#addSimpleComponent(java.lang.Object, java.lang.Object)
     */
    public void addSimpleComponent(Object container, Object component) {
        addComponent(container, component, 0, STANDARD_WEIGHTX,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                GridBagConstraints.REMAINDER, 1, defaultInsets);
        nextRow();
    }
    
    /*
     * utility methods
     */
    
    /**
     * Add a component to the panel.
     * @param container The container to add to
     * @param component The component to add
     * @param x The x coordinate
     * @param weightx The <code>GridBagConstraints</code>'s weightx value
     */
    public final void addComponent(final Object container,
            final Object component, int x, double weightx) {
        addComponent(container, component, x, weightx,
                GridBagConstraints.LINE_START, GridBagConstraints.BOTH);
    }
    
    /**
     * @param container The container to add to
     * @param component The component to add
     * @param x The x coordinate
     * @param weightx The <code>GridBagConstraints</code>'s weightx value
     * @param anchor The <code>GridBagConstraints</code>'s anchor to use
     * @param fill The <code>GridBagConstraints</code>'s fill to use
     */
    public final void addComponent(final Object container,
            final Object component, int x,
            double weightx, int anchor, int fill) {
        addComponent(container, component,
                x, weightx, anchor, fill, 1, 1, defaultInsets);
    }
    
    /**
     * @param container The container to add to
     * @param component The component to add
     * @param x The x coordinate
     * @param weightx The <code>GridBagConstraints</code>'s weightx value
     * @param anchor The <code>GridBagConstraints</code>'s anchor to use
     * @param fill The <code>GridBagConstraints</code>'s fill to use
     * @param gridwidth The <code>GridBagConstraints</code>'s gridwidth to use
     * @param gridheight The <code>GridBagConstraints</code>'s gridheight to use
     * @param insets The <code>GridBagConstraints</code>'s defaultInsets to use
     */
    public final void addComponent(final Object container,
            final Object component, int x, double weightx, int anchor,
            int fill, int gridwidth, int gridheight, Insets insets) {
        // TODO: unnecessary repetition of this...
        ((Container) container).setLayout(this.layout);
        
        this.gridBagConstraints.anchor = anchor;
        this.gridBagConstraints.fill = fill;
        this.gridBagConstraints.gridwidth = gridwidth;
        this.gridBagConstraints.gridheight = gridheight;
        this.gridBagConstraints.gridx = x;
        this.gridBagConstraints.gridy = this.line;
        this.gridBagConstraints.insets = insets;
        this.gridBagConstraints.weightx = weightx;
        ((Container) container).add(
                (Component) component, this.gridBagConstraints);
    }
    
    /**
     * Break the current line and move the pointer to the next one.
     */
    public final void nextRow() {
        this.line++;
    }

    /**
     * @see org.devyant.magicbeans.layout.AbstractMagicLayout#createChildBehaviour()
     * @todo
     */
    @Override
    protected LayoutIsolatedBehaviour createChildBehaviour()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.devyant.magicbeans.layout.AbstractMagicLayout#createTabbedBehaviour()
     * @todo
     */
    @Override
    protected LayoutIsolatedBehaviour createTabbedBehaviour()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.devyant.magicbeans.layout.AbstractMagicLayout#createTreeBehaviour()
     * @todo
     */
    @Override
    protected LayoutIsolatedBehaviour createTreeBehaviour()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
