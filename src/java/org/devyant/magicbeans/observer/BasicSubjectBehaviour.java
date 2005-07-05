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
package org.devyant.magicbeans.observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * BasicSubjectBehaviour is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Jun/2005 5:16:13
 */
public class BasicSubjectBehaviour implements Subject, SubjectBehaviour {
    /**
     * The observers <code>Collection</code>.
     */
    private Collection observers = new ArrayList();
    
    /**
     * @see org.devyant.magicbeans.observer.Subject#addObserver(org.devyant.magicbeans.observer.Observer)
     */
    public void addObserver( Observer o ) {
        observers.add( o );
    }
    
    /**
     * @see org.devyant.magicbeans.observer.Subject#removeObserver(org.devyant.magicbeans.observer.Observer)
     */
    public void removeObserver( Observer o ) {
        observers.remove( o );
    }
    
    /**
     * Notify all observers.
     */
    public void notifyObservers( Subject subject ) {
        // loop through and notify each observer
        final Iterator i = observers.iterator();
        while( i.hasNext() ) {
            final Observer o = ( Observer ) i.next();
            o.update( subject );
        }
    }
    
}
