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
public class BasicSubjectBehaviour implements Subject {
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
