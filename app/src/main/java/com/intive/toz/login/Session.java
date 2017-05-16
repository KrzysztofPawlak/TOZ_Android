package com.intive.toz.login;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Class session.
 */
public final class Session {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private Session() {
    }

    /**
     * The constructor.
     *
     * @param context the context
     */
    public static void session(final Context context) {
        preferences = context.getSharedPreferences("com.intive.toz", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Return session state.
     *
     * @return state of session
     */
    public static boolean isLogged() {

        return preferences.getBoolean("loggedState", false);
    }

    /**
     * Set session log in.
     *
     * @param jwt    to keep token in preferences.
     * @param userId the user id
     * @param role   the role
     */
    public static void logIn(final String jwt, final String userId, final String role, long tokenExpirationDate) {
        editor.putBoolean("loggedState", true);
        editor.putString("jwt", jwt);
        editor.putString("userId", userId);
        editor.putString("role", role);
        editor.putLong("expirationDate", tokenExpirationDate);
        editor.commit();
    }

    /**
     * Set session log out.
     */
    public static void logOut() {
        editor.putBoolean("loggedState", false);
        editor.putString("jwt", "");
        editor.putString("scope", "");
        editor.putString("userId", "");
        editor.putString("role", "");
        editor.putLong("expirationDate", 0);
        editor.commit();
    }

    /**
     * get jwt from session.
     *
     * @return String jwt.
     */
    public static String getJwt() {
        return preferences.getString("jwt", "");
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public static String getUserId() {
        return preferences.getString("userId", "");
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public static String getRole() {
        return preferences.getString("role", "");
    }

    public static Long getExpirationDate() {
        return preferences.getLong("expirationDate", 0);
    }
}
