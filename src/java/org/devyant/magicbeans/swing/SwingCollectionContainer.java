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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
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
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.swing.generalizers.ListGeneralizer;
import org.devyant.magicbeans.swing.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.swing.utils.BasicDialog;

/**
 * SwingCollectionContainer is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 17/Jun/2005 2:41:31
 */
public class SwingCollectionContainer extends SwingNestedContainer
        implements UpdateButtonActionListener {
    private final ListGeneralizer component;
    private final DefaultComboBoxModel model = new DefaultComboBoxModel();
    private final JButton addButton = new JButton();
    private final JButton editButton = new JButton();
    private final JButton removeButton = new JButton();
    
    /**
     * The title <code>String</code>.
     * <p>This is used for the add/edit window title.</p>
     */
    private String title = "";
    
    /**
     * Creates a new <code>SwingCollectionContainer</code> instance.
     */
    public SwingCollectionContainer(final ListGeneralizer component) {
        this.component = component;
    }
    
    protected final void init() {
        super.init(); // init super gui
        
        component.setModel(model);
        
        addButton.setText(MagicConfiguration.resources.getString(MagicResources.STRING_ADDBUTTON));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        editButton.setText(MagicConfiguration.resources.getString(MagicResources.STRING_EDITBUTTON));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        removeButton.setText(MagicConfiguration.resources.getString(MagicResources.STRING_REMOVEBUTTON));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        
        // add list
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().setView((Component) component);
        scrollPane.setMinimumSize(new Dimension(
                this.property.getConfiguration()
                    .getInt(MagicConfiguration.GUI_COMPONENT_MINIMUM_WIDTH_KEY),
                this.property.getConfiguration()
                    .getInt(MagicConfiguration.GUI_COMPONENT_MINIMUM_WIDTH_KEY) * 2));
        // leave the dirty work to the layout manager :)
        layout.addControledComponent(this, scrollPane,
                new Component[] {addButton, editButton, removeButton});
    }
    /**
     * The addButton action.
     * @param evt <code>ActionEvent</code> data
     */
    public void addButtonActionPerformed(ActionEvent evt) {
        model.addElement("Yoooooo!!");
    }
    /**
     * The editButton action.
     * @param evt <code>ActionEvent</code> data
     */
    public void editButtonActionPerformed(ActionEvent evt) {
        final MagicBean bean = (MagicBean) component.getSelectedValue();
        try {
            // add listener to the update button
            final Container container = bean.render();
            ((MagicView) container).addUpdateButtonActionListener(this);
            // create the dialog
            BasicDialog dialog =
                new BasicDialog(container, (Frame) null, title, false);
            // show dialog
            dialog.setVisible(true);
        } catch (Exception e) {
            MagicUtils.debug(e);
        }
    }
    /**
     * The removeButton action.
     * @param evt <code>ActionEvent</code> data
     */
    public void removeButtonActionPerformed(ActionEvent evt) {
        final Object [] items = component.getSelectedValues();
        // remove all the selected items
        for (int i = 0; i < items.length; i++) {
            model.removeElement(items[i]);
        }
    }

    /**
     * @see org.devyant.magicbeans.swing.SwingContainer#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws Exception {
        this.property = property;
        
        init(); // init gui
        
        // fill with property's value
        final Collection collection = (Collection) this.property.get();
        if (collection == null) {
            return;
        }
        
        // add elements to the list
        for (Iterator i = collection.iterator(); i.hasNext(); ) {
            model.addElement(new MagicBean(i.next()));
        }
    }
    
    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
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
