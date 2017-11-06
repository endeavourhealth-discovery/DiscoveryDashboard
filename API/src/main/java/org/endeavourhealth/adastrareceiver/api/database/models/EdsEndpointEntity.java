package org.endeavourhealth.adastrareceiver.api.database.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eds_endpoint", schema = "adastra_receiver", catalog = "")
public class EdsEndpointEntity {
    private String edsUrl;
    private byte useKeycloak;
    private String keycloakTokenUri;
    private String keycloakRealm;
    private String keycloakUsername;
    private String keycloakPassword;
    private String keycloakClientid;

    @Basic
    @Column(name = "eds_url")
    public String getEdsUrl() {
        return edsUrl;
    }

    public void setEdsUrl(String edsUrl) {
        this.edsUrl = edsUrl;
    }

    @Basic
    @Column(name = "use_keycloak")
    public byte getUseKeycloak() {
        return useKeycloak;
    }

    public void setUseKeycloak(byte useKeycloak) {
        this.useKeycloak = useKeycloak;
    }

    @Basic
    @Column(name = "keycloak_token_uri")
    public String getKeycloakTokenUri() {
        return keycloakTokenUri;
    }

    public void setKeycloakTokenUri(String keycloakTokenUri) {
        this.keycloakTokenUri = keycloakTokenUri;
    }

    @Basic
    @Column(name = "keycloak_realm")
    public String getKeycloakRealm() {
        return keycloakRealm;
    }

    public void setKeycloakRealm(String keycloakRealm) {
        this.keycloakRealm = keycloakRealm;
    }

    @Basic
    @Column(name = "keycloak_username")
    public String getKeycloakUsername() {
        return keycloakUsername;
    }

    public void setKeycloakUsername(String keycloakUsername) {
        this.keycloakUsername = keycloakUsername;
    }

    @Basic
    @Column(name = "keycloak_password")
    public String getKeycloakPassword() {
        return keycloakPassword;
    }

    public void setKeycloakPassword(String keycloakPassword) {
        this.keycloakPassword = keycloakPassword;
    }

    @Basic
    @Column(name = "keycloak_clientid")
    public String getKeycloakClientid() {
        return keycloakClientid;
    }

    public void setKeycloakClientid(String keycloakClientid) {
        this.keycloakClientid = keycloakClientid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdsEndpointEntity that = (EdsEndpointEntity) o;

        if (useKeycloak != that.useKeycloak) return false;
        if (edsUrl != null ? !edsUrl.equals(that.edsUrl) : that.edsUrl != null) return false;
        if (keycloakTokenUri != null ? !keycloakTokenUri.equals(that.keycloakTokenUri) : that.keycloakTokenUri != null)
            return false;
        if (keycloakRealm != null ? !keycloakRealm.equals(that.keycloakRealm) : that.keycloakRealm != null)
            return false;
        if (keycloakUsername != null ? !keycloakUsername.equals(that.keycloakUsername) : that.keycloakUsername != null)
            return false;
        if (keycloakPassword != null ? !keycloakPassword.equals(that.keycloakPassword) : that.keycloakPassword != null)
            return false;
        if (keycloakClientid != null ? !keycloakClientid.equals(that.keycloakClientid) : that.keycloakClientid != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = edsUrl != null ? edsUrl.hashCode() : 0;
        result = 31 * result + (int) useKeycloak;
        result = 31 * result + (keycloakTokenUri != null ? keycloakTokenUri.hashCode() : 0);
        result = 31 * result + (keycloakRealm != null ? keycloakRealm.hashCode() : 0);
        result = 31 * result + (keycloakUsername != null ? keycloakUsername.hashCode() : 0);
        result = 31 * result + (keycloakPassword != null ? keycloakPassword.hashCode() : 0);
        result = 31 * result + (keycloakClientid != null ? keycloakClientid.hashCode() : 0);
        return result;
    }
}
