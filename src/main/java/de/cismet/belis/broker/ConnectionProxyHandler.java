/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.broker;

import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.openide.util.Exceptions;

import javax.swing.SwingUtilities;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class ConnectionProxyHandler extends DefaultConnectionProxyHandler {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ConnectionProxyHandler object.
     *
     * @param  connectionSession  DOCUMENT ME!
     */
    public ConnectionProxyHandler(final ConnectionSession connectionSession) {
        super(connectionSession);
        proxyHandler = new DefaultConnectionProxy();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    protected class DefaultConnectionProxy extends DefaultConnectionProxyHandler.DefaultConnectionProxy {

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean deleteNode(final Node node) throws ConnectionException {
            final boolean ret = super.deleteNode(node);
            if (ret && (node instanceof MetaObjectNode)) {
                final MetaObjectNode metaObjectNode = (MetaObjectNode)node;
                final MetaObject metaObject = metaObjectNode.getObject();
                SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                            }
                            CidsBroker.getInstance()
                                    .fireListenerForKeyTableChange(metaObject.getMetaClass().getTableName());
                        }
                    });
            }
            return ret; // To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int updateMetaObject(final MetaObject metaObject, final String domain) throws ConnectionException {
            final int ret = super.updateMetaObject(metaObject, domain);
            CidsBroker.getInstance().fireListenerForKeyTableChange(metaObject.getMetaClass().getTableName());
            return ret;
        }
    }
}
