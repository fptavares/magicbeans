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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.devyant.magicbeans.MagicUtils;
import org.devyant.magicbeans.conf.MagicConfiguration;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.i18n.MagicResources;
import org.devyant.magicbeans.ui.AbstractComplexComponent;
import org.devyant.magicbeans.ui.listeners.WindowListener;
import org.devyant.magicbeans.ui.swing.generalizers.ListGeneralizer;
import org.devyant.magicbeans.utils.InternalMagicBean;

/**
 * SwingCollectionComponent is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 17/Jun/2005 2:41:31
 * @todo extend SwingContainer? -> re-use of code and waist of code
 *        abstract swing container? can't be -> AbstractMagicContainer
 */
public class SwingCollectionComponent
        extends AbstractComplexComponent<JComponent> {
    protected final ListGeneralizer listComponent;
    private final DefaultComboBoxModel model = new DefaultComboBoxModel();
    private final JButton addButton = new JButton();
    private final JButton editButton = new JButton();
    private final JButton removeButton = new JButton();
    
    /**
     * Creates a new <code>SwingCollectionComponent</code> instance.
     */
    public SwingCollectionComponent(final ListGeneralizer listComponent) {
        this.listComponent = listComponent;
    }
    
    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#initializeComponent()
     */
    @Override
    protected final void initializeComponent() throws MagicException {
        this.listComponent.setModel(this.model);
        
        this.addButton.setText(MagicConfiguration.resources.get(MagicResources.STRING_ADDBUTTON));
        this.addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        this.editButton.setText(MagicConfiguration.resources.get(MagicResources.STRING_EDITBUTTON));
        this.editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        this.removeButton.setText(MagicConfiguration.resources.get(MagicResources.STRING_REMOVEBUTTON));
        this.removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        
        // add list
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().setView((Component) this.listComponent);
        // leave the dirty work to the layout manager :)
        this.layout.addControledComponent(this.component, scrollPane,
                this.listComponent.isExpandable(),
                this.addButton, this.editButton, this.removeButton);
    }
    /**
     * The addButton action.
     * @param evt <code>ActionEvent</code> data
     * @todo how? there may be more than one type...
     */
    public void addButtonActionPerformed(ActionEvent evt) {
        //try {
            /*this.getProperty().getConfiguration().getSpecialClassInstance(
                    MagicConfiguration.XML_COLLECTION_CLASS);*/
            this.model.addElement(
                    new InternalMagicBean<JComponent>("Yoooooo!!", getFactory()));
        /*} catch (MagicException e) {
            // @todo Auto-generated catch block
            MagicUtils.error(e);
        }*/
    }
    /**
     * The editButton action.
     * @param evt <code>ActionEvent</code> data
     */
    public void editButtonActionPerformed(ActionEvent evt) {
        try {
            final InternalMagicBean<JComponent> bean =
                (InternalMagicBean<JComponent>) listComponent.getSelectedValue();
            if (bean == null) {
                return; // no item has been selected
            }
            // create the dialog
            bean.showInternalFrame(this.component, this.getName(),
                    new WindowListener() {
                        public void closeWindowActionPerformed() {
                            ((Component) SwingCollectionComponent.this.
                                    listComponent).repaint();
                        }
                    });
        } catch (MagicException e) {
            MagicUtils.debug(e);
        }
    }

    /**
     * The removeButton action.
     * @param evt <code>ActionEvent</code> data
     */
    public void removeButtonActionPerformed(ActionEvent evt) {
        final Object [] items = this.listComponent.getSelectedValues();
        // remove all the selected items
        for (int i = 0; i < items.length; i++) {
            this.model.removeElement(items[i]);
        }
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#getValue()
     */
    @Override
    protected Object getValue() throws MagicException {
        // get the collection instance
        Collection collection = (Collection) getProperty().get();
        if (collection == null) {
            collection = new ArrayList(this.model.getSize());
        } else {
            collection.clear();
        }
        // add the objects
        for (int i = 0; i < this.model.getSize(); i++) {
            final InternalMagicBean<JComponent> bean =
                (InternalMagicBean<JComponent>) this.model.getElementAt(i);
            // call container's update() method 
            bean.update();
            // add bean to the collection
            collection.add(bean.getValue());
        }
        return collection;
    }

    /**
     * @see org.devyant.magicbeans.ui.AbstractMagicComponent#setValue(java.lang.Object)
     */
    @Override
    protected void setValue(final Object value) throws MagicException {
        System.out.println("dfsgsdfgfdgsdg-------------------------");
        System.out.println(((Collection<?>)value).size());
        // add elements to the list
        for (Object item : (Collection<?>) value) {
            System.out.println("-------------------------");
            System.out.println(item);
            this.model.addElement(
                    new InternalMagicBean<JComponent>(item, getFactory()));
        }
    }

}
