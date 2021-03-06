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
package org.devyant.magicbeans.ui.swing.generalizers.list;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.ListModel;

import org.devyant.magicbeans.ui.swing.generalizers.ListGeneralizer;

/**
 * ComboBoxGeneralizerImpl is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Jun/2005 2:49:35
 */
public class ComboBoxGeneralizerImpl extends JComboBox implements
        ListGeneralizer {

    /**
     * @see org.devyant.magicbeans.ui.swing.generalizers.ListGeneralizer#getSelectedValue()
     */
    public Object getSelectedValue() {
        return this.getSelectedItem();
    }

    /**
     * @see org.devyant.magicbeans.ui.swing.generalizers.ListGeneralizer#setModel(javax.swing.ListModel)
     */
    public void setModel(ListModel model) {
        this.setModel((ComboBoxModel) model);
    }

    /**
     * @see org.devyant.magicbeans.ui.swing.generalizers.ListGeneralizer#getSelectedValues()
     */
    public Object[] getSelectedValues() {
        return this.getSelectedObjects();
    }

    /**
     * @see org.devyant.magicbeans.ui.swing.generalizers.ListGeneralizer#isExpandable()
     */
    public boolean isExpandable() {
        return false;
    }

}
