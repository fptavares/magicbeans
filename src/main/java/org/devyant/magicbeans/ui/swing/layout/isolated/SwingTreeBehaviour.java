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
package org.devyant.magicbeans.ui.swing.layout.isolated;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicContainer;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layout.AbstractIsolatedBehaviour;

/**
 * SwingTreeBehaviour is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Mar 25, 2006 4:09:04 PM
 */
public class SwingTreeBehaviour
        extends AbstractIsolatedBehaviour<JComponent> {
    
    private static TreeContainer treeContainer;

    /**
     * Creates a new <code>SwingTreeBehaviour</code> instance.
     * @param layout The layout instance
     */
    public SwingTreeBehaviour(MagicLayout<JComponent> layout) {
        super(layout);
    }

    /**
     * @see org.devyant.magicbeans.layout.LayoutIsolatedBehaviour#addLabeledIsolatedComponent(java.lang.Object, java.lang.Object, org.devyant.magicbeans.MagicComponent)
     */
    public void addLabeledIsolatedComponent(JComponent container,
            JComponent label, MagicComponent<? extends JComponent> component) {
        if (treeContainer == null
                || (!treeContainer.getRoot().equals(component.getParent())
                && !component.getProperty().getParent().getConfiguration()
                .get(MagicConfiguration.GUI_ISOLATED_TYPE_KEY)
                .equals(MagicConfiguration.ISOLATED_TREE_VALUE))) {
            treeContainer = new TreeContainer((MagicContainer<? extends JComponent>) component.getParent());
            this.layout.addSimpleComponent(container, treeContainer);
        }
        //treeContainer.addNode(component);
    }
    
    /**
     * Utility class.
     */
    private static class TreeContainer extends JPanel {
        private final MagicTreeNode root;
        private final JTree tree;
        protected JRootPane panel = new JRootPane();
        
        /**
         * Creates a new <code>SwingTreeBehaviour.TreeContainer</code> instance.
         * @param rootComponent The root component
         */
        public TreeContainer(MagicContainer<? extends JComponent> rootComponent) {
            this.setLayout(new BorderLayout());
            
            this.root = createNode(rootComponent);
            this.tree = new JTree(this.root);
            //this.tree.setToggleClickCount(1);
            this.tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);
            // listen for when the selection changes
            this.tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    try {
                        TreeContainer.this.panel.setContentPane(
                                ((MagicTreeNode) e.getPath()
                                        .getLastPathComponent()).render());
                    } catch (MagicException e1) {
                        MagicUtils.debug(e1);
                    }
                }
            });
            
            add(this.tree, BorderLayout.WEST);
            add(this.panel, BorderLayout.EAST);
        }
        
        /**
         * Get the root component.
         * @return The root component
         */
        public MagicComponent<?> getRoot() {
            return this.root.getComponent();
        }

        /**
         * Add a component to the tree.
         * @param component The component
         */
        public void addNode(MagicComponent<? extends JComponent> component) {
            this.root.add(createNode(component));
        }
        
        private MagicTreeNode createNode(MagicComponent<? extends JComponent> component) {
            final MagicTreeNode node = new MagicTreeNode(component);
            if (component instanceof MagicContainer) {
                for (MagicComponent<? extends JComponent> child
                        : ((MagicContainer<? extends JComponent>) component)
                        .getComponents()) {
                    // TODO: have to check if it is isolated
                    // TODO: have to check whether it may be isolated or not
                    // FIXME: should the components collection be at the layout?
                    // getting a bit too complicated?...
                    if (!child.isNested()) {
                        node.add(createNode(child));
                    }
                }
            }
            return node;
        }
        
    }
    /**
     * The tree node that wraps a <code>MagicComponent</code>.
     */
    private static final class MagicTreeNode extends DefaultMutableTreeNode {
        /**
         * The component <code>MagicComponent<?></code>.
         */
        private final MagicComponent<? extends JComponent> component;

        /**
         * Creates a new <code>MagicTreeNode</code> instance.
         * @param component The component
         */
        public MagicTreeNode(final MagicComponent<? extends JComponent> component) {
            this.component = component;
            if (this.component == null) {
                this.setUserObject("uidgsgdf");
            } else {
                this.setUserObject(this.component.getName());
            }
        }

        /**
         * The getter method for the component property.
         * @return The property's <code>MagicComponent<?></code> value
         */
        public MagicComponent<?> getComponent() {
            return this.component;
        }
        
        public JComponent render() throws MagicException {
            return this.component.render();
        }
        
    }

}
