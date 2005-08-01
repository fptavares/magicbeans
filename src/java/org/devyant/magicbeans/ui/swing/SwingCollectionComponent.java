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
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.listeners.UpdateButtonActionListener;
import org.devyant.magicbeans.ui.swing.generalizers.ListGeneralizer;
import org.devyant.magicbeans.utils.InternalMagicBean;

/**
 * SwingCollectionComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 17/Jun/2005 2:41:31
 */
public class SwingCollectionComponent extends SwingUnlabeledContainer {
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
     * Creates a new <code>SwingCollectionComponent</code> instance.
     */
    public SwingCollectionComponent(final ListGeneralizer component) {
        this.component = component;
    }
    
    protected final void init() {
        super.init(); // init super gui
        
        component.setModel(model);
        
        addButton.setText(MagicConfiguration.resources.get(MagicResources.STRING_ADDBUTTON));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        editButton.setText(MagicConfiguration.resources.get(MagicResources.STRING_EDITBUTTON));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        removeButton.setText(MagicConfiguration.resources.get(MagicResources.STRING_REMOVEBUTTON));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        
        // add list
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().setView((Component) component);
        // leave the dirty work to the layout manager :)
        layout.addControledComponent(this, scrollPane,
                new Component[] {addButton, editButton, removeButton},
                component.isExpandable());
    }
    /**
     * The addButton action.
     * @param evt <code>ActionEvent</code> data
     * @todo how? there may be more than one type...
     */
    public void addButtonActionPerformed(ActionEvent evt) {
        try {
            this.property.getConfiguration().getSpecialClassInstance(
                    MagicConfiguration.XML_COLLECTION_CLASS);
            model.addElement("Yoooooo!!");
        } catch (MagicException e) {
            // @todo Auto-generated catch block
            MagicUtils.error(e);
        }
    }
    /**
     * The editButton action.
     * @param evt <code>ActionEvent</code> data
     */
    public void editButtonActionPerformed(ActionEvent evt) {
        try {
            final InternalMagicBean bean = (InternalMagicBean) component.getSelectedValue();
            if (bean == null) {
                return; // no item has been selected
            }
            // create the dialog
            bean.showInternalFrame(this, this.title,
                    new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent evt) {
                            ((Component) component).repaint();
                        }
                    });
        } catch (MagicException e) {
            MagicUtils.debug(e);
            showErrorMessage(e.toString());
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
     * @see org.devyant.magicbeans.ui.swing.SwingContainer#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws MagicException {
        this.property = property;
        
        init(); // init gui
        
        // fill with property's value
        final Collection collection = (Collection) this.property.get();
        if (collection == null) {
            return;
        }
        
        // add elements to the list
        for (Iterator i = collection.iterator(); i.hasNext(); ) {
            model.addElement(new InternalMagicBean(i.next()));
        }
    }
    
    /**
     * @see org.devyant.magicbeans.MagicComponent#update()
     */
    public void update() throws MagicException {
        // get the collection instance
        Collection collection = (Collection) this.property.get();
        if (collection == null) {
            collection = new ArrayList(this.model.getSize());
        } else {
            collection.clear();
        }
        // add the objects
        for (int i = 0; i < this.model.getSize(); i++) {
            final InternalMagicBean bean =
                (InternalMagicBean) this.model.getElementAt(i);
            // call container's update() method 
            bean.update();
            // add bean to the collection
            collection.add(bean.getRealValue());
        }
        // set the collection back to it's bean
        this.property.set(collection);
    }
    
    /**
     * @see org.devyant.magicbeans.ui.swing.SwingNestedContainer#setTitle(java.lang.String)
     */
    public void setTitle(String title) {
        super.setTitle(title);
        this.title = title;
    }

}
