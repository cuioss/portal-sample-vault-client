/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.portal.client.vault.server;

import de.cuioss.portal.client.vault.EnableVaultTest;
import de.cuioss.portal.client.vault.EnabledIfVaultIsReachable;
import de.cuioss.portal.core.test.mocks.configuration.PortalTestConfiguration;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldBeNotNull;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.service.ServiceState;
import jakarta.inject.Inject;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@EnableVaultTest
// Can be run if integration tests are working or locally
@EnabledIfVaultIsReachable(url = "http://127.0.0.1:8200")
class BackendHealthCheckIntegrationTest implements ShouldBeNotNull<BackendHealthCheck> {

    @Inject
    @Getter
    private BackendHealthCheck underTest;

    @Inject
    private PortalTestConfiguration configuration;

    @Test
    void shouldAccessHealthCheck() {
        var serverInfo = underTest.retrieveServerInfo();
        assertEquals(ServiceState.ACTIVE, serverInfo.getServiceState());
        assertFalse(MoreStrings.isEmpty(serverInfo.getInformation()));
        assertFalse(serverInfo.getInformation().contains(BackendHealthCheck.UNKNOWN),
                serverInfo.getInformation() + " Must not contain the String " + BackendHealthCheck.UNKNOWN);
    }
}
