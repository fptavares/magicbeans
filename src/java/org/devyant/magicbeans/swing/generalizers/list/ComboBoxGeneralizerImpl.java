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
package org.devyant.magicbeans.swing.generalizers.list;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.ListModel;

import org.devyant.magicbeans.swing.generalizers.ListGeneralizer;

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
     * @see org.devyant.magicbeans.swing.generalizers.ListGeneralizer#getSelectedValue()
     */
    public Object getSelectedValue() {
        return this.getSelectedItem();
    }

    /**
     * @see org.devyant.magicbeans.swing.generalizers.ListGeneralizer#setModel(javax.swing.ListModel)
     */
    public void setModel(ListModel model) {
        this.setModel((ComboBoxModel) model);
    }

	/**
	 * @see org.devyant.magicbeans.swing.generalizers.ListGeneralizer#getSelectedValues()
	 */
	public Object[] getSelectedValues() {
		return this.getSelectedObjects();
	}

}
