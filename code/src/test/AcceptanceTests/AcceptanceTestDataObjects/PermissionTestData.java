package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.Set;

public class PermissionTestData {
    private String username;
    private Set<PermissionsTypeTestData> permissions;

    public PermissionTestData(String username, Set<PermissionsTypeTestData> permissions) {
        this.username = username;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public Set<PermissionsTypeTestData> getPermissions() {
        return permissions;
    }
}
