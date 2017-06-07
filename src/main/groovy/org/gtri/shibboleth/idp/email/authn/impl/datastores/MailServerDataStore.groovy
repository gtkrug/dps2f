/*
 * Copyright 2016 Stefan Wold <ratler@stderr.eu>
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
 *
 */

package org.gtri.shibboleth.idp.email.authn.impl.datastores

import org.gtri.shibboleth.idp.email.authn.api.DeviceDataStore
import org.gtri.shibboleth.idp.email.authn.impl.G2fUserContext
import groovy.util.logging.Slf4j
import net.shibboleth.idp.authn.AuthnEventIds
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.protocol.HttpContext

// Mailserver Stuff
// TBD


@Slf4j
class MailServerDataStore implements DeviceDataStore {
    private String Server
    private String Address
    
    // TBD - Private Data Neccessary for Connection to SMTP Server

    /** Constructor */
    MailServerDataStore(String smtpServer, String fromAddress) {
        log.debug("Mail Server constructor: ({})", smtpServer);
        try {
            Server  = smtpServer;
            Address = fromAddress;
        } catch (Exception ex) {
           log.error("Could not connect to Mail Server: {} failure", ex);
        }

    }

    @Override
    def beginAuthentication(G2fUserContext g2fUserContext) {
        def username  = g2fUserContext.username
        def token     = g2fUserContext.token
        def email     = g2fUserContext.email
        log.debug("Send e-mail to e-mail address {} for user {} with token {}", email, username, token)
        try {
            log.debug("beginAuthentication() - Transmitting 2nd factor to user via e-mail")
            // TBD - Send e-mail with 2nd factor code
        } catch (Exception e) {
            log.error("E-mail Transmission error: {} {}", e.statusCode, e.responseBodyAsString)
            return false
        }
        return true
    }

    @Override
    boolean finishAuthentication(G2fUserContext g2fUserContext) {
        def username  = g2fUserContext.username
        def tokenResp = g2fUserContext.tokenResponse
        def token     = g2fUserContext.token
        log.debug("finishAuthentication() ")
        log.debug("Verifying token matches for user {}; token sent was {} with response {}", username, token, tokenResp)
        
        return ( tokenResp == token );
    }

}
