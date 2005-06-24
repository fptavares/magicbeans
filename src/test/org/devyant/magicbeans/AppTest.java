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
package org.devyant.magicbeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AppTest is a <b>cool</b> class.
 * 
 * @author Filipe Tavares
 * @version $Revision$ $Date$ ($Author$)
 * @since 19/Abr/2005 14:06:27
 */
public class AppTest 
    extends AbstractTestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws Exception
     */
    public static void testApp() throws Exception
    {
        final MagicBean bean = new MagicBean(new Dummy());
        bean.includeResources("org.devyant.magicbeans.Resources");
        
        bean.showFrame(null);
    }
    
    /**
     * @param args
     * @throws Exception
     */
    public static final void main(String [] args) throws Exception {
        testApp();
    }

    public static class Dummy {
        private String name = "Dummy";
        private String description = "A dummy class for testing";
        private String bye;
        private Calendar date = Calendar.getInstance();
        private Dummy2 dummy = new Dummy2();
        
        public String getBye() {
            return bye;
        }
        public final void setBye(String bye) {
            this.bye = bye;
        }
        public String getDescription() {
            return description;
        }
        public final void setDescription(String description) {
            this.description = description;
        }
        public String getName() {
            return name;
        }
        public final void setName(String name) {
            this.name = name;
        }
        public final boolean validateName() {
            if (name.length() > 5) {
                return true;
            } else {
                return false;
            }
        }
        public final Dummy2 getDummy() {
            return dummy;
        }
        public final void setDummy(Dummy2 dummy) {
            this.dummy = dummy;
        }
        public final Calendar getDate() {
            return date;
        }
        public final void setDate(Calendar date) {
            this.date = date;
        }
        
        public final String toString() {
            return "\t" + name
                + "\n\t" + description
                + "\n\t" + bye
                + "\n\t" + date
                + "\n\t" + dummy;
        }
    }
    public static class Dummy2 {
        private String name = "Dummy2";
        private String description = "A dummy2 class for testing";
        private String bye;
        private Collection collection = new ArrayList();
        
        public Dummy2() {
            Random r = new Random();
            for (int i = 0; i < 6; i++) {
                collection.add(RandomStringUtils.randomAlphanumeric(r.nextInt(48)+4));
            }
        }
        
        public String getBye() {
            return bye;
        }
        public final void setBye(String bye) {
            this.bye = bye;
        }
        public String getDescription() {
            return description;
        }
        public final void setDescription(String description) {
            this.description = description;
        }
        public String getName() {
            return name;
        }
        public final void setName(String name) {
            this.name = name;
        }
        public final Collection getCollection() {
            return collection;
        }
        public final void setCollection(Collection collection) {
            this.collection = collection;
        }
        
        public final boolean validateName() {
            if (name.length() > 5) {
                return true;
            } else {
                return false;
            }
        }
        
        public final String toString() {
            return "\t" + name
                + "\n\t" + description
                + "\n\t" + bye
                + "\n\t" + collection;
        }
    }
}
