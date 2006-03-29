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
package org.devyant.magicbeans.ui.swing.layout.gridbag;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.AbstractIsolatedBehaviour;

/**
 * GridBagTreeBehaviour is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Mar 25, 2006 4:09:04 PM
 */
public class GridBagTreeBehaviour
        extends AbstractIsolatedBehaviour<SwingGridBagMagicLayout> {
    
    private static TreeContainer treeContainer;

    /**
     * Creates a new <code>GridBagTreeBehaviour</code> instance.
     * @param layout The layout instance
     */
    public GridBagTreeBehaviour(SwingGridBagMagicLayout layout) {
        super(layout);
    }

    /**
     * @see org.devyant.magicbeans.layout.LayoutIsolatedBehaviour#addLabeledIsolatedComponent(java.lang.Object, java.lang.Object, org.devyant.magicbeans.MagicComponent)
     */
    public void addLabeledIsolatedComponent(Object container, Object label,
            MagicComponent<?> component) {
        if (treeContainer == null || !component.getProperty().getParent()
                .getConfiguration().get(MagicConfiguration.GUI_ISOLATED_TYPE_KEY)
                .equals(MagicConfiguration.ISOLATED_TREE_VALUE)) {
            treeContainer = new TreeContainer(component.getParent());
            this.layout.addSimpleComponent(container, treeContainer);
        }
        treeContainer.addNode(component);
        
    }
    
    /**
     * Utility class.
     */
    private static class TreeContainer extends JPanel {
        private final MagicTreeNode root;
        private final JTree tree;
        protected JRootPane panel = new JRootPane();
        
        /**
         * Creates a new <code>GridBagTreeBehaviour.TreeContainer</code> instance.
         * @param rootComponent The root component
         */
        public TreeContainer(MagicContainer<?> rootComponent) {
            this.setLayout(new BorderLayout());
            
            this.root = createNode(rootComponent);
            this.tree = new JTree(this.root);
            this.tree.setToggleClickCount(1);
            this.tree.expandRow(0);
            this.tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);
            // listen for when the selection changes
            this.tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    try {
                        TreeContainer.this.panel.setContentPane(
                                (Container) ((MagicTreeNode) e.getPath()
                                        .getLastPathComponent()).render());
                    } catch (MagicException e1) {
                        // @todo Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            
            add(this.tree, BorderLayout.WEST);
            add(this.panel, BorderLayout.EAST);
        }
        
        /**
         * Add a component to the tree.
         * @param component The component
         */
        public void addNode(MagicComponent<?> component) {
            this.root.add(createNode(component));
        }
        
        private MagicTreeNode createNode(MagicComponent<?> component) {
            return new MagicTreeNode(component);
        }
        
    }
    /**
     * The tree node that wraps a <code>MagicComponent</code>.
     */
    private static final class MagicTreeNode extends DefaultMutableTreeNode {
        /**
         * The component <code>MagicComponent<?></code>.
         */
        private final MagicComponent<?> component;

        /**
         * Creates a new <code>MagicTreeNode</code> instance.
         * @param component The component
         */
        public MagicTreeNode(final MagicComponent<?> component) {
            this.component = component;
            this.setUserObject(this.component.getName());
        }

        /**
         * The getter method for the component property.
         * @return The property's <code>MagicComponent<?></code> value
         */
        public MagicComponent<?> getComponent() {
            return this.component;
        }
        
        public Object render() throws MagicException {
            return this.component.render();
        }
        
    }

}
