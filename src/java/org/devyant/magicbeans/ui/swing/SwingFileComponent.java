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
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.devyant.magicbeans.MagicComponent;
import org.devyant.magicbeans.MagicLayout;
import org.devyant.magicbeans.beans.MagicProperty;
import org.devyant.magicbeans.exceptions.MagicException;
import org.devyant.magicbeans.layouts.GridBagMagicLayout;
import org.devyant.magicbeans.utils.Previewable;

/**
 * SwingFileComponent is a <b>cool</b> class.
 * 
 * @author ftavares
 * @version $Revision$ $Date$ ($Author$)
 * @since Jul 4, 2005 2:31:29 AM
 */
public class SwingFileComponent extends JPanel
        implements MagicComponent, Previewable {
    private final JTextField textField = new JTextField();
    private final JButton button = new JButton("...");
    private JFileChooser fileChooser;
    
    /**
     * The <code>MagicLayout</code>.
     */
    private final MagicLayout layout = new GridBagMagicLayout();
    
    /**
     * The property to bind to.
     */
    private MagicProperty property;
    
    /**
     * The selected file.
     */
    private File currentFile = null;
    
    protected final void init() {
        // gui initialization
        setLayout((LayoutManager) layout);
        
        textField.setEditable(false);
        
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        
        layout.addControledComponent(this, textField,
                new Component[] {button}, false);
    }

    /**
     * @param evt
     */
    protected void buttonActionPerformed(ActionEvent evt) {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        fileChooser.setCurrentDirectory(currentFile);
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            updateFile(fileChooser.getSelectedFile());
        }
    }

    /**
     * @param file
     */
    private void updateFile(File file) {
        currentFile = file;
        if (currentFile == null) {
            return;
        }
        textField.setText(currentFile.getAbsolutePath());
    }
    
    /**
     * @see org.devyant.magicbeans.ui.swing.SwingContainer#bindTo(org.devyant.magicbeans.beans.MagicProperty)
     */
    public void bindTo(MagicProperty property) throws MagicException {
        this.property = property;
        
        init(); // init gui
        
        updateFile((File) this.property.get());
    }
    
    /**
     * @see org.devyant.magicbeans.ui.swing.SwingContainer#update()
     */
    public void update() throws MagicException {
        this.property.set(preview());
    }

    /**
     * @see org.devyant.magicbeans.MagicComponent#getProperty()
     */
    public MagicProperty getProperty() {
        return this.property;
    }

    /**
     * @see org.devyant.magicbeans.utils.Previewable#preview()
     */
    public Object preview() {
        return currentFile;
    }
}
