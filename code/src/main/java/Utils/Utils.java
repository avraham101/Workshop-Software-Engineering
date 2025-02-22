package Utils;

public class Utils {

    public static String DB = "workshop";

    public static void TestMode(){
        DB="test";
    }

    /**
     * url - https://www.geeksforgeeks.org/edit-distance-dp-5/
     * min function between 3 values
     * @param x - the x number
     * @param y - the y number
     * @param z - the z number
     * @return the min number between 3
     */
    public static int min(int x, int y, int z) {
        if (x <= y && x <= z)
            return x;
        if (y <= x && y <= z)
            return y;
        else
            return z;
    }

    /**
     * url - https://www.geeksforgeeks.org/edit-distance-dp-5/
     * @param str1 - first string
     * @param str2 - second string
     * @param m - the length of str1
     * @param n - the length of str2
     * @return number how much the string distance from one 2 another
     */
    public static int editDistDP(String str1, String str2, int m, int n) {
        // Create a table to store results of subproblems
        int dp[][] = new int[m + 1][n + 1];

        // Fill d[][] in bottom up manner
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // If first string is empty, only option is to
                // insert all characters of second string
                if (i == 0)
                    dp[i][j] = j; // Min. operations = j

                    // If second string is empty, only option is to
                    // remove all characters of second string
                else if (j == 0)
                    dp[i][j] = i; // Min. operations = i

                    // If last characters are same, ignore last char
                    // and recur for remaining string
                else if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];

                    // If the last character is different, consider all
                    // possibilities and find the minimum
                else
                    dp[i][j] = 1 + min(dp[i][j - 1], // Insert
                            dp[i - 1][j], // Remove
                            dp[i - 1][j - 1]); // Replace
            }
        }

        return dp[m][n];
    }
}
