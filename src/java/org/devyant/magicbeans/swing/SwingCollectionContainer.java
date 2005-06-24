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
package org.devyant.magicbeans.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.devyant.magicbeans.MagicBean;
import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.MagicView;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.swing.generalizers.ListGeneralizer;
import org.devyant.magicbeans.swing.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.swing.utils.BasicDialog;

/**
 * SwingCollectionContainer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 17/Jun/2005 2:41:31
 * @todo buttons with life?.. :)p
 * @todo how to define set and get for a non-magic property?... damn...:(
 */
public class SwingCollectionContainer extends SwingNestedContainer
        implements UpdateButtonActionListener {
    private final ListGeneralizer component;
    private final DefaultComboBoxModel model = new DefaultComboBoxModel();
    private final JButton addButton = new JButton("Add");
    private final JButton editButton = new JButton("Edit");
    private final JButton removeButton = new JButton("Remove");
    
    private String title = "";
    
    /**
     * Creates a new <code>SwingCollectionContainer</code> instance.
     */
    public SwingCollectionContainer(ListGeneralizer component) {
        this.component = component;
    }
    
    protected final void init() {
        super.init(); // init super gui
        
        component.setModel(model);
        
        //addButton.setText(resources.getString(MagicResources.STRING_OKBUTTON));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                model.addElement("Yoooooo!!");
            }
        });
        // addButton.setText(resources.getString(MagicResources.STRING_OKBUTTON));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                final MagicBean bean = (MagicBean) component.getSelectedValue();
                try {
                    // add listener to the update button
                    final Container container = bean.render();
                    ((MagicView) container).addUpdateButtonActionListener(
                            SwingCollectionContainer.this);
                    // create the dialog
                    BasicDialog dialog =
                        new BasicDialog(container, (Frame) null, title, false);
                    // show dialog
                    dialog.setVisible(true);
                } catch (Exception e) {
                    MagicUtils.debug(e);
                }
            }
        });
        
        // add list
        final JScrollPane scrollPane = new JScrollPane();
        //scrollPane.setBackground(Color.RED);
        //component.setBackground(Color.GREEN);
        scrollPane.getViewport().setView((Component) component);
        addComponent(scrollPane, 1, line, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 3, insets);
        // add buttons
        addComponent(addButton, 2, line++, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 1, insets);
        addComponent(editButton, 2, line++, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 1, insets);
        addComponent(removeButton, 2, line++, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 1, 1, insets);
    }

    /**
     * @see org.devyant.magicbeans.swing.SwingContainer#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws Exception {
        init(); // init gui
        
        this.property = property;
        
        // fill with property's value
        final Collection collection = (Collection) this.property.get();
        if (collection == null) {
            return;
        }
        
        // add elements to the list
        for (Iterator i = collection.iterator(); i.hasNext(); ) {
            MagicBean bean = new MagicBean(i.next());
            bean.setResources(resources);
            model.addElement(bean);
        }
    }
    
    public void update() throws Exception {
        // get the collection instance
        Collection collection = (Collection) this.property.get();
        if (collection == null) {
            collection = new Vector(this.model.getSize());
        } else {
            collection.clear();
        }
        // add the objects
        for (int i = 0; i < this.model.getSize(); i++) {
            final MagicBean bean = (MagicBean) this.model.getElementAt(i);
            collection.add(bean.getRealValue());
        }
        // set the collection back to it's bean
        this.property.set(collection);
    }
    
    /**
     * @see org.devyant.magicbeans.swing.SwingNestedContainer#setTitle(java.lang.String)
     */
    public void setTitle(String title) {
        super.setTitle(title);
        this.title = title;
    }

    /**
     * @see org.devyant.magicbeans.swing.listeners.UpdateButtonActionListener#updateButtonActionPerformed()
     */
    public void updateButtonActionPerformed() {
        ((Component) component).repaint();
    }

}
