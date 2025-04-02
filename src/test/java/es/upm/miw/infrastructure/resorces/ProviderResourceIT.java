package es.upm.miw.infrastructure.resorces;

import es.upm.miw.infrastructure.resources.ProviderResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProviderResourceIT {

    @Autowired
    ProviderResource providerResource;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRead() throws Exception {
        mockMvc.perform(get(ProviderResource.PROVIDERS + ProviderResource.COMPANY_ID, "pro1")
                        .with(jwt().jwt(jwt -> jwt.claim("scope", "admin")))
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "666666003", authorities = {"SCOPE_customer"})
    void testFindCompaniesNotAuthorized() throws Exception {
        mockMvc.perform(get(ProviderResource.PROVIDERS + ProviderResource.COMPANIES))
                .andExpect(status().isUnauthorized());
    }

}
