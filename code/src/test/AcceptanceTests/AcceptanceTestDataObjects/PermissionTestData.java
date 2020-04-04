package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.Set;

public class PermissionTestData {
    private String username;
    private Set<PermissionsTypeTestData> permissions;
    private String givenBy;

    public PermissionTestData(String username, Set<PermissionsTypeTestData> permissions, String givenBy) {
        this.username = username;
        this.permissions = permissions;
        this.givenBy=givenBy;
    }

    public String getUsername() {
        return username;
    }

    public Set<PermissionsTypeTestData> getPermissions() {
        return permissions;
    }

    public String getGivenBy() {
        return givenBy;
    }

}
