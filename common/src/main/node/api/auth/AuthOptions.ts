import { type AuthOptions } from "next-auth";
import KeycloakProvider from "next-auth/providers/keycloak";
import { jwtDecode } from "jwt-decode";
import axios from "axios";

export const getKeycloakAuthOptions = (
  clientId: string,
  clientSecret: string,
  issuer: string,
): AuthOptions => {
  return {
    providers: [
      KeycloakProvider({
        clientId,
        clientSecret,
        issuer,
      }),
    ],
    callbacks: {
      async jwt({ token, account }) {
        if (account) {
          // This is the first time the user signs in
          token.id_token = account.id_token!;
          token.access_token = account.access_token!;
          token.refresh_token = account.refresh_token!;
          token.expires_at = account.expires_at!;
          token.decodedToken = jwtDecode(account.access_token!);
        } else if (Math.floor(Date.now() / 1000) > token.expires_at) {
          // The user has been signed in before
          // But the token is about to expire;
          const response = await axios.post(
            `${issuer}/protocol/openid-connect/token`,
            new URLSearchParams({
              grant_type: "refresh_token",
              client_id: `${clientId}`,
              client_secret: `${clientSecret}`,
              refresh_token: token.refresh_token,
            }),
            {
              headers: { "Content-Type": "application/x-www-form-urlencoded" },
            },
          );

          if (response.status !== 200) {
            await axios.post(
              `${issuer}/protocol/openid-connect/logout?id_token_hint=${token.id_token}`,
            );

            throw new Error("Failed to refresh token");
          }

          const refreshedTokens = response.data;

          token.id_token = refreshedTokens.id_token;
          token.access_token = refreshedTokens.access_token;
          token.refresh_token = refreshedTokens.refresh_token;
          token.expires_at =
            Math.floor(Date.now() / 1000) + refreshedTokens.expires_in;
          token.decodedToken = jwtDecode(refreshedTokens.access_token);
        }

        return token;
      },
      async session({ session, token }) {
        // Send properties to the client.
        session.access_token = token.access_token;
        session.isAdmin =
          token.decodedToken.realm_access.roles.includes("admin");
        session.emailVerified = token.decodedToken.email_verified;

        return session;
      },
    },
    events: {
      signOut: async ({ token }) => {
        await axios.get(
          `${issuer}/protocol/openid-connect/logout?id_token_hint=${token.id_token}`,
        );
      },
    },
  };
};
